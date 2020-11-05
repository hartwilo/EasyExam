package GUI;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;

public class Fragekatalog extends JFrame{
	private JTextField tfPunktzahl;
	public Fragekatalog() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{416, 0, 251, 266, 204, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 222, 0, 0, 222, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblFragestellungEingeben = new JLabel("Fragestellung eingeben:");
		GridBagConstraints gbc_lblFragestellungEingeben = new GridBagConstraints();
		gbc_lblFragestellungEingeben.insets = new Insets(0, 0, 5, 5);
		gbc_lblFragestellungEingeben.gridx = 0;
		gbc_lblFragestellungEingeben.gridy = 1;
		getContentPane().add(lblFragestellungEingeben, gbc_lblFragestellungEingeben);
		
		JComboBox cbThemengebiete = new JComboBox();
		cbThemengebiete.setToolTipText("");
		GridBagConstraints gbc_cbThemengebiete = new GridBagConstraints();
		gbc_cbThemengebiete.insets = new Insets(0, 0, 5, 5);
		gbc_cbThemengebiete.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbThemengebiete.gridx = 2;
		gbc_cbThemengebiete.gridy = 1;
		getContentPane().add(cbThemengebiete, gbc_cbThemengebiete);
		
		JButton button = new JButton("+");
		button.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.WEST;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 3;
		gbc_button.gridy = 1;
		getContentPane().add(button, gbc_button);
		
		JTextArea taFragestellung = new JTextArea();
		GridBagConstraints gbc_taFragestellung = new GridBagConstraints();
		gbc_taFragestellung.insets = new Insets(0, 0, 5, 5);
		gbc_taFragestellung.fill = GridBagConstraints.BOTH;
		gbc_taFragestellung.gridx = 0;
		gbc_taFragestellung.gridy = 2;
		getContentPane().add(taFragestellung, gbc_taFragestellung);
		
		JRadioButton rdbtnNiveau = new JRadioButton("Niveau 1");
		GridBagConstraints gbc_rdbtnNiveau = new GridBagConstraints();
		gbc_rdbtnNiveau.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNiveau.gridx = 2;
		gbc_rdbtnNiveau.gridy = 3;
		getContentPane().add(rdbtnNiveau, gbc_rdbtnNiveau);
		
		JRadioButton rdbtnNiveau_1 = new JRadioButton("Niveau 2");
		GridBagConstraints gbc_rdbtnNiveau_1 = new GridBagConstraints();
		gbc_rdbtnNiveau_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNiveau_1.gridx = 3;
		gbc_rdbtnNiveau_1.gridy = 3;
		getContentPane().add(rdbtnNiveau_1, gbc_rdbtnNiveau_1);
		
		JRadioButton rdbtnNiveau_2 = new JRadioButton("Niveau 3");
		GridBagConstraints gbc_rdbtnNiveau_2 = new GridBagConstraints();
		gbc_rdbtnNiveau_2.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNiveau_2.gridx = 4;
		gbc_rdbtnNiveau_2.gridy = 3;
		getContentPane().add(rdbtnNiveau_2, gbc_rdbtnNiveau_2);
		
		JLabel lblMusterloesung = new JLabel("Musterlösung:");
		GridBagConstraints gbc_lblMusterloesung = new GridBagConstraints();
		gbc_lblMusterloesung.insets = new Insets(0, 0, 5, 5);
		gbc_lblMusterloesung.gridx = 0;
		gbc_lblMusterloesung.gridy = 4;
		getContentPane().add(lblMusterloesung, gbc_lblMusterloesung);
		
		JTextArea taMusterloesung = new JTextArea();
		GridBagConstraints gbc_taMusterloesung = new GridBagConstraints();
		gbc_taMusterloesung.insets = new Insets(0, 0, 5, 5);
		gbc_taMusterloesung.fill = GridBagConstraints.BOTH;
		gbc_taMusterloesung.gridx = 0;
		gbc_taMusterloesung.gridy = 5;
		getContentPane().add(taMusterloesung, gbc_taMusterloesung);
		
		JLabel lblPunktzahl = new JLabel("Punktzahl");
		GridBagConstraints gbc_lblPunktzahl = new GridBagConstraints();
		gbc_lblPunktzahl.anchor = GridBagConstraints.EAST;
		gbc_lblPunktzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblPunktzahl.gridx = 2;
		gbc_lblPunktzahl.gridy = 6;
		getContentPane().add(lblPunktzahl, gbc_lblPunktzahl);
		
		tfPunktzahl = new JTextField();
		GridBagConstraints gbc_tfPunktzahl = new GridBagConstraints();
		gbc_tfPunktzahl.insets = new Insets(0, 0, 5, 5);
		gbc_tfPunktzahl.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPunktzahl.gridx = 3;
		gbc_tfPunktzahl.gridy = 6;
		getContentPane().add(tfPunktzahl, gbc_tfPunktzahl);
		tfPunktzahl.setColumns(10);
	}
}
