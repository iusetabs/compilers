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
     System.out.println(debugTab + "DEBUG: Entering scope " + scope + ".");
     this.scopeTracker.push(currentScope);
     this.currentScope = scope;
     this.debugScopeNo++;
     this.debugTab = "";
     for (int i = 0; i < debugScopeNo; i++)
        debugTab = debugTab + " ";
  }

  public void decreaseScope(){
        System.out.println(debugTab + "DEBUG: Leaving scope " + this.currentScope + ".");
  	this.currentScope = scopeTracker.pop();
	this.debugScopeNo--;
        this.debugTab = "";
        for (int i = 0; i < debugScopeNo; i++)
           debugTab = debugTab + " ";
        System.out.println(debugTab + "DEBUG: Now in scope " + this.currentScope + ".");
  }

  public Object visit(ASTProg_Start node, Object data){
    //TODO System.out.println("TEST: " + data);
    //TODO SymbolTable test = (SymbolTable)(data);
    //TODO test.output(true, false, false);
    this.init_STC(data);
    int noChildren = node.jjtGetNumChildren();
    System.out.println(debugTab + "DEBUG: PROG Children: " + Integer.toString(noChildren)); 
    for(int i = 0; i < noChildren; i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
       node.jjtGetChild(i).jjtAccept(this, data); 
    }
    //TODO: do_checks_here
    return data;
   }

  public void isIDInScope(Node thisNode, String scope, String desc){ //SEMANTIC CHECK 5 
	String id = this.visit((ASTID)(thisNode), null).toString();
	System.out.println(debugTab + "DEBUG: ID = " + id + ". SCOPE = " + scope + ".");
    	if(this.STC.getMostRecentType(id, scope).equals("-1"))
		System.out.println("ERROR: ID " + id + " in scope " + scope + " used in a " + desc  + " has not been declared within reachable scope.\nSOLUTION: Declare " + id + " before calling it.");
  }

  public void isIDDupdInScope(String id){ //SEMANTIC CHECK 10
     System.out.println("entered");
     String prev_scope;
     if(!this.currentScope.equals("global"))
	    prev_scope = this.scopeTracker.pop().toString();
     else
	    prev_scope = "global";
     String temp_type = this.STC.getType(id+"*"+prev_scope);
     boolean isDup = this.STC.checkScopeTableForDups(prev_scope, id); 
     if(isDup)
        System.out.println("ERROR: ID " + id + " already declared in this scope as type " + temp_type + ".\nSOLUTION: Change the variable name.");	
     if(!this.currentScope.equals("global"))
        this.scopeTracker.push(prev_scope);
  }

  public Object visit(ASTvariable_decl node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTvariable_decl: " + node);
    this.isIDDupdInScope(this.visit((ASTID)(node.jjtGetChild(0)), data).toString());
    return data;
   }
  public Object visit(ASTconstant_decl node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTconstant_decl: " + node);
    this.isIDDupdInScope(this.visit((ASTID)(node.jjtGetChild(0)), data).toString());
    return data;
   }
  public Object visit(ASTVAL_TYPE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTVAL_TYPE: " + node);
    return node.value;
   }

  public Object visit(ASTFunction node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTFunction: " + node);
    String scope = (String) node.jjtGetChild(1).jjtAccept(this, data); 
    this.increaseScope(scope);
    boolean noReturnValues = true; 
    boolean isNotVoid = true; 
    int noChildren = node.jjtGetNumChildren();
    System.out.println("DEBUG: Function Children: " + Integer.toString(noChildren));
    String function_name = "";
    String function_type = ""; 
    String return_type = "";
    String return_id = "";
    for(int i = 0; i < noChildren; i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
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
		System.out.println("ERROR: Function " + function_name + " of type" + function_type + " is not returning values!\nSOLUTION: " + function_name + " must return values of type " + function_type + ".");
    }
    else{ //If a value is returned
    	if(!isNotVoid) //SEMANTIC CHECK 2
           System.out.println("ERROR: Function " + function_name + " of type " + function_type + " is returning values.\nSOLUTION: Type void should not return values.");
        else{
		if(return_type.equals("-1"))
			System.out.println("ERROR: Function " + function_name + ". Return value " + return_id + " has not been declared in scope\nSOLUTION: Ensure the value " + return_id + " has been declared previously.");  //SEMANTIC CHECK 4
		else if(!return_type.equals(function_type))
			System.out.println("ERROR: Function " + function_name + " of type " + function_type + " is returning value of type " + return_type  + ". This is the wrong type.\nSOLUTION: Type " + function_type + " should return values matching type " + function_type + "."); //SEMANTIC CHECK 3
	}
    }
    return data;
   }

  public Object visit(ASTPARAM_LIST node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTPARAM_LIST:" + node);
    return data;
   }
  public Object visit(ASTDECL_LIST node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTDECL_LIST: " + node);
    return data;
   }
  public Object visit(ASTFUNCTION_CODE node, Object data){
    //checkAllIDs used
    System.out.println(debugTab + "DEBUG: entering code");
    int noChildren = node.jjtGetNumChildren();
    checkForEmpties monitor = new checkForEmpties();
    for(int i = 0; i < noChildren; i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
	monitor.give(node.jjtGetChild(i));
        node.jjtGetChild(i).jjtAccept(this, data); 
	if(i + 1 == noChildren)
		monitor.give(node.jjtGetChild(i));
       }
    System.out.println(debugTab + "DEBUG: Node ASTFUNCTION_CODE: " + node);
    return data;
   }
  public Object visit(ASTRETURNS node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTRETURNS: " + node);
    String return_id = this.visit((ASTID)(node.jjtGetChild(0)), data).toString();
    String return_type = this.STC.getMostRecentType(return_id, this.currentScope);
    this.decreaseScope();
    return return_type+"*"+return_id;
   }
  public Object visit(ASTParameter node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTParameter: " + node);
    return data;
   }
  public Object visit(ASTMAIN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTMAIN: " + node);
    this.increaseScope("main");
    int noChildren = node.jjtGetNumChildren();
    for(int i = 0; i < noChildren; i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
       node.jjtGetChild(i).jjtAccept(this, data); 
    }
    return data;
   }
  public Object visit(ASTMAIN_DECLS node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTMAIN_DECLS: " + node);
    return data;
   }
  public Object visit(ASTMAIN_CODE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTMAIN_CODE: " + node);
    checkForEmpties monitor = new checkForEmpties(); 
    System.out.println(node.jjtGetNumChildren());
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
	monitor.give(node.jjtGetChild(i));
	node.jjtGetChild(i).jjtAccept(this, data);
	if(i + 1 == node.jjtGetNumChildren())
		monitor.give(node.jjtGetChild(i));
    }
    return data;
   }

  public void checkLHS_RHS_ofAssign(String LHS_type, Node RHS){
	//RHS is equal to a PLUS_OP or a SUB_OP
	System.out.println("HI:" + RHS.toString());
	System.out.println(Integer.toString(RHS.jjtGetNumChildren()));
	for (int i = 0; i < RHS.jjtGetNumChildren(); i++){
		System.out.println("TEST" + RHS.jjtGetChild(i).toString());
		Node s = RHS.jjtGetChild(i);
		if(s.toString().equals("PLUS_OP") || s.toString().equals("SUBTRACT_OP"))
			this.checkLHS_RHS_ofAssign(LHS_type, s); 
		else if (s.toString().equals("ID")){
			String id = this.visit((ASTID)(s), null).toString();
			String id_type = this.STC.getMostRecentType(id, this.currentScope);
			if(!id_type.equals("-1") && !id_type.equals(LHS_type))
				System.out.println("ERROR: ID " + id + " of type " + id_type + " on RHS of ASSIGN does not match LHS type " + LHS_type); //SEMANTIC CHECK 11
		}
		else if(s.toString().equals("INT") && !(LHS_type.equals("integer"))){
			System.out.println("ERROR: integer found on RHS of ASSIGN does not match LHS type " + LHS_type); //SEMANTIC CHECK 12
		}
		else if((s.toString().equals("IS_FALSE") || s.toString().equals("IS_TRUE")) && !(LHS_type.equals("boolean")))
			System.out.println("ERROR: boolean found on RHS of ASSIGN does not match LHS type " + LHS_type); //SEMANTIC CHECK 13

	}
  }

  public Object visit(ASTASSIGN_OP node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTASSIGN_OP: " + node);
    String id1 = "";
    System.out.println(this.visit((ASTID)(node.jjtGetChild(0)), null).toString());
    String LHS_ID = this.visit((ASTID)(node.jjtGetChild(0)), null).toString();
    if(this.STC.getMostRecentDesc(LHS_ID, this.currentScope).equals("const")){
       System.out.println("ERROR: A constant cannot be set to a new value.\nSOLUTION: Re-define the identifier"); //SEMANTIC CHECK 10
    }
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(!n.toString().equals("ID")){
		n.jjtAccept(this,data);
		System.out.println("No: " + Integer.toString(n.jjtGetNumChildren()));
		if(n.toString().equals("PLUS_OP") || n.toString().equals("SUB_OP")){
			System.out.println("yep!");
			this.checkLHS_RHS_ofAssign(this.STC.getType(LHS_ID+"*"+this.currentScope), n);
		}
	}
	if(node.jjtGetNumChildren() == 2){
		if (i == 0 && n.toString().equals("ID"))
			id1 = this.visit((ASTID)(n), null).toString();
		if(i == 1 && n.toString().equals("ID") && this.visit((ASTID)(n), null).toString().equals(id1)){
			System.out.println("WARNING: Assign_Op assigning same value to itself. No change in LHS. " + id1 + " := " + id1 + ";\nSOLUTION: Don't do this!"); //SEMANTIC CHECK 6
			return data;
		}
			
	}
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
    }
    return data;
   }

  public Object visit(ASTIF_CONDITION node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIF_CONDITION: " + node);
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
    System.out.println(debugTab + "DEBUG: Node ASTIF_CODE: " + node);
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
    System.out.println(debugTab + "DEBUG: Node ASTELSE_CODE: " + node);
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
    System.out.println(debugTab + "DEBUG: Node ASTELSE: " + node);
    return node.value;
   }
  public Object visit(ASTWHILE_CONDITION node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTWHILE_CONDITION: " + node);
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
    System.out.println(debugTab + "DEBUG: Node ASTWHILE_CODE: " + node);
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
  System.out.println(debugTab + "DEBUG: Node ASTSKIP: " + node);
    return data;
   }
  public Object visit(ASTPLUS_OP node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTPLUS_OP: " + node);
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else
		n.jjtAccept(this,data);
    }
    return data;
   }
  public Object visit(ASTSUBTRACT_OP node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTSUBTRACT_OP: " + node);
    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	Node n = node.jjtGetChild(i);
	if(n.toString().equals("ID"))
		isIDInScope(n, this.currentScope, "assignment");
	else
		n.jjtAccept(this,data);
    }
    return data;
   }
  public Object visit(ASTNEGATION node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTNEGATION: " + node);
    return data;
   }
  public Object visit(ASTEQUAL node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTEQUAL: " + node);
    return data;
   }
  public Object visit(ASTNOT_EQUAL node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTNOT_EQUAL: " + node);
    return data;
   }
  public Object visit(ASTLESS_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTLESS_THAN: " + node);
    return data;
   }
  public Object visit(ASTEQ_LESS_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTEQ_LESS_THAN: " + node);
    return data;
   }
  public Object visit(ASTGREATER_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTGREATER_THAN: " + node);
    return data;
   }
  public Object visit(ASTEQ_GREATER_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTEQ_GREATER_THAN: " + node);
    return data;
   }
  public Object visit(ASTOR node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTOR: " + node);
    return data;
   }
  public Object visit(ASTAND node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTAND: " + node);
    return data;
   }
  public Object visit(ASTARG_LIST node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTARG_LIST: " + node);
    for(int i = 0; i < node.jjtGetNumChildren(); i++)
	isIDInScope(node.jjtGetChild(i), this.currentScope, "argument list");
    return data;
   }
  public Object visit(ASTID node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTID: " + node);
    return node.value;
   }
  public Object visit(ASTINT node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTINT: " + node);
    return data;
   }
  public Object visit(ASTIS_TRUE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIS_TRUE: " + node);
    return data;
   }
  public Object visit(ASTIS_FALSE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIS_FALSE: " + node);
    return data;
   }
}
