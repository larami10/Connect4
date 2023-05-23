/**
 * The contents of this class hold the layout, instructions and interactions of a
 * Connect4 board game that is limited to 2 players. Each player is assigned 21 'X' or
 * 'O' pieces in which they will take turns to connect 4 of their pieces in a row on
 * the Connect 4 board to win with the possibility of a tied game if no winning 
 * condition is met and both players are out of pieces to play.
 *
 * @author Luis Ramirez-Zamacona with guidance from Professor Buck and SER216 course
 * @version 09/18/2019
 */
package core;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Connect4
{
	//Private variable used in Connect4 class
	protected char board[][];
	protected int playerXSpace;
	protected int playerOSpace;
	protected boolean game = false;
	protected int pXnumOfPieces = 21;
	protected int pOnumOfPieces = 21;
	protected int[] numberOfCols = {6, 6, 6, 6, 6, 6, 6};
	protected int col;
	protected boolean check;
	Scanner scan = new Scanner(System.in);
	
	/**
	 * Initializes the blank Connect4 board as a 2D char array
	 * 
	 * @return The Connect4 board
	 */
	public char[][] makeBoard()
    {        
        char board[][] =
			{{' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' '}};
    
        return board;
    }
	
	/**
	 * This method will be used as the base interaction for the
	 * players and will use other methods to display, update,
	 * and/or declare the winner of the Connect4 game.
	 */
	public void gameLogic()
	{
		// While game is not true (game is not over)
		while(game != true)
		{
			/*
			 * if both players are out of pieces and no winning
			 * condition has been met, declare the game as a tie.
			 */
			if(pXnumOfPieces == 0 && pOnumOfPieces == 0)
			{
				System.out.println("\nThe game is a tie!");
				game = true;
			}
			// else continue with Connect4 game
			else
			{
				check = false;
				
				while(!check)
				{
					playerXMove();
				}
				
				// update board with playerX's move
				updateBoard(board, playerXSpace, 'X');
				
				// Update number of pieces left for playerX
				pXnumOfPieces--;
				
				// Declare playerX winner if winning condition has been met for playerX
				if(checkWin(board, 'X') == 4)
				{
					System.out.println("\nPlayerX wins!");
					game = true;
				}
				// else continue with playerO's move
				else
				{
					check = false;
					
					while(!check)
					{
						playerOMove();
					}
					
					// update board with playerO's move
					updateBoard(board, playerOSpace, 'O');
					
					// Update number of pieces left for playerO
					pOnumOfPieces--;
		
					// Declare playerO winner if winning condition has been met for playerO
					if(checkWin(board, 'O') == 4)
					{
						System.out.println("\nPlayerO wins!");
						game = true;
					}
				}
			}
		}
	}
	
	protected void playerXMove()
	{
		try
		{
			// Ask playerX to choose a column
			System.out.println("\nPlayerX � your turn. Choose a column number from 1-7.");
			playerXSpace = scan.nextInt();
			
			// Verify that column chosen is within range
			while(playerXSpace > 7 || playerXSpace < 1)
			{
				System.out.println("Please choose a number from 1-7.");
				playerXSpace = scan.nextInt();
			}
			
			// Associate column chosen to array index
			col = playerXSpace - 1;
			
			// Subtract 1 from array index to indicate 1
			// less space available in chosen column
			numberOfCols[col] -= 1;
			
			/*
			 * While there are no more spaces available in chosen column, ask
			 * playerX to chose a different column number until appropriate
			 * column number is chosen.
			 */
			while(numberOfCols[col] < 0)
			{
				System.out.println("There are no more spaces available for column "
						+ playerXSpace + ". Please choose a different number.");
				playerXSpace = scan.nextInt();
				
				// Verify that column chosen is within range
				while(playerXSpace > 7 || playerXSpace < 1)
				{
					System.out.println("Please choose a number from 1-7.");
					playerXSpace = scan.nextInt();
				}
				
				// Associate column chosen to array index
				col = playerXSpace - 1;
				
				// Subtract 1 from array index to indicate 1
				// less space available in chosen column
				numberOfCols[col] -= 1;
			}
			
			check = true;
		}
		catch(InputMismatchException ex)
		{
			scan.next();
			System.out.print("Invalid input.");
		}
	}
	
	protected void playerOMove()
	{
		try
		{
			// Ask playerO to choose a column
			System.out.println("\nPlayerO � your turn. Choose a column number from 1-7.");
			playerOSpace = scan.nextInt();
			
			// Verify that column chosen is within range
			while(playerOSpace > 7 || playerOSpace < 1)
			{
				System.out.println("Please choose a number from 1-7.");
				playerOSpace = scan.nextInt();
			}
			
			// Associate column chosen to array index
			col = playerOSpace - 1;
			
			// Subtract 1 from array index to indicate 1
			// less space available in chosen column
			numberOfCols[col] -= 1;
			
			/*
			 * While there are no more spaces available in chosen column, ask
			 * playerO to chose a different column number until appropriate
			 * column number is chosen.
			 */
			while(numberOfCols[col] < 0)
			{
				System.out.println("There are no more spaces available for column "
						+ playerOSpace + ". Please choose a different number.");
				playerOSpace = scan.nextInt();
				
				// Verify that column chosen is within range
				while(playerOSpace > 7 || playerOSpace < 1)
				{
					System.out.println("Please choose a number from 1-7.");
					playerOSpace = scan.nextInt();
				}
				
				// Associate column chosen to array index
				col = playerOSpace - 1;
				
				// Subtract 1 from array index to indicate 1
				// less space available in chosen column
				numberOfCols[col] -= 1;
			}
			
			check = true;
		}
		catch(InputMismatchException ex)
		{
			scan.next();
			System.out.print("Invalid input.");
		}
	}
	
	/**
	 * This method is used to find the next empty space on the column
	 * chosen by the player to update the Connect4 board according
	 * to the current player's column choice and display the updated
	 * board to the players.
	 * 
	 * @param board The current status of the Connect4 board
	 * @param column int 1-7 dependent on player's choice
	 * @param value 'X' or 'O'depending on latest piece played
	 * @return an updated board
	 */
	public void updateBoard(char[][] board, int column, char value)
	{
		// Player's chosen column converted to appropriate array column index
		int boardColumn = column - 1;
		
		// Number of rows on board
		int boardRow = board.length - 1;
		
		// Initialize successfullMove to false
		boolean successfulMove = false;
		
		// check for the next empty space on the chose column
		while(successfulMove != true)
		{
			// if ' ' set space to 'X' or 'O'
			if(board[boardRow][boardColumn] == ' ')
			{
				board[boardRow][boardColumn] = value;
				successfulMove = true;
			}
			// else move to the next available row on chosen column
			else
			{
				boardRow--;
			}
		}
		
		// Display board
		numberColumns();
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				System.out.print("|" + board[i][j]);
			}
			System.out.println("|");
		}
		numberColumns();
	}
	
	/**
	 * This method carries the current board and latest piece that
	 * was played as parameters to find a winning condition
	 * (horizontal, vertical, diagonal) on the board and returns
	 * count. If a winning condition is found, this method will return
	 * 4 to signify a 4 in a row win.
	 * 
	 * @param board The current status of the Connect4 board
	 * @param playerPiece 'X' or 'O' depending on player that moved
	 * @return count The number of playerPieces in a row, need 4 to win
	 */
	public int checkWin(char[][] board, char playerPiece)
	{
		// Number of rows on board
		int boardRow = board.length - 1;
		
		// Number of columns on board
		int boardColumn = board[0].length - 1;
		
		// Initialize count to 0
		int count = 0;
		
		// Nested for loop is used to find a horizontal win on the board.
		for(int i = boardRow; i >= 0; i--)
		{
			for(int j = 0; j < boardColumn; j++)
			{
				// if statement used to increment count
				if(board[i][j] == playerPiece)
				{
					count++;
					
					// return 4 if 4 in a row on board
					if(count == 4)
					{
						return count;
					}
				}
				else
				{
					count = 0;
				}
			}
			count = 0;
		}
		
		// Nested for loop is used to find a vertical win on the board
		for(int j = 0; j < boardColumn; j++)
		{
			for(int i = boardRow; i >= 0; i--)
			{
				// if statement used to increment count
				if(board[i][j] == playerPiece)
				{
					count++;
					
					// return 4 if 4 in a row on board
					if(count == 4)
					{
						return count;
					}
				}
				else
				{
					count = 0;
				}
			}
			count = 0;
		}

		/*
		 * Nested for loop is used to find a diagonal win on the board.
		 * This loop only finds the diagonals on the bottom left side
		 * of the board with diagonals going down diagonally to the right.
		 */
		for(int i = 0; i < boardRow; i++)
		{
		    for(int row = i, col = 0; row <= boardRow && col <= boardColumn; row++, col++)
		    {
		    	// if statement used to increment count
		        if(board[row][col] == playerPiece)
		        {
		            count++;
		            
		            // return 4 if 4 in a row on board
		            if(count == 4)
		            {
		            	return count;
		            }
		        }
		        else
				{
					count = 0;
				}
		    }
		    count = 0;
		}
		
		/*
		 * Nested for loop is used to find a diagonal win on the board.
		 * This loop only finds the diagonals on the top right side
		 * of the board with diagonals going down diagonally to the right.
		 */
		for(int i = 1; i < boardColumn; i++)
		{
		    for(int row = 0, col = i; row <= boardRow && col <= boardColumn; row++, col++)
		    {
		    	// if statement used to increment count
		        if(board[row][col] == playerPiece)
		        {
		            count++;
		            
		            // return 4 if 4 in a row on board
		            if(count == 4)
		            {
		            	return count;
		            }
		        }
		        else
				{
					count = 0;
				}
		    }
		    count = 0;
		}
		
		/*
		 * Nested for loop is used to find a diagonal win on the board.
		 * This loop only finds the diagonals on the top left side
		 * of the board with diagonals going down diagonally to the left.
		 */
		for(int i = boardColumn; i >= 0; i--)
		{
		    for(int row = 0, col = i; row <= boardRow && col >= 0; row++, col--)
		    {
		    	// if statement used to increment count
		        if(board[row][col] == playerPiece)
		        {
		            count++;
		            
		            // return 4 if 4 in a row on board
		            if(count == 4)
		            {
		            	return count;
		            }
		        }
		        else
				{
					count = 0;
				}
		    }
		    count = 0;
		}
		
		/*
		 * Nested for loop is used to find a diagonal win on the board.
		 * This loop only finds the diagonals on the bottom right side
		 * of the board with diagonals going down diagonally to the left.
		 */
		for(int i = 1; i < boardRow; i++)
		{
		    for(int row = i, col = boardColumn; row <= boardRow && col >= 0; row++, col--)
		    {
		    	// if statement used to increment count
		        if(board[row][col] == playerPiece)
		        {
		            count++;
		            
		            // return 4 if 4 in a row on board
		            if(count == 4)
		            {
		            	return count;
		            }
		        }
		        else
				{
					count = 0;
				}
		    }
		    count = 0;
		}
		
		return count;
	}
	
	/**
	 * drawBoard method will provide a visual of the board
	 * to the players
	 */
	public void drawBoard()
	{
		board = makeBoard();
		
		numberColumns();
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				System.out.print("|" + board[i][j]);
			}
			System.out.println("|");
		}
		
		numberColumns();
	}
	
	/**
	 * numberColumns() will provide the numbers of the
	 * columns on the top and bottom for the user to be able
	 * to distinguish their moves easier.
	 */
	protected void numberColumns()
	{
		for(int j = 1; j < 8; j++)
		{
			System.out.print(" " + j);
		}
		System.out.println();
	}
}