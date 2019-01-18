import javax.swing.JCheckBox;
import javax.swing.table.*;

import java.util.*;

class MemberStatusTableModel extends AbstractTableModel {

    private String[] columnNames = {"First Name", "Last Name", "Email", "Active", "Attended", "Paid"};
	private MemberStatus[] statuses;
    private MemberManager mMgr;

	public MemberStatusTableModel(MemberStatus[] statusArray, MemberManager mMgr) {
		super();
		this.statuses = statusArray;
        this.mMgr = mMgr;
	}	
	
	
	public Object getValueAt(int rowIndex, int columnIndex) {

		
	    switch (columnIndex) {
	    	
            case 0: return this.statuses[rowIndex].firstName;

            case 1: return this.statuses[rowIndex].lastName;

            case 2: return this.statuses[rowIndex].emailAddr;

            case 3: return this.statuses[rowIndex].active;

            case 4: return this.statuses[rowIndex].attended;

            case 5: return this.statuses[rowIndex].paid;

        }

        return null;
	}

	public int getColumnCount() {
		return this.columnNames.length;
	}

    public String getColumnName(int col) {
        return this.columnNames[col];
    }

	public int getRowCount() {
		return this.statuses.length;
	}

    public MemberStatus getMemberAtRow(int row) {
        return this.statuses[row];
    }

}
