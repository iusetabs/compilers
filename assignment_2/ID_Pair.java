public class ID_Pair extends Object{

	int ID; 
	int table; 
	int scope; 
	
	public ID_Pair(){}
	public ID_Pair(int x, int y, int z){
		this.ID = x;
		this.table = y;
		this.scope = z;
	}

	public int get_ID(){
		return this.ID; 
	}

	public int get_table(){
		return this.table; 
	}

	public int get_scope(){
		return this.scope; 
	}

	public void set_ID(int x){
		this.ID = x;
	}

	public void set_table(int x){
		this.table = x;
	}

	public void set_scope(int x){
		this.scope = x;
	}

	public String pair_info(){
		return ("ID: " + this.ID + ". Table: " + this.table + ". Scope: " + this.scope);	
	}
}
