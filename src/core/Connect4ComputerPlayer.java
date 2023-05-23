/**
 * This class inherits the methods from Connect4.java. This class will provide
 * the user the chance to play against the computer that will make random moves
 * on the Connect4 board.
 *
 * @author Luis Ramirez-Zamacona with guidance from Professor Buck and SER216 course
 * @version 09/18/2019
 */
package core;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Connect4ComputerPlayer extends Connect4
{
	/**
	 * This method will be used as the base interaction for the
	 * player and the computer. This method will use other methods
	 * from Connect4.java to display, update, and/or declare the
	 * winner of the Connect4 game.
	 */
	public void gameLogic()
	{
		Random random = new Random();
		
		// Create Connect4 board
		board = super.makeBoard();
		
		// While game is not true (game is not over)
		while(game != true)
		{
			/*
			 * If both players are out of pieces and no winning
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
				
				// update board with player one's move
				super.updateBoard(board, playerXSpace, 'X');
				
				// Update number of pieces left for playerX
				pXnumOfPieces--;
				
				// Declare playerX winner if winning condition has been met for playerX
				if(super.checkWin(board, 'X') == 4)
				{
					System.out.println("\nPlayerX wins!");
					game = true;
				}
				// else continue with playerO's move
				else
				{
					// Notify user of computer's turn
					System.out.println("\nComputer's turn...");
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Computer's random move between 1 and 7
					playerOSpace = random.nextInt(7 - 1 + 1) + 1;
					
					// Associate column chosen to array index
					col = playerOSpace - 1;
					
					// Subtract 1 from array index to indicate 1
					// less space available in chosen column
					numberOfCols[col] -= 1;

					/*
					 * Set computer's move to a different random number
					 * if initial move is set to a full column
					 */
					while(numberOfCols[col] < 0)
					{
						playerOSpace = random.nextInt(7 - 1 + 1) + 1;
						
						// Associate column chosen to array index
						col = playerOSpace - 1;
						
						// Subtract 1 from array index to indicate 1
						// less space available in chosen column
						numberOfCols[col] -= 1;
					}
					
					// Show PlayerX the computer's move
					System.out.println("\nPlayerO chose column: " + playerOSpace + "\n");
					
					// update board with computer's move
					super.updateBoard(board, playerOSpace, 'O');
					
					// Update number of pieces left for computer
					pOnumOfPieces--;
		
					// Declare computer winner if winning condition has been met for playerO
					if(super.checkWin(board, 'O') == 4)
					{
						System.out.println("\nPlayerO wins!");
						game = true;
					}
				}
			}
		}
	}
}