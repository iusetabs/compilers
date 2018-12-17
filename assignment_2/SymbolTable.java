import java.util.*;
public class SymbolTable extends Object{

	private Hashtable<String, LinkedList<String>> ScopeTable;
	private Hashtable<String, String> TypeTable;
	private Hashtable<String, String> InfoTable; 
	private Stack<String> scopeMonitor; //simply record scopes
	private Stack<String> allScopes; //Stack that only gets added to. 
	private String currentScope;

	public SymbolTable(){
		this.ScopeTable = new Hashtable<>();
		this.TypeTable = new Hashtable<>();
		this.InfoTable = new Hashtable<>();
		this.scopeMonitor = new Stack<String>();
		this.allScopes = new Stack<String>();
		this.currentScope = "global"; 
		this.allScopes.push("global");
	}
	/*--- SCOPE METHODS  ---*/
	public String getScope(){
		return this.currentScope;
	}
	
	public LinkedList<String> getScopeLL(String scope){
		return this.ScopeTable.get(scope);
	}

	public boolean addToScopeLL(String scope, String value){
		try{
			LinkedList<String> ll = getScopeLL(scope);
			if (ll == null)
				ll = new LinkedList<>();
			ll.addFirst(value);
			this.ScopeTable.put(scope, ll);
//HELP			System.out.print("DEBUG: Added " + scope + " and LL "); 
			return true; 
		}
		catch (Exception e){
			System.out.print("!ERROR " + e.getMessage());
			return false;
		}
	}

	public void increaseScope(String scope){
		this.scopeMonitor.push(this.currentScope);
		this.currentScope = scope; 
		LinkedList<String> ll = new LinkedList<>();
		this.ScopeTable.put(scope, ll); 
		this.allScopes.push(scope);
	}
	public void decreaseScope(){
		//this.ScopeTable.remove(this.currentScope); 
		//this.removeAllItemsFromScope(this.currentScope); don't do this yet.
		this.currentScope = scopeMonitor.pop(); 
		this.allScopes.push(this.currentScope); //need to track which scopes we are coming in and out of. 
	}
	public Hashtable<String, LinkedList<String>> getScopeTable(){
		return this.ScopeTable;
	}
	private void moveLLElemToFront(String id){
		LinkedList<String> LL = this.getScopeLL(this.currentScope);
		int i = LL.indexOf(id);
		System.out.println("DEBUG: value of i in moveLLElemToFront: " + i);
		LL.addFirst(LL.get(i)); //copy element to start of LL
		LL.remove(i+1); //remove the old copy. Index also gone up 1.
		
	}
	public ArrayList<String> checkAllScopesFor(String id){
		Enumeration iter = this.ScopeTable.keys(); 
		ArrayList<String> keys_list = new ArrayList<>();
		if (!this.ScopeTable.containsValue(id))
			return keys_list;
		String key = "";
		LinkedList<String> ll = new LinkedList<>();
		while (iter.hasMoreElements()) {
			//return a list of ID_scope's
			key = iter.nextElement().toString();
			ll = this.ScopeTable.get(key); 
			if(ll.contains(id))
    				keys_list.add(key);
		}
		return keys_list;		 
	}
	/*-- END SCOPE METHODS --*/

	/*-- TYPE METHODS --*/
	public boolean TypeTable_add(String id_scope, String type){
		if (this.TypeTable.containsKey(id_scope)){
//HELP			System.out.println("DEBUG: Typetable contains " + id_scope + " already.");
			return false; //value is contained within the table
		}
		this.TypeTable.put(id_scope, type);
		return true;
	}
	public String getType(String id_scope){
//HELP		System.out.println("DEBUG: " + id_scope);
		if (!this.TypeTable.containsKey(id_scope)){
			return ("-1"); //doesNotContain
		}
		return this.TypeTable.get(id_scope); 	
	}
	public Hashtable<String, String> getTypeTable(){
		return this.TypeTable;
	}
	/*--END TYPE METHODS --*/

	/*-- INFO METHODS --*/
	public boolean InfoTable_add(String id_scope, String info){
		if (this.InfoTable.containsKey(id_scope)){
			return false; //value is contained within the table
		}
		this.InfoTable.put(id_scope, info);
		return true;
	}
	public String getInfo(String id_scope){
		if (this.InfoTable.containsKey(id_scope)){
			return ("-1"); //doesNotContain
		}
		return this.InfoTable.get(id_scope); 	
	}
	public Hashtable<String, String> getInfoTable(){
		return this.InfoTable;
	}
	/*-- END INFO METHODS --*/

	/*-- CLASS FUNCTIONS --*/
	public ArrayList<String> getallOfValueFromHash(Hashtable<String, String> target, String target_value){
		Enumeration iter = target.keys();
		ArrayList<String> keys_list = new ArrayList<>();
		if (!target.containsValue(target_value))
			return keys_list;
		String key = "";
		while (iter.hasMoreElements()) {
			//return a list of ID_scope's
			key = iter.nextElement().toString();
			if (target.get(key).equals(target_value))
				keys_list.add(key); 
		}
		return keys_list;		 
	}
	public void print_hashTable(Hashtable<String, String> target){
			Enumeration iter = target.keys();
			String key = "";
			while (iter.hasMoreElements()){
				key = iter.nextElement().toString();
				System.out.println("Key: " + key + ". Value: " + target.get(key) + "."); 
			} 
			System.out.println("*************************\n");

	}
	public void print_LL(LinkedList<String> target){
		for (int i = 0; i < target.size(); i++)
			System.out.println(Integer.toString(i+1) + ": " + target.get(i));
		System.out.println();
	}

	public void output(boolean scope_table, boolean type_table, boolean info_table) {
		if (scope_table){
			System.out.println("\n*************************");
			System.out.println("SCOPE_TABLE: Key = SCOPE\n");
			Enumeration iter = this.ScopeTable.keys();
			String key = "";
			while (iter.hasMoreElements()){
				key = iter.nextElement().toString();
				System.out.println("Key: " + key + ". LL values:");
				this.print_LL(this.ScopeTable.get(key));  
			}
			System.out.println("*************************\n");
		}
		if (type_table){
			System.out.println("*************************");
			System.out.println("TYPE_TABLE: Key = ID*SCOPE\n");
		
			print_hashTable(this.TypeTable);
		}
		if(info_table){
			System.out.println("*************************");
			System.out.println("INFO_TABLE: Key = ID*SCOPE\n");
			print_hashTable(this.InfoTable); 
		}
	}
	public void enter(String id, String type, String info){
		//scope table: key[scope] values[all ID's]
//HELP		System.out.println("DEBUG: Attempting to add: ID " + id + ". Type: " + type + ". Info: " + info + " Scope: " + this.currentScope);
		String id_scope = id + "*" + this.currentScope; 
		String getType_val = this.getType(id_scope);
		
		if (getType_val.equals("-1")){
			if(!TypeTable_add(id_scope, type))
				System.out.println("!ERROR: Cannot add type " + type + " to id " + id_scope + " in the type table");
		}
		else{ //Type is declared in the scope
			if(!this.getInfo(id_scope).equals("function")){
				//we've discovered another function in same scope with same name
					System.out.println("!WARNING: DUPLICATE FUNCTION FOUND IN SAME SCOPE.");	
			}
			else if(!getType_val.equals(type)){//the type has been changed
				System.out.println("!WARNING: Identifier " + id + " has been changed from type " + getType_val + " to type " + type + ".");
				//declare a new scope for a var change? Is it necessary?TODO  
				if(!TypeTable_add(id_scope, type))
					System.out.println("!ERROR: Cannot add type " + type + " to id " + id_scope + " in the type table");
			}
			//We will move it to the front of the LL for scope for semantics
			else{
				this.moveLLElemToFront(id);
			}
		}
		if(!this.addToScopeLL(this.currentScope, id))
			System.out.println("!ERROR: Cannot add id " + id + " to scope " + this.currentScope + " table.");
		if(!this.InfoTable_add(id_scope, info))
			System.out.println("!WARNING: ID " + id + " already existing in InfoTable!");
		
	}
	private void removeAllItemsFromScope(String scope){
		Enumeration iter = this.TypeTable.keys();
		String key = "";
		while (iter.hasMoreElements()){
			key = iter.nextElement().toString();
			if (key.split("\\*")[1].equals(scope))
				this.TypeTable.remove(key); 
		}
		iter = this.InfoTable.keys();
		while (iter.hasMoreElements()){
			key = iter.nextElement().toString();
			if (key.split("\\*")[1].equals(scope))
				this.InfoTable.remove(key); 
		}
	}
        public String getMostRecentType(String id, String scope){
		String id_type = "-1"; 
		@SuppressWarnings("unchecked")
		Stack<String> allScopesCopy = (Stack<String>)(this.allScopes.clone());
		while(!allScopesCopy.pop().equals(scope)){}
		allScopesCopy.push(scope); 
		while(!allScopesCopy.empty()){
			String s = allScopesCopy.pop();
			try{
				if(this.getScopeLL(s).contains(id)){
					id_type=this.getType(id+"*"+s);
					break;
				}
			}
			catch (NullPointerException e){
				id_type = "-1"; //not declared in scope
			}
		}	
		return id_type;
	}
	/*-- END CLASS FUNCTIONS --*/
}
