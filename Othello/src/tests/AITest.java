package tests;

import inteligenca.Inteligenca;
import logic.Game;
import logic.GameStatus;
import logic.Stone;

public class AITest {
	public static void main(String[] args) {
		Inteligenca carlos = new Inteligenca("Carlos");
		
		int carlosWins = 0;
		
		for (int i = 1; i < 1000; i++) {
			Stone carlosColour = Stone.BLACK;
			switch (i % 2) {
			case 0:
				carlosColour = Stone.BLACK;
				break;
			case 1:
				carlosColour = Stone.WHITE;
				break;
			}
			
			Game g = new Game(carlos, carlosColour);
			
			while (g.getStatus() == GameStatus.ONGOING) {
				if (g.getActivePlayer() != carlosColour) {
					g.odigraj(Inteligenca.getRandomMove(g));
				}
			}
			
			if (g.getWinner() == carlosColour) carlosWins++;
			
			System.out.println(carlosColour + " " + carlosWins + " / " + i);
		}
		
	}
}
