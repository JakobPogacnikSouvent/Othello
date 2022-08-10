package inteligenca;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.Coords;
import logic.Game;
import logic.GameStatus;
import splosno.KdoIgra;

public class Inteligenca extends KdoIgra {
		
	public Inteligenca(String ime) {
		super(ime);
		// TODO Auto-generated constructor stub
	}
	
	public Coords getMove(Game g) {
		return getMCTSMove(g);
	}
	
	private Coords getMCTSMove(Game game) {
		Tree root = new Tree(game.copy());
		
		final int THINKING_TIME = 2000;
		
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		
		while (currTime < startTime + THINKING_TIME) {
			root.cycle();
			currTime = System.currentTimeMillis();
		}
		
		System.out.println(root.getnOfWins() + " / " + root.getnOfSimulations());
		
		return root.getBestMove();
	}
	
	public static Coords getRandomMove(Game game) {
		if (game.getStatus() == GameStatus.FINISHED) return null;
		
		List<Coords> possibleMoves = new ArrayList<Coords>();
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords c = new Coords(x,y);
				if (game.isLegalMove(c, game.getActivePlayer())) {
					possibleMoves.add(c);
				}
			}
		}
		
	    Random rand = new Random();
	    Coords randomMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
	    
	    
	    return randomMove;
	}
}
