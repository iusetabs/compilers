/* Generated By:JavaCC: Do not edit this line. MyParserConstants.java */

/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface MyParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SINGLE_COMMENT = 2;
  /** RegularExpression Id. */
  int MAIN = 6;
  /** RegularExpression Id. */
  int BEGIN = 7;
  /** RegularExpression Id. */
  int END = 8;
  /** RegularExpression Id. */
  int RETURN = 9;
  /** RegularExpression Id. */
  int IF = 10;
  /** RegularExpression Id. */
  int WHILE = 11;
  /** RegularExpression Id. */
  int ELSE = 12;
  /** RegularExpression Id. */
  int DECL_VARIABLE = 13;
  /** RegularExpression Id. */
  int DECL_INTEGER = 14;
  /** RegularExpression Id. */
  int DECL_CONSTANT = 15;
  /** RegularExpression Id. */
  int VOID = 16;
  /** RegularExpression Id. */
  int IS = 17;
  /** RegularExpression Id. */
  int DO_SKIP = 18;
  /** RegularExpression Id. */
  int IS_TRUE = 19;
  /** RegularExpression Id. */
  int IS_FALSE = 20;
  /** RegularExpression Id. */
  int BOOLEAN = 21;
  /** RegularExpression Id. */
  int COMMA = 27;
  /** RegularExpression Id. */
  int SEMICOLUMN = 28;
  /** RegularExpression Id. */
  int COLUMN = 29;
  /** RegularExpression Id. */
  int IS_VALUE = 30;
  /** RegularExpression Id. */
  int OPEN_BRACKET = 31;
  /** RegularExpression Id. */
  int CLOSE_BRACKET = 32;
  /** RegularExpression Id. */
  int PLUS_SIGN = 33;
  /** RegularExpression Id. */
  int MINUS_SIGN = 34;
  /** RegularExpression Id. */
  int TILDA = 35;
  /** RegularExpression Id. */
  int OR_SIGN = 36;
  /** RegularExpression Id. */
  int AND_SIGN = 37;
  /** RegularExpression Id. */
  int EQUALS_SIGN = 38;
  /** RegularExpression Id. */
  int NEGATION_EQUALS = 39;
  /** RegularExpression Id. */
  int LESS_THAN = 40;
  /** RegularExpression Id. */
  int EQUAL_LESS_THAN = 41;
  /** RegularExpression Id. */
  int GREATER_THAN = 42;
  /** RegularExpression Id. */
  int EQUAL_GREATER_THAN = 43;
  /** RegularExpression Id. */
  int INTEGER = 44;
  /** RegularExpression Id. */
  int DIGIT = 45;
  /** RegularExpression Id. */
  int ID = 46;
  /** RegularExpression Id. */
  int LETTER = 47;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_COMMENT = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\"/*\"",
    "<SINGLE_COMMENT>",
    "\"/*\"",
    "\"*/\"",
    "<token of kind 5>",
    "\"main\"",
    "\"begin\"",
    "\"end\"",
    "\"return\"",
    "\"if\"",
    "\"while\"",
    "\"else\"",
    "\"variable\"",
    "\"integer\"",
    "\"constant\"",
    "\"void\"",
    "\"is\"",
    "\"skip\"",
    "\"true\"",
    "\"false\"",
    "\"boolean\"",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "\",\"",
    "\";\"",
    "\":\"",
    "\":=\"",
    "\"(\"",
    "\")\"",
    "\"+\"",
    "\"-\"",
    "\"~\"",
    "\"|\"",
    "\"&\"",
    "\"=\"",
    "\"!=\"",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "<INTEGER>",
    "<DIGIT>",
    "<ID>",
    "<LETTER>",
  };

}
