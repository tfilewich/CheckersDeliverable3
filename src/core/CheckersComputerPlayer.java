package core;

import core.CheckerBoard.Checker;
import java.util.Random;

/**
 * CheckersComputerPlayer contains the logic of a computer player
 * 
 * @author Tyler Filewich
 * @version 1.1  2022-01-27
 */
public class CheckersComputerPlayer {
	
	/** The CheckerBoard being played on */
	public CheckerBoard board;
	
	/** The collection of Checkers belonging to the computer */
	public Checker[] pieces;
	
	/** Random object used to select a piece to play */
	public Random random = new Random();
	
	
	/**
	 * Constructor
	 * @param board  The CheckerBoard being played on.
	 */
	public CheckersComputerPlayer(CheckerBoard board) {
		this.board = board;
		pieces = board.getOPieces();
	}
	
	/**
	 * Plays one complete turn for the computer.
	 */
	public void playTurn() {
		int start = random.nextInt(12);
		Checker current;
		
		for (int i = start; i < pieces.length; i++) {
			current = pieces[i];
			if (! current.isCaptured()) {
				boolean madeJump = tryJump(current);
				if (madeJump) return;
			}
		}
		for (int i = start -1; i >= 0; i--) {
			current = pieces[i];
			if (! current.isCaptured()) {
				boolean madeJump = tryJump(current);
				if (madeJump) return;
			}
		}
		for (int i = start; i < pieces.length; i++) {
			current = pieces[i];
			if (! current.isCaptured()) {
				boolean madeMove = tryMove(current);
				if (madeMove) return;
			}
		}
		for (int i = start -1; i >= 0; i--) {
			current = pieces[i];
			if (! current.isCaptured()) {
				boolean madeMove = tryMove(current);
				if (madeMove) return;
			}
		}
		
	}	
	
	/**
	 * Attempts to complete a jump move.
	 * @param checker The checker to play.
	 * @return if the move was completed.
	 */
	public boolean tryJump(Checker checker) {
		boolean successful = false;
		int fromRow = checker.getPosition()[0];
		int fromColumn = checker.getPosition()[1];
		int[] move = new int[4];
		move[0] = fromRow;
		move[1] = fromColumn;
		move[2] = fromRow - 2;
		move[3] = fromColumn - 2;
		if (CheckersLogic.isValid(move, board)) {
			board.movePiece(move);
			successful = true;
		} else {
			move[3] = fromColumn + 2;
			if (CheckersLogic.isValid(move, board)) {
				board.movePiece(move);
				successful = true;
			}
		}
		return successful;
	}
	
	/**
	 * Attempts to complete a non-jump move.
	 * @param checker The checker to play.
	 * @return if the move was completed.
	 */
	public boolean tryMove(Checker checker) {
		boolean successful = false;
		int fromRow = checker.getPosition()[0];
		int fromColumn = checker.getPosition()[1];
		int[] move = new int[4];
		move[0] = fromRow;
		move[1] = fromColumn;
		move[2] = fromRow - 1;
		move[3] = fromColumn - 1;
		if (CheckersLogic.isValid(move, board)) {
			board.movePiece(move);
			successful = true;
		} else {
			move[3] = fromColumn + 1;
			if (CheckersLogic.isValid(move, board)) {
				board.movePiece(move);
				successful = true;
			}
		}
		return successful;
	}
	
	/**
	 * Adds a two second delay.
	 */
	public void delay() {
		final int TIME = 2000; //delay time in ms
		try {
    		Thread.sleep(TIME);
    	} catch (InterruptedException e) {
    		Thread.currentThread().interrupt();
    	}
	}
}









