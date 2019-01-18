/*
 * Will process payments to the income statement and paying clients to list of members who have paid
 * 
 */

import java.util.UUID;


public class Manager {
	UUID session;
	UUID income_statement;  
    
	//UUID of either pre-existing or new session and incomestatement 
    public Manager(UUID session, UUID income_statement){
    	this.session = session;
    	this.income_statement = income_statement;
    }
    
    public void member_payment(Session session, income_statement income_statement, int amount, UUID memberID){
		//session.addPaidMemberId(memberID);
		income_statement.collection(amount);  
    }
    
    public void member_refund(int amount, income_statement income_statement){
    	income_statement.refund(amount); 
    }
   
    public void expenses_incurred(int cost, income_statement income_statement){
    	income_statement.expenses(cost);
    } 
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
    	/*
    	 * This is a test
    	UUID mock = UUID.randomUUID(); 
    	Session session = new Session();
    	income_statement is = new income_statement(); 
    	Manager manager = new Manager(session.getId(), is.getId());
    	manager.member_payment(session, is, 200, mock);  
    	*/
    	
    }

}
