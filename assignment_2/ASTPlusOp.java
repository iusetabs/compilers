/* Generated By:JJTree: Do not edit this line. ASTPlusOp.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTPlusOp extends SimpleNode {
  public ASTPlusOp(int id) {
    super(id);
  }

  public ASTPlusOp(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=01a1808f4b1858bfdc4c805282fedb4e (do not edit this line) */
