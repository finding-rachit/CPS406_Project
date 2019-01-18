import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

	class income_statement_manager {

	    private List<income_statement> is_List;

	    public income_statement_manager() {
	        is_List = new LinkedList<income_statement>();
	    }

	    public void add_income_statement(income_statement newIS) {

	        is_List.add(newIS);

	    }

	    public boolean delete_income_statement(UUID isId) {

	        income_statement tmpis = this.find_income_statement_ById(isId);

	        if (tmpis != null) {
	            this.is_List.remove(tmpis);
	            return true;
	        }

	        return false;
	    }

	    public income_statement find_income_statement_ById(UUID isId) {

	        ListIterator<income_statement> li = is_List.listIterator();

	        income_statement tmpis;

	        while (li.hasNext()) {

	            tmpis = li.next();

	            if (tmpis.getId().equals(isId)) {
	                return tmpis;
	            }
	        }

	        return null;

	    }

	    public int numberOfSessions() {

	        return is_List.size();

	    }

	}
	
	

