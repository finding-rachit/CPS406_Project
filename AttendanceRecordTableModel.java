import javax.swing.table.*;
import java.util.*;

class AttendanceRecordTableModel extends AbstractTableModel {

    private String[] columnNames = {"First Name", "Last Name", "Email", "Paid"};
	private AttendanceRecord[] attendance;
    private MemberManager mMgr;

	public AttendanceRecordTableModel(AttendanceRecord[] attendance, MemberManager mMgr) {
		super();
		this.attendance = attendance;
        this.mMgr = mMgr;
	}	

	public Object getValueAt(int rowIndex, int columnIndex) {

        Member m = this.mMgr.findMemberById(this.attendance[rowIndex].getMemberId());

	    switch (columnIndex) {
            case 0: return m.getFirstName();

            case 1: return m.getLastName();

            case 2: return m.getEmailAddr();

            case 3: return this.attendance[rowIndex].getPaid();

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
		return this.attendance.length;
	}

}
