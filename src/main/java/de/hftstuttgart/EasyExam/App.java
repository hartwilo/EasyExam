package de.hftstuttgart.EasyExam;

import javax.swing.JFrame;

import GUI.LoginFrame;

public class App 
{
    public static void main( String[] args )
    {
    	
    	DB.DBConn.buildConn();
    	
        LoginFrame frame = new LoginFrame();
        frame.setVisible(true);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setTitle("EasyExam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
    }
}
