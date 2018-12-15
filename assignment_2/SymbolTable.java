import java.util.*;
public class SymbolTable extends Object{

	Hashtable<String, LinkedList<String>> STC;
	Hashtable<String, LinkedList<String>> TTable;
	Hashtable<String, LinkedList<ID_Pair>> IDTable; 
	Hashtable<String, LinkedList<String>> InfoTable; 	
	Stack<String> scopeMonitor;
	int currentScope; 

	public SymbolTable(){
		this.STC = new Hashtable<>();
		this.TTable = new Hashtable<>();
		this.IDTable = new Hashtable<>();
		this.InfoTable = new Hashtable<>();
		this.scopeMonitor = new Stack<String>();
		this.currentScope = -1;
	}
	public int get_scope(){
		return this.currentScope;
	}
	public void increase_scope(){
		currentScope++;
		this.scopeMonitor.push("}");
	}
	public void decrease_scope(){
		String popped_item = "-1";
		while (popped_item != "}"){
			popped_item = this.scopeMonitor.pop().toString();
		}
		currentScope--;
	}
	public void STC_add(String indx, String value){
	        LinkedList<String> l = this.STC.get(indx);
	        l.addFirst(value); 		

	}
	public void TTable_add(String indx, String value){
	        LinkedList<String> l = this.TTable.get(indx);
	        l.addFirst(value); 		

	}
	public void IDTable_add(String indx, int table_indx, int table_num, int scope){
		ID_Pair new_pair = new ID_Pair(table_indx, table_num, scope);
	        LinkedList<ID_Pair> l = this.IDTable.get(indx);
	        l.addFirst(new_pair); 		
	}
	public void InfoTable_add(String indx, String value){
	        LinkedList<String> l = this.InfoTable.get(indx);
	        l.addFirst(value); 		
	}
}
