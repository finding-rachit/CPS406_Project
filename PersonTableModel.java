import javax.swing.table.*;
import java.util.*;

class PersonTableModel extends AbstractTableModel {

    private String[] columnNames = {"First Name", "Last Name", "Email"};
	private Person[] people;

	public PersonTableModel(Person[] people) {
		super();
		this.people = people;
	}	

	public Object getValueAt(int rowIndex, int columnIndex) {

	    switch (columnIndex) {
            case 0: return this.people[rowIndex].getFirstName();

            case 1: return this.people[rowIndex].getLastName();

            case 2: return this.people[rowIndex].getEmailAddr();

        }

        return null;
	}

    public Person getObjectAtRow(int row) {
        return people[row];
    }

	public int getColumnCount() {
		return this.columnNames.length;
	}

    public String getColumnName(int col) {
        return this.columnNames[col];
    }

	public int getRowCount() {
		return this.people.length;
	}

    public Person getPersonAtRow(int row) {
        return this.people[row];
    }

}
