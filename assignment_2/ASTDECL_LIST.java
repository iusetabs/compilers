/* Generated By:JJTree: Do not edit this line. ASTDECL_LIST.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTDECL_LIST extends SimpleNode {
  public ASTDECL_LIST(int id) {
    super(id);
  }

  public ASTDECL_LIST(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e07aecca195488cf7f668d0e7442461b (do not edit this line) */