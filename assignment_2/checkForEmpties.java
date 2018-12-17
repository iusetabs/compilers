public class checkForEmpties{
    private boolean ifHit; 
    private boolean whileHit;
    private boolean elseHit;
    private Node lastNode;
    private Node currentNode;
	
    public checkForEmpties(){
	this.ifHit = false;
        this.whileHit = false;
        this.elseHit = false;
	this.lastNode = null;
	this.currentNode = null;
    }

    public boolean give(Node n){
	this.currentNode = n;
	if (this.lastNode != null){
		this.check(this.lastNode);
	}
	this.lastNode = n;
       if(n.toString().equals("IF_CONDITION")){
		this.ifHit = true; 
       }
       else if(n.toString().equals("WHILE_CONDITION")){
		this.whileHit = true;
       }
       else if(n.toString().equals("ELSE")){
       		this.elseHit = true;
       }
	return false;
    }
    public boolean check(Node n){
       if(this.ifHit){
	   this.ifHit = false; 
	   if(!this.currentNode.toString().equals("IF_CODE")){
		System.out.println("ERROR: If found without any body code.\nSOLUTION: Remove."); //SEMANTIC CHECK 7
	   }
       }
       else if(this.whileHit){ 
	   this.whileHit = false;
	   if(!this.currentNode.toString().equals("WHILE_CODE")){
		System.out.println("ERROR: WHILE found without any body code.\nSOLUTION: Remove."); //SEMANTIC CHECK 8
	   }
       }
       else if(this.elseHit){
	    this.elseHit = false;
	    if(!this.currentNode.toString().equals("ELSE_CODE")){
		System.out.println("ERROR: ELSE found without any body code.\nSOLUTION: Remove."); //SEMANTIC CHECK 9
	   }
	}
	return false;
    }
}
