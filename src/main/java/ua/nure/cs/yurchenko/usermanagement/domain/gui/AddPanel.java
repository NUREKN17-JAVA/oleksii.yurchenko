package ua.nure.cs.yurchenko.usermanagement.domain.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.cs.yurchenko.usermanagement.domain.User;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DatabaseException;

public class AddPanel extends JPanel implements ActionListener {
	
	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField dateOfBirthField;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private Color bgColor;
	
	public AddPanel(MainFrame parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		this.setName("addPanel");
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonPanel() {
		// TODO Auto-generated method stub
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel;
	}

	private JButton getCancelButton() {
		// TODO Auto-generated method stub
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.setName("cancelButton");
			cancelButton.setActionCommand("cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		// TODO Auto-generated method stub
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("Ok");
			okButton.setName("okButton");
			okButton.setActionCommand("ok");
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		// TODO Auto-generated method stub
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(2, 3));
			addLabeledField(fieldPanel, "Name", getFirstNameField());
			addLabeledField(fieldPanel, "Surname", getLastNameField());
			addLabeledField(fieldPanel, "Date of birth", getDateOfBirthField());
		}
		return fieldPanel;
	}

	private JTextField getDateOfBirthField() {
		// TODO Auto-generated method stub
		if (dateOfBirthField == null) {
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName("dayOfBirthField");
		}
		return dateOfBirthField;
	}

	private JTextField getLastNameField() {
		// TODO Auto-generated method stub
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField");
		}
		return lastNameField;
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		// TODO Auto-generated method stub
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	private JTextField getFirstNameField() {
		// TODO Auto-generated method stub
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField");
		}
		return firstNameField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("ok".equalsIgnoreCase(e.getActionCommand())) {
			User user = new User();
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			DateFormat format = DateFormat.getDateInstance();
			try {
				user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				getDateOfBirthField().setBackground(Color.RED);
				return;
			}
			try {
				parent.getDao().create(user);
			} catch (DatabaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		clearFields();
		this.setVisible(false);
		parent.showBrowsePanel();
	}

	private void clearFields() {
		// TODO Auto-generated method stub
		getFirstNameField().setText("");
		getFirstNameField().setBackground(bgColor);
		
		getLastNameField().setText("");
		getLastNameField().setBackground(bgColor);
		
		getDateOfBirthField().setText("");
		getDateOfBirthField().setBackground(bgColor);
	}
}