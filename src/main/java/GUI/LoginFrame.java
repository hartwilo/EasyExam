/**
 * 
 */
package GUI;

/**
 * @author bachir
 *
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window.Type;

public class LoginFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container container = getContentPane();
	JLabel userLabel = new JLabel("USERNAME");
	JLabel passwordLabel = new JLabel("PASSWORD");
	JTextField userTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("LOGIN");
	JButton resetButton = new JButton("RESET");
	JCheckBox showPassword = new JCheckBox("Password zeigen");

	ImageIcon logo = new ImageIcon("logo1.png");
	private final JLabel lblPic = new JLabel("pic");

	public LoginFrame() {
		setType(Type.UTILITY);
		getContentPane().setBackground(new Color(153, 204, 204));
		setTitle("EasyExam");
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		lblPic.setText("");
		lblPic.setIcon(logo);

	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		userLabel.setBounds(33, 149, 100, 30);
		passwordLabel.setBounds(33, 219, 100, 30);
		userTextField.setBounds(132, 150, 150, 30);
		passwordField.setBounds(132, 220, 150, 30);
		showPassword.setBounds(132, 249, 150, 30);
		loginButton.setBounds(50, 364, 100, 30);
		resetButton.setBounds(200, 364, 100, 30);

	}

	public void addComponentsToContainer() {
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(resetButton);
		lblPic.setBounds(132, 10, 150, 130);

		getContentPane().add(lblPic);
	}

	public void addActionEvent() {
		loginButton.addActionListener(this);
		resetButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Coding Part of LOGIN button
		if (e.getSource() == loginButton) {
			String userText;
			String pwdText;
			userText = userTextField.getText();
			pwdText = passwordField.getText();

//			
			boolean isLogin = tryLogin(userText, pwdText);

			if (isLogin) {
				JOptionPane.showMessageDialog(this, "Login ist erfolgreich");
			} else {
				JOptionPane.showMessageDialog(this,
						"falsche Username oder Password,Bitte richtige Username oder Password eingeben");
			}

		}
		// Coding Part of RESET button
		if (e.getSource() == resetButton) {
			userTextField.setText("");
			passwordField.setText("");
		}
		// Coding Part of showPassword JCheckBox
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}

		}

	}

	public static void main(String[] a) {
		LoginFrame frame = new LoginFrame();
		frame.setTitle("EasyExam");
		frame.setVisible(true);
		frame.setBounds(10, 10, 370, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

//      to start the interface in the middle of the screen.
		frame.setLocationRelativeTo(null);

	}

	public boolean tryLogin(String userText, String pwdText) {

		// (equalsIgnoreCase)= when it is not important if the first letter is upper or
		// lower case
		if (userText.equalsIgnoreCase("User") && pwdText.equals("Ba12345")) {
			return true;
		} else {
			return false;
		}
	}
}
