/* Generated By:JJTree: Do not edit this line. ASTparameter_list.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTparameter_list extends SimpleNode {
  public ASTparameter_list(int id) {
    super(id);
  }

  public ASTparameter_list(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b10a662c6d584b0ea4518118dce6e533 (do not edit this line) */