/* Generated By:JJTree: Do not edit this line. ASTnemp_parameter_list.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTnemp_parameter_list extends SimpleNode {
  public ASTnemp_parameter_list(int id) {
    super(id);
  }

  public ASTnemp_parameter_list(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=dfdfa2c774134465538dda27779b33b9 (do not edit this line) */