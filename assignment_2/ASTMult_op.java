/* Generated By:JJTree: Do not edit this line. ASTMult_op.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTMult_op extends SimpleNode {
  public ASTMult_op(int id) {
    super(id);
  }

  public ASTMult_op(ExprLang p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ExprLangVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=2cb1d6ac5bd511844249bc4d416b8a19 (do not edit this line) */
