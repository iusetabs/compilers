/* Generated By:JavaCC: Do not edit this line. ExprLangVisitor.java Version 5.0 */
public interface ExprLangVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTprogram node, Object data);
  public Object visit(ASTStms node, Object data);
  public Object visit(ASTDecl node, Object data);
  public Object visit(ASTAdd_op node, Object data);
  public Object visit(ASTBool_op node, Object data);
  public Object visit(ASTNot_op node, Object data);
  public Object visit(ASTMult_op node, Object data);
  public Object visit(ASTExp node, Object data);
  public Object visit(ASTnumber node, Object data);
  public Object visit(ASTidentifier node, Object data);
}
/* JavaCC - OriginalChecksum=e9590a1a3c168947661498b3b579507f (do not edit this line) */
