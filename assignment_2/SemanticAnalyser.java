import java.util.*;
public class SemanticAnalyser implements MyParserVisitor{

  private SymbolTable STC;
  private String currentScope = "global";
  private Stack<String> scopeTracker = new Stack<>();
  private int debugScopeNo = 0;
  private String debugTab = "";
  private int ifID = 0;
  private int whileID = 0;
  private int elseID = 0; 

  public Object visit(SimpleNode node, Object data){
	throw new RuntimeException("Visit SimpleNode");
  }
 
  private void init_STC(Object data){
     this.STC = (SymbolTable)(data);
  }

  public void increaseScope(String scope){
     System.out.println(debugTab + "Entering scope " + scope + ".\n");
     this.scopeTracker.push(currentScope);
     this.currentScope = scope;
     this.debugScopeNo++;
     this.debugTab = "";
     for (int i = 0; i < debugScopeNo; i++)
        debugTab = debugTab + " ";
  }

  public void decreaseScope(){
        System.out.println(debugTab + "Leaving scope " + this.currentScope + ".\n");
  	this.currentScope = scopeTracker.pop();
	this.debugScopeNo--;
        this.debugTab = "";
        for (int i = 0; i < debugScopeNo; i++)
           debugTab = debugTab + " ";
        System.out.println(debugTab + "Now in scope " + this.currentScope + ".\n");
  }

  public Object visit(ASTProg_Start node, Object data){
    this.init_STC(data);
    int noChildren = node.jjtGetNumChildren();
    for(int i = 0; i < noChildren; i++){
       node.jjtGetChild(i).jjtAccept(this, data); 
    }
    return node.value;
   }

  public void isIDInScope(Node thisNode, String scope, String desc){ //SEMANTIC CHECK 5 
	String id = this.visit((ASTID)(thisNode), null).toString();
    	if(this.STC.getMostRecentType(id, scope).equals("-1"))
		System.out.println("ERROR: ID " + id + " in scope " + scope + " used in a " + desc  + " has not been declared within reachable scope.\nSOLUTION: Declare " + id + " before calling it.\n");
  }

  public void isIDDupdInScope(String id){ //SEMANTIC CHECK 10
     String prev_scope;
     if(!this.currentScope.equals("global"))
	    prev_scope = this.scopeTracker.pop().toString();
     else
	    prev_scope = "global";
     String temp_type = this.STC.getType(id+"*"+prev_scope);
     boolean isDup = this.STC.checkScopeTableForDups(prev_scope, id); 
     if(isDup)
        System.out.println("ERROR: ID " + id + " already declared in this scope as type " + temp_type + ".\nSOLUTION: Change the variable name.\n");	
     if(!this.currentScope.equals("global"))
        this.scopeTracker.push(prev_scope);
  }

  public Object visit(ASTvariable_decl node, Object data){
    this.isIDDupdInScope(this.visit((ASTID)(node.jjtGetChild(0)), data).toString());
    return node.value;
   }
  public Object visit(ASTconstant_decl node, Object data){
    this.isIDDupdInScope(this.visit((ASTID)(node.jjtGetChild(0)), data).toString());
    return node.value;
   }
  public Object visit(ASTVAL_TYPE node, Object data){
    return node.value;
   }

  public Object visit(ASTFunction node, Object data){
    String scope = (String) node.jjtGetChild(1).jjtAccept(this, data); 
    this.increaseScope(scope);
    boolean noReturnValues = true; 
    boolean isNotVoid = true; 
    int noChildren = node.jjtGetNumChildren();
    String function_name = "";
    String function_type = ""; 
    String return_type = "";
    String return_id = "";
    for(int i = 0; i < noChildren; i++){
       Node thisNode = node.jjtGetChild(i); 
    
       if(thisNode.toString().equals("VAL_TYPE")){
          function_type = this.visit((ASTVAL_TYPE)(thisNode), data).toString();
          if(function_type.equals("void"))
	     isNotVoid = false; 
       }
       else if(thisNode.toString().equals("ID")){
	  function_name = this.visit((ASTID)(thisNode), data).toString();
	  this.isIDDupdInScope(function_name);
	}
       else if(thisNode.toString().equals("RETURNS")){	
		noReturnValues = false; 
		return_type = this.visit((ASTRETURNS)(thisNode), data).toString();
		String t = return_type.split("\\*")[1];
		return_type = return_type.split("\\*")[0];
		return_id = t;
	}
       else 
		thisNode.jjtAccept(this,data);
    }  
    if(noReturnValues){
       this.decreaseScope(); //NO RETURN NODE PRESENT. DECREASE SCOPE DONE MANUALLY AS VISIT RETURN EXECUTES A decreseScope().
       if(isNotVoid) //SEMANTIC CHECK 1
		System.out.println("ERROR: Function " + function_name + " of type" + function_type + " is not returning values!\nSOLUTION: " + function_name + " must return values of type " + function_type + ".\n");
    }
    else{ //If a value is returned
    	if(!isNotVoid) //SEMANTIC CHECK 2
           System.out.println("ERROR: Function " + function_name + " of type " + function_type + " is returning values.\nSOLUTION: Type void should not return values.\n");
        else{
		if(return_type.equals("-1"))
			System.out.println("ERROR: Function " + function_name + ". Return value " + return_id + " has not been declared in scope\nSOLUTION: Ensure the value " + return_id + " has been declared previously.\n");  //SEMANTIC CHECK 4
		else if(!return_type.equals(function_type))
			System.out.println("ERROR: Function " + function_name + " of type " + function_type + " is returning value of type " + return_type  + ". This is the wrong type.\nSOLUTION: Type " + function_type + " should return values matching type " + function_type + ".\n"); //SEMANTIC CHECK 3
	}
    }
    return node.value;
   }

  public Object visit(ASTPARAM_LIST node, Object data){
    return node.value;
   }
  public Object visit(ASTDECL_LIST node, Object data){
    return node.value;
   }
  public Object visit(ASTFUNCTION_CODE node, Object data){
    //checkAllIDs used
    int noChildren = node.jjtGetNumChildren();
    checkForEmpties monitor = new checkForEmpties();
    for(int i = 0; i < noChildren; i++){
	monitor.give(node.jjtGetChild(i));
        node.jjtGetChild(i).jjtAccept(this, data); 
	if(i + 1 == noChildren)
		monitor.give(node.jjtGetChild(i));
       }
    return node.value;
   }
  public Object visit(ASTRETURNS node, Object data){
    String return_id = this.visit((ASTID)(node.jjtGetChild(0)), data).toString();
    String return_type = this.STC.getMostRecentType(return_id, this.currentScope);
    this.decreaseScope();
    return return_type+"*"+return_id;
   }
  public Object visit(ASTParameter node, Object data){
    return node.value;
   }
  public Object visit(ASTMAIN node, Object data){
    this.increaseScope("main");
    int noChildren = node.jjtGetNumChildren();
    for(int i = 0; i < noChildren; i++){
       node.jjtGetChild(i).jjtAccept(this, data); 
    }
    return node.value;
   }
  public Object visit(ASTMAIN_DECLS node, Object data){
    return node.value;
   }
  public Object visit(ASTMAIN_CODE node, Object data){
    checkForEmpties monitor = new checkForEmpties(); 
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	monitor.give(node.jjtGetChild(i));
	node.jjtGetChild(i).jjtAccept(this, data);
	if(i + 1 == node.jjtGetNumChildren())
		monitor.give(node.jjtGetChild(i));
    }
    return node.value;
   }

  public void checkLHS_RHS_ofAssign(String LHS_type, Node RHS){
	//RHS is equal to a PLUS_OP or a SUB_OP
	for (int i = 0; i < RHS.jjtGetNumChildren(); i++){
		Node s = RHS.jjtGetChild(i);
		if(s.toString().equals("PLUS_OP") || s.toString().equals("SUBTRACT_OP"))
			this.checkLHS_RHS_ofAssign(LHS_type, s); 
		else if (s.toString().equals("ID")){
			String id = this.visit((ASTID)(s), null).toString();
			String id_type = this.STC.getMostRecentType(id, this.currentScope);
			if(!id_type.equals("-1") && !id_type.equals(LHS_type))
				System.out.println("ERROR: ID " + id + " of type " + id_type + " on RHS of ASSIGN does not match LHS type " + LHS_type + "\n"); //SEMANTIC CHECK 11
		}
		else if(s.toString().equals("INT") && !(LHS_type.equals("integer"))){
			System.out.println("ERROR: integer found on RHS of ASSIGN does not match LHS type " + LHS_type + "\n"); //SEMANTIC CHECK 12
		}
		else if((s.toString().equals("IS_FALSE") || s.toString().equals("IS_TRUE")) && !(LHS_type.equals("boolean")))
			System.out.println("ERROR: boolean found on RHS of ASSIGN does not match LHS type " + LHS_type + "\n"); //SEMANTIC CHECK 13

	}
  }

  public Object visit(ASTASSIGN_OP node, Object data){
    String id1 = "";
    String LHS_ID = this.visit((ASTID)(node.jjtGetChild(0)), null).toString();
    if(this.STC.getMostRecentDesc(LHS_ID, this.currentScope).equals("const")){
       System.out.println("ERROR: A constant cannot be set to a new value.\nSOLUTION: Re-define the identifier\n"); //SEMANTIC CHECK 10
    }
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(!n.toString().equals("ID")){
		n.jjtAccept(this,data);
		if(n.toString().equals("PLUS_OP") || n.toString().equals("SUB_OP")){
			this.checkLHS_RHS_ofAssign(this.STC.getType(LHS_ID+"*"+this.currentScope), n);
		}
	}
	if(node.jjtGetNumChildren() == 2){
		if (i == 0 && n.toString().equals("ID"))
			id1 = this.visit((ASTID)(n), null).toString();
		if(i == 1 && n.toString().equals("ID") && this.visit((ASTID)(n), null).toString().equals(id1)){
			System.out.println("WARNING: Assign_Op assigning same value to itself. No change in LHS. " + id1 + " := " + id1 + ";\nSOLUTION: Don't do this!\n"); //SEMANTIC CHECK 6
			return node.value;
		}
			
	}
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
    }
    return node.value;
   }

  public Object visit(ASTIF_CONDITION node, Object data){
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else
		n.jjtAccept(this,data);
		
    }
    return node.value;
   }
  public Object visit(ASTIF_CODE node, Object data){
    checkForEmpties monitor = new checkForEmpties();
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else{
		monitor.give(node.jjtGetChild(i));
		n.jjtAccept(this,data);
		if(i + 1 == node.jjtGetNumChildren())
			monitor.give(node.jjtGetChild(i));
	}
     }
    return node.value;
  }
  public Object visit(ASTELSE_CODE node, Object data){
    checkForEmpties monitor = new checkForEmpties();
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else{
		monitor.give(node.jjtGetChild(i));
		n.jjtAccept(this,data); 
		if(i + 1 == node.jjtGetNumChildren())
			monitor.give(node.jjtGetChild(i));
	}
    }
    return node.value;
  }
  public Object visit(ASTELSE node, Object data){
    return node.value;
   }
  public Object visit(ASTWHILE_CONDITION node, Object data){
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else
		n.jjtAccept(this,data);
    }
    return node.value;
   }
  public Object visit(ASTWHILE_CODE node, Object data){
    checkForEmpties monitor = new checkForEmpties();
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else{
		monitor.give(node.jjtGetChild(i));
		n.jjtAccept(this,data);
		if(i + 1 == node.jjtGetNumChildren())
			monitor.give(node.jjtGetChild(i)); 
	}
      }
    return node.value;
   }
  public Object visit(ASTSKIP node, Object data){
    return node.value;
   }
  public Object visit(ASTPLUS_OP node, Object data){
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else
		n.jjtAccept(this,data);
    }
    return node.value;
   }
  public Object visit(ASTSUBTRACT_OP node, Object data){
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else
		n.jjtAccept(this,data);
    }
    return node.value;
   }
  public Object visit(ASTNEGATION node, Object data){
    return node.value;
   }
  public Object visit(ASTEQUAL node, Object data){
    return node.value;
   }
  public Object visit(ASTNOT_EQUAL node, Object data){
    return node.value;
   }
  public Object visit(ASTLESS_THAN node, Object data){
    return node.value;
   }
  public Object visit(ASTEQ_LESS_THAN node, Object data){
    return node.value;
   }
  public Object visit(ASTGREATER_THAN node, Object data){
    return node.value;
   }
  public Object visit(ASTEQ_GREATER_THAN node, Object data){
    return node.value;
   }
  public Object visit(ASTOR node, Object data){
    return node.value;
   }
  public Object visit(ASTAND node, Object data){
    return node.value;
   }
  public Object visit(ASTARG_LIST node, Object data){
    for(int i = 0; i < node.jjtGetNumChildren(); i++)
	isIDInScope(node.jjtGetChild(i), this.currentScope, "argument list");
    return node.value;
   }
  public Object visit(ASTID node, Object data){
    return node.value;
   }
  public Object visit(ASTINT node, Object data){
    return node.value;
   }
  public Object visit(ASTIS_TRUE node, Object data){
    return node.value;
   }
  public Object visit(ASTIS_FALSE node, Object data){
    return node.value;
   }
}
