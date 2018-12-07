/**
* @type :: CLASS
* @name :: main
* @author :: Steven Hanna <steventhanna@gmail.com>
* @description :: The main class that handles multithreading, and the cli implementation.
*/

mod rendering;
mod parser;
use rendering::rendering::*;
use std::path::{Path, PathBuf};
use std::thread;
use std::sync::Arc;
use std::fs;

extern crate glob;
use glob::glob;

extern crate simple_error;

extern crate clap;
extern crate walkdir;

use clap::{Arg, App};

use parser::*;

use walkdir::{WalkDir};

fn main() {
    let matches = App::new("Argot")
        .version("0.2.0")
        .author("Steven Hanna <steventhanna@gmail.com>")
        .about("Parse documentation from codebases into Markdown for easy doc creation.")
        .arg(Arg::with_name("destination")
            .short("d")
            .long("destination")
            .value_name("DESTINATION")
            .help("Sets a custom destination path for rendered markdown files")
            .takes_value(true)
            .required(true)
        )
        .arg(Arg::with_name("origin")
            .short("o")
            .long("origin")
            .value_name("ORIGIN")
            .help("Sets the origin of where Argot should start parsing from")
            .takes_value(true)
            .required(true)
        )
        .arg(Arg::with_name("recursive")
            .short("r")
            .long("recursive")
            .value_name("RECURSIVE")
            .help("Recursively walk the file tree parsing")
            .takes_value(false)
        )
        .get_matches();

    // println!("{:?}", matches);

    let input = matches.value_of("origin").unwrap();
    let destination = matches.value_of("destination").unwrap();

    let is_recursive = matches.is_present("recursive");
    let list_of_files = collect_list_of_files(input, is_recursive);

    // Create the destination folder if necessary
    fs::create_dir_all(destination).unwrap();

    let dest_path = Arc::new(PathBuf::from(destination).canonicalize().unwrap());

    let mut threads = Vec::new();

    for file in list_of_files {
        let dest_path = Arc::clone(&dest_path);
        threads.push(thread::spawn(move || {
            handle_file(file.into_os_string().to_str().unwrap(), &dest_path.to_str().unwrap());
        }));
    }

    for x in threads {
        x.join().unwrap();
    }
}

/**
* @type :: FUNC
* @name :: collect_list_of_files
* @param :: `input: &str` - the input filepath
* @param :: `is_recursive: bool` - flag whether to search recursively or not
*/
fn collect_list_of_files(input: &str, is_recursive: bool) -> Vec<PathBuf> {
    let p = Path::new(input);
    let mut result: Vec<PathBuf> = Vec::new();
    if !p.is_dir() {
        result.push(p.canonicalize().unwrap());
        return result;
    }

    if !is_recursive {
        let path_str = String::from(p.to_str().unwrap()) + &"/*";
        for e in glob(path_str.as_str()).expect("Failed to read glob pattern") {
            let x = e.unwrap().canonicalize().unwrap();
            // let x = PathBuf::from(e.unwrap().file_name().unwrap().to_str().unwrap());
            match x.as_path().extension() {
                Some(y) => {
                    if is_extension_supported(y.to_str().unwrap()) {
                        result.push(x.clone())
                    }
                },
                _ => continue
            };
        }
    } else {
        for entry in WalkDir::new(input).into_iter().filter_map(|e| e.ok()) {
            if entry.metadata().unwrap().is_file() {
                let x = PathBuf::from(entry.path().file_name().unwrap().to_str().unwrap());
                match x.as_path().extension() {
                    Some(y) => {
                        if is_extension_supported(y.to_str().unwrap()) {
                            result.push(entry.path().canonicalize().unwrap())
                        }
                    },
                    _ => continue
                };
            }
        }
    }
    result
}

/**
* @type :: FUNC
* @name :: is_extension_supported
* @param :: x - &str - the extension type to test against
* @return :: bool - whether or not that extension is currently supported
*/
fn is_extension_supported(x: &str) -> bool {
    let list = ["js", "rs", "c", "cpp", "py", "java"];
    list.contains(&x)
}

/**
* @type :: FUNC
* @name :: handle_file
* @param :: `filename: &str` - the file path to parse
* @param :: `destination: &str` - the destination folder to place the file
* @description :: Encapsulates logic to extract, prepare, and otherwise manage the inline docs.
*/
fn handle_file(filename: &str, destination: &str) {
    let x = match get_comments_from_file(filename) {
        Ok(x) => x,
        Err(e) => panic!(e)
    };

    let mut contents: Vec<String> = Vec::new();
    for set in x {
        let new_set = set.into_iter().map(|j| extract_types(j)).collect();
        let joined = join_extracted_comments(new_set);
        let mut param_rep = ParameterRep::new(joined).unwrap();
        contents.push(param_rep.render().clone());
    }

    let stem = match Path::new(filename).file_stem() {
        None => String::from("unnamed"),
        Some(x) => String::from(x.to_str().unwrap())
    };


    let destination_path = Path::new(destination);
    let final_file_path = destination_path.join(stem.as_str()).with_extension("md");
    write_string_to_file(final_file_path.as_path().to_str().unwrap(), contents.join("\n"));
}
