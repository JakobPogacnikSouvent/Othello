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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import inteligenca.Inteligenca;
import logic.Stone;

@SuppressWarnings("serial")
public class StartScreen extends JFrame implements ActionListener, WindowListener{

	JButton playAgainstCPU, playAgainstPlayer, exit, settings;
	GameWindow gameWindow;
	SettingsWindow settingsWindow;
	
	JLabel title;
	
	public Color figureColourBlack, figureColourWhite, squareColourPrimary, squareColourSecondary;
	
	public StartScreen() {
		super();
		setTitle("Othello 1.3");
		
		figureColourBlack = Platno.defaultFigureColourBlack();
		figureColourWhite = Platno.defaultFigureColourWhite();
		squareColourPrimary = Platno.defaultSquareColourPrimary();
		squareColourSecondary = Platno.defaultSquareColourSecondary();
		
		JPanel glavnejsaPlosca = new JPanel();
		glavnejsaPlosca.setLayout(new BoxLayout(glavnejsaPlosca, BoxLayout.Y_AXIS));
		this.add(glavnejsaPlosca);
		
		// Glavna plošča
		JPanel glavnaPlosca1 = new JPanel();
		glavnaPlosca1.setLayout(new GridBagLayout());
		glavnejsaPlosca.add(glavnaPlosca1);
		
		title = new JLabel("Othello");
		title.setFont(new Font(title.getName(), Font.PLAIN, 100));
		glavnaPlosca1.add(title);
		
		// Glavna plošča
		JPanel glavnaPlosca2 = new JPanel();
		glavnaPlosca2.setLayout(new BoxLayout(glavnaPlosca2, BoxLayout.Y_AXIS));
		glavnejsaPlosca.add(glavnaPlosca2);
		
		JPanel orodjarna = new JPanel();
		orodjarna.setLayout(new FlowLayout());
		
		playAgainstCPU = new JButton("VS CPU");
		playAgainstCPU.addActionListener(this);
		orodjarna.add(playAgainstCPU);
		
		playAgainstPlayer = new JButton("VS Player");
		playAgainstPlayer.addActionListener(this);
		orodjarna.add(playAgainstPlayer);
		
		settings = new JButton("Settings");
		settings.addActionListener(this);
		orodjarna.add(settings);
		
		exit = new JButton("Exit");
		exit.addActionListener(this);
		orodjarna.add(exit);
		
		glavnaPlosca2.add(orodjarna);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == playAgainstPlayer) {
			gameWindow = new GameWindow(null, null);
			gameWindow.setColours(figureColourBlack, figureColourWhite, squareColourPrimary, squareColourSecondary);			
			
			gameWindow.pack();
			gameWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			gameWindow.setLocationRelativeTo(null);
			gameWindow.addWindowListener(this);
			gameWindow.setVisible(true);
			
			this.setVisible(false);
		} else if (source == playAgainstCPU) {
			
			Stone aiColour = null;
			String[] options = new String[] {"Black", "White"};
		     
			int playerChoice = JOptionPane.showOptionDialog(null, "Choose your colour:", "Player colour", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		      
		    switch (playerChoice) {
		    case 0 :
		    	aiColour = Stone.WHITE;
		    	break;
		    case 1:
		    	aiColour = Stone.BLACK;
		    	break;
		    }

			
			gameWindow = new GameWindow(new Inteligenca("Carlos"), aiColour);
			gameWindow.setColours(figureColourBlack, figureColourWhite, squareColourPrimary, squareColourSecondary);

			
			gameWindow.pack();
			gameWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			gameWindow.setLocationRelativeTo(null);
			gameWindow.addWindowListener(this);
			gameWindow.setVisible(true);
			
			this.setVisible(false);
		} else if (source == settings) {
			settingsWindow = new SettingsWindow(this);
			settingsWindow.pack();
			settingsWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			settingsWindow.setLocationRelativeTo(null);
			settingsWindow.addWindowListener(this);
			settingsWindow.setVisible(true);
			
			this.setVisible(false);
		} else if (source == exit) {
			System.exit(0);
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
		Object source = e.getSource();
		
		if (source == gameWindow || source == settingsWindow) {
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
