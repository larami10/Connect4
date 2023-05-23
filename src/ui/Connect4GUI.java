/**
 * The purpose of Connect4GUI class is to provide the user
 * with a graphical user interface of a Connect4 game in which
 * they can choose to play versus another player or play versus
 * a computer player. The players will first be provided a board
 * and the current player's turn will be instructed at the bottom
 * of the board. The players have the ability to click on any cell
 * to make their moves until winning condition is met or the board
 * is full leading to a draw
 *  
 * @author Luis Ramirez-Zamacona with guidance from Professor Buck and SER216 course
 * @version 09/18/2019
 */
package ui;

import core.Connect4;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.animation.PauseTransition;  
import javafx.animation.SequentialTransition; 

public class Connect4GUI extends Application
{
	private int col;
	
	// Indicate which player has a turn, initially it is the Red player
	private String who = "Red";
	
	// Indicate which player has a turn, initially it is the X player
	private char whoseTurn = 'X';
	
	// Indicates that computer Player is off
	private boolean computerOn = false;
	
	// Pane to hold cell
	private GridPane pane = new GridPane();
	
	// BorderPane to hold GridPane and Label
	private BorderPane borderPane = new BorderPane();
	
	// Create and initialize cell
	private Cell[][] cell = new Cell[6][7];
	
	// Create Connect4 Object
	private Connect4 connect = new Connect4();
	
	// Create a 2D board array
	private char[][] board = connect.makeBoard();
	
	// Array that shows the number of rows in columns
	private int[] numberOfRows = {5, 5, 5, 5, 5, 5, 5};
	
	// Create and initialize a status label
	private Label lblStatus = new Label("Red Player's turn to play");
	
	/**
	 * Override the start method in the Application class to first create a
	 * box with 2 buttons requesting the user to select whether they would
	 * like to play versus another player or against the computer player.
	 * Once either button is selected, create the Connect4 board to
	 * display to the players and begin the game.
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		
		Text text = new Text("Select Game Type");
		text.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 20));
		
		// Create Player vs Player Button
		Button player = new Button("Player vs Player");
		player.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent e)
			{
				for(int i = 0; i < 6; i++)
				{
					for(int j = 0; j < 7; j++)
					{
						pane.add(cell[i][j] = new Cell(), j, i);
						GridPane.setHgrow(cell[i][j], Priority.ALWAYS);
						GridPane.setVgrow(cell[i][j], Priority.ALWAYS);
					}
				}
				
				borderPane.setCenter(pane);
				borderPane.setBottom(lblStatus);
				Scene scene = new Scene(borderPane, 450, 170);
				primaryStage.setScene(scene);
			}
		});
		
		// Create Player vs Computer Button
		Button computer = new Button("Player vs. Computer");
		computer.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent e)
			{
				// Set computerOn to true if this button selected
				computerOn = true;
				for (int i = 0; i < 6; i++)
				{
					for (int j = 0; j < 7; j++)
					{
						pane.add(cell[i][j] = new Cell(), j, i);
						GridPane.setHgrow(cell[i][j], Priority.ALWAYS);
						GridPane.setVgrow(cell[i][j], Priority.ALWAYS);
					}
				}
				
				borderPane.setCenter(pane);
				borderPane.setBottom(lblStatus);
				Scene scene = new Scene(borderPane, 450, 170);
				primaryStage.setScene(scene);
			}
		});

		vBox.getChildren().add(text);
		hBox.getChildren().addAll(player, computer);
		vBox.getChildren().add(hBox);
	    
		player.prefWidthProperty().bind(vBox.widthProperty());
		player.prefHeightProperty().bind(vBox.prefHeightProperty());
		computer.prefWidthProperty().bind(vBox.widthProperty());
		computer.prefHeightProperty().bind(vBox.prefHeightProperty());
		
		vBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vBox, 450, 170);
		primaryStage.setTitle("Connect4"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		GridPane.setFillHeight(borderPane, true);
		GridPane.setFillWidth(borderPane, true);
	}

	/**
	 * Check if the board is full.
	 * @return false if not full, else return true
	 */
	public boolean isFull() 
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				if (board[i][j] == ' ')
				{
					return false;
				}
			}
		}
		return true;
    }

	/**
	 * Cell objects are created that will be add to the GridPane. These
	 * cells will initially show will hollowed circles to signify that
	 * the cell does not contain a token. The hollowed circles will
	 * be replaced by the Red or Yellow token as the players or
	 * computers make their moves.
	 */
	public class Cell extends Pane 
	{
		// Token used for this cell
		private char token = ' ';

		/**
		 * Create the Cell and add hollow circles to the cells to show the
		 * player that the cell is empty. These hollow circles will be replaced
		 * by the player's tokens as the game continues.
		 */
		public Cell() 
		{
			setStyle("-fx-border-color: POWDERBLUE; -fx-background-color: blue;"
					+ "-fx-font-size:20px;");
			this.setPrefSize(2000, 2000);
		 	
			Circle hollowCircle = new Circle(this.getHeight() / 2,
					this.getWidth() / 2, this.getHeight() / 2);
		 	hollowCircle.setFill(Color.WHITE);
		 	hollowCircle.centerXProperty().bind(this.widthProperty().divide(2));
		 	hollowCircle.centerYProperty().bind(this.heightProperty().divide(2));
		 	hollowCircle.radiusProperty().bind(this.heightProperty()
		 			.divide(2).subtract(2));
		 	this.getChildren().add(hollowCircle);

		 	this.setOnMouseClicked(e -> handleMouseClick());
		 	
		 	this.prefWidthProperty().bind(pane.widthProperty());
		 	this.prefHeightProperty().bind(pane.heightProperty());
		}
		
		/**
		 * Get the token
		 * @return token
		 */
		public char getToken()
		{
			return token;
		}
		
		/**
		 * Create and add the Players' Tokens on to the Connect4 board
		 * @param c Player's Token
		 */
		public void setToken(char c) 
		{
			token = c;
			
			// Initialize currentColumn to column clicked on pane
			int currentColumn = GridPane.getColumnIndex(this);
			col = currentColumn;
			
			// Player Red's (One's) Token
			if(token == 'X') 
			{
				// Create redCircle Token
				Circle redCircle = new Circle(this.getHeight() / 2,
						this.getWidth() / 2, this.getHeight() / 2);
				redCircle.centerXProperty().bind(this.widthProperty().divide(2));
				redCircle.centerYProperty().bind(this.heightProperty().divide(2));
				redCircle.radiusProperty().bind(this.heightProperty().divide(2).subtract(1));
				redCircle.setFill(Color.RED);
				
				// Add redCircle to the pane
				pane.add(redCircle, currentColumn, numberOfRows[col]);
				GridPane.setHalignment(redCircle, HPos.CENTER);
				GridPane.setValignment(redCircle, VPos.CENTER);
				
				// Update the board of specified cell to 'X'
				board[numberOfRows[col]][col] = 'X';
			}
			
			// Execute only if player vs player is selected
			if(computerOn == false && token == 'O')
			{
				// Create yellowCircle token
				Circle yellowCircle = new Circle(this.getHeight() / 2,
						this.getWidth() / 2, this.getHeight() / 2);
				yellowCircle.centerXProperty().bind(this.widthProperty().divide(2));
				yellowCircle.centerYProperty().bind(this.heightProperty().divide(2));
				yellowCircle.radiusProperty().bind(this.heightProperty().divide(2).subtract(1));
				yellowCircle.setFill(Color.YELLOW);
				
				// Add yellowCircle to the pane
				pane.add(yellowCircle, currentColumn, numberOfRows[col]);
				GridPane.setHalignment(yellowCircle, HPos.CENTER);
				GridPane.setValignment(yellowCircle, VPos.CENTER);
				
				// Update the board of specified cell to 'O'
				board[numberOfRows[col]][col] = 'O';
			}
			
			// Update the number of rows in column
			if(numberOfRows[col] > 0)
			{
				numberOfRows[col]-=1;
			}
		}

		/**
		 * Handle the mouse clicks that players make on the board
		 */
		private void handleMouseClick() 
		{
			pane.prefWidthProperty().bind(borderPane.widthProperty());
			pane.prefHeightProperty().bind(borderPane.heightProperty()
					.subtract(lblStatus.heightProperty()));
			pane.setMinHeight(Region.USE_PREF_SIZE);
			pane.setMaxHeight(Region.USE_PREF_SIZE);
			
			// Initialize currentColumn to column clicked on pane
			int currentColumn = GridPane.getColumnIndex(this);
			col = currentColumn;
			
			// If cell is empty and game is not over
			if (whoseTurn != ' ') 
			{
				// if row 0 of col is not empty, player makes new move
				if(board[0][col] == 'X' || board[0][col] == 'O')
				{
					if(whoseTurn == 'X')
					{
						who = "Red";
					}
					else
					{
						who = "Yellow";
					}

					lblStatus.setText("Invalid move. " + "Player "
							+ who + " please make a new move.");
					setOnMouseClicked(e -> handleMouseClick());
				}
				else
				{
					// Set token in the cell
					setToken(whoseTurn);

					// if winning condition is met, declare winner
					if(connect.checkWin(board, whoseTurn) == 4)
					{
						if(whoseTurn == 'X')
						{
							who = "Red";
						}
						else
						{
							who = "Yellow";
						}
						lblStatus.setText(who + " Player won! The game is over");
						whoseTurn = ' '; // Game is over
					}
					// else if board is full, declare a draw
					else if(isFull())
					{
						lblStatus.setText("Draw! The game is over");
						whoseTurn = ' '; // Game is over
					}
					// else continue with Connect4 game
					else
					{
						// Change the turn
						whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
						who = (whoseTurn == 'O') ? "Yellow" : "Red";
						// Display whose turn
						lblStatus.setText(who + " Player's turn");
					}
					
					// Execute only if player is playing against Computer
					if(computerOn == true && whoseTurn == 'O')
					{
						// Computer player pause for 2 seconds
						PauseTransition pause = new PauseTransition(Duration.seconds(2));
						pause.setOnFinished(event -> {
							computerTurn();

							// If winning condition is met, declare Computer Player winner
							if(connect.checkWin(board, 'O') == 4)
							{
								lblStatus.setText("Computer Player won! The game is over");
								whoseTurn = ' '; // Game is over
							}
							// else if board is full, declare a draw
							else if(isFull())
							{
								lblStatus.setText("Draw! The game is over");
								whoseTurn = ' '; // Game is over
							}
							// else continue with Connect4 game
							else
							{
								// Adjust column number by 1 to display to user accordingly
								int computerMove = col + 1;
								// Change the turn
								whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
								// Display Computer Player's move and Display Red Player's turn
								lblStatus.setText("Computer Player placed Yellow token"
										+ " in column: " + computerMove +". Red Player's turn");
							}
						});
						pause.play();
					}
				}
			}
		}
	    
		/**
		 * computerTurn will create a yellowCircle and find a random number that
		 * it can use as the column number and add the yellowCircle to the board. 
		 */
		private void computerTurn()
		{
			Circle yellowCircle = new Circle(this.getHeight() / 2,
					this.getWidth() / 2, this.getHeight() / 2);
			yellowCircle.centerXProperty().bind(this.widthProperty().divide(2));
			yellowCircle.centerYProperty().bind(this.heightProperty().divide(2));
			yellowCircle.radiusProperty().bind(this.heightProperty().divide(2).subtract(1));
			Random random = new Random();
			
			// Initialize random integer between 0and 6
			int randomColumn = random.nextInt(7);
			
			// Set col equal to randomColumn
			col = randomColumn;
			
			// Continue to search for a random column if row 0 already has a token
			while(board[0][col] == 'X' || board[0][col] == 'O')
			{
				randomColumn = random.nextInt(7);
				col = randomColumn;
			}
			
			GridPane.setHalignment(yellowCircle, HPos.CENTER);
			GridPane.setValignment(yellowCircle, VPos.CENTER);
			
			// Update board with 'O' token
			board[numberOfRows[col]][col] = 'O';
			
			yellowCircle.setFill(Color.YELLOW);
			
			// Add yellowCircle to pane
			pane.add(yellowCircle, col, numberOfRows[col]);

			// Update the number of rows in column
			if(numberOfRows[col] > 0)
			{
				numberOfRows[col]-=1;
			}
		}
	}
}