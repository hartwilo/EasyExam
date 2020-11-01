/**
 * 
 */
package GUI;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.*;
import org.junit.jupiter.api.Test;

/**
 * @author bachir
 *
 */
class LoginFrameTest {

	/**
	 * Test method for tryLogin
	 * 
	 */
	@Test
	void testUser_Password() {
//		cut=class under test 
		LoginFrame cut = new LoginFrame();

		String password = "Ba12345";
		String user ="User";
		boolean output = cut.tryLogin( user,password);
		assertEquals(output,true);

	}

	@Test
	void testEmpty_User_Password() {
//		cut=class under test 
		LoginFrame cut = new LoginFrame();

		String password ="";
		String user ="";
		boolean output = cut.tryLogin(user,password);
		assertEquals(output,false);

	}
	@Test
	void testEmpty_User() {
//		cut=class under test 
		LoginFrame cut = new LoginFrame();
		String password ="Ba12345";
		String user ="";
		boolean output = cut.tryLogin(user,password);
		assertEquals(output,false);

	}
	@Test
	void testEmpty_Password() {
//		cut=class under test 
		LoginFrame cut = new LoginFrame();
		String password ="";
		String user ="User";
		boolean output = cut.tryLogin(user,password);
		assertEquals(output,false);
	}
	@Test
	void testWrong_PassWord_User() {
//		cut=class under test 
		LoginFrame cut = new LoginFrame();
		String password ="ba12";
		String user ="asdff";
		boolean output = cut.tryLogin(user,password);
		assertEquals(output,false);
}
}
