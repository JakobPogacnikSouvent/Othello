package gui;

import javax.swing.*;

import inteligenca.Inteligenca;
import logic.Game;
import logic.Stone;

import java.awt.Color;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener {
	
	protected Platno platno;

	private JLabel status;
	private JLabel rezPrimary, rezSecondary;
	
	private JMenuItem menuBarvaIgralecEna, menuBarvaIgralecDve, menuBarvaPolja;
	
	public GameWindow(Inteligenca ai, Stone aiColour) {
		super();
		setTitle("Othello 2");
		
		// Ureditev orodne vrstice
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu menuNastavitve = dodajMenu(menubar, "Nastavitve");
		
		menuBarvaIgralecEna = dodajItem(menuNastavitve, "Nastavi barvo prvega igralca.");
		menuBarvaIgralecDve = dodajItem(menuNastavitve, "Nastavi barvo drugega igralca.");
		menuBarvaPolja = dodajItem(menuNastavitve, "Nastavi barvo polj.");
		
		// Glavna ploÅ¡Ä�a
		JPanel glavnaPlosca = new JPanel();
		glavnaPlosca.setLayout(new BoxLayout(glavnaPlosca, BoxLayout.Y_AXIS));
		this.add(glavnaPlosca);
		
		//===========================================================================
		
		// JPanel, ki je nad igralnim poljem
		JPanel strop = new JPanel();
		
		// Sporočilo na stropu
		status = new JLabel();
		strop.add(status);
		
		glavnaPlosca.add(strop);
		
		//===========================================================================
		
		// Igralno polje
		platno = new Platno(600, 600);
		glavnaPlosca.add(platno);
		
		if (ai != null) {
			platno.setGame(new Game(ai, aiColour, true));			
		} else {
			platno.setGame(new Game());
		}
		
		//===========================================================================
		
		// JPanel, ki je pod igralnim poljem
		JPanel temelj = new JPanel();
		
		// Števec točk
		rezPrimary = new JLabel();
		rezPrimary.setBackground(platno.squareColourPrimary);
		rezPrimary.setForeground(Color.BLACK);
		rezPrimary.setOpaque(true);
		rezSecondary = new JLabel();
		rezSecondary.setBackground(platno.squareColourSecondary);
		rezSecondary.setForeground(Color.WHITE);
		rezSecondary.setOpaque(true);
		temelj.add(rezPrimary);
		temelj.add(rezSecondary);
		
		glavnaPlosca.add(temelj);
		
		status.setText("Pozdravljeni!");
		rezPrimary.setText(String.valueOf(platno.game.blackScore));
		rezSecondary.setText(String.valueOf(platno.game.whiteScore));
		
		platno.repaint();
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
	
	public static void updateMessages() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == menuBarvaIgralecEna) {
			Color izbira = JColorChooser.showDialog(this, "Barva kamenčkov prvega igralca", platno.figureColourBlack);
			if (izbira == null) return;
			platno.figureColourBlack = izbira;
			platno.repaint();
		}
		else if (source == menuBarvaIgralecDve) {
			Color izbira = JColorChooser.showDialog(this, "Barva kamenčkov drugega igralca", platno.figureColourWhite);
			if (izbira == null) return;
			platno.figureColourWhite = izbira;
			platno.repaint();
		}
		else if (source == menuBarvaPolja) {
			Color izbira = JColorChooser.showDialog(this, "Barva igralnih polj", platno.squareColourPrimary);
			if (izbira == null) return;
			platno.squareColourPrimary = izbira;
			Color darker = izbira.darker();
			platno.squareColourSecondary = darker;
			platno.repaint();
		}
	}
	
}