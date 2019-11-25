package ua.nure.cs.yurchenko.usermanagement.domain.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.nure.cs.yurchenko.usermanagement.domain.db.DatabaseException;

public class BrowsePanel extends JPanel implements ActionListener {

	private MainFrame parent;
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton detailsButton;
	private JButton deleteButton;
	private JButton editButton;
	private JScrollPane tablePanel;
	private JTable userTable;

	public BrowsePanel(MainFrame frame) {
		// TODO Auto-generated constructor stub
		parent = frame;
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		this.setName("browsePanel");
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonsPanel() {
		// TODO Auto-generated method stub
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getAddButton(), null);
			buttonPanel.add(getEditButton(), null);
			buttonPanel.add(getDeleteButton(), null);
			buttonPanel.add(getDetailsButton(), null);
		}
		return buttonPanel;
	}

	private JButton getDetailsButton() {
		// TODO Auto-generated method stub
		if (detailsButton == null) {
			detailsButton = new JButton();
			detailsButton.setText("Details");
			detailsButton.setName("detailsButton");
			addButton.setActionCommand("details");
			detailsButton.addActionListener(this);
		}
		return detailsButton;
	}

	private JButton getDeleteButton() {
		// TODO Auto-generated method stub
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete");
			deleteButton.setName("deleteButton");
			addButton.setActionCommand("delete");
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}

	private JButton getEditButton() {
		// TODO Auto-generated method stub
		if (editButton == null) {
			editButton = new JButton();
			editButton.setText("Edit");
			editButton.setName("editButton");
			addButton.setActionCommand("edit");
			editButton.addActionListener(this);
		}
		return editButton;
	}

	private JButton getAddButton() {
		// TODO Auto-generated method stub
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("Add");
			addButton.setName("addButton");
			addButton.setActionCommand("add");
			addButton.addActionListener(this);
		}
		return addButton;
	}

	private JScrollPane getTablePanel() {
		// TODO Auto-generated method stub
		if(tablePanel == null) {
			tablePanel = new JScrollPane(getUserTable());
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		// TODO Auto-generated method stub
		if (userTable == null) {
			userTable = new JTable();
			userTable.setName("userTable");
		}
		//initTable();
		return userTable;
	}

	public void initTable() {
		UserTableModel model;
		try {
			model = new UserTableModel(parent.getDao().findAll());
		} catch (DatabaseException e) {
			model = new UserTableModel(new ArrayList());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		//userTable.setModel(model);
		getUserTable().setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = e.getActionCommand();
		if ("add".equalsIgnoreCase(actionCommand)) {
			this.setVisible(false);
			parent.showAddPanel();
		}
		else if ("edit".equalsIgnoreCase(actionCommand)) {
			this.setVisible(false);
			parent.showEditPanel(userTable.getSelectedRow());
		}
		else if ("details".equalsIgnoreCase(actionCommand)) {
			this.setVisible(false);
			parent.showDetailsPanel(userTable.getSelectedRow());
		}
		else if ("delete".equalsIgnoreCase(actionCommand)) {
			this.setVisible(false);
			parent.showDeletePanel(userTable.getSelectedRow());
		}
	}

}