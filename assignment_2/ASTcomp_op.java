/* Generated By:JJTree: Do not edit this line. ASTcomp_op.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTcomp_op extends SimpleNode {
  public ASTcomp_op(int id) {
    super(id);
  }

  public ASTcomp_op(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c598a4f42d54150f93a7bfb0da38d831 (do not edit this line) */
