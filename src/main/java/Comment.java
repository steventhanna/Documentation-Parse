/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/25/16
* @class :: Comment
* @description :: An interface to make sure that future comment tags
* all follow the same format.
*/

import java.util.ArrayList;


public class Comment {

  /**
  * @type :: VAR
  * @name :: type
  * @description :: The type of the specific comment
  */
  private String type;

  /**
  * @type :: VAR
  * @name :: rawData
  * @description :: the raw strings directly from the constructor
  */
  private ArrayList<String> rawData = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: cleanedComments
  * @description :: the sanitized version of the comments
  */
  private ArrayList<String> cleanedComments = new ArrayList<String>();

  public Comment(ArrayList<String> rawData, String commentStyle) {
    this.rawData = rawData;
    clean(commentStyle);
    extractType();
  }

  public Comment(ArrayList<String> rawData, String commentStyle, String beginStyle, String endStyle) {
    this.rawData = rawData;
    clean(commentStyle, beginStyle, endStyle);
    extractType();
  }

  public Comment(ArrayList<String> rawData, String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {
    this.rawData = rawData;
    clean(commentStyle, beginStyle, endStyle, commentEndStyle);
    extractType();
  }

  /**
  * @type :: FUNC
  * @name :: extractType
  * @description :: Extracts the type of the function.
  */
  public void extractType() {
    String gettingTag = "";
    for(int i = 0; i < cleanedComments.size(); i++) {
      if(cleanedComments.get(i).toLowerCase().contains("@type")) {
        gettingTag = cleanedComments.get(i).toLowerCase();
        break;
      }
    }
    if(!gettingTag.equals("")) {
      String[] tagArr = gettingTag.split("::");
      if(tagArr.length == 2) {
        type = Utility.removeWhitespace(tagArr[1].toLowerCase());
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: strip
  * @description :: Strips whitespace, and other language specific comments
  * from the given String
  * @param :: String - the string to strip the LSC's from
  * @param :: String commentStyle - the commenting style to remove
  * @param :: String beginStyle - the specific beginning style of the comment to remove
  * @param :: String endStyle - the specific ending style of the comment to remove
  * @param :: String commentEndStyle - the specific comment ending to remove like in HTML
  * @return :: String - The cleaned string
  */
  public String strip(String s, String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {
    // Strip the whitespace from the beginning
    s = s.trim();

    // Specific conditions
    if(beginStyle == null || endStyle == null) {
      if(s.length() >= commentStyle.length() && s.substring(0, commentStyle.length()).equals(commentStyle)) {
        s = s.substring(commentStyle.length());
      }
      s = s.trim();
    } else {
      if(beginStyle != null && endStyle != null) {
        if(s.length() >= beginStyle.length() && s.substring(0, beginStyle.length()).equals(beginStyle)) {
          s = " ";
        } else if(s.equals(endStyle)) {
          s = " ";
        } else if(s.length() >= commentStyle.length() && s.substring(0, commentStyle.length()).equals(commentStyle)) {
          s = s.substring(commentStyle.length());
        }
      }
      if(commentEndStyle != null) {
        if(s.length() > commentEndStyle.length() && s.substring(s.length() - commentEndStyle.length()).equals(commentEndStyle)) {
          s = s.substring(0, s.length() - commentEndStyle.length());
        }
      }
    }
    s = s.trim();
    return s;
  }

  /**
  * @type :: FUNC
  * @name :: clean
  * @description :: removes commenting styles from the arraylist
  * @param :: String commentStyle - the comment style to remove from the string
  */
  public void clean(String commentStyle) {
  }

  /**
  * @type :: FUNC
  * @name :: clean
  * @description :: removes commenting styles from the arraylist
  * @param :: String commentStyle - the comment style to remove from the string
  * @param :: String beginStyle - the style that starts the comment set
  * @param :: String endStyle - the style that ends the comment set
  */
  public void clean(String commentStyle, String beginStyle, String endStyle) {

  }

  /**
  * @type :: FUNC
  * @name :: clean
  * @description :: removes commenting styles from the arraylist
  * @param :: String commentStyle - the comment style to remove from the string
  * @param :: String beginStyle - the style that starts the comment set
  * @param :: String endStyle - the style that ends the comment set
  * @param :: String commentEndStyle - the specific ending of a comment like in HTML
  */
  public void clean(String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {

  }

  /**
  * @type :: FUNC
  * @name :: extract
  * @description :: Extracts the individual tags from the cleaned
  * comments from the constructor
  * @note :: Big ass switch statement here
  */
  public void extract() {

  }

  /**
  * @type :: FUNC
  * @name :: generateMarkdown
  * @description :: Generates markdown for a variable
  * @return :: String - the variable in markdown
  * @note :: Might have to switch to a different data-structure as
  * a String might not be large enough
  */
  public String generateMarkdown() {
    return null;
  }

}
