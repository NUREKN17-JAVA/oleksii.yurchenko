package ua.nure.cs.yurchenko.usermanagement.domain.gui;

import java.awt.Component;
import java.awt.Window;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.cs.yurchenko.usermanagement.domain.User;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DaoFactory;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DaoFactoryImpl;
import ua.nure.cs.yurchenko.usermanagement.domain.db.MockDaoFactory;
import ua.nure.cs.yurchenko.usermanagement.domain.db.MockUserDao;
import com.mockobjects.dynamic.Mock;

public class MainFrameTest extends JFCTestCase {

	private Window mainFrame;

	private Mock mockUserDao;
	protected void setUp() throws Exception {
			super.setUp();
			
			try {
				Properties properties = new Properties();
				//properties.setProperty("ua.nure.cs.yurchenko.usermanagement.domain.db.UserDao", 
				//					MockUserDao.class.getName());
				properties.setProperty("dao.factory", MockDaoFactory.class.getName());
				DaoFactory.getInstance().init(properties);
				mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
				mockUserDao.expectAndReturn("findAll", new ArrayList());
				setHelper(new JFCTestHelper());
				mainFrame = new MainFrame();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mainFrame.setVisible(true);
	}

	protected void tearDown() throws Exception {
			try {
				mockUserDao.verify();
				mainFrame.setVisible(false);
				getHelper().cleanUp(this);
				super.tearDown();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	private Component find (Class componentClass, String name) {
			NamedComponentFinder finder;
			finder = new NamedComponentFinder(componentClass, name);
			finder.setWait(0);
			Component component = finder.find(mainFrame, 0);
			assertNotNull("Could not find component '" + name + "'", component);
			return component;
	}
	
	public void testBrowseControls() {
			find(JPanel.class, "browsePanel");
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(3, table.getColumnCount());
			assertEquals("ID", table.getColumnName(0));
			assertEquals("Name", table.getColumnName(1));
			assertEquals("Surname", table.getColumnName(2));
			
			find(JButton.class, "addButton");
			find(JButton.class, "editButton");
			find(JButton.class, "deleteButton");
			find(JButton.class, "detailsButton");
	}
	
	public void testAddUser() {
			String lastName = "Doe";
			String firstName = "John";
			Date now = new Date();
			
			User user = new User(firstName, lastName, now);
			
			User expectedUser = new User(new Long(1), firstName, lastName, now);
			mockUserDao.expectAndRun("create", user, expectedUser);
			
			ArrayList users = new ArrayList();
			mockUserDao.expectAndRun("findAll", users);
			
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
			
			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			
			find(JPanel.class, "addPanel");
			
			
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			JButton okButton = (JButton) find(JButton.class, "okButton");
			find(JButton.class, "cancelButton");
			
			getHelper().sendString(new StringEventData(this, firstNameField, firstName));
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
			DateFormat formatter = DateFormat.getDateInstance();
			String date = formatter.format(now);
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
	}
	
	public void testEditUser() { 
			String lastName = "Doe";
			String firstName = "John";
			Date now = new Date();
			
			User user = new User(firstName, lastName, now);
			
			
			find(JPanel.class, "browsePanel");

            User expectedUser = new User(new Long(2), "Michael", "Rush", new Date());
            mockUserDao.expect("update", expectedUser);
            List users = new ArrayList();
            users.add(user);
            users.add(expectedUser);

            mockUserDao.expectAndReturn("findAll", users);

            JTable userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(1, userTable.getRowCount());
            JButton editButton = (JButton) find(JButton.class, "editButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, "editPanel");
            JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
            getHelper().sendString(new StringEventData(this, firstNameField, "1"));
            getHelper().sendString(new StringEventData(this, lastNameField, "1"));

            JButton okButton = (JButton) find(JButton.class, "okButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
            

            find(JPanel.class, "browsePanel");
            userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(2, userTable.getRowCount());
    }
	
}