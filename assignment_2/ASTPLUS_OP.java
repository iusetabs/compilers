/* Generated By:JJTree: Do not edit this line. ASTPLUS_OP.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTPLUS_OP extends SimpleNode {
  public ASTPLUS_OP(int id) {
    super(id);
  }

  public ASTPLUS_OP(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1ec54670b551bce167dab336fe528f06 (do not edit this line) */
