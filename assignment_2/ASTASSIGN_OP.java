/* Generated By:JJTree: Do not edit this line. ASTASSIGN_OP.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTASSIGN_OP extends SimpleNode {
  public ASTASSIGN_OP(int id) {
    super(id);
  }

  public ASTASSIGN_OP(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=998970ea33ed26f24d6fe0c84e866fde (do not edit this line) */