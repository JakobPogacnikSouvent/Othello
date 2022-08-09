package tests;

import javax.swing.WindowConstants;

import gui.StartScreen;

public class GUITest {
	public static void main(String[] args) {
		StartScreen okno = new StartScreen();
		okno.pack();
		okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);
	}
}
