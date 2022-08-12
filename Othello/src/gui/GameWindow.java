package gui;

import javax.swing.*;

import inteligenca.Inteligenca;
import logic.Game;
import logic.GameStatus;
import logic.Stone;

import java.awt.Color;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener {	
	private JLabel rez;
	
	protected Platno platno;

	private JLabel status;
	
	private JMenuItem menuBarvaIgralecEna, menuBarvaIgralecDve, menuBarvaPolja;

	
	Timer timer;
	
	public void changeScore(int scoreWhite, int scoreBlack) {
		Color whiteClr = platno.getFigureColourWhite();
		Color blackClr = platno.getFigureColourBlack();
		
		rez.setText("<html><font color='rgb(" + whiteClr.getRed() + ", " + whiteClr.getGreen() + ", " + whiteClr.getBlue() + ")'>⬤</font> " + scoreWhite +
				" || " + scoreBlack + " <font color='rgb(" + blackClr.getRed() + ", " + blackClr.getGreen() + ", " + blackClr.getBlue() + ")'>⬤</font></html>");
	}
	
	public GameWindow(Inteligenca ai, Stone aiColour) {
		super();
		setTitle("Othello 1.3");
		
		// Ureditev orodne vrstice
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu menuNastavitve = dodajMenu(menubar, "Nastavitve");
		
		menuBarvaIgralecEna = dodajItem(menuNastavitve, "Nastavi barvo prvega igralca.");
		menuBarvaIgralecDve = dodajItem(menuNastavitve, "Nastavi barvo drugega igralca.");
		menuBarvaPolja = dodajItem(menuNastavitve, "Nastavi barvo polj.");
		
		// Glavna plošča
		JPanel glavnaPlosca = new JPanel();
		glavnaPlosca.setLayout(new BoxLayout(glavnaPlosca, BoxLayout.Y_AXIS));
		this.add(glavnaPlosca);
		
		//===========================================================================
		
		// JPanel, ki je nad igralnim poljem
		JPanel strop = new JPanel();
		
		// Sporočilo na stropu
		status = new JLabel(); //TODO
		strop.add(status);
		
		glavnaPlosca.add(strop);
		
		//===========================================================================

		
		// Igralno polje
		platno = new Platno(800, 800, this);
		glavnaPlosca.add(platno);
		
		if (ai != null) {
			platno.setGame(new Game(ai, aiColour, true));			
		} else {
			platno.setGame(new Game());
		}
		
		// JPanel, ki je pod igralnim poljem
		JPanel temelj = new JPanel();
		
		// Števec točk
		rez = new JLabel();
		temelj.add(rez);
		
		glavnaPlosca.add(temelj);
		
		platno.repaint();
		
		timer = new Timer(1000, this);
		timer.start();
		
		update();
	}
	
	public void update() {
		platno.repaint();
		int whiteScore = platno.getGame().getWhiteScore();
		int blackScore = platno.getGame().getBlackScore();
		
		changeScore(whiteScore, blackScore);
		
		Color activeColor = null;
				
		switch (platno.getGame().getActivePlayer()) {
		case WHITE:
			activeColor = platno.getFigureColourWhite();
			break;
		case BLACK:
			activeColor = platno.getFigureColourBlack();
			break;
		case EMPTY:
		default:
			break;
		}
		
		status.setText("<html><font color='rgb(" + activeColor.getRed() + ", " + activeColor.getGreen() + ", " + activeColor.getBlue() + ")'>⬤</font> to move</html>");
		
		
		if (platno.getGame().getStatus() == GameStatus.FINISHED) {
	    	timer.stop();
			
			String[] options = new String[] {"Play again!", "Main menu!", "Exit"};
		    
			String winningText = "";
			Color winClr = null;
			
			switch (platno.getGame().getWinner()) {
			case WHITE:
				winClr = platno.getFigureColourWhite();
				winningText = "<html>Final score is: " + whiteScore + " to " + blackScore + " <font color='rgb(" + winClr.getRed() + ", " + winClr.getGreen() + ", " + winClr.getBlue() + ")'>⬤</font> wins!</html>";
				break;
			case BLACK:
				winClr = platno.getFigureColourBlack();
				winningText = "<html>Final score is: " + whiteScore + " to " + blackScore + " <font color='rgb(" + winClr.getRed() + ", " + winClr.getGreen() + ", " + winClr.getBlue() + ")'>⬤</font> wins!</html>";
				break;
			case EMPTY:
				winningText = "Final score is: " + whiteScore + " to " + blackScore + "\nGame ended in a TIE!";
				break;
			}
			
			int playerChoice = JOptionPane.showOptionDialog(null, winningText, "Game finished", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		      
		    switch (playerChoice) {
		    case 0 :
		    	platno.getGame().reset();
		    	timer.restart();
		    	update();
		    	break;
		    case 1:
		    	dispose();
		    	break;
		    case 2:
		    	System.exit(0);
		    	break;
		    }
		}
	}
	
	private JMenu dodajMenu(JMenuBar menubar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menubar.add(menu);
		return menu;
	}

	private JMenuItem dodajItem(JMenu menu, String naslov) {
		JMenuItem menuItem = new JMenuItem(naslov);
		menu.add(menuItem);
		menuItem.addActionListener(this);
		return menuItem;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == timer) {
			update();
		} else if (source == menuBarvaIgralecEna) {
			Color izbira = JColorChooser.showDialog(this, "Barva kamenčkov prvega igralca", platno.getFigureColourBlack());
			if (izbira == null) return;
			setFigureColourBlack(izbira);
		} else if (source == menuBarvaIgralecDve) {
			Color izbira = JColorChooser.showDialog(this, "Barva kamenčkov drugega igralca", platno.getFigureColourWhite());
			if (izbira == null) return;
			setFigureColourWhite(izbira);
		} else if (source == menuBarvaPolja) {
			Color izbira = JColorChooser.showDialog(this, "Barva igralnih polj", platno.getSquareColourPrimary());
			if (izbira == null) return;
			setSquareColourPrimary(izbira);
			Color darker = izbira.darker();
			setSquareColourSecondary(darker);
		}
	}

	private void setSquareColourSecondary(Color darker) {
		platno.setSquareColourSecondary(darker);
		platno.repaint();
	}

	private void setSquareColourPrimary(Color izbira) {
		platno.setSquareColourPrimary(izbira);
		platno.repaint();		
	}

	private void setFigureColourWhite(Color izbira) {
		platno.setFigureColourWhite(izbira);
		platno.repaint();		
	}

	public void setFigureColourBlack(Color izbira) {
		platno.setFigureColourBlack(izbira);
		platno.repaint();		
	}

	public void setColours(Color figureColourBlack, Color figureColourWhite, Color squareColourPrimary, Color squareColourSecondary) {
		setFigureColourBlack(figureColourBlack);
		setFigureColourWhite(figureColourWhite);
		setSquareColourPrimary(squareColourPrimary);
		setSquareColourSecondary(squareColourSecondary);
		
		
	}
	
}