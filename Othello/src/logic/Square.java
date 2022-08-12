package logic;

public class Square {
	private Stone stoneOnSquare;
	private boolean canPlaceBlack;
	private boolean canPlaceWhite;
	
	public Stone getStoneOnSquare() {
		return stoneOnSquare;
	}
	
	public void setStoneOnSquare(Stone s) {
		stoneOnSquare = s;
	}
	
	public boolean getCanPlace(Stone s) {
		switch (s) {
		case BLACK:
			return canPlaceBlack;
		case WHITE:
			return canPlaceWhite;
		case EMPTY:
			System.out.println("WARNING: Nonsensical argument EMPY.");
			return true;
		default:
			System.out.println("ERROR: Unsupported argument.");
			return false;
		}
	}
	
	public void setCanPlace(Stone s, boolean value) {
		switch (s) {
		case BLACK:
			canPlaceBlack = value;
			break;
		case WHITE:
			canPlaceWhite = value;
			break;
		default:
			System.out.println("ERROR: Unsupported argument.");
		}
	}
	
	public void flipStone() {
		switch (stoneOnSquare) {
		case WHITE:
			stoneOnSquare = Stone.BLACK;
			break;
		case BLACK:
			stoneOnSquare = Stone.WHITE;
			break;
		case EMPTY:
		default:
			System.out.println("ERROR: No stone to flip.");
		}
	}
	
	public Square(Stone onSqr, boolean canWhite, boolean canBlack) {
		stoneOnSquare = onSqr;
		canPlaceBlack = canBlack;
		canPlaceWhite = canWhite;
	}
	
	public Square copy() {
		return new Square(stoneOnSquare, canPlaceWhite, canPlaceBlack);
	}
	
	public Square() {
		stoneOnSquare = Stone.EMPTY;
		canPlaceBlack = false;
		canPlaceWhite = false;
	}
	
	public String toString() {
		switch (stoneOnSquare) {
		case BLACK:
			return "B";
		case WHITE:
			return "W";
		case EMPTY:
		default:
			return " ";
		}
	}
}
