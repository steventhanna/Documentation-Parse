/**
* @author Steven T Hanna
* @date 5/2/15
* @class JavaAnalysis
* @description Analyze syntax of Java files
*/

import java.util.ArrayList;

public class JavaAnalysis {

  /**
  * @description The filename of the file in a String
  */
  private String filename;

  /**
  * @description Contents of the file
  */
  private ArrayList<String> contents;

  /**
  * @description System specified classname
  */
  private String systemClass;

  /**
  * @description User specified classname
  */
  private String userClass;

  /**
  * @description User specified description of class.  Array in case multi-line
  */
  private String[] description;




  /**
  * @description Constructor for JSAnalysis
  * @param DPFIle file - Predetermined JS File
  */
  public JavaAnalysis(DPFile file) {
    filename = file.getFilename();
    contents = file.read();
    systemClass = file.getClassname();
  }

  public void extractHeader() {
    // Look for the class declartion
    int classDeclaration;
    for(int i = 0; i < contents.size(); i++) {
      if(indexOf("class") > 0) {
        classDeclaration = i;
        break;
      }
    }
    ArrayList<String> header = new ArrayList<String>();
    // Strip all blank space and transfer header
    for(int i = 0; i < classDeclaration; i++) {
      if(contets.get(i) != "") {
        header.add(contents.get(i));
      }
    }
  }
}
