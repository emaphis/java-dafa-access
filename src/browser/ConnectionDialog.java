package browser;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ConnectionDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -542463775754628216L;
	private JTextField useridField;
	private JTextField passwordField;
	private JTextField urlField;
	
	//private boolean canceled;
	private Connection connect;
	
	public ConnectionDialog(JFrame f) {
		super(f, "Connect To Database", true);
		buildDialogLayout();
		setSize(300, 200);
	}
	
	public Connection getConnection() {
		setVisible(true);
		return connect;
	}
	
	private void buildDialogLayout() {
		JLabel label;
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 10, 5, 10);
		 
		constraints.gridx = 0;
		constraints.gridy = 0;
		label = new JLabel("Userid:", JLabel.LEFT);
		pane.add(label, constraints);
		 
		constraints.gridy++;
		label = new JLabel("Password:", JLabel.LEFT);
		pane.add(label, constraints);
		 
		constraints.gridy++;
		label = new JLabel("URL:", JLabel.LEFT);
		pane.add(label, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		 
		useridField = new JTextField(10);
		pane.add(useridField, constraints);
		 
		constraints.gridy++;
		passwordField = new JTextField(10);
		pane.add(passwordField, constraints);
		 
		constraints.gridy++;
		urlField = new JTextField(15);
		pane.add(urlField, constraints);
		 
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.anchor = GridBagConstraints.CENTER;
		pane.add(getButtonPanel(), constraints);
	}
	
	private JPanel getButtonPanel() {
		JPanel panel = new JPanel();
		JButton btn = new JButton("Ok");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				onDialogOk();
			}
		});
		panel.add(btn);
		btn = new JButton("Cancel");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				onDialogCancel();
			}
		});
		panel.add(btn);
		return panel;
	}
	
	private void onDialogOk() {
		if (attemptConnection()) {
			setVisible(false);
		}
	}
	
	private void onDialogCancel() {
		System.exit(0);
	}
	
	private boolean attemptConnection() {
		try {
			connect = DriverManager.getConnection(
					urlField.getText(),
					useridField.getText(),
					passwordField.getText());
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, 
					"Error connection to " +
			         "database: " + e.getMessage());
		}
		return false;
	}
}
