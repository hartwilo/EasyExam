package GUI;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Startseite extends JFrame{
	public Startseite() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{430, 0, 459, 0};
		gridBagLayout.rowHeights = new int[]{173, 95, 0, 95, 0, 95, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		getContentPane().setBackground(new Color(153, 204, 204));
		
		JButton btnFragekatalog = new JButton("Fragekatalog erstellen");
		GridBagConstraints gbc_btnFragekatalog = new GridBagConstraints();
		gbc_btnFragekatalog.insets = new Insets(0, 5, 5, 5);
		gbc_btnFragekatalog.gridx = 1;
		gbc_btnFragekatalog.gridy = 1;
		getContentPane().add(btnFragekatalog, gbc_btnFragekatalog);
		
		JButton btnPruefung = new JButton("Prüfung starten");
		btnPruefung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnPruefung = new GridBagConstraints();
		gbc_btnPruefung.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPruefung.insets = new Insets(0, 5, 5, 5);
		gbc_btnPruefung.gridx = 1;
		gbc_btnPruefung.gridy = 3;
		getContentPane().add(btnPruefung, gbc_btnPruefung);
		
		JButton btnStatistik = new JButton("Statistik ansehen");
		GridBagConstraints gbc_btnStatistik = new GridBagConstraints();
		gbc_btnStatistik.insets = new Insets(0, 0, 0, 5);
		gbc_btnPruefung.insets = new Insets(0, 5, 5, 0);
		gbc_btnStatistik.gridx = 1;
		gbc_btnStatistik.gridy = 5;
		getContentPane().add(btnStatistik, gbc_btnStatistik);
	}

}
