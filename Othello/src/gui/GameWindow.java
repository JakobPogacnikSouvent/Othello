package gui;

import javax.swing.*;

import inteligenca.Inteligenca;
import logic.Game;
import logic.Stone;

import java.awt.event.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener {
	
	protected Platno platno;
	private JMenuItem menuOdpri, menuShrani, menuKoncaj;
	private JMenuItem menuPrazen, menuCikel, menuPoln, menuPolnDvodelen;
	private JMenuItem menuBarvaPovezave, menuBarvaTocke, menuBarvaAktivneTocke, menuBarvaIzbraneTocke, menuBarvaRoba;
	private JMenuItem menuDebelinaRoba, menuDebelinaPovezave;
	
	public GameWindow(Inteligenca ai, Stone aiColour) {
		super();
		setTitle("Othello 1.2");
		platno = new Platno(800, 800);
		add(platno);
		
		// Glavna plošča
		JPanel glavnaPlosca = new JPanel();
		glavnaPlosca.setLayout(new BoxLayout(glavnaPlosca, BoxLayout.Y_AXIS));
		this.add(glavnaPlosca);
		
		// Igralno polje
		platno = new Platno(800, 800);
		glavnaPlosca.add(platno);
		
		if (ai != null) {
			platno.setGame(new Game(ai, aiColour, true));			
		} else {
			platno.setGame(new Game());
		}
		
		platno.repaint();
		
//		JMenuBar menubar = new JMenuBar();
//		setJMenuBar(menubar);
//		
//		JMenu menuDatoteka = dodajMenu(menubar, "Datoteka");
//		JMenu menuGraf = dodajMenu(menubar, "Graf");
//		JMenu menuNastavitve = dodajMenu(menubar, "Nastavitve");
//		
//		menuOdpri = dodajMenuItem(menuDatoteka, "Odpri ...");
//		menuShrani = dodajMenuItem(menuDatoteka, "Shrani ...");
//		menuKoncaj = dodajMenuItem(menuDatoteka, "Končaj");
//		menuPrazen = dodajMenuItem(menuGraf, "Prazen ...");
//		menuCikel = dodajMenuItem(menuGraf, "Cikel ...");
//		menuPoln = dodajMenuItem(menuGraf, "Poln ...");
//		menuPolnDvodelen = dodajMenuItem(menuGraf, "Poln dvodelen ...");
//		menuBarvaPovezave = dodajMenuItem(menuNastavitve, "Barva povezave ...");
//		menuBarvaTocke = dodajMenuItem(menuNastavitve, "Barva točke ...");
//		menuBarvaAktivneTocke = dodajMenuItem(menuNastavitve, "Barva aktivne točke ...");
//		menuBarvaIzbraneTocke = dodajMenuItem(menuNastavitve, "Barva izbrane točke ...");
//		menuBarvaRoba = dodajMenuItem(menuNastavitve, "Barva roba ...");
//		menuDebelinaRoba = dodajMenuItem(menuNastavitve, "Debelina roba ...");
//		menuDebelinaPovezave = dodajMenuItem(menuNastavitve, "Debelina povezave ...");
//		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JMenu dodajMenu(JMenuBar menubar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menubar.add(menu);
		return menu;
	}

	private JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menuItem = new JMenuItem(naslov);
		menu.add(menuItem);
		menuItem.addActionListener(this);
		return menuItem;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		Object source = e.getSource();
//		if (source == menuOdpri) {
//			JFileChooser dialog = new JFileChooser();
//			int izbira = dialog.showOpenDialog(this);
//			if (izbira == JFileChooser.APPROVE_OPTION) {
//				String ime = dialog.getSelectedFile().getPath();
//				Graf graf = Graf.preberi(ime);
//				platno.nastaviGraf(graf);
//			}
//		} else if (source == menuShrani) {
//			JFileChooser dialog = new JFileChooser();
//			int izbira = dialog.showSaveDialog(this);
//			if (izbira == JFileChooser.APPROVE_OPTION) {
//				String ime = dialog.getSelectedFile().getPath();
//				platno.graf.shrani(ime);
//			}
//		} else if (source == menuKoncaj) {
//			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//		} else if (source == menuPrazen) {
//			String steviloTock = JOptionPane.showInputDialog(this, "Število točk:");
//			if (steviloTock != null && steviloTock.matches("\\d+")) {
//				Graf g = Graf.prazen(Integer.parseInt(steviloTock));
//				g.razporedi(400, 400, 300);
//				platno.nastaviGraf(g);
//			}
//		} else if (source == menuCikel) {
//		} else if (source == menuPoln) {
//		} else if (source == menuPolnDvodelen) {
//			JTextField nn = new JTextField();
//			JTextField mm = new JTextField();
//			JComponent[] polja = {
//				new JLabel("Vnesi N:"), nn,	
//				new JLabel("Vnesi M:"), mm	
//			};
//			int izbira = JOptionPane.showConfirmDialog(this, polja, "Input", JOptionPane.OK_CANCEL_OPTION);
//			if (izbira == JOptionPane.OK_OPTION && nn.getText().matches("\\d+") && mm.getText().matches("\\d+")) {
//				Graf g = Graf.polnDvodelen(Integer.parseInt(nn.getText()), Integer.parseInt(mm.getText()));
//				g.razporedi(400, 400, 300);
//				platno.nastaviGraf(g);	
//			}
//		} else if (source == menuBarvaPovezave) {
//		} else if (source == menuBarvaTocke) {
//		} else if (source == menuBarvaAktivneTocke) {
//		} else if (source == menuBarvaIzbraneTocke) {
//		} else if (source == menuBarvaRoba) {
//		} else if (source == menuDebelinaRoba) {
//		} else if (source == menuDebelinaPovezave) {
//		}
//		
	}
	
}