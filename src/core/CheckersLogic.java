package core;

import core.CheckerBoard.Square;


/**
 * CheckersLogic contains the logic of a checkers game
 * 
 * @author Tyler Filewich
 * @version 1.1  2022-01-28
 */
public class CheckersLogic {

   
    /**
     * Checks if a move is legal.
     * @param coordinates   Array containing the starting row, starting column,
     *      finishing row, and finishing column of the move to be checked.
     *@param board 		The CheckerBoard being played on.
     * @return If the move is legal.
     */
    public static boolean isValid(int[] coordinates, CheckerBoard board) {
        boolean valid = false;
        int fromRow = coordinates[0];
        int fromColumn = coordinates[1];
        int toRow = coordinates[2];
        int toColumn = coordinates[3];
        Square fromSquare;
        Square toSquare;
        
        if (! onBoard(coordinates)) return false;	//out of bounds
            
        fromSquare = board.getSquare(fromRow, fromColumn);
        toSquare = board.getSquare(toRow, toColumn);
        
        if (isTurn(fromSquare, board))  {			//current player's checker is selected
            if (isEmpty(toSquare)) {				//destination square is empty
                if (isMoveable(coordinates, board) || isJumpable(coordinates, board)) { //is valid move or jump
                        valid = true;
                }
            }
        } 
        return valid;
    }
    
    
    /**
     * Checks if a move is within the bounds of the board.
     * @param coordinates   Array containing the starting row, starting column,
     *      finishing row, and finishing column of the move to be checked.
     * @return If the move is within bounds.
     */
    private static boolean onBoard(int[] coordinates) {
        for (int i : coordinates) {
            if (i < 1 || i > 8) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if a move is a valid single move.
     * @param coordinates   Array containing the starting row, starting column,
     *      finishing row, and finishing column of the move to be checked.
     * @param board 	The CheckerBoard being played on.
     * @return If the move is one square forward and one square left or right.
     */
    public static boolean isMoveable(int[] coordinates, CheckerBoard board) {
        boolean moveable = false;
        int fromRow = coordinates[0];
        int fromColumn = coordinates[1];
        int toRow = coordinates[2];
        int toColumn = coordinates[3];
        Square currentPlayer = board.getCurrentPlayer();
        //check distance and direction
        if ((currentPlayer.isX()) && (toRow == fromRow + 1)  && ( (toColumn == fromColumn + 1) || (toColumn == fromColumn - 1))) {
           moveable = true;
        } else if ( (currentPlayer.isO()) && (toRow == fromRow - 1)  && ( (toColumn == fromColumn + 1) || (toColumn == fromColumn - 1))) {
            moveable = true;
         }
        return moveable;
    }
    
    
    /**
     * Checks if a move is a valid capturing move.
     * @param coordinates   Array containing the starting row, starting column,
     *      finishing row, and finishing column of the move to be checked.
     * @param board 		The CheckerBoard being played on.
     * @return If the move is two squares forward and two squares left or right over an opponent.
     */
    public static boolean isJumpable(int[] coordinates, CheckerBoard board) {
        boolean jumpable = false;
        int fromRow = coordinates[0];
        int fromColumn = coordinates[1];
        int toRow = coordinates[2];
        int toColumn = coordinates[3];
        Square currentPlayer = board.getCurrentPlayer();
        
        //check distance, direction, and type of piece to be jumped
        if ((currentPlayer.isX()) && (toRow == fromRow + 2) && ( (toColumn == fromColumn + 2) || (toColumn == fromColumn - 2))) {
            int jumpRow = (fromRow + toRow) / 2; 
            int jumpColumn = (fromColumn + toColumn) / 2;
            Square jumpPiece = board.getSquare(jumpRow, jumpColumn);
            if (jumpPiece.isO()) {
                jumpable = true;
            }
        } else if ((currentPlayer.isO()) && (toRow == fromRow - 2) && ( (toColumn == fromColumn + 2) || (toColumn == fromColumn - 2))) {
            int jumpRow = (fromRow + toRow) / 2; 
            int jumpColumn = (fromColumn + toColumn) / 2;
            Square jumpPiece = board.getSquare(jumpRow, jumpColumn);
            if (jumpPiece.isX()) {
                jumpable = true;
            }
        }
        return jumpable;
    }
    
    
    /**
     * Checks if the Square contains a piece which belongs to the current player.
     * @param square 	The Square to be checked.
     * @param board 	The CheckerBoard being played on.
     * @return  If the Square contains a piece which belongs to the current player.
     */
    static private boolean isTurn(Square square, CheckerBoard board) {
        return (square.isX() && board.getCurrentPlayer().isX() || square.isO() && board.getCurrentPlayer().isO());
    }
    
    /**
     * Checks if the Square contains a piece which belongs to the opponent.
     * @param square 	The Square to be checked.
     * @param board 	The CheckerBoard being played on.
     * @return  If the Square contains a piece which belongs to the opponent.
     */
    private static boolean isOpponent(Square square, CheckerBoard board) {
        return (square.isX() && board.getCurrentPlayer().isO() || square.isO() && board.getCurrentPlayer().isX());
    }
    
    /**
     * Checks if a Square is empty.
     * @param square The Square to be checked.
     * @return  If the Square is empty.
     */
    private static boolean isEmpty (Square square) {
        return square.isEmpty();
    }

    /**
     * Checks if a Square contains a piece which has a valid move available.
     * @param row       The x coordinate of the Square to check.
     * @param column    The y coordinate of the Square to check.
     * @param board 	The CheckerBoard being played on.
     * @return      If the Square contains a piece which has a valid move available.
     */
    public static boolean canMove(int row, int column, CheckerBoard board){
        Square square = board.getSquare(row, column);
        int fromRow = row;
        int fromColumn = column;
        int moveLeft = fromColumn - 1;
        int moveRight = fromColumn + 1;
        int jumpLeft = fromColumn - 2;
        int jumpRight = fromColumn + 2;
        int moveRow = 0;
        int jumpRow = 0;
        boolean canMove = false;
        
        if(square.isX()) {
            moveRow = fromRow + 1;
            jumpRow = fromRow + 2;
        } else if(square.isO()) {
            moveRow = fromRow - 1;
            jumpRow = fromRow - 2;
        }
        //get all possible moves (valid and invalid)
        int[][] possibleMoves = { {fromRow, fromColumn, moveRow, moveLeft}, 
                {fromRow, fromColumn, moveRow, moveRight},
                {fromRow, fromColumn, jumpRow, jumpLeft},
                {fromRow, fromColumn, jumpRow, jumpRight} };
        
        //check any move is valid
        for (int[] coordinates : possibleMoves) {
            if (isValid(coordinates, board)){
                canMove = true;        
            }
        }
        return canMove;
    }    
    
    /**
     * Checks if the current player has won.
     * @param 	board The CheckerBoard being played on.
     * @return If the current player has won.
     */
    public static boolean checkWin(CheckerBoard board) {
        boolean opponentAllGone = (board.getCurrentPlayer().isX() && board.noO() || board.getCurrentPlayer().isO() && board.noX());
        
        if (opponentAllGone) {  //no opponent checkers remain 
            return true;
        }
        
        for (Square[] row : board.getBoard()) {
            for (Square square : row) {
                if (isOpponent(square, board)) {
                    int[] position = square.getPosition();
                    board.switchTurn();								//switch opponent to current player so moves can be checked
                    if (canMove(position[0],position[1], board)) {	//opponent has valid move available
                    	board.switchTurn();;						//switch back	
                        return false;
                    }
                    board.switchTurn();								//switch back
                }
            }
        }
        return true;
    }
    
    
}
