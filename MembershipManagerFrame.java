/* MembershipManagerFrame.java */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * MembershipManagerFrame class
 */
public class MembershipManagerFrame extends JFrame
{

    private static final int MAIN_WIDTH = 1000;
    private static final int MAIN_HEIGHT = 700;

    private static final int LOGIN_WIDTH = 800;
    private static final int LOGIN_HEIGHT = 400;

    private static final int LOGIN_VIEW = 0;
    private static final int TREASURER_VIEW = 1;
    private static final int INSTRUCTOR_VIEW = 2;
    private static final int MEMBER_VIEW = 3;

	// Initializations
    private Calendar currentDate;

    private JButton logoutButton;

    private JButton treasurerViewButton;
    private JButton instructorViewButton;
    private JButton memberViewButton;
    private JButton monthBackwardButton;
    private JButton monthForwardButton;

    private JLabel tLabel1, tLabel2, tLabel3;
    private JTextField tField1, tField2;
    private JTextArea tText1, tText2;
    private JButton tButton;

    private JPanel calendarPanel;
    
    private String instructorMessage;

    private JTabbedPane tabs = new JTabbedPane();

    private MemberManager mMgr;
    private SessionManager sMgr;
    private InstructorManager iMgr;
    private AttendanceRecordManager aMgr;
    private income_statement_manager isMgr;

    private int currentView = LOGIN_VIEW;
    private int currentTabTreasurer = -1;

    //for area of text
    JTextArea incomeStatementArea = new JTextArea();
    JButton createanewsession = new JButton("Create new session"); 
    
	JLabel revenueLabel, expenseLabel;
	JTextField ISrevenueField, ISexpenseField;
	
	double revenuevalue, expensevalue; 
    
    //create holder for the current session;
    private Session current_session;

    public MembershipManagerFrame()
    {

		new SimpleDateFormat("EEE, MMM dd, yyyy");
		new SimpleDateFormat("MMM");

        this.mMgr = new MemberManager();
        this.sMgr = new SessionManager();
        this.iMgr = new InstructorManager();
        this.aMgr = new AttendanceRecordManager();
        this.isMgr = new income_statement_manager();

        this.currentDate = this.getCurrentDate();
        this.createTestData();

        this.calendarPanel = createMonthViewPanel();

        this.switchViewLogin();

    }

    /**
     * Listen for events that switch the user's view
     */
    class SwitchViewListener implements ActionListener
    {

        /**
         * Check the button that was clicked, and switch to the corresponding
         * view.
         */
        public void actionPerformed(ActionEvent event)
        {

            Object clickSrc = event.getSource();

            // check the button attached to the event
            if (event.getSource() == treasurerViewButton)
            {
                switchViewTreasurer();
            }
            else if (clickSrc == instructorViewButton)
            {
                switchViewInstructor();
            }
            else if (clickSrc == memberViewButton)
            {
                switchViewMember();
                try {
                	 if(!instructorMessage.isEmpty())
                         JOptionPane.showMessageDialog(null, instructorMessage);
                	}catch(NullPointerException e)
                {
                		System.out.println("");
                }

            }
            else if (clickSrc == logoutButton)
            {
                switchViewLogin();
            }

        }

    }

    class ChangeMonthListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == monthForwardButton) {
                setDateAheadByAMonth();
            }

            if (event.getSource() == monthBackwardButton) {
                setDateBackByAMonth();
            }
        }
    }

    class SelectDayListener implements ActionListener
    {

        private int year;
        private int month;
        private int day;

        public SelectDayListener(int year, int month, int day) {
            super();
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public void actionPerformed(ActionEvent event) {
            setDate(this.year, this.month, this.day);
            refreshCalendar();
            refreshView();
        }
    }

    private void clearView() {
        this.getContentPane().removeAll();
    }

    private void switchViewLogin() {

        this.clearView();
		this.setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
		this.setLayout(new GridLayout(2,1));
        this.add(loginPanel());
        
        JPanel newPanel = new JPanel();
        JLabel newLabel = new JLabel();
        newLabel.setText("Test Mode, no login required. Use the buttons to browse!");
        newPanel.add(newLabel);
        this.add(newPanel);

        this.currentView = LOGIN_VIEW;

        this.revalidate();
    }

    private void switchViewTreasurer() {

        this.currentTabTreasurer = tabs.getSelectedIndex();
        this.clearView();
        this.setSize(MAIN_WIDTH, MAIN_HEIGHT);

        this.setLayout(new BorderLayout());

        this.add(ctrlPanel(), BorderLayout.NORTH);
        this.add(treasurerPanel(), BorderLayout.CENTER);

        this.currentView = TREASURER_VIEW;
        	
        this.revalidate();

    }

    private void switchViewInstructor() {

        this.clearView();
        this.setSize(MAIN_WIDTH, MAIN_HEIGHT);

        this.setLayout(new BorderLayout());

        this.add(ctrlPanel(), BorderLayout.NORTH);
        this.add(instructorPanel(), BorderLayout.CENTER);

        this.currentView = INSTRUCTOR_VIEW;

        this.revalidate();

    }

    private void switchViewMember() {
        this.clearView();
        this.setSize(MAIN_WIDTH, MAIN_HEIGHT);

        this.setLayout(new BorderLayout());

        this.add(ctrlPanel(), BorderLayout.NORTH);
        this.add(memberPanel(), BorderLayout.CENTER);

        this.currentView = MEMBER_VIEW;

        this.revalidate();
    }

    private void createTestData() {

        Member m1 = new Member("Homer", "Simpson", "homer@example.org");
        Member m2 = new Member("Marge", "Simpson", "marge@example.org");
        Member m3 = new Member("Bart", "Simpson", "bart@example.org");
        Member m4 = new Member("Lisa", "Simpson", "lisa@example.org");
        Member m5 = new Member("Janet", "Jackson", "jackson@example.org");
        Member m6 = new Member("Michael", "Scott", "scott@example.org");
        Member m7 = new Member("Peter", "Piper", "piper@example.org");

        this.mMgr.addMember(m1);
        this.mMgr.addMember(m2);
        this.mMgr.addMember(m3);
        this.mMgr.addMember(m4);
        this.mMgr.addMember(m5);
        this.mMgr.addMember(m6);
        this.mMgr.addMember(m7);

        Session s1 = new Session((Calendar) this.currentDate.clone());
        Session s2 = new Session(new GregorianCalendar(2018, 3, 8));

        this.sMgr.addSession(s1);
        this.sMgr.addSession(s2);

        this.aMgr.addMemberToSession(m1.getId(), s1.getId(), true);
        this.aMgr.addMemberToSession(m2.getId(), s1.getId(), true);
        this.aMgr.addMemberToSession(m3.getId(), s1.getId(), true);

        this.aMgr.addMemberToSession(m1.getId(), s2.getId(), false);
        this.aMgr.addMemberToSession(m3.getId(), s2.getId(), false);
        this.aMgr.addMemberToSession(m4.getId(), s2.getId(), true);

        this.iMgr.addInstructor(new Instructor("Ned", "Flanders", "ned@example.org"));
        this.iMgr.addInstructor(new Instructor("Clancy", "Quimby", "clancy@example.org"));

    }

	// Login Page Panel
    private JPanel loginPanel()
    {
    	JPanel loginSubpanelOne = new JPanel();
    	JPanel loginSubpanelTwo = new JPanel();
    	JPanel loginSubpanelThree = new JPanel();
    	JPanel returnPanel = new JPanel();

    	returnPanel.setBorder(new TitledBorder(new EtchedBorder(), "LOGIN PANEL"));

    	tLabel2 = new JLabel("USER ID :");
    	tField1 = new JTextField(5);
    	tLabel3 = new JLabel("PASSWORD :");
    	tField2 = new JTextField(5);
    	tButton = new JButton("LOGIN");

    	loginSubpanelOne.add(tLabel2);
    	loginSubpanelOne.add(tField1);

    	loginSubpanelTwo.add(tLabel3);
    	loginSubpanelTwo.add(tField2);

		loginSubpanelThree.add(tButton);

		returnPanel.setLayout(new GridLayout(3, 1));

		returnPanel.add(loginSubpanelOne);
		returnPanel.add(loginSubpanelTwo);
        returnPanel.add(ctrlPanel());

    	return returnPanel;
    }

    private void setDate(int year, int month, int day) {

        this.currentDate.set(Calendar.YEAR, year);
        this.currentDate.set(Calendar.MONTH, month);
        this.currentDate.set(Calendar.DAY_OF_MONTH, day);

    }

    private void setDateAheadByAMonth() {
        this.currentDate.add(Calendar.MONTH, 1);
        this.refreshCalendar();
        this.refreshView();
    }

    private void setDateBackByAMonth() {
        this.currentDate.add(Calendar.MONTH, -1);
        this.refreshCalendar();
        this.refreshView();
    }

    private void refreshView() {
        switch (this.currentView) {
            case LOGIN_VIEW: switchViewLogin();
                break;

            case TREASURER_VIEW: switchViewTreasurer();
                break;

            case INSTRUCTOR_VIEW: switchViewInstructor();
                break;

            case MEMBER_VIEW: switchViewMember();
                break;
        }

    }

    private void refreshCalendar() {
        this.calendarPanel.removeAll();
        this.calendarPanel.add(createMonthViewPanel());
        this.revalidate();
        this.repaint();
    }

	private JPanel createMonthViewPanel()
	{

		JPanel newMonthViewPanel = new JPanel(new GridLayout(7,7));

		String[] dates = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		// Sun	Mon	Tue	Wed	Thu	Fri	Sat
		for (int i = 0; i < dates.length; i++)
		{
			JPanel panel = new JPanel();
			panel.add(new JLabel(dates[i]));
			newMonthViewPanel.add(panel);
		}

		Calendar tmpDate = new GregorianCalendar(this.currentDate.get(Calendar.YEAR), this.currentDate.get(Calendar.MONTH), 1, 0, 0);
		int firstDay = tmpDate.get(Calendar.DAY_OF_WEEK);
		int lastDay = tmpDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		int currentDay = this.currentDate.get(Calendar.DAY_OF_MONTH);

        int year = this.currentDate.get(Calendar.YEAR);
        int month = this.currentDate.get(Calendar.MONTH);

		int counter = 1;
		int start = 1;

        HashSet<Integer> daysWithSessions = this.sMgr.getDaysWithSessionsInMonth(this.currentDate);

		for (int rows = 1; rows < 7; rows++)
		{
			for (int cols = 0; cols < 7; cols++)
			{
				if (counter >= firstDay && start <= lastDay)
				{
					JButton button = new JButton(start + "");

					if (start == currentDay)
					{
						button.setBackground(Color.RED);
					} else if (daysWithSessions.contains(start)) {
                        button.setBackground(Color.LIGHT_GRAY);
                    }
					//give buttons functionality
                    button.addActionListener(new SelectDayListener(year, month, start));
                    //add action listeners to handle session manager
                    //will create an new session and income statement
                    //button.addActionListener(new sessionaction());
					newMonthViewPanel.add(button);
					start++;
				}
				else
				{
					JPanel empty = new JPanel();
					newMonthViewPanel.add(empty);
				}
				counter++;
			}
		}
		JPanel returnPanel = new JPanel(new BorderLayout());

        JLabel calendarTitle = new JLabel((new SimpleDateFormat("MMMMM yyyy")).format(this.currentDate.getTime()));

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        calendarTitle.setFont(titleFont);

        ChangeMonthListener listener = new ChangeMonthListener();
        this.monthBackwardButton = new JButton("<");
        this.monthForwardButton = new JButton(">");

        JPanel navButtonPanel = new JPanel();

        monthBackwardButton.addActionListener(listener);
        monthForwardButton.addActionListener(listener);

        navButtonPanel.add(this.monthBackwardButton);
        navButtonPanel.add(this.monthForwardButton);

        JPanel northPanel = new JPanel();

        northPanel.add(calendarTitle);
        northPanel.add(navButtonPanel);

        returnPanel.add(northPanel, BorderLayout.NORTH);
		returnPanel.add(newMonthViewPanel, BorderLayout.CENTER);

		return returnPanel;
	}

    private JPanel ctrlPanel() {

        treasurerViewButton = new JButton("TREASURER");
        instructorViewButton = new JButton("INSTRUCTOR");
        memberViewButton = new JButton("MEMBER");
        logoutButton = new JButton("LOGOUT");

        SwitchViewListener listener = new SwitchViewListener();
        treasurerViewButton.addActionListener(listener);
        instructorViewButton.addActionListener(listener);
        memberViewButton.addActionListener(listener);
        logoutButton.addActionListener(listener);


        JPanel ctrlPanel = new JPanel();

        ctrlPanel.add(treasurerViewButton);
        ctrlPanel.add(instructorViewButton);
        ctrlPanel.add(memberViewButton);
        ctrlPanel.add(logoutButton);

        return ctrlPanel;
    }
    
    //series of actionlisteners to interact with managers
    
    /*
    class incomestatementaction implements ActionListener{
    	 
    	@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
    		//we are just adding revenues and expenses
    		if (e.getSource() == submitvalues){
    			//System.out.println("Activated Once");
    			//System.out.println(e.getActionCommand());
    			incomeStatementArea.selectAll();
    			incomeStatementArea.replaceSelection("");
    		
    			try{
    					revenuevalue = Double.parseDouble(ISrevenueField.getText());
    				}
    				catch(NumberFormatException ex){
    					  incomeStatementArea.append(String.format("Please input a number"));
    				}
    			
    		        try{
    		        	expensevalue = Double.parseDouble(ISexpenseField.getText()); 
    		        }
    		        catch(NumberFormatException ex){
    		        	 incomeStatementArea.append(String.format("Please input a number"));
    		        }
    		    
    		        
	    		income_statement current_IS = current_session.getIS();  
	    		current_IS.collection(revenuevalue);
	    		current_IS.expenses(expensevalue);
	    		
	    		isMgr.add_income_statement(current_IS); 
	    	    incomeStatementArea.append(String.format("Income Statement Highlights:\n"));
	    	    incomeStatementArea.append(String.format("Revenue: %f\n", current_IS.getRevenue()));
	    	    //System.out.println(revenuevalue);
	    	    incomeStatementArea.append(String.format("Expenses: %f\n", current_IS.getExpenses()));
	    	   incomeStatementArea.append(String.format("Profit/Loss: %f\n", current_IS.getProfit()));
    		}    		
		}
    }
*/
	private JPanel treasurerPanel()
	{
		JPanel returnPanel = new JPanel();
		JPanel subPanel = new JPanel();
		JPanel interfacePanel = new JPanel();
		JPanel interfaceSubPanelOne = new JPanel();
		JPanel interfaceSubPanelTwo = new JPanel();
		JPanel interfaceSubPanelThree = new JPanel();
		JPanel interfaceSubPanelFour = new JPanel();
		JPanel incomeStatementPanel = new JPanel();

		JPanel enterinfoPanel = new JPanel();
        JPanel userInfoPanel = new JPanel();

		JLabel firstNameLabel, lastNameLabel, emailAddLabel;
		JTextField firstNameTextField, lastNameTextField, emailAddTextfield;

		JButton addnewmember = new JButton("Add a new Member");
		JButton submitvalues = new JButton("Submit Values");
		JPanel addNewMemberPanel = new JPanel();
		addNewMemberPanel.add(addnewmember);
		
		JButton addInstructorButton = new JButton("Add Instructor");
		JPanel addInstructorButtonPanel = new JPanel();
		addInstructorButtonPanel.add(addInstructorButton);
	
		//JLabel revenueLabel, expenseLabel;
		//JTextField revenueField, expenseField;

		returnPanel.setBorder(new TitledBorder(new EtchedBorder(), "TREASURER PANEL"));

        revenueLabel = new JLabel("REVENUE :");
        ISrevenueField = new JTextField(10);
        JPanel revenueLabelPanel = new JPanel();
        JPanel revenueFieldPanel = new JPanel();
        revenueLabelPanel.add(revenueLabel);
        revenueFieldPanel.add(ISrevenueField);
        expenseLabel = new JLabel("EXPENSE :");
        ISexpenseField = new JTextField(10);
        JPanel expenseLabelPanel = new JPanel();
        JPanel expenseFieldPanel = new JPanel();
        expenseLabelPanel.add(expenseLabel);
        expenseFieldPanel.add(ISexpenseField);

        //to submit values in the text field
		//JButton submitvalues = new JButton("Submit Values");
		JPanel submitValuesPanel = new JPanel();
		submitValuesPanel.add(submitvalues);
		
		//incomestatementaction transactions = new incomestatementaction();
		 ActionListener incomestatementaction = new ActionListener(){
			 
		    	@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
		    		//we are just adding revenues and expenses
		    		if (e.getSource() == submitvalues){
		    			//System.out.println("Activated Once");
		    			//System.out.println(e.getActionCommand());
		    			incomeStatementArea.selectAll();
		    			incomeStatementArea.replaceSelection("");
		    		
		    			try{
		    					revenuevalue = Double.parseDouble(ISrevenueField.getText());
		    				}
		    				catch(NumberFormatException ex){
		    					  incomeStatementArea.append(String.format("Please input a number"));
		    				}
		    			
		    		        try{
		    		        	expensevalue = Double.parseDouble(ISexpenseField.getText()); 
		    		        }
		    		        catch(NumberFormatException ex){
		    		        	 incomeStatementArea.append(String.format("Please input a number"));
		    		        }
		    		    
		    		        
			    		income_statement current_IS = current_session.getIS();  
			    		current_IS.collection(revenuevalue);
			    		current_IS.expenses(expensevalue);
			    		
			    		isMgr.add_income_statement(current_IS); 
			    	    incomeStatementArea.append(String.format("Income Statement Highlights:\n"));
			    	    incomeStatementArea.append(String.format("Revenue: %f\n", current_IS.getRevenue()));
			    	    //System.out.println(revenuevalue);
			    	    incomeStatementArea.append(String.format("Expenses: %f\n", current_IS.getExpenses()));
			    	   incomeStatementArea.append(String.format("Profit/Loss: %f\n", current_IS.getProfit()));
		    		}    		
				}
		    };
		
		submitvalues.addActionListener(incomestatementaction);

    	interfaceSubPanelOne.add(revenueLabelPanel);
    	interfaceSubPanelOne.add(revenueFieldPanel);
    	
    	interfaceSubPanelOne.add(expenseLabelPanel);
    	interfaceSubPanelOne.add(expenseFieldPanel);
    	
    	//subpaneltwo components

        Session session = this.sMgr.findSessionByDate(this.currentDate);

        JComboBox<Instructor> instructorComboBox = new JComboBox<Instructor>(iMgr.getArray());

    	interfaceSubPanelTwo.setLayout(new GridLayout(2,1));
    	interfaceSubPanelTwo.add(createanewsession);

		//JButton createSessionButton = new JButton("Create a new session");
		JPanel createNewSessionPanel = new JPanel();

		ActionListener createSessionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Session s = new Session((Calendar) currentDate.clone());
				current_session = s; 
                sMgr.addSession(s);
                refreshView();
            }
		};
		createanewsession.addActionListener(createSessionListener);

		JButton deleteSessionButton = new JButton("Delete session");
		JPanel deleteSessionPanel = new JPanel();

		ActionListener deleteSessionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
                sMgr.deleteSession(session.getId());
                refreshView();
            }
		};
		deleteSessionButton.addActionListener(deleteSessionListener);

		JButton setInstructorButton = new JButton("Set Instructor");
		
		ActionListener setInstructorListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
                Instructor tempInstructor = (Instructor) instructorComboBox.getSelectedItem();
                session.setInstructorId(tempInstructor.getId());
                refreshView();
            }

		};
		setInstructorButton.addActionListener(setInstructorListener);


        JButton removeInstructorButton = new JButton("Remove Instructor");

		ActionListener removeInstructorListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
                session.setInstructorId(null);
                refreshView();
            }
		};
		
		removeInstructorButton.addActionListener(removeInstructorListener);

        if (session == null) {
            createNewSessionPanel.add(createanewsession);
            interfaceSubPanelTwo.add(createNewSessionPanel);
        } else {
            if (session.getInstructorId() == null) {
                deleteSessionPanel.add(instructorComboBox);
                deleteSessionPanel.add(setInstructorButton);
            } else {
                JLabel instructorLabel = new JLabel("Instructor: " + this.iMgr.findInstructorById(session.getInstructorId()).getFullName());
                deleteSessionPanel.add(instructorLabel);
                deleteSessionPanel.add(removeInstructorButton);
            }
            deleteSessionPanel.add(deleteSessionButton);
            interfaceSubPanelTwo.add(deleteSessionPanel);
        }

    	
    	//subpanelthreecomponents
    	interfaceSubPanelThree.add(addNewMemberPanel);
    	
    	//subpanelfourcomponents
        JPanel instructorListPanel = new JPanel();
		instructorListPanel.setBorder(new TitledBorder(new EtchedBorder(), "Instructor List"));
        PersonTableModel instructorTableModel = new PersonTableModel(this.iMgr.getArray());
        JTable instructorTable = new JTable(instructorTableModel);
        instructorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane instructorListScrollPane = new JScrollPane(instructorTable);
        instructorListScrollPane.setPreferredSize(new Dimension(300, 300));
        instructorListPanel.add(new JScrollPane(instructorTable));
        
        userInfoPanel.setBorder(new TitledBorder(new EtchedBorder(),"New Instructor"));
        userInfoPanel.setLayout(new GridLayout(6, 1));
        firstNameLabel = new JLabel("First Name: ");
        userInfoPanel.add(firstNameLabel);
		firstNameTextField =  new JTextField();
		userInfoPanel.add(firstNameTextField);
		lastNameLabel = new JLabel("Last Name: ");
		userInfoPanel.add(lastNameLabel);
		lastNameTextField =  new JTextField();
		userInfoPanel.add(lastNameTextField);
		emailAddLabel = new JLabel("Email Address: ");
		userInfoPanel.add(emailAddLabel);
		emailAddTextfield =  new JTextField();
		userInfoPanel.add(emailAddTextfield);
		
        enterinfoPanel.setLayout(new GridLayout(2,1));
        enterinfoPanel.add(userInfoPanel);
        enterinfoPanel.add(addInstructorButtonPanel);
        
		ActionListener addInstructorListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String firstName = firstNameTextField.getText();
				String lastName = lastNameTextField.getText();
				String emailAddr = emailAddTextfield.getText();

				if(firstName.isEmpty()||lastName.isEmpty()||emailAddr.isEmpty())
				{
					if(firstName.isEmpty())
						JOptionPane.showMessageDialog(null, "Enter First Name");
					else if(lastName.isEmpty())
						JOptionPane.showMessageDialog(null, "Enter Last Name");
					else if(emailAddr.isEmpty())
						JOptionPane.showMessageDialog(null, "Enter Email Address");
				}

				else {
					Instructor newInstructor = new Instructor(firstNameTextField.getText(), lastNameTextField.getText(),
							emailAddTextfield.getText());
					iMgr.addInstructor(newInstructor);
	                refreshView();
				}
			}
		};

		addInstructorButton.addActionListener(addInstructorListener);
		
		JButton deleteInstructorButton = new JButton("Delete Instructor");
		addInstructorButtonPanel.add(deleteInstructorButton);
		ActionListener deleteInstructorListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
            	
            	try {
            		UUID tmpInstructorId = instructorTableModel.getPersonAtRow(instructorTable.getSelectedRow()).id;
                	iMgr.deleteInstructor(tmpInstructorId);
    		   		refreshView(); 
            	}catch(ArrayIndexOutOfBoundsException e) {
            		JOptionPane.showMessageDialog(null, "Select a instructor in a table.");
            	}
				
            }
        };
		
		deleteInstructorButton.addActionListener(deleteInstructorListener);
        
        interfaceSubPanelFour.setLayout(new GridLayout(1,2));
    	interfaceSubPanelFour.add(instructorListPanel);
    	interfaceSubPanelFour.add(enterinfoPanel);

		interfacePanel.setLayout(new GridLayout(3, 1));
		
		interfaceSubPanelThree.setLayout(new GridLayout(3,1));
		interfacePanel.setBorder(new TitledBorder(new EtchedBorder(), "USER INTERFACE"));

		//JTextArea incomeStatementArea = new JTextArea();
		//incomeStatementArea.setText("Income Statement Area\n\n");
		incomeStatementArea.setPreferredSize(new Dimension(400, 400)); 

		incomeStatementPanel.add(incomeStatementArea);
		
		JPanel interfaceSubOne = new JPanel();
		interfaceSubOne.setLayout(new GridLayout(3,1));
		interfaceSubOne.add(interfaceSubPanelOne);
		interfaceSubOne.add(submitValuesPanel);
		interfaceSubOne.add(incomeStatementPanel);
		
		// adding tabs
		tabs.removeAll();
		tabs.addTab("Finances", interfaceSubOne);
		tabs.addTab("Session Management", interfaceSubPanelTwo);
		tabs.addTab("Instructors", interfaceSubPanelFour);

        if (this.currentTabTreasurer > -1) {
            tabs.setSelectedIndex(this.currentTabTreasurer);
        }

		subPanel.setLayout(new GridLayout(2, 1));
		subPanel.add(this.calendarPanel);
		subPanel.add(tabs);

		returnPanel.setLayout(new GridLayout(1,2));
		returnPanel.add(subPanel);
		return returnPanel;	
	}
	
	private JPanel instructorPanel()
	{
		
		JPanel returnPanel = new JPanel();
		JPanel enterinfoPanel = new JPanel();
		JPanel memberListPanel = new JPanel();
		JPanel userInfoPanel = new JPanel();
	    JPanel contactMemberPanel = new JPanel();

		JLabel firstNameLabel, lastNameLabel, emailAddLabel;
		
		JTextField firstNameTextField = new JTextField();
		JTextField lastNameTextField = new JTextField();
		JTextField emailAddTextfield = new JTextField();
		
		JTextArea sendTextArea;
		JButton enterMemberListButton, cancelMemberListButton, sendTextButton;
		
		returnPanel.setBorder(new TitledBorder(new EtchedBorder(), "INSTRUCTOR PANEL"));
		
		returnPanel.setLayout(new GridLayout(1,2));
		
		returnPanel.add(memberListPanel);
		returnPanel.add(enterinfoPanel);
		
		
		memberListPanel.setBorder(new TitledBorder(new EtchedBorder(), "Member List"));
		
        MemberStatusTableModel memberStatusTableModel = new MemberStatusTableModel(this.getMemberStatus(), this.mMgr);
        JTable memberTable = new JTable(memberStatusTableModel);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane memberListScrollPane = new JScrollPane(memberTable);
        memberListScrollPane.setPreferredSize(new Dimension(340, 300));
        memberListPanel.add(new JScrollPane(memberTable));
        memberListPanel.setLayout(new GridLayout(2, 1));

        JPanel optionPanel = new JPanel();
        memberListPanel.add(optionPanel);
        optionPanel.setLayout(new GridLayout(10, 1));
       
        
        ActionListener deActivateemberListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
               try {
            		UUID tmpMemberId = memberStatusTableModel.getMemberAtRow(memberTable.getSelectedRow()).id;
                    mMgr.deActivateMember(tmpMemberId);
                    refreshView();
               }catch(ArrayIndexOutOfBoundsException e) {
            	   JOptionPane.showMessageDialog(null, "Select a member in a table.");
               }
            
            }

        };

        ActionListener activateMemberListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                
            	try {
            		UUID tmpMemberId = memberStatusTableModel.getMemberAtRow(memberTable.getSelectedRow()).id;
                    mMgr.activateMember(tmpMemberId);
                    refreshView();
            	}catch(ArrayIndexOutOfBoundsException e) {
            		JOptionPane.showMessageDialog(null, "Select a member in a table.");
            	}
            	
            }

        };

        ActionListener selectMemberListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	
            	try {
            		String firstName;
    				String lastName;
    				String emailAddr;
                	UUID tmpMemberId = memberStatusTableModel.getMemberAtRow(memberTable.getSelectedRow()).id;
                    firstName = mMgr.findMemberById(tmpMemberId).getFirstName();
                    lastName = mMgr.findMemberById(tmpMemberId).getLastName();
                    emailAddr = mMgr.findMemberById(tmpMemberId).getEmailAddr();
                    firstNameTextField.setText(firstName);
                    lastNameTextField.setText(lastName);
                    emailAddTextfield.setText(emailAddr);
            	}catch(ArrayIndexOutOfBoundsException e) {
            		JOptionPane.showMessageDialog(null, "Select a member in a table.");
            	}
            }
        };
        
        ActionListener deleteMemberListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                
            	try {
	            		UUID tmpMemberId = memberStatusTableModel.getMemberAtRow(memberTable.getSelectedRow()).id;
	                    
	             	   if(memberStatusTableModel.getMemberAtRow(memberTable.getSelectedRow()).attended == 0 
	             			   && memberStatusTableModel.getMemberAtRow(memberTable.getSelectedRow()).paid == 0)
	             	   		{
	             		   		mMgr.deleteMember(tmpMemberId);
	             		   		refreshView();
	             	   		}
	             	   else {
	             		   JOptionPane.showMessageDialog(null, "you can't delete the member who had attended at least one time.");
             	   }
            	}catch(ArrayIndexOutOfBoundsException e) {
            		JOptionPane.showMessageDialog(null, "Select a member in a table.");
            	}
              
            }

        };
        
        JButton deActivateMemberButton = new JButton("Deactivate Member");
        deActivateMemberButton.addActionListener(deActivateemberListener);
        optionPanel.add(deActivateMemberButton); 
        JButton activateMemberButton = new JButton("Activate Member");
        activateMemberButton.addActionListener(activateMemberListener);
        optionPanel.add(activateMemberButton);
        JButton selectMemberButton = new JButton("Select");
        selectMemberButton.addActionListener(selectMemberListener);
        optionPanel.add(selectMemberButton);
        

        JButton deleteMemberButton = new JButton("Delete Member");
        deleteMemberButton.addActionListener(deleteMemberListener);
        optionPanel.add(deleteMemberButton);

        //Panel for entering members information.
        enterinfoPanel.setLayout(new BorderLayout());
        
        userInfoPanel.setLayout(new GridLayout(8, 2));
        firstNameLabel = new JLabel("First Name: ");
        userInfoPanel.add(firstNameLabel);
		//firstNameTextField =  new JTextField();
		userInfoPanel.add(firstNameTextField);
		lastNameLabel = new JLabel("Last Name: ");
		userInfoPanel.add(lastNameLabel);
		//lastNameTextField =  new JTextField();
		userInfoPanel.add(lastNameTextField);
		emailAddLabel = new JLabel("Email Addr: ");
		userInfoPanel.add(emailAddLabel);
		//emailAddTextfield =  new JTextField();
		userInfoPanel.add(emailAddTextfield);
		enterMemberListButton = new JButton("Add Member");
		
        enterinfoPanel.setBorder(new TitledBorder(new EtchedBorder(),""));
        enterinfoPanel.setLayout(new GridLayout(2,1));
        enterinfoPanel.add(userInfoPanel);
        enterinfoPanel.add(contactMemberPanel);
		
		ActionListener addMemberListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				String firstName = firstNameTextField.getText();
				String lastName = lastNameTextField.getText();
				String emailAddr = emailAddTextfield.getText();
				
				if(firstName.isEmpty()||lastName.isEmpty()||emailAddr.isEmpty()){
						if(firstName.isEmpty())
							JOptionPane.showMessageDialog(null, "Enter First Name");
						else if(lastName.isEmpty())
							JOptionPane.showMessageDialog(null, "Enter Last Name");
						else if(emailAddr.isEmpty())
							JOptionPane.showMessageDialog(null, "Enter Email Address");
					}
				else {
					Member newMember = new Member(firstName, lastName, emailAddr);
					mMgr.addMember(newMember);
					System.out.println("welcome");
	                refreshView();
					}
					
			}
		};

		enterMemberListButton.addActionListener(addMemberListener);
		cancelMemberListButton = new JButton("Cancel");
		cancelMemberListButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				firstNameTextField.setText(null);
				lastNameTextField.setText(null);
				emailAddTextfield.setText(null);
			}
		});
		
		userInfoPanel.add(enterMemberListButton);
		userInfoPanel.add(cancelMemberListButton);
		
		contactMemberPanel.setBorder(new TitledBorder(new EtchedBorder(), "Message"));
		sendTextButton = new JButton("Send");
		
		sendTextArea = new JTextArea();
	    contactMemberPanel.setLayout(new BorderLayout());
		JScrollPane sendTextScrollPane = new JScrollPane(sendTextArea);
	    contactMemberPanel.add("Center",sendTextScrollPane);
		contactMemberPanel.add("South",sendTextButton);
		sendTextArea.getBorder();
		
		sendTextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				instructorMessage = sendTextArea.getText();
				System.out.println(instructorMessage);
			}
		});
		

		return returnPanel;
	}

	private JPanel memberPanel()
	{
		JPanel returnPanel = new JPanel();
		JPanel subPanel = new JPanel();
		JPanel interfacePanel = new JPanel();
		JPanel interfaceSubPanelOne = new JPanel();
		JPanel interfaceSubPanelTwo = new JPanel();
		JPanel interfaceSubPanelThree = new JPanel();
		JPanel memberListPanel = new JPanel();
		JPanel memberListAttendancePanel = new JPanel();
        memberListAttendancePanel.setBorder(new TitledBorder(new EtchedBorder(), "Attendance"));
		JPanel memberListAddPanel = new JPanel();
		memberListAddPanel.setBorder(new TitledBorder(new EtchedBorder(), "Add Member"));
		
		JLabel revenueLabel, expenseLabel;
		JTextField revenueField, expenseField;
		
		returnPanel.setBorder(new TitledBorder(new EtchedBorder(), "MEMBER PANEL"));

		JButton contactCoachButton = new JButton("Contact The Coach");

		returnPanel.setBorder(new TitledBorder(new EtchedBorder(), "MEMBER PANEL"));

        revenueLabel = new JLabel("MEMBER :");
        revenueField = new JTextField(5);
        expenseLabel = new JLabel("THINGS :");
        expenseField = new JTextField(5);

        interfaceSubPanelOne.add(revenueLabel);
        interfaceSubPanelOne.add(revenueField);

        interfaceSubPanelTwo.add(expenseLabel);
        interfaceSubPanelTwo.add(expenseField);

        interfaceSubPanelThree.add(contactCoachButton);

        interfacePanel.setLayout(new GridLayout(3, 1));

        interfacePanel.setBorder(new TitledBorder(new EtchedBorder(), "USER INTERFACE"));

        Session session = this.sMgr.findSessionByDate(this.currentDate);
        JTable attendanceTable = null;

        if (session != null) {
            attendanceTable = new JTable(new AttendanceRecordTableModel(this.aMgr.getAttendanceArrayBySessionId(session.getId()), this.mMgr));
        } else {
            attendanceTable = new JTable(new AttendanceRecordTableModel(new AttendanceRecord[0], this.mMgr));
        }

        JScrollPane memberListScrollPane = new JScrollPane(attendanceTable);
        memberListAttendancePanel.add(new JScrollPane(attendanceTable));

        // set column widths
        int[] widths = {90, 90, 100, 30};
        for (int i = 0; i < attendanceTable.getColumnCount(); i++) {
            attendanceTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        JScrollPane memberAddScrollPane = new JScrollPane();
        memberListScrollPane.setPreferredSize(new Dimension(250, 200));

        //Making the combo box
        JComboBox<Member> comboBox = new JComboBox<Member>(mMgr.getArray());
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(comboBox);

        // Paid checkbox
        JCheckBox paidBox = new JCheckBox("PAID");
        JPanel paidBoxPanel = new JPanel();
        paidBoxPanel.add(paidBox);

        // add member button
		JButton addMember = new JButton("Add Member");
		
		
		//action listener to add members for attendance
		addMember.addActionListener(new ActionListener()
		{


			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (paidBox.isSelected())
				{
					Member tempMember = (Member) comboBox.getSelectedItem();
					System.out.println(tempMember.getId());
					aMgr.addMemberToSession(tempMember.getId(), session.getId(), true);
					refreshView();

				}
				else
				{
					Member tempMember = (Member) comboBox.getSelectedItem();
					aMgr.addMemberToSession(tempMember.getId(), session.getId(), false);
					refreshView();
				}
			}
		});
		JPanel addMemberPanel = new JPanel();
		addMemberPanel.add(addMember);

        // Adding the components to the add member panel on a 3x1 grid
        memberListAddPanel.setLayout(new GridLayout(3,1));
        if (session != null) {
            memberListAddPanel.add(comboBoxPanel);
            memberListAddPanel.add(paidBoxPanel);
            memberListAddPanel.add(addMemberPanel);
        } else {
            memberListAddPanel.add(new JLabel("No Class Today"));
        }

        //second half panel(right side) of member panel
        memberListPanel.setLayout(new GridLayout(1,1));
        memberListPanel.add(memberListAttendancePanel);
		//memberListPanel.add(memberListAddPanel);
		
        // Subpanel, the right half of member panel
        subPanel.setLayout(new GridLayout(2, 1));
        //adding calendar
        subPanel.add(this.calendarPanel);
        subPanel.add(memberListAddPanel);

		//final panel with left and right half on a 2x1 grid
		returnPanel.setLayout(new GridLayout(1,2));
		returnPanel.add(subPanel);
		returnPanel.add(memberListPanel);

		return returnPanel;
	}

	class addMemeberPress implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
		}
	}
	
    private static Calendar getCurrentDate()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private MemberStatus[] getMemberStatus() {

        HashMap<UUID, MemberStatus> statusMap = new HashMap<UUID, MemberStatus>();

        Member[] members = this.mMgr.getArray();
        Member m;
        AttendanceRecord[] records = this.aMgr.getAllAttendanceRecordsArray();

        for (int i = 0; i < members.length; i++) {
            m = members[i];
            statusMap.put(m.getId(), new MemberStatus(m.getId(), m.getFirstName(), m.getLastName(), m.getEmailAddr(), m.getActive(), 0, 0));
        }

        for (int i = 0; i < records.length; i++) {
            statusMap.get(records[i].getMemberId()).attended++;
            if (records[i].getPaid()) {
                statusMap.get(records[i].getMemberId()).paid++;
            }
        }

        return statusMap.values().toArray(new MemberStatus[0]);
    }

}
