/* Generated By:JJTree: Do not edit this line. ASTLtOp.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTLtOp extends SimpleNode {
  public ASTLtOp(int id) {
    super(id);
  }

  public ASTLtOp(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=053da33563a58e5ea8ec19a88b4c9f15 (do not edit this line) */