package inteligenca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import logic.Coords;
import logic.Game;
import logic.GameStatus;
import logic.Stone;

public class Tree {
	/*
	 * Each node in tree is a representation of a game board state.
	 * Each also has number of simulations run from said board state and
	 * the number of those that have been victories for the active player of current board state
	 */
	
	
	private Game game; // Board state. Value doesn't change once assigned
	
	protected int nOfSimulations;
	protected int nOfWins;
		
	protected Map<Coords, Tree> children;
	
	/* 
	 **************************************************************************	
	 *                         GETTERS AND SETTERS                            *
	 **************************************************************************  
	 */
	
	public Game getGame() {
		return game;
	}
	
	public Map<Coords, Tree> getChildren() {		
		return children;
	}
	
	public int getnOfSimulations() {
		return nOfSimulations;
	}
	
	protected void setnOfSimulations(int x) {
		nOfSimulations = x;
	}
	
	public int getnOfWins() {
		return nOfWins;
	}
	
	protected void setnOfWins(int x) {
		nOfWins = x;
	}
	
	public float getWinPercentage() {
		return nOfWins / nOfSimulations;
	}
		
	/* 
	 **************************************************************************	
	 *                         CONSTRUCTORS                                   *
	 **************************************************************************  
	 */
	
	public Tree(Game g) {
		game = g;
		nOfSimulations = 0;
		nOfWins = 0;
		
		children = new HashMap<Coords, Tree>();
	}
	
	/* 
	 **************************************************************************	
	 *                 PRIVATE / PROTECTED METHODS                            *
	 **************************************************************************  
	 */	
	
	
	/* 
	 **************************************************************************	
	 *                            PUBLIC METHODS                              *
	 **************************************************************************  
	 */
	
	public Stone cycle() {
		/*
		 * Performs a single cycle of Monte Carlo tree search
		 */
				
		// Cycle
		if (children.size() != 0) {
			
			// Select a child
			Tree bestChild = findBestNodeUTC();
			Stone winner = bestChild.cycle();
			
			
			
			updateScore(winner);
			return winner;
			
		} else {
			// Expansion

			if (game.getWinner() != null) return game.getWinner();
			
			addLegalMoves();
			
			if(children.size() == 0) {
				System.out.println(game.toString());				
			}
			
			Tree bestChild = getRandomChild();
			
			// Simulation
			Stone winner = bestChild.randomPlayout();
			bestChild.updateScore(winner);
			updateScore(winner);
			return winner;
		}
		
	}
	
	private void updateScore(Stone winner) {
		nOfSimulations++;
		if (winner == game.getActivePlayer()) {
			nOfWins++;
		}
	}
	
	private Stone randomPlayout() {
		Game game = this.game.copy();
		while (game.getStatus() != GameStatus.FINISHED) {			
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
			
			
			game.odigraj(randomMove);
		}
		
		Stone winner = game.getWinner();
		return winner;
	}
	
	private Tree getRandomChild() {
		Random generator = new Random();
		
		Object[] values = children.values().toArray();
		
		return (Tree) values[generator.nextInt(values.length)];

	}
	
	private void addLegalMoves() {
		
		// System.out.println(game.toString());
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords c = new Coords(x,y);
				if (game.isLegalMove(c, game.getActivePlayer())) {
					Game childGame = game.copy();
					childGame.odigraj(c);					
					children.put(c, new Tree(childGame));
				}
			}
		}
	}
	
	private Tree findBestNodeUTC() {
		Tree bestNode = null;
		double bestNodeUTC = -1;
		
		for (Coords c : children.keySet()) {
			Tree child = children.get(c);
			if (child.getnOfSimulations() == 0) {
				return child;
			} else {
				double childUTC = ((double) child.getnOfWins() / (double) child.getnOfSimulations()) + 1.41 * Math.sqrt(Math.log(this.nOfSimulations) / (double) child.getnOfSimulations());
				
				if (childUTC > bestNodeUTC) {
					bestNode = child;
					bestNodeUTC = childUTC;
				}

			}
		}
		
		return bestNode;
	}
	
	public String toString(int zamik) {
		/*
		 * Turns the entire tree into human readable string
		 */
		
		String z = new String(new char[zamik]).replace("\0", "-");
		
		String t = z + "\n" + game.toString() + "\n" + Integer.toString(nOfWins) + " / " + Integer.toString(nOfSimulations) + "\n";
		
		for (Tree c : children.values()) {
			t +=  c.toString(zamik + 1);
		}

		return t;

	}
	
	public String toString() {
		return toString(0);
	}

	public Coords getBestMove() {
		// Best move for parent is to chose child with lowest win percentage
		Coords bestMove = null;
		double bestMoveScore = -1;
		
		for (Coords c : children.keySet()) {
			Tree child = children.get(c);
			double childScore = ((double) child.getnOfWins() / (double) child.getnOfSimulations());
			
			if (child.getGame().getActivePlayer() != this.game.getActivePlayer()) {
				childScore = 1 - childScore;
			}

			
			if (childScore > bestMoveScore) {
				bestMove = c;
				bestMoveScore = childScore;
			}

		}
		
		return bestMove;
	}
		
}
