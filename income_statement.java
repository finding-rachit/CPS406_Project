import java.util.UUID;

/*
 * ideally with every new session created there should be an associated income statement instance
 * 
 * */

public class income_statement {
	UUID id;
	public double revenue;
	public double expenses; 
	public double profit;
	
   public UUID getId() {
        return this.id;
   }
	   
   public income_statement(){
		id = UUID.randomUUID();
		revenue = 0;
		expenses = 0; 
		profit = 0;
	}
	
   	public double getRevenue(){
   		return revenue;
   	}
   	public double getExpenses(){
   		return expenses;
   	}
   
   	public double getProfit(){
   		profit = revenue - expenses; 
   		return profit;
   	}
   	
	public void collection(double sales){
		revenue = sales + revenue; 
	}
	
	public void refund(double amount){
		revenue = revenue - amount; 
	}
	
	public void expenses(double cost){
		expenses = cost + expenses;	
	}
	
}
