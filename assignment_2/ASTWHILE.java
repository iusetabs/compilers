/* Generated By:JJTree: Do not edit this line. ASTWHILE.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTWHILE extends SimpleNode {
  public ASTWHILE(int id) {
    super(id);
  }

  public ASTWHILE(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1e9c96bef5961921eec4ffa800e49240 (do not edit this line) */