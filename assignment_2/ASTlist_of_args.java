/* Generated By:JJTree: Do not edit this line. ASTlist_of_args.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTlist_of_args extends SimpleNode {
  public ASTlist_of_args(int id) {
    super(id);
  }

  public ASTlist_of_args(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=be72c31b467e5c0615836bed44c6fa95 (do not edit this line) */
