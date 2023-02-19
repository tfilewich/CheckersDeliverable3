package core;


import ui.CheckersTextConsole;

/**
 * Driver runs the chess program
 *  contains the main method
 * 
 * @author Tyler Filewich
 * @version 1.1  2022-01-27
 */
public class Driver {
	
	/**
	 * Main method to run game.
	 * @param args	The args.
	 */
	public static void main(String[] args) {
		
		Driver driver = new Driver();
		driver.run();
	}
	
	
    /** The board to be used for the game */
    private CheckerBoard board;
    
    /** The text ui to be used for the game */
    private CheckersTextConsole console;
    
    /** The computer opponent for 1 player games */
    CheckersComputerPlayer computer; 
    
    /** The coordinates to be used for moves */
    private int[] coordinates;
    
    /** If the game has been won */
    private  boolean won;
	
	/** The mode for the game (1 for 1-Player, 2 for 2-Player)*/
	int gameMode;
	
	
    /**
     * Runs a complete game of Chess.
     */
	void run() {
		//start game
    	console.begin();
    	
    	//select 1 player or 2 player mode
    	int mode = chooseMode(); 
    	if (mode == 1) {
    		computer = new CheckersComputerPlayer(board);
    		console.confirmOnePlayerMode();
    	}

    	//play game
    	while (! won) {			 
            console.displayBoard(board);
            if (mode == 1 && board.getCurrentPlayer().isO()) {
            	playComputerTurn();
            }
            else {
            	playTurn();
            }
            won = CheckersLogic.checkWin(board);
            board.switchTurn(); 
        }
    	
    	//end game
        announceWin(); 
	}

    
    /**
     * Constructor
     */
    public Driver(){
    	board = new CheckerBoard();
    	console = new CheckersTextConsole();
    	coordinates = new int[] {0, 0, 0, 0};
    	won = false;
    }
    
	
    
    /**
     * Allows user to select 1-Player or 2-Player game.
     * @return 1 for 1-Player, 2 for 2-Player
     */
    public int chooseMode() {
    	int mode;
    	try {
    		mode = console.getMode();			//get mode from user
    	}
    	catch (IllegalArgumentException ex) {	//invalid input
    		console.rejectInput();				//reject invalid input
    		mode = chooseMode();				//get mode from user
    	}
    	return mode;
    }
    
    /**
     * Plays a single move for one player.                                   
     */
    private void playTurn(){	
        boolean valid = false;
        
        if (board.getCurrentPlayer().isX()) {
            console.requestXMove();
        } else {
            console.requestOMove();
        }
        
        while (!valid) {
            coordinates = console.getMove();	//get move from player
            valid = CheckersLogic.isValid(coordinates, board);
            if (!valid) {
                console.rejectInput();			//reject invalid move
            }
        }
        board.movePiece(coordinates);			//make valid move
    }
    
    /**
     * Plays a single move for the computer.
     */
    private void playComputerTurn() {
    	console.requestComputerMove();			//display computer turn
    	computer.delay();						//wait 2 seconds
    	computer.playTurn();					//make computer move
    }
    
    /**
     * Ends the game.
     */
    private void announceWin() {
        //display final board
    	console.displayBoard(board);
    	
    	//announce winner
        if (board.getCurrentPlayer().isX()) {
            console.oWon();
        } else console.xWon();
    }
    
}
