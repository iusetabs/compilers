/* Generated By:JJTree: Do not edit this line. ASTMAIN.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTMAIN extends SimpleNode {
  public ASTMAIN(int id) {
    super(id);
  }

  public ASTMAIN(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b01f1094011d52dd9e480ba55ef7ec00 (do not edit this line) */
