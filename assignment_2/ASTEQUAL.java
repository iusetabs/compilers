/* Generated By:JJTree: Do not edit this line. ASTEQUAL.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTEQUAL extends SimpleNode {
  public ASTEQUAL(int id) {
    super(id);
  }

  public ASTEQUAL(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5581349e04e5ed93392b0cb07cb74561 (do not edit this line) */
