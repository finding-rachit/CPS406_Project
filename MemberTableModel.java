import javax.swing.table.*;
import java.util.*;

class MemberTableModel extends AbstractTableModel {

    private String[] columnNames = {"First Name", "Last Name", "Email", "Attended", "Paid"};
	private Member[] members;
    private MemberManager mMgr;

	public MemberTableModel(Member[] members, MemberManager mMgr) {
		super();
		this.members = members;
        this.mMgr = mMgr;
	}	

	public Object getValueAt(int rowIndex, int columnIndex) {

	    switch (columnIndex) {
            case 0: return this.members[rowIndex].getFirstName();

            case 1: return this.members[rowIndex].getLastName();

            case 2: return this.members[rowIndex].getEmailAddr();

        }

        return null;
	}

    public Member getObjectAtRow(int row) {
        return members[row];
    }

	public int getColumnCount() {
		return this.columnNames.length;
	}

    public String getColumnName(int col) {
        return this.columnNames[col];
    }

	public int getRowCount() {
		return this.members.length;
	}

    public Member getMemberAtRow(int row) {
        return this.members[row];
    }

}
