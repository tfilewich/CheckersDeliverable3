package core;

/**
 * Checkerboard contains a representation of a checkerboard and pieces 
 * 
 * @author Tyler Filewich
 * @version 1.1  2022-01-27
 */
public class CheckerBoard {
    
    /** 2d array containing the squares of a checkerboard.*/
    private Square[][] board;
    
    /** Text representation of an X Checker.*/
    final String X = " x |";
    
    /** Text representation of an O Checker.*/
    final String O = " o |";
    
    /** Text representation of an empty square.*/
    final Square E = new Square(" _ |");
    
    /** The number of X Checkers remaining on the board.*/
    public int xCount = 12;
    
    /** The number of O Checkers remaining on the board.*/
    public int oCount = 12;
    
    /** Contains the X Checkers. */
    private Checker[] xPieces;
    
    /** Contains the O Checkers. */
    private Checker[] oPieces;
    
    /** The piece who is currently playing */
    private Square currentPlayer;
    
    /** The piece who is currently not playing */
    private Square opponent;
    

    /**
     * Constructs a complete board with pieces for a new game.
     */
    public CheckerBoard(){
    	//build rows
        Square[] row8 = {new Square("8 |"), E, new Checker(8,2,O), E, new Checker(8,4,O), E, new Checker(8,6,O), E, new Checker(8,8,O)};
        Square[] row7 = {new Square("7 |"), new Checker(7,1,O), E, new Checker(7,3,O), E, new Checker(7,5,O), E, new Checker(7,7,O), E};
        Square[] row6 = {new Square("6 |"), E, new Checker(6,2,O), E, new Checker(6,4,O), E, new Checker(6,6,O), E, new Checker(6,8,O)};
        Square[] row5 = {new Square("5 |"), E, E, E, E, E, E, E, E};
        Square[] row4 = {new Square("4 |"), E, E, E, E, E, E, E, E};
        Square[] row3 = {new Square("3 |"), new Checker(3,1,X), E, new Checker(3,3,X), E, new Checker(3,5,X), E, new Checker(3,7,X), E};
        Square[] row2 = {new Square("2 |"), E, new Checker(2,2,X), E, new Checker(2,4,X), E, new Checker(2,6,X), E, new Checker(2,8,X)};
        Square[] row1 = {new Square("1 |"), new Checker(1,1,X), E, new Checker(1,3,X), E, new Checker(1,5,X), E, new Checker(1,7,X), E};
        Square[] row0 = {new Square("  "), new Square("  a "), new Square("  b "), new Square("  c "),
                new Square("  d "), new Square("  e "), new Square("  f "), new Square("  g "), new Square("  h ")};
        //build columns
        board = new Square[][] {row0, row1, row2, row3, row4, row5, row6, row7, row8};
        
        Square playerX = new Square(X);
        Square playerO = new Square(O);
        currentPlayer = playerX;
        opponent = playerO;
        xCount = 0;
        oCount = 0;
        xPieces = new Checker[12];
        oPieces = new Checker[12];
        
        //count and add checkers to list
        for (Square[] row : board) {
        	for (Square square : row) {
        		if (square.isX()) {
        			xPieces[xCount] = (Checker)square;
        			xCount++;
        		} else if (square.isO()) {
        			oPieces[oCount] = (Checker)square;
        			oCount++;
        		}
        	}  	
        }   
    }


    /**
     * Removes a piece that has been jumped and updates the count.
     * @param row       The x coordinate of the piece being jumped.
     * @param column    The y coordinate of the piece being jumped.
     */
    public void capturePiece(int row, int column) {
        if (board[row][column].isX()) {
            xCount--;
        } else {
            oCount--;
        }
        ((Checker)board[row][column]).capture();
        board[row][column] = E;
    }
    
    
    /**
     * Moves a chess piece from one square to another and captures the opponent if necessary.
     * @param coordinates   The array containing the starting x coordinate, starting y coordinate,
     *  finishing x coordinate, and finishing y coordinate of the move.  Must be a valid move.
     */
    public void movePiece(int[] coordinates) { 
    	try{
    		int fromRow = coordinates[0];
	        int fromColumn = coordinates[1];
	        int toRow = coordinates[2];
	        int toColumn = coordinates[3];
	        boolean isJump = Math.abs(toRow - fromRow) == 2;
	        Checker piece = (Checker) board[fromRow][fromColumn];
	        
	        board[toRow][toColumn] = board[fromRow][fromColumn];
	        board[fromRow][fromColumn] = E;
	        piece.setPosition(toRow, toColumn);
	        
	        if (isJump) {
	            int jumpRow = (fromRow + toRow) / 2; 
	            int jumpColumn = (fromColumn + toColumn) / 2;
	            capturePiece(jumpRow, jumpColumn);
	        } 
    	}
    	catch (RuntimeException e) {
    		System.err.println("Invalid parameter - Coordinates must be a valid move");
    	}
    }
        
    /**
     * Get a square.
     * @param row       The Square's x coordinate.
     * @param column    The Square's y coordinate.
     * @return          The Square.
     * @throws IllegalArgumentException if the index is out of bounds.
     */
    public Square getSquare(int row, int column) throws IllegalArgumentException{
        try {
        	return board[row][column];
        }
        catch (ArrayIndexOutOfBoundsException e) {
        	throw new IllegalArgumentException("index out of bounds");
        }
    }
    
    /**
     * Checks if a square contains an X Checker.
     * @param square    The square to check.
     * @return          If the square contains an X Checker.
     */
    public boolean isX(Square square) {
        return (square.isX());
    }
    
    /**
     * Checks if a square contains an O Checker.
     * @param square    The square to check.
     * @return          If the square contains an O Checker.
     */
    public boolean isO(Square square) {
        return (square.isO());
    }
    
    /**
     * Checks if a square is empty.
     * @param square    The square to check.
     * @return          If the square contains is empty.
     */
    public boolean isEmpty(Square square) {
        return (square.equals(E));
    }
    
    /**
     * Checks if there are no X Checkers remaining on the board.
     * @return if there are no X Checkers remaining on the board
     */
    public boolean noX() {
        return xCount <= 0;
    }
    
    /**
     * Checks if there are no O Checkers remaining on the board.
     * @return if there are no O Checkers remaining on the board
     */
     public boolean noO() {
        return oCount <= 0;
    }

    /**
     * Creates a String representation of the CheckerBoard.
     * @return A String representation of the CheckerBoard.
     */
    @Override
     public String toString(){
        String string = "";
        for (int i = board.length - 1; i >= 0; i--) {
            Square[] row = board[i];
            for (Square square : row) {
                string += square.toString();
            }
            string += "\n";
        }        
        return string;  
    }
      
    /**
     * Gets the board.
     * @return  The board.
     */
     public Square[][] getBoard() {
        return board;
    }
    
     /**
     * Gets the collection of X checkers. 
     * @return the array of X checkers.
     */
    Checker[] getXPieces() {
    	 return xPieces;
     }
     
     /**
     * Gets the collection of O checkers. 
     * @return the array of O checkers.
     */
    Checker[] getOPieces() {
    	 return oPieces;
     }
    
     /**
     * Gets the current player.
     * @return  The current player.
     */
    public Square getCurrentPlayer() {
    	 return currentPlayer;
     }
     
     /**
     * Gets the current opponent.
     * @return  The opponent.
     */
    public Square getOpponent() {
    	 return opponent;
     }
     
     /**
      * Switches the turn to the other player.
      */
    public void switchTurn() {
         Square temp = currentPlayer;
         currentPlayer = opponent;
         opponent = temp;
     }
     
     
    /**
     * Square is a single square on the checkerboard grid
     **/
    public class Square {
        
        /** The text representation of this type of Square */
        private String type;
        
        /** The x and y coordinates of the Square */
        protected int[] position = {-1, -1};
        
        /**
         * Constructor
         * @param type The type of square.
         */
        public Square(String type){
            this.type = type;
        }
  
        /**
         * Checks if the square is of type X.
         * @return If the square is of type X.
         */
         public boolean isX(){
            return (type.equals(X));
        }
        
        /**
         * Checks if the square is of type O.
         * @return If the square is of type O.
         */
         public boolean isO(){
            return (type.equals(O));
        }
        
        /**
         * Checks if the square is empty.
         * @return If the square is of type E.
         */
         public boolean isEmpty() {
            return (this.equals(E));
        }
              
        /**
         * Get the String representation of the Square.
         * @return The String representation of the Square.
         */
        @Override
        public String toString(){
            return type;
        }
        
        /**
         * Getter for position.
         * @return The position;
         */
        int[] getPosition() {
            return position;
        }
    }
    
    /**
     * Checker is a single checker piece
     **/
    public class Checker extends Square {      
       
    	/** Whether the Checker has been captured */
    	private boolean captured;
    	
        /**
         * Constructor.
         * @param row       The x coordinate.
         * @param column    The y coordinate.
         * @param type      The type.
         */
        Checker(int row, int column, String type) {
            super(type);
            position = new int[] {row, column};
            if (isX()) {
                xCount ++;
            } else {
                oCount ++;
            }   
            captured = false;
        }   
        
        /**
         * Setter for position.
         * @param row       The new x coordinate.
         * @param column    The new y coordinate.
         */
        void setPosition(int row, int column) {
            position[0] = row;
            position[1] = column;
        }
        
        /**
         * Sets this Checker's captured to true; 
         */
        void capture() {
        	setPosition(-1, -1);
        	captured = true;
        }  
            
        /**
         * Checks if the Checker has been captured.
         * @return if the Checker has been captured.
         */
        public boolean isCaptured() {
        	return captured;
        }
    }
    
}
