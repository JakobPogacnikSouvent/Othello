package logic;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

import inteligenca.Inteligenca;
import splosno.Poteza;

public class Game {
	private Board gameBoard;
	
	private Stone activePlayer;
	private Stone notActivePlayer;
	
	private Inteligenca opponent = null;
	private Stone opponentColour = null;
	
	private GameStatus status;
	
	private boolean aiThinking;
	private boolean asyncMoves = false;
	
	private int blackScore;
	private int whiteScore;
	
	public boolean getAiThinking() {
		return aiThinking;
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public Stone getActivePlayer() {
		return activePlayer;
	}
	
	public Stone getWinner() {
		if (status == GameStatus.FINISHED) {
			if (blackScore > whiteScore) {
				return Stone.BLACK;			
			} else if (blackScore < whiteScore) {
				return Stone.WHITE;
			} else {
				return Stone.EMPTY;
			}
		}
		
		return null;
	}
	
	public Game(Inteligenca ai, Stone aiColour) {
		opponent = ai;
		asyncMoves = false;
		
		this.opponentColour = aiColour;
		
		initVars();

		// Make first move if ai is black
		
		if (this.opponentColour == activePlayer) {
			makeAiMove();
		}
		
	}	
	
	public Game(Inteligenca ai, Stone aiColour, boolean asyncMoves) {
		opponent = ai;
		this.asyncMoves = asyncMoves; 
		
		this.opponentColour = aiColour;
		
		initVars();

		// Make first move if ai is black
		
		if (this.opponentColour == activePlayer) {
			makeAiMove();
		}
		
	}
	
	public Game() {
		aiThinking = false;
		initVars();
	}
	
	public Game(Board b, Stone activePlayer, GameStatus s) {
		gameBoard = b;
		this.activePlayer = activePlayer;
		
		switch (activePlayer) {
		case WHITE:
			this.notActivePlayer = Stone.BLACK;
			break;
		case BLACK:
			this.notActivePlayer = Stone.WHITE;
			break;
		case EMPTY:
		default:
			System.out.println("ERROR: Unsupported argument for activePlayer!");
			break;
			
		}
		
		status = s;
		
		aiThinking = false;
		
		endMoveTriggers();
	}
	
	public Game copy() {
		return new Game(this.gameBoard.copy(), activePlayer, status);
	}
	
	private void initVars() {
		gameBoard = new Board();
		
		activePlayer = Stone.BLACK;
		notActivePlayer = Stone.WHITE;
		
		status = GameStatus.ONGOING;
		
		endMoveTriggers();
	}
	
	public Board getBoard() {
		return gameBoard;
	}
	
	public boolean odigraj(Poteza p) {
		Coords c = new Coords(p.getX(), p.getY());
		return odigraj(c);
	}
	
	public boolean odigraj(Coords c) {
		// Active players makes move on c
		
		if (isLegalMove(c, activePlayer)) {
			// Place stone
			gameBoard.at(c).setStoneOnSquare(activePlayer);
			
			// Flip stones
			int[] dir = {-1, 0, 1};

			for (int x : dir) {
				for (int y : dir) {
					Coords n = new Coords(x, y);
					Coords at = Coords.add(n, c);
					
					List<Square> toFlip = new ArrayList<Square>();
					boolean legalEnd = false;
										
					// While in bounds
					while (at.x < 8 && at.x >= 0 && at.y < 8 && at.y >= 0) {
						Stone s = gameBoard.at(at).getStoneOnSquare();
						
						if (s == activePlayer) {
							legalEnd = true;
							break;
						}
						else if (s == Stone.EMPTY) break;
						else toFlip.add(gameBoard.at(at));
						
						at.add(n);
					}
										
					if (legalEnd) {
						for (Square s : toFlip) {
							s.flipStone();
						}
					}				
				}
			}
			
			endMove();

			return true;

		}
		
		return false;
	}
	
	private void endMove() {
		
		endMoveTriggers();
		
		// Change players
		// TODO: Player can have not possible moves
		Stone tmp = activePlayer;
		activePlayer = notActivePlayer;
		notActivePlayer = tmp;
		
		// System.out.println(activePlayer + " to move");
		
		startMoveTriggers();
		
	}
	
	private void endMoveTriggers() {

		// Update legal squares
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (isLegalMove(x, y, Stone.WHITE)) {
					gameBoard.at(x, y).setCanPlace(Stone.WHITE, true);
				} else {
					gameBoard.at(x, y).setCanPlace(Stone.WHITE, false);	
				}
				
				if (isLegalMove(x, y, Stone.BLACK)) {
					gameBoard.at(x, y).setCanPlace(Stone.BLACK, true);
				} else {
					gameBoard.at(x, y).setCanPlace(Stone.BLACK, false);	
				}
			}
		}
		
		updateScore();
	}

	private void startMoveTriggers() {

		// Check if game has ended
		boolean activePlayerHasLegalMoves = false;
		boolean notActivePlayerHasLegalMoves = false;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {				
				if (gameBoard.at(x, y).getCanPlace(activePlayer)) {
					activePlayerHasLegalMoves = true;
				}
				
				if (gameBoard.at(x,y).getCanPlace(notActivePlayer)) {
					notActivePlayerHasLegalMoves = true;
				}
			}	
			
		}
		
		if (!activePlayerHasLegalMoves) {
			if (!notActivePlayerHasLegalMoves) endGame();
			else endMove();
		}
	
		
		// If it is ai's turn get move
		if (opponent != null && activePlayer == opponentColour && status != GameStatus.FINISHED) {
			makeAiMove();

		}
	}
	
	private void makeAiMove() {
		if (asyncMoves) {
			aiThinking = true;
			final Game g = this;
			
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void> () {			
				@Override
				protected Void doInBackground() {
					return null;
				}
				
				@Override
				protected void done () {
					Coords move = opponent.getMove(g);
					odigraj(move);
					
					aiThinking = false;
					
				}
			};
			
			worker.execute();			
		} else {
			Coords move = opponent.getMove(this);
			odigraj(move);
		}
	
	
	}
	

	private void endGame() {
		status = GameStatus.FINISHED;
		
		updateScore();
		
//		if (blackScore > whiteScore) {
//			System.out.println("BLACK wins!");			
//		} else if (blackScore < whiteScore) {
//			System.out.println("WHITE wins!");
//		} else {
//			System.out.println("TIE!");
//		}
		
		
	}

	public void updateScore() {
		int blackScore = 0;
		int whiteScore = 0;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				switch (gameBoard.at(x, y).getStoneOnSquare()) {
				case BLACK:
					blackScore++;
					break;
				case WHITE:
					whiteScore++;
					break;
				case EMPTY:
				default:
					break;
				}
			}
		}
		
		this.blackScore = blackScore;
		this.whiteScore = whiteScore;
	}
	
	public boolean isLegalMove(int x, int y, Stone p) {
		Coords c = new Coords(x, y);
		return isLegalMove(c, p);
	}
	
	public boolean isLegalMove(Coords c, Stone p) {		
		if (gameBoard.at(c).getStoneOnSquare() != Stone.EMPTY) return false;
		
		int[] dir = {-1, 0, 1};
		
		boolean legalMove = false;
		
		for (int x : dir) {
			for (int y : dir) {
				Coords n = new Coords(x, y);
				Coords at = Coords.add(n, c);
				
				boolean foundFlips = false;
				boolean legalEnd = false;
								
				// While in bounds
				while (at.x < 8 && at.x >= 0 && at.y < 8 && at.y >= 0) {
					Stone s = gameBoard.at(at).getStoneOnSquare();
					
					if (s == p) {
						legalEnd = true;
						break;
					}
					else if (s == Stone.EMPTY) break;
					else foundFlips = true;
					
					at.add(n);
				}
				
				
				if (foundFlips && legalEnd) {
					legalMove = true;
				}				
			}
		}
		
		return legalMove;
	}
	
	public String toString() {
		return gameBoard.toString();
	}
}
