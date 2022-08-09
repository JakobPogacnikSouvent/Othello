package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import inteligenca.Inteligenca;
import logic.Stone;

@SuppressWarnings("serial")
public class StartScreen extends JFrame implements ActionListener, WindowListener{

	JButton playAgainstCPU, playAgainstPlayer, exit;
	GameWindow gameWindow;
	
	public StartScreen() {
		super();
		setTitle("Chess 2");
		
		// Glavna ploÅ¡Ä�a
		JPanel glavnaPlosca = new JPanel();
		glavnaPlosca.setLayout(new BoxLayout(glavnaPlosca, BoxLayout.Y_AXIS));
		this.add(glavnaPlosca);
		
		JPanel orodjarna = new JPanel();
		orodjarna.setLayout(new FlowLayout());
		
		playAgainstCPU = new JButton("VS CPU");
		playAgainstCPU.addActionListener(this);
		orodjarna.add(playAgainstCPU);
		
		playAgainstPlayer = new JButton("VS Player");
		playAgainstPlayer.addActionListener(this);
		orodjarna.add(playAgainstPlayer);
		
		exit = new JButton("Exit");
		exit.addActionListener(this);
		orodjarna.add(exit);
		
		glavnaPlosca.add(orodjarna);
		
		
		
//		JMenuBar menubar = new JMenuBar();
//		setJMenuBar(menubar);
//		
//		JMenu menuDatoteka = dodajMenu(menubar, "Datoteka");
//		JMenu menuGraf = dodajMenu(menubar, "Graf");
//		JMenu menuNastavitve = dodajMenu(menubar, "Nastavitve");
//		
//		menuOdpri = dodajMenuItem(menuDatoteka, "Odpri ...");
//		menuShrani = dodajMenuItem(menuDatoteka, "Shrani ...");
//		menuKoncaj = dodajMenuItem(menuDatoteka, "KonÄ�aj");
//		menuPrazen = dodajMenuItem(menuGraf, "Prazen ...");
//		menuCikel = dodajMenuItem(menuGraf, "Cikel ...");
//		menuPoln = dodajMenuItem(menuGraf, "Poln ...");
//		menuPolnDvodelen = dodajMenuItem(menuGraf, "Poln dvodelen ...");
//		menuBarvaPovezave = dodajMenuItem(menuNastavitve, "Barva povezave ...");
//		menuBarvaTocke = dodajMenuItem(menuNastavitve, "Barva toÄ�ke ...");
//		menuBarvaAktivneTocke = dodajMenuItem(menuNastavitve, "Barva aktivne toÄ�ke ...");
//		menuBarvaIzbraneTocke = dodajMenuItem(menuNastavitve, "Barva izbrane toÄ�ke ...");
//		menuBarvaRoba = dodajMenuItem(menuNastavitve, "Barva roba ...");
//		menuDebelinaRoba = dodajMenuItem(menuNastavitve, "Debelina roba ...");
//		menuDebelinaPovezave = dodajMenuItem(menuNastavitve, "Debelina povezave ...");
//		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == playAgainstPlayer) {
			gameWindow = new GameWindow(null, null);
			gameWindow.pack();
			gameWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			gameWindow.setLocationRelativeTo(null);
			gameWindow.addWindowListener(this);
			gameWindow.setVisible(true);
			
			this.setVisible(false);
		} else if (e.getSource() == playAgainstCPU) {
			
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
			gameWindow.pack();
			gameWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			gameWindow.setLocationRelativeTo(null);
			gameWindow.addWindowListener(this);
			gameWindow.setVisible(true);
			
			this.setVisible(false);
		} else if (e.getSource() == exit) {
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
