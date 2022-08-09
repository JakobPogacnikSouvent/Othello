package logic;

public class Coords {
	public int x;
	public int y;
	
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Coords add(Coords a, Coords b) {
		return new Coords(a.x + b.x, a.y + b.y);
	}
	
	public Coords add(Coords b) {
		x = x + b.x;
		y = y + b.y;
		
		return this;
	}
	
	public static Coords subtract(Coords a, Coords b) {
		return new Coords(a.x - b.x, a.y - b.y);		
	}
	
	public Coords subtract(Coords b) {
		x = x - b.x;
		y = y - b.y;
		
		return this;
	}
	
	public static Coords divide(Coords a, int n) {
		return new Coords(a.x / n, a.y / n);
	}
	
	public Coords divide(int n) {
		x = x/n;
		y = y/n;
		
		return this;
	}
	
	public static Coords multiply(Coords a, int n) {
		return new Coords(a.x * n, a.y * n);
	}
	
	public Coords multiply(int n) {
		x = x * n;
		y = y * n;
		
		return this;
	}

	public Coords norm() {
		int newX = 0;
		int newY = 0;
		
		if (x != 0) {
			newX = x / Math.abs(x); 
		}
		
		if (y != 0) {
			newY = y / Math.abs(y);
		}
		
		return new Coords(newX, newY);
	}
	
	public boolean equals(Coords c) {
		return (x == c.x && y == c.y);
	}
	
	public double size2Norm() {
		return Math.sqrt(x*x + y*y);
	}
	
	public String toString() {
		return String.valueOf(x) + " , " + String.valueOf(y);
	}
}
