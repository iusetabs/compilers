/* Generated By:JJTree: Do not edit this line. ASTFuncRet.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTFuncRet extends SimpleNode {
  public ASTFuncRet(int id) {
    super(id);
  }

  public ASTFuncRet(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ef12aef2cb1d9d99de9e505724147922 (do not edit this line) */