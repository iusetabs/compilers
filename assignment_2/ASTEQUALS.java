/* Generated By:JJTree: Do not edit this line. ASTEQUALS.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTEQUALS extends SimpleNode {
  public ASTEQUALS(int id) {
    super(id);
  }

  public ASTEQUALS(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=597c612e12ff0935289e25a668fe1470 (do not edit this line) */