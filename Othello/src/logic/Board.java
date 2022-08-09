package logic;

import java.util.Arrays;

public class Board {
	private Square[][] gameBoard;
	
	public Board() {
		gameBoard = initialBoard();
	}
	
	public Board(Square[][] gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	private Square[][] initialBoard() {
		Square[][] out = new Square[8][8];
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				out[y][x] = new Square();
			}
		}
		
		out[3][3].setStoneOnSquare(Stone.WHITE);
		out[4][4].setStoneOnSquare(Stone.WHITE);
		out[3][4].setStoneOnSquare(Stone.BLACK);
		out[4][3].setStoneOnSquare(Stone.BLACK);
		
		return out;
	}
	
	public Square at(int x, int y) {
		return gameBoard[y][x];
	}
	
	public Square at(Coords c) {
		return gameBoard[c.y][c.x];
	}
	
	public String toString() {
		return Arrays.deepToString(gameBoard).replace("], ", "]\n").replace("[", "").replace("]", "").replace(",", " |");
	}

	public Board copy() {
		Square[][] b = new Square[8][8];
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				b[y][x] = this.at(x, y).copy();
			}
		}
		
		return new Board(b);
	}

}
