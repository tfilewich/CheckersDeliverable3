package ui;
import java.util.Scanner;

/**
 * CheckersTextConsole contains the console based text ui
 * 
 * @author Tyler Filewich
 * @version 1.1  2022-01-27
 */
public class CheckersTextConsole {
    
	
	/**
	 * Constructor.
	 */
	public CheckersTextConsole(){
	}
	
    /** Scanner Object for user input */
    private Scanner scanner = new Scanner(System.in);
    
    /** Name of the X player */
    private final String X = "PlayerX";
    
    /** Name of the O player */
    private  final String O = "PlayerO";
    
    /** Phrase to announce a win */
    private final String WON = " Won the Game";
    
    /** Phrase to get a move from a player */
    private final String turn = " - your turn.\nChoose a cell position of piece to be moved and the new position. e.g., 3a-4b";
    
    /** 
     * Announces the beginning of a game.
     */
    public void begin() {
        System.out.println("Begin Game.\n Enter 'P' if you want to play against another player; enter 'C' to play against computer.");
    }
    
    /**
     * Gets the user input to select 1-Player or 2-Player mode.
     * @return 1 for 1-Player, 2 for 2-Player.
     * @throws IllegalArgumentException when input is invalid.
     */
    public int getMode() throws IllegalArgumentException{
        String input;
        input = scanner.next();
        if (input.equalsIgnoreCase("P")) {
        	return 2;
        }
        if (input.equalsIgnoreCase("C")) {
        	return 1;
        }
        else {
        	throw new IllegalArgumentException("Invalid user input");
        }
    }
    
    /**
     * Announces that user is playing against computer.
     */
    public void confirmOnePlayerMode() {
    	System.out.println("Start game against computer. You are Player X and Computer is Player O.");
    }
    
    /**
     * Displays the current state of the CheckerBoard.
     * @param checkerBoard The CheckerBoard to be displayed.
     */
    public void displayBoard(Object checkerBoard){
        System.out.println(checkerBoard);
    }
    
    /** 
     * Prompts player X to input a move 
     */
    public void requestXMove() {
        System.out.println(X + turn);
    }
    
    /** 
     * @deprecated  Multi-jump functionality removed for 1.1
     * Prompts player X to input a follow-up move after a jump. 
     */
    public void requestAnotherXMove() {
        System.out.println(X + " play again");
    }
    
    /** 
     * Prompts player O to input a move 
     */
    public void requestOMove() {
        System.out.println(O + turn);
    }
    
	/**
	 * Announces that it is the computer's turn.
	 */
	public void requestComputerMove() {
		System.out.println(O + " -  Computer's turn.\n");
	}
    
    /** 
     * Prompt to try again after an invalid input.
    */
    public void rejectInput(){
        System.out.println("Invalid input.  Try again");
    }
    
    /** 
     * Announces a win by player X 
     */
    public void xWon() {
        System.out.println(X + WON);
    }
    
    /** 
     * Announces a win by player O 
     */
    public void oWon() {
        System.out.println(O + WON);
    }
    

    /**
     * Gets a move from the user.
     * @return  The coordinates of a move.
     */
    public int[] getMove(){
        int coordinates[];
        String input;
        input = scanner.next();
        try {
        	coordinates = getCoordinates(input);
        }
        catch (IllegalArgumentException ex) {
        	rejectInput();
        	coordinates = getMove();
        }
        return coordinates;
    }
    
    
    /**
     * Converts user input to coordinates of a move.
     * @param input     The user input.
     * @return  The coordinates of a move if the input is in a valid format, 
     *          {0, 0, 0, 0} if it is not.
     */
    public int[] getCoordinates(String input) {
        int[] coordinates;
        int fromRow = 0;
        int fromColumn = 0;
        int toRow = 0;
        int toColumn = 0;
        char first;
        char second;
        char third;
        char fourth;
        //check input length
        if (input.length() >= 5) {
            first = input.charAt(0);
            second = input.charAt(1);
            third = input.charAt(3);
            fourth = input.charAt(4);   
        } else {
        	throw new IllegalArgumentException("Invalid input.  Must be 5 characters.  Length: " + input.length());
        }
        //check input type
        if (Character.isDigit(first)) {
            fromRow = numberToRow(first);
            if (Character.isLetter(second)) {
                fromColumn = letterToColumn(second);
                if (Character.isDigit(third)) {
                    toRow = numberToRow(third); 
                    if (Character.isLetter(fourth)) {
                        toColumn = letterToColumn(fourth);
                    }
                }
            }
        }
        //check input values
        coordinates = new int[] {fromRow, fromColumn, toRow, toColumn};
        for (int i : coordinates) {
            if (i < 1 || i > 8) {
            	throw new IllegalArgumentException("Invalid input at char: " + i);
            }
        }
        return coordinates;
    }
    
    /**
     * Converts an integer column position to its ascii letter representation.
     * @param number    The column value.
     * @return          The letter.
     */
    private char columnToLetter(int column){
        return (char) (column + 'a' - 1 );  
    }
    
    /**
     * Converts an integer row position to its ascii number representation.
     * @param number    The row value.
     * @return          The number.
     */
    private char rowToNumber(int row) {
        return (char) (row + '0');
    }  
    
    
    /**
     * Converts an ascii letter to an integer column position.
     * @param letter    The letter.
     * @return          The column value.
     */
    private int letterToColumn(char letter) {
        return (int)(letter - 'a' + 1);
    }
    
    /**
     * Converts an ascii number to an integer row position.
     * @param num       The number.
     * @return          The row value.
     */
    private int numberToRow(char number) {
        return (int)(number - '0');
    }
     
    
    
    
    
    /** 
     * @deprecated  Multi-jump functionality removed for 1.1
     * Prompts player O to input a follow-up move after a jump. 
     * */
    public void requestAnotherOMove() {
        System.out.println(O + " play again");
    }
    
    /**
     * @deprecated  Multi-jump functionality removed for 1.1
     * Gets a follow-up move from the user.
     * @param previousCoordinates The coordinates of the previous move.
     * @return The coordinates of a follow-up move.
     */
    public int[] getAnotherMove(int[] previousCoordinates){
        int coordinates[];
        String input;
        String output = "";
        
        output += rowToNumber(previousCoordinates[2]);
        output += columnToLetter(previousCoordinates[3]);
        output += "-";
        
        System.out.print(output); 
        input = output + scanner.next();
        System.out.println();
        coordinates = getCoordinates(input);
        return coordinates;
    } 


}


