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
    System.out.println(debugTab + "DEBUG: Node ASTProg_Start: " + node + ". Data: " + data.toString());
    System.out.println(debugTab + "DEBUG: PROG Children: " + Integer.toString(noChildren)); 
    for(int i = 0; i < noChildren; i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
       node.jjtGetChild(i).jjtAccept(this, data); 
    }
    //TODO: do_checks_here
    return data;
   }
  public Object visit(ASTvariable_decl node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTvariable_decl: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTconstant_decl node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTconstant_decl: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTVAL_TYPE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTVAL_TYPE: " + node + ". Data: " + data.toString());
    return node.value;
   }

  public Object visit(ASTFunction node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTFunction: " + node + ". Data: " + data.toString());
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
       else if(thisNode.toString().equals("ID"))
	  function_name = this.visit((ASTID)(thisNode), data).toString();
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
    System.out.println(debugTab + "DEBUG: Node ASTPARAM_LIST:" + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTDECL_LIST node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTDECL_LIST: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTFUNCTION_CODE node, Object data){
    //checkAllIDs used
    System.out.println(debugTab + "DEBUG: entering code");
    int noChildren = node.jjtGetNumChildren();
    for(int i = 0; i < noChildren; i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
       node.jjtGetChild(i).jjtAccept(this, data); 
    }
    
    System.out.println(debugTab + "DEBUG: Node ASTFUNCTION_CODE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTRETURNS node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTRETURNS: " + node + ". Data: " + data.toString());
    String return_id = this.visit((ASTID)(node.jjtGetChild(0)), data).toString();
    String return_type = this.STC.getMostRecentType(return_id, this.currentScope);
    System.out.println("TEST: return_type = " + return_type);
    this.decreaseScope();
    return return_type+"*"+return_id;
   }
  public Object visit(ASTParameter node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTParameter: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTMAIN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTMAIN: " + node + ". Data: " + data.toString());
    this.increaseScope("main");
    int noChildren = node.jjtGetNumChildren();
    for(int i = 0; i < noChildren; i++){
       System.out.println(debugTab + "DEBUG: get child " + Integer.toString(i) + "."); 
       node.jjtGetChild(i).jjtAccept(this, data); 
    }
    return data;
   }
  public Object visit(ASTMAIN_DECLS node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTMAIN_DECLS: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTMAIN_CODE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTMAIN_CODE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTASSIGN_OP node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTASSIGN_OP: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTFUNC_CALL node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTFUNC_CALL: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTIF_CONDITION node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIF_CONDITION: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTIF_CODE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIF_CODE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTIF node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIF: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTELSE_CODE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTELSE_CODE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTELSE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTELSE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTWHILE_CONDITION node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTWHILE_CONDITION: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTWHILE_CODE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTWHILE_CODE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTWHILE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTWHILE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTSKIP node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTSKIP: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTPLUS_OP node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTPLUS_OP: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTSUBTRACT_OP node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTSUBTRACT_OP: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTNEGATION node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTNEGATION: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTEQUAL node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTEQUAL: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTNOT_EQUAL node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTNOT_EQUAL: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTLESS_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTLESS_THAN: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTEQ_LESS_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTEQ_LESS_THAN: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTGREATER_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTGREATER_THAN: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTEQ_GREATER_THAN node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTEQ_GREATER_THAN: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTOR node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTOR: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTAND node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTAND: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTARG_LIST node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTARG_LIST: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTID node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTID: " + node + ". Data: " + data.toString());
    return node.value;
   }
  public Object visit(ASTINT node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTINT: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTIS_TRUE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIS_TRUE: " + node + ". Data: " + data.toString());
    return data;
   }
  public Object visit(ASTIS_FALSE node, Object data){
    System.out.println(debugTab + "DEBUG: Node ASTIS_FALSE: " + node + ". Data: " + data.toString());
    return data;
   }

}