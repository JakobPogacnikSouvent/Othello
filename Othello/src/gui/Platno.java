package gui;

import javax.swing.JPanel;

import inteligenca.Inteligenca;
import logic.Coords;
import logic.Game;
import logic.GameStatus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.*;


@SuppressWarnings("serial") 
public class Platno extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	private Game game;

	private Color squareColourPrimary, squareColourSecondary, figureColourWhite, figureBorderWhite, figureColourBlack, figureBorderBlack;
	private Stroke edgeSize;
	
	private Coords boardZeroVector;
	
	private Coords mouseHoverSquare;
			
	private int fullWidth, fullHeight, boardSize, squareSize, stoneSize, offset;
		
	private GameWindow parent;
	
	public Color getSquareColourPrimary() {
		return squareColourPrimary;
	}
	
	public void setSquareColourPrimary(Color c) {
		squareColourPrimary = c;
	}
	
	public static Color defaultSquareColourPrimary() {
		return new Color(17, 59, 8);
	}
	
	public Color getSquareColourSecondary() {
		return squareColourSecondary;
	}
	
	public void setSquareColourSecondary(Color c) {
		squareColourSecondary = c;
	}
	
	public static Color defaultSquareColourSecondary() {
		return new Color(13, 47, 7);
	}
	
	public Color getFigureColourWhite() {
		return figureColourWhite;
	}
	
	public void setFigureColourWhite(Color c) {
		figureColourWhite = c;
	}
	
	public static Color defaultFigureColourWhite() {
		return Color.WHITE;
	}
	
	public Color getFigureColourBlack() {
		return figureColourBlack;
	}
	
	public void setFigureColourBlack(Color c) {
		figureColourBlack = c;
	}
	
	public static Color defaultFigureColourBlack() {
		return Color.BLACK;
	}
	
	public Platno(int sirina, int visina, GameWindow parent) {
		super();
		setPreferredSize(new Dimension(sirina, visina));
		
		this.parent = parent;
		
		game = null;
		
		squareColourPrimary = defaultSquareColourPrimary();
		squareColourSecondary = defaultSquareColourSecondary();
		
		figureColourWhite = defaultFigureColourWhite();
		figureColourBlack = defaultFigureColourBlack();
		
		figureBorderWhite = Color.BLACK;
		figureBorderBlack = Color.BLACK;
		
		
		edgeSize = new BasicStroke(1);
		
		setBackground(Color.LIGHT_GRAY);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		
	}
	
	public void setGame(Game g) {
		game = g;
		repaint();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setWindowVars() {
		fullWidth = getWidth();
		fullHeight = getHeight();
		boardSize = Math.min(fullWidth, fullHeight);
		squareSize = boardSize / 8;
		boardZeroVector = new Coords((Math.max(fullWidth, boardSize) - Math.min(fullWidth, boardSize))/2, (Math.max(fullHeight, boardSize) - Math.min(fullHeight, boardSize))/2);
		stoneSize = (int) (squareSize * 0.9);
		offset = (squareSize - stoneSize) / 2;

	}

	@Override
	protected void paintComponent(Graphics g) {
		if (game == null) return;
		
		super.paintComponent(g);
		
		setWindowVars();
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(edgeSize);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Paint squares
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {				
				Coords squareZeroVector = Coords.add(boardZeroVector, new Coords(i * squareSize, j * squareSize));
												
				if ((i + j) % 2 != 0) {
					g2.setColor(squareColourSecondary);										
				} else {
					g2.setColor(squareColourPrimary);					
				}
				
				
				g2.fillRect(squareZeroVector.x, squareZeroVector.y, squareSize, squareSize);
				
			}
		}
		
		// Paint stones
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; ++j) {
				Coords squareZeroVector = Coords.add(boardZeroVector, new Coords(i * squareSize, j * squareSize));
				
				Color borderClr = null;
				Color fillClr = null;
				
				switch (game.getBoard().at(i, j).getStoneOnSquare()) {
				case WHITE:
					fillClr = figureColourWhite;
					borderClr = figureBorderWhite;
					break;
				case BLACK:
					fillClr = figureColourBlack;
					borderClr = figureBorderBlack;
					break;
				case EMPTY:
					if (game.getBoard().at(i, j).getCanPlace(game.getActivePlayer())) {
						borderClr = figureBorderWhite;
					}
				default:
						;
				}
				
				if (fillClr != null) {
					g2.setColor(fillClr);
					g2.fillOval(squareZeroVector.x + offset, squareZeroVector.y + offset, stoneSize, stoneSize);
				} 
				if (borderClr != null) {
					g2.setColor(borderClr);
					g2.drawOval(squareZeroVector.x + offset, squareZeroVector.y + offset, stoneSize, stoneSize);
				}
				
			}
		}
		
		if (mouseHoverSquare != null && !game.getAiThinking() && game.isLegalMove(mouseHoverSquare, game.getActivePlayer())) {
			Coords squareZeroVector = Coords.add(boardZeroVector, new Coords(mouseHoverSquare.x * squareSize, mouseHoverSquare.y * squareSize));
			
			Color stoneClr = Color.GRAY;
			
			switch (game.getActivePlayer()) {
			case WHITE:
				stoneClr = figureColourWhite;
				break;
			case BLACK:
				stoneClr = figureColourBlack;
				break;
			case EMPTY:
			default:
					;
			}
			
			g2.setColor(stoneClr);
			g2.fillOval(squareZeroVector.x + offset,squareZeroVector.y  + offset, stoneSize, stoneSize);
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (game == null) return;
		
		char key = e.getKeyChar();
		if (key == 'r' && !game.getAiThinking() && game.getStatus() == GameStatus.ONGOING) {
			game.odigraj(Inteligenca.getRandomMove(game));
			parent.update();
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		if (!game.getAiThinking() && game.getStatus() == GameStatus.ONGOING) {
//			game.odigraj(Inteligenca.getRandomMove(game));
//		} else if (game.getStatus() == GameStatus.FINISHED){
//			System.out.println(game.getWinner());
//		}
		
		Coords mouseSquare = Coords.subtract(new Coords(e.getX(), e.getY()), boardZeroVector).divide(squareSize);
		
		if (mouseHoverSquare == null || !mouseSquare.equals(mouseHoverSquare)) {
			mouseHoverSquare = mouseSquare;
			repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		if (!game.getAiThinking()) {			
			game.odigraj(mouseHoverSquare); // mouseHoverSquare should be same as pressed square
			
			parent.update();				
//			if (game.getStatus() == GameStatus.ONGOING) {
//			}
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
