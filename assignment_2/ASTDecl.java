/* Generated By:JJTree: Do not edit this line. ASTDecl.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTDecl extends SimpleNode {
  public ASTDecl(int id) {
    super(id);
  }

  public ASTDecl(ExprLang p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ExprLangVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4b4daa3bb84850c696e456a32f201003 (do not edit this line) */
