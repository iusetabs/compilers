/* Generated By:JJTree: Do not edit this line. ASTvar_decl.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTvar_decl extends SimpleNode {
  public ASTvar_decl(int id) {
    super(id);
  }

  public ASTvar_decl(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=2dfcbb027b2bb70417bfadb5d45243c1 (do not edit this line) */
