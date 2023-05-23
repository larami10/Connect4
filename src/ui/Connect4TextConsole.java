/**
 * The purpose of Connect4TextConsole class is to initialize
 * the game play for a player vs player or player vs computer 
 * Connect 4 game by calling on the drawBoard method to provide
 * a visual of the Connect4 board to the players and call the
 * appropriate gameLogic method which will interact
 * with the players to proceed with the game play and declare a
 * winner if any.
 *  
 * @author Luis Ramirez-Zamacona with guidance from Professor Buck and SER216 course
 * @version 09/18/2019
 */
package ui;

import java.util.Scanner;

import core.Connect4;
import core.Connect4ComputerPlayer;

public class Connect4TextConsole
{
	static Scanner scan = new Scanner(System.in);
	private static String playerType;
	private static String playerChosen = "";
	private static String uiType = "";
	private static String uiChosen = "";
	
	public static void main(String[] args)
	{
		// Create an instance of Connect4 and Connect4ComputerPlayer classes
		Connect4 connect4 = new Connect4();
		Connect4ComputerPlayer connect4CPU = new Connect4ComputerPlayer();

		System.out.println("Welcome to Connect4!");
		
		// User will choose which UI to player Connect4 on
		chooseUIType();
		
		// If player enters C, begin console-based Connect4 game
		if(uiType.equals("C"))
		{
			System.out.println("Enter 'P' if you want to play against another player"
					+ " or enter 'C' to play against computer.");
			playerType = scan.next().toUpperCase();
					
			// Ask user to enter P or C until valid input is met
			while(playerType.equals("P") == false && playerType.equals("C") == false)
			{
				System.out.println("Please enter 'P' if you want to play against another player"
						+ " or enter 'C' to play against computer.");
				playerType = scan.next().toUpperCase();
			}
						
			if(playerType.equals("C"))
			{
				playerChosen += "computer player";
			}
			else
			{
				playerChosen += "another player";
			}
						
			System.out.println("Player has chosen to play against " + playerChosen + ".\n");
			
			// Draw a blank Connect4 board
			connect4.drawBoard();
		
			// If player enters P, begin Player vs. Player Connect4 game
			if(playerType.equals("P"))
			{
				System.out.println("Begin Game. Player vs. Player!");
				// Call on Connect4 gameLogic to play
				connect4.gameLogic();
			}
			// Else if player enters C, begin Player vs. Computer Connect4 game
			else if(playerType.equals("C"))
			{
				System.out.println("Begin Game. Player vs. Computer Player!");
				connect4CPU.gameLogic();
			}
		}
		// Else if player enters G, begin GUI Connect4 game
		else if(uiType.equals("G"))
		{
			javafx.application.Application.launch(Connect4GUI.class);
		}
	}
	
	/**
	 * chooseUIType instructs the player to choose between playing the
	 * console-based UI or the GUI.
	 */
	public static void chooseUIType()
	{
		System.out.println("Enter 'C' to play the console-based UI or enter 'G' to"
				+ " play the GUI.");
		uiType = scan.next().toUpperCase();
		
		// Ask user to enter C or G until valid input is met
		while(uiType.equals("C") == false && uiType.equals("G") == false)
		{
			System.out.println("Please enter 'C' to play the console-based UI or"
					+ "enter 'G' to play the GUI.");
			uiType = scan.next().toUpperCase();
		}
		
		if(uiType.equals("C"))
		{
			uiChosen += "console-based UI";
		}
		else
		{
			uiChosen += "GUI";
		}
		
		System.out.println("Player has chosen to play Connect4"
				+ " on the " + uiChosen + ".\n");
	}
}