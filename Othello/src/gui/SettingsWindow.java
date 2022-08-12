package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SettingsWindow extends JFrame implements ActionListener, WindowListener{

	JButton menuBarvaIgralecEna, menuBarvaIgralecDve, menuBarvaPolja, exit;
	GameWindow gameWindow;
	StartScreen parent;
	
	JLabel title;
	
	public SettingsWindow(StartScreen parent) {
		super();
		setTitle("Othello 1.3");
			
		this.parent = parent;
		
		JPanel glavnejsaPlosca = new JPanel();
		glavnejsaPlosca.setLayout(new BoxLayout(glavnejsaPlosca, BoxLayout.Y_AXIS));
		this.add(glavnejsaPlosca);
		
		// Glavna plošča
		JPanel glavnaPlosca1 = new JPanel();
		glavnaPlosca1.setLayout(new GridBagLayout());
		glavnejsaPlosca.add(glavnaPlosca1);
		
		title = new JLabel("Settings");
		title.setFont(new Font(title.getName(), Font.PLAIN, 50));
		glavnaPlosca1.add(title);
		
		// Glavna plošča
		JPanel glavnaPlosca2 = new JPanel();
		glavnaPlosca2.setLayout(new BoxLayout(glavnaPlosca2, BoxLayout.Y_AXIS));
		glavnejsaPlosca.add(glavnaPlosca2);
		
		JPanel orodjarna = new JPanel();
		orodjarna.setLayout(new FlowLayout());
		
		menuBarvaIgralecEna = new JButton("Barva prvega igralca.");
		menuBarvaIgralecEna.addActionListener(this);
		orodjarna.add(menuBarvaIgralecEna);
		
		menuBarvaIgralecDve = new JButton("Barva drugega igralca.");
		menuBarvaIgralecDve.addActionListener(this);
		orodjarna.add(menuBarvaIgralecDve);
		
		menuBarvaPolja = new JButton("Barva igralnega polja.");
		menuBarvaPolja.addActionListener(this);
		orodjarna.add(menuBarvaPolja);
				
		exit = new JButton("Back");
		exit.addActionListener(this);
		orodjarna.add(exit);
		
		glavnaPlosca2.add(orodjarna);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == menuBarvaIgralecEna) {
			Color izbira = JColorChooser.showDialog(this, "Barva kamenčkov prvega igralca", parent.figureColourBlack);
			if (izbira == null) return;
			parent.figureColourBlack = izbira;
		} else if (source == menuBarvaIgralecDve) {
			Color izbira = JColorChooser.showDialog(this, "Barva kamenčkov drugega igralca", parent.figureColourWhite);
			if (izbira == null) return;
			parent.figureColourWhite = izbira;
		} else if (source == menuBarvaPolja) {
			Color izbira = JColorChooser.showDialog(this, "Barva igralnih polj", parent.squareColourPrimary);
			if (izbira == null) return;
			parent.squareColourPrimary = izbira;
			Color darker = izbira.darker();
			parent.squareColourSecondary = darker;
		} else if (source == exit) {
			dispose();
		}

	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == gameWindow) {
			this.setVisible(true);			
		}
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
