/**
* @author Steven T Hanna
* @date 5/7/15
* @class Method
* @description Abstract class to base other method class's off of
* @child JavaMethod
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Method {


  /**
  * @description Constructor for Method
  * @param ArrayList<String> contents - contents of method
  */
  public Method(ArrayList<String> contents) {
    this.contents = contents;
    extractInfo();
  }
  /** @end */

  /**
  * @desciption Name of method as a String
  */
  private String name;

  /**
  * @description Method signature
  */
  private String methodSignature;

  /**
  * @desciption Parameters of method in String array
  */
  private String[] parameters;

  /**
  * @desciption Description of the method in String array
  */
  private String[] description;

  /**
  * @description Date of method
  */
  private String date;

  /**
  * @description What is returned from method as a String
  */
  private String returned;

  /**
  * @description What method throws as a String
  */
  private String thrown;

  /**
  * @description Exceptions of the method
  */
  private String exception;

  /**
  * @description See related as a String array
  */
  private String[] see;

  /**
  * @description Notes as a String array
  */
  private String[] note;

  /**
  * @description Body of the method
  */
  public ArrayList<String> body = new ArrayList<String>();

  /**
  * @description Header of the Method
  */
  public ArrayList<String> header = new ArrayList<String>();

  /**
  * @description Entire contents of the method
  */
  public ArrayList<String> contents = new ArrayList<String>();

  /**
  * Get method name
  * @return String - method name
  */
  public String getName() {
    return name;
  }
  /** @end */

  /**
  * Set method name
  * @param String name - method name
  */
  public void setName(String name) {
    this.name = name;
  }
  /** @end */

  /**
  * Get method signature
  * @return String - method signature
  */
  public String getMethodSignature() {
    return methodSignature;
  }
  /** @end */

  /**
  * Set method signature
  * @param MString methodSignature - method signature
  */
  public void setMethodSignature(String methodSignature) {
    this.methodSignature = methodSignature;
  }
  /** @end */

  /**
  * Get parameters
  * @return String array - parameters of method
  */
  public String[] getParameters() {
    return parameters;
  }
  /** @end */

  /**
  * Set parameters
  * @param String array params - parameters of method
  */
  public void setParameters(String[] params) {
    parameters = params;
  }
  /** @end */

  /**
  * Get description
  * @return String array - multi-line description of method
  */
  public String[] getDescription() {
    return description;
  }
  /** @end */

  /**
  * Set description
  * @param String array - multi-line description of method
  */
  public void setDescription(String[] description) {
    this.description = description;
  }
  /** @end */

  /**
  * Get date
  * @return String - date of method
  */
  public String getDate() {
    return date;
  }
  /** @end */

  /**
  * Set date
  * @param String - date of method
  */
  public void setDate(String date) {
    this.date = date;
  }
  /** @end */

  /**
  * Get returned
  * @return String - what method returns
  */
  public String getReturned() {
    return returned;
  }
  /** @end */

  /**
  * Set returned
  * @param String - set return
  */
  public void setReturned(String returned) {
    this.returned = returned;
  }
  /** @end */

  /**
  * Get Thrown
  * @return String - what method throws
  */
  public String getThrown() {
    return thrown;
  }
  /** @end */

  /**
  * Set thrown
  * @param String - set thrown
  */
  public void setThrown(String thrown) {
    this.thrown = thrown;
  }
  /** @end */

  /**
  * Get Exception
  * @return String - what exceptions the method has
  */
  public String getException() {
    return exception;
  }
  /** @end */

  /**
  * Set exception
  * @param String - set exception
  */
  public void setException(String exception) {
    this.exception = exception;
  }
  /** @end */

  /**
  * Get See
  * @return String array - see for other methods in the header
  */
  public String[] getSee() {
    return see;
  }
  /** @end */

  /**
  * Set see
  * @param String array - set see
  */
  public void setSee(String[] see) {
    this.see = see;
  }
  /** @end */

  /**
  * Get notes
  * @return String array - notes for the method header
  */
  public String[] getNote() {
    return note;
  }
  /** @end */

  /**
  * Set notes
  * @param String array - set notes
  */
  public void setNote(String[] note) {
    this.note = note;
  }
  /** @end */

  /**
  * Extract the header from the method
  */
  public abstract void extractHeader();
  /** @end */

  /**
  * Extract the method signature from the rest of the method
  * Use header to get the method... Should be the last line
  */
  public abstract void extractSignature();
  /** @end */

  /**
  * Extract the body from the method
  * Harvest everything after the method signature
  */
  public abstract void extractBody();
  /** @end */

  /**
  * Remove the tag from a specified string.
  * The tag must be included within the string
  * @param String tag - the tag to be removed
  * @param String s - the String that the tag must be removed from
  * @return String - the string sans tag
  */
  public String removeTag(String tag, String s) {
    // There should be a white space after the tag... If not the user fucked up
    // Take into account the * space
    int tagLength = tag.length() + 3;
    return s.substring(tagLength);
  }
  /** @end */

  /**
  * Extract all information from a method
  */
  public void extractInfo() {
    extractHeader();
    extractSignature();
    extractBody();
    extractParameters();
    // extractDescription();
    // extractReturned();
    // extractDate();
    // extractThrown();
    // extractSee();
    // extractNote();
  }
  /** @end */

  /**
  * Extract the the text following the tag from the header
  * @note Can extract multiple lines of text
  * @note Only extracts a sinlge occurence
  * @param String tag - the tag to be extracted
  */
  public String extractSingle(String tag) {
    // Search header for tag
    int begin = -1;
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains(tag)) {
        begin = i;
      }
    }
    // System.out.println(tag + " begins at: " + begin);
    // Get next tag
    int nextTag = nextTag(tag);
    // System.out.println("New tag begins at: " + nextTag);

    // Error handling
    if(begin == -1) {
      System.out.println(tag + " could not be found");
      return null;
    } else {
      // Get first line
      String temp = header.get(begin).substring(3 + tag.length());
      // System.out.println("Temp: " + temp);
      // Check if multi-line
      if(nextTag > begin) {
        // Add spacing
        temp += " ";
        // Iterate through header until nextTag
        for(int i = begin + 1; i <= nextTag; i++) {
          // Slice off beginning of header
          String sliced = header.get(i).substring(2) + " ";
          temp += sliced;
        }
        // System.out.println("End of stream: " + temp);
        return temp;
      } else {
        return temp;
      }
    }
  }
  /** @end */

  /**
  * Extract the text following the tag from param text
  * @note Only extracts a single occurence
  * @note Removes the tag.  Results unknown if tag is empty string
  * @param String tag - the tag to be extracted
  * @param String content - the content from which the tag is extracted
  * @param booelan t - if a tag exists, extract it
  */
  public String extractSingle(String tag, String content, boolean t) {
    // Search content for tag
    if(content.contains(tag)) {
      if(t) {
        return content.substring(3 + tag.length());
      } else {
        return content.substring(3);
      }
    } else {
      System.out.println(tag + " does not exist");
      return null;
    }
  }


  public String[] extractMultiple(String tag) {
    ArrayList<String> temp = new ArrayList<String>();
    int totalTags = 0;
    // Find tags
    for(int i = 0; i < header.size(); i++) {
      // Tag exists
      if(header.get(i).contains(tag)) {
        temp.add(header.get(i));
        totalTags++;
        // Check for multi-line
        if(nextLineEscapePosition(i) != i) {
          // Multi-line == true
          for(int j = i; j < nextLineEscapePosition(i); j++) {
            temp.add(header.get(j));
          }
        }
      }
    }
    // Declare array
    String[] array = new String[totalTags];
    // Combine the multi-line
    for(int i = 0; i < array.length; i++) {
      for(int j = 0; j < temp.size(); j++) {
        if(temp.get(j).contains("@")) {
          array[i] = extractSingle(tag, temp.get(j), true);
        } else {
          array[i] += extractSingle(tag, temp.get(j), false);
        }
      }
    }
    return array;
  }
  /** @end */

  /**
  * Extract multiple lines of text following the tag
  * @note Extracts one and multiple occurences
  */
  public String[] extractMultiple1(String tag) {
    ArrayList<String> temp = new ArrayList<String>();
    ArrayList<Integer> position = new ArrayList<Integer>();
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains(tag)) {
        int p = i;
        temp.add(header.get(i));
        // ERROR LIES HERE
        int nextPosition = nextLineEscapePosition(i);
        if(nextPosition == i) {
          temp.add(header.get(i));
        } else {
          for(int j = i; j > nextPosition; j++) {
            temp.add(header.get(j));
          }
        }

        // END ERROR LIES
        p = i;
        position.add(i);
      }
    }
    // Look inside header for debug
    for(int i = 0; i < temp.size(); i++) {
      System.out.println(temp.get(i));
    }
    for(int i = 0; i < position.size(); i++) {
      System.out.println(position.get(i));
    }

    if(temp.size() > 0) {
      String[] array = new String[temp.size()];
      for(int i = 0; i < array.length; i++) {
        // int x = position.get(i);
        array[i] = extractSingle(tag, temp.get(i), true);
        // System.out.println(array[i] + " " + array.length);
        // Get end position

        System.out.println(array[i]);
      }
      return array;
    } else {
      System.out.println(tag + " does not exist");
      return null;
    }
  }
  /** @end */

  public boolean nextLineContainsEscape(int position) {
    if(header.get(position).contains("@")) {
      return true;
    } else {
      return false;
    }
  }
  /** @end */

  public int nextLineEscapePosition(int startingPosition) {
    int endPosition = startingPosition;
    while(!header.get(endPosition).contains("@")) {
      endPosition++;
    }
    return endPosition;
  }
  /** @end */

  /**
  * Find the line number of the next tag start
  * @param String tag - the origin tag
  * @return int - line number of the next tag start
  */
  public int nextTag(String tag) {
    // Find line number of tag
    int origin = -1;
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains(tag)) {
        origin = i;
        break;
      }
    }
    // System.out.println("Origin: " + origin);
    // Error handling
    if(origin == -1) {
      // Tag does not exist
      System.out.println(tag + " does not exist");
      return -1;
    } else {
      // Find next tag
      int endTag = -1;
      for(int i = origin + 1; i < header.size(); i++) {
        String temp = header.get(i);
        if(temp.indexOf("@") > 0) {
          endTag = i;
          break;
        }
      }
      // Error handle
      if(endTag == -1) {
        // No end tag... Go to end of header
        int size = header.size() - 1;
        // System.out.println("Header: " + size);
        return size;
      } else {
        // System.out.println("Next tag: " + (endTag - 1));
        // System.out.println("Next tag: " + header.get(endTag));
        return endTag - 1;
      }
    }
  }
  /** @end */

  /**
  * Extract parameters from method header
  */
  public void extractParameters() {
    parameters = extractMultiple("@param");
  }

  /**
  * Extract the description from the methhod header
  * @note Does not support multi-line... Will always return with one element in
  * array
  * @note Technically, descriptions happen before any tags begin...
  */
  public void extractDescription() {
    // description = extractMultiple("@description");
    // Determine if description exists
    // Determine first position of tag
    int tagPosition = -1;
    // Check to see if line contains data
    boolean[] data = new boolean[header.size()];
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains("@")) {
        tagPosition = i;
        if(header.get(i).length() > 2) {
          data[i] = true;
        } else {
          data[i] = false;
        }
        break;
      }
    }
    // Determine if anything in data is true
    boolean anythingTrue = false;
    for(int i = 0; i < data.length; i++) {
      if(data[i]) {
        anythingTrue = true;
        break;
      }
    }
    if(tagPosition == -1) {
      // There might not be any tags, but possibly a description
      if(!anythingTrue) {
        // Nothing there... Assume no description
        System.out.println("No description found");
        description = null;
      } else {
        ArrayList<String> temp = new ArrayList<String>();
        // Ignore line breaks
        for(int i = 0; i < header.size(); i++) {
          if(header.get(i).substring(3).length() != 0) {
              temp.add(header.get(i).substring(2));
          }
        }
        description = new String[temp.size()];
        for(int i = 0; i < description.length; i++) {
          description[i] = temp.get(i);
        }
      }
    }  else {
      // Look through from the second line, to the tag
      ArrayList<String> temp = new ArrayList<String>();
      for(int i = 0; i < tagPosition; i++) {
        // Ignore line breaks
        if(header.get(i).substring(3).length() != 0) {
          temp.add(header.get(i).substring(2));
        }
      }
      description = new String[temp.size()];
      for(int i = 0; i < description.length; i++) {
        description[i] = temp.get(i);
      }
    }
  }
  /** @end */

  /**
  * Extract the returned tag from method header
  */
  public void extractReturned() {
    returned = extractSingle("@return");
  }
  /** @end */

  /**
  * Extract the date from the method header
  */
  public void extractDate() {

    date = extractSingle("@date");
  }
  /** @end */

  /**
  * Extract what method throws from header
  */
  public void extractThrown() {

    // System.out.println("thrown: " + extractSingle("@thrown"));
    thrown = extractSingle("@thrown");
  }
  /** @end */

  /**
  * Extract see from the header
  */
  public void extractSee() {

    see = extractMultiple("@see");
  }
  /** @end */

  /**
  * Extract note from the header
  */
  public void extractNote() {

    note = extractMultiple("@note");
  }
  /** @end */

  public void print() {
    System.out.println(getName());
    for(int i = 0; i < contents.size(); i++) {
      System.out.println(contents.get(i));
    }
  }
  /** @end */

}
//
