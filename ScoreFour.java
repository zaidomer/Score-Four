/**
 * ScoreFour class
 * a 3d version of connect 4
 * Calls the Select Constructor
 * @author Zaid Omer
 * @version March 2019
 */

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ScoreFour extends JFrame{
  public static void main(String[] args) {

    // call class
    new Select();
  }
}

/**
 * Select class
 * Contains the entire game, the whole game loop and all methods used
 * @author Zaid Omer
 * @version March 2019
 */
class Select extends JFrame {
  int goesFirst = -1;
  int gameBoardSize = 0;
  boolean gameOver = false;

  // Select class constructor
  Select(){
    super("Start Menu");
    this.setSize(900, 400);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);

    // while loop continuing while there is no input (while user selects cancel)
    while(goesFirst != 1 && goesFirst != 0){
      goesFirst = JOptionPane.showConfirmDialog(null, "Do you want to go first?");
    }
    
    // while loop with try catch to chekc to see a valid gameboard size is selected
    while(gameBoardSize < 4){
      try{
        gameBoardSize = Integer.parseInt(JOptionPane.showInputDialog("Please enter the dimension of the gameBoard, greater than 3"));
      }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Please enter an integer value.");
      }
    }

    // create gameboard and set it to be empty
    String[][][] gameBoard = new String[gameBoardSize][gameBoardSize][gameBoardSize];
    gameBoard = blankGameBoard(gameBoard, gameBoardSize);

    // winner string, stores who went last as winner. The winner will always be the last player who plays
    String winner = "";

    // Game Loop
    do{
      if(goesFirst == 0){
        gameBoard = playerChoice(gameBoard, gameBoardSize);
        winner = " You win!"; // set to player win
        gameOver = checkGameOver(gameBoard);
        if(gameOver==false) {
          gameBoard = computerChoice(gameBoard, gameBoardSize);
          winner = " The computer wins!"; // set to computer win
          gameOver = checkGameOver(gameBoard);
        }
      } else {
        gameBoard = computerChoice(gameBoard, gameBoardSize);
        winner = " The computer wins!"; // set to computer win
        gameOver = checkGameOver(gameBoard);
        if(gameOver == false) {
          gameBoard = playerChoice(gameBoard, gameBoardSize);
          winner = " You win!"; // set to player win
          gameOver = checkGameOver(gameBoard);
        }
      }
      
    }while(gameOver == false);

    // Message for when the game is over
    JOptionPane.showMessageDialog(null, "Game is over." + winner);
  } // end of constructor
  
  /**
  * blankGameBoard 
  * This method sets every cell in the gameboard to be empty, indicated by []
  * @author Zaid Omer
  * @param gameBoard A 3D array that holds all the data for pieces played in the game
  * @param gameBoardSize the size of the game board (the length of 1 of the dimesnions. Its a cube)
  * @return String[][][], a 3D string array that holds the updated gameboard
  */
  public static String[][][] blankGameBoard(String[][][] gameBoard, int gameBoardSize){
    for(int i = 0; i < gameBoardSize; i++){
      for(int j = 0; j < gameBoardSize; j++){
        for(int k = 0; k < gameBoardSize; k++){
          gameBoard[i][j][k] = "[]";
        }
      }
    }
    printBoard(gameBoard);
    return gameBoard;
  }
  
  /**
  * printBoard 
  * This method prints the entire board to the console
  * @author Zaid Omer
  * @param gameBoard A 3D array that holds all the data for pieces played in the game
  */
  public static void printBoard(String[][][] gameBoard) {
    for(int i = 0; i < gameBoard.length; i++){
      System.out.println("Z" + i);
      for(int j = 0; j < gameBoard.length; j++){
        for(int k = 0; k < gameBoard.length; k++){
          System.out.print(gameBoard[i][j][k]);
        }
        System.out.println();
      }
      System.out.println();
    }
    System.out.println("////////////////////////////////////////////////");
  }
  
  /**
  * playerChoice 
  * This method allows the user to select where to move on the board
  * @author Zaid Omer
  * @param gameBoard A 3D array that holds all the data for pieces played in the game
  * @param gameBoardSize the size of the game board (the length of 1 of the dimesnions. Its a cube)
  * @return String[][][], a 3D string array that holds the updated gameboard, with the user selected position
  */
  public static String[][][] playerChoice(String[][][] gameBoard, int gameBoardSize) {

    // set to -1 due to the while conditon
    int zCoordinate = -1;
    int column = -1;
    int row = -1;

    do{
      // while loop and try catch error checking z coordinate input
      while(zCoordinate > gameBoard.length-1 || zCoordinate < 0){
        try{
          // the z coordinate is looked at from the front side of the cube inwards, with the fromt beginning at 0
          zCoordinate = Integer.parseInt(JOptionPane.showInputDialog("Please enter the row (from a birds eye view) or Z coordinate of your move, between 0 and " + (gameBoardSize-1)));
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, "Please enter an integer value within the indicated value.");
        }
      }

      // while loop and try catch error checking column input
      while(column > gameBoard.length-1 || column < 0){
        try{
          column = Integer.parseInt(JOptionPane.showInputDialog("Please enter the column, between 0 and " + (gameBoardSize-1)));
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, "Please enter an integer value within the indicated value.");
        }
      }
      row = findRow(gameBoard, gameBoardSize, column, zCoordinate);
      if(row == -1){
        JOptionPane.showMessageDialog(null, "Enter a new value. This column is full");

        // Set values to -1 again, so the while conditions become true, and it runs again
        zCoordinate = -1;
        column = -1;
      }
    }while(row == -1); // Runs untill a non-filled column is selected

    gameBoard[zCoordinate][row][column] = "X "; // X is the dting value represnting the user selection
    printBoard(gameBoard);
    return gameBoard;
  }
  
  /**
  * findRow 
  * This method takes the user input, and drops the piece to the bottom
  * @author Zaid Omer
  * @param gameBoard A 3D array that holds all the data for pieces played in the game
  * @param gameBoardSize the size of the game board (the length of 1 of the dimesnions. Its a cube)
  * @param column the integer value of the column the user selected to move 
  * @param zCoordinate the integer value of the z dimension the user selected to move to
  * @return int, an integer value returning the row numerical dimension
  */
  public static int findRow(String[][][] gameBoard, int gameBoardSize, int column, int zCoordinate) {
    int row = -1;
    boolean found = false;

    // Cycle through with reverse for loop, find the empty index with the highest row number
    for(int i = gameBoardSize-1; i >= 0; i--){
      if((((gameBoard[zCoordinate][i][column]).equals("[]")) && (found == false))) {
        row = i;
        found = true;
      }
    }
    return row;
  }
  
  /**
  * computerChoice 
  * This method takes the user input, and drops the piece to the bottom
  * @author Zaid Omer
  * @param gameBoard A 3D array that holds all the data for pieces played in the game
  * @param gameBoardSize the size of the game board (the length of 1 of the dimesnions. Its a cube)
  * @return String[][][], a 3D string array that holds the value of the gameboard pieces, 
  * with the computer movement
  */
  public static String[][][] computerChoice(String[][][] gameBoard, int gameBoardSize) {
    int column = 0;
    int zCoordinate = 0;
    int row = -1; 
    boolean winComboFound = false;
    boolean defendThree = false;

    /* With 3 nested for loops, the program checks through all points on the board, and goes through
    * all points on the board, and checks through all possible movements to either win, or defend a loss
    * The boolean values declared a bove act similar to the minimax algo, where moves are ranked when
    * compared to one another, to decide where the computer goes
    */
    for(int i = 0; i < gameBoard.length; i++){
      for(int j = 0; j < gameBoard.length; j++){
        for(int k = 0; k < gameBoard.length; k++){
          //2 in a row
          // Horizontal "[]X[]X or []O[]O", accross 1 z dimension
          if((gameBoard[i][j][k].equals("[]")) 
          && (k <= gameBoard.length-4)
          && (gameBoard[i][j][k+1].equals(gameBoard[i][j][k+3])) 
          && (gameBoard[i][j][k+2].equals("[]")) 
          && !(gameBoard[i][j][k+1].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, k+2, i))){
            zCoordinate = i;
            column = k+2;
            row = j;

          // Horizontal "[]X[]X or []O[]O", accross multiple z dimensions
          }else if((gameBoard[k][j][i].equals("[]")) 
          && (k <= gameBoard.length-4)
          && (gameBoard[k+1][j][i].equals(gameBoard[k+3][j][i])) 
          && (gameBoard[k+2][j][i].equals("[]")) 
          && !(gameBoard[k+1][j][i].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, i, k+2))){
            zCoordinate = k+2;
            column = i;
            row = j;

          // Horizontal "X[]X[] or O[]O[]" accross 1 z dimension
          }else if((k <= gameBoard.length-4)
          && (gameBoard[i][j][k+1].equals("[]")) 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+2])) 
          && (gameBoard[i][j][k+3].equals("[]")) 
          && !(gameBoard[i][j][k].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, k+3, i))){
            zCoordinate = i;
            column = k+3;
            row = j;

          // Horizontal "X[]X[] or O[]O[]" accross multiple z dimensions
          }else if((k <= gameBoard.length-4)
          && (gameBoard[k+1][j][i].equals("[]")) 
          && (gameBoard[k][j][i].equals(gameBoard[i][j][k+2])) 
          && (gameBoard[k+3][j][i].equals("[]")) 
          && !(gameBoard[k][j][i].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, i, k+3))){
            zCoordinate = k+3;
            column = i;
            row = j;

          // Horizontal Placement Condition "[]XX" or "[]OO" accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]")) 
          && (k <= gameBoard.length-3)
          && (gameBoard[i][j][k+1].equals(gameBoard[i][j][k+2])) 
          && !(gameBoard[i][j][k+1].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, k, i))){
            zCoordinate = i;
            column = k;
            row = j;

          // Horizontal Placement Condition "[]XX" or "[]OO" accross multiple z dimensions
          }else if((gameBoard[k][j][i].equals("[]")) 
          && (k <= gameBoard.length-3)
          && (gameBoard[k+1][j][i].equals(gameBoard[k+2][j][i])) 
          && !(gameBoard[k+1][j][i].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, i, k))){
            zCoordinate = k;
            column = i;
            row = j;

          // Horizontal Placement Condition "XX[]" or "OO[]" 
          }else if((k <= gameBoard.length-3) 
          && (gameBoard[i][j][k+2].equals("[]")
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+1])) 
          && !(gameBoard[i][j][k].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false))
          && (j == findRow(gameBoard, gameBoard.length, k+2, i))){
            zCoordinate = i;
            column = k+2;
            row = j;
            
          // Horizontal Placement Condition "XX[]" or "OO[]" accross mutiple z dimensions
          }else if((k <= gameBoard.length-3) 
          && (gameBoard[k+2][j][i].equals("[]")
          && (gameBoard[k][j][i].equals(gameBoard[k+1][j][i])) 
          && !(gameBoard[k][j][i].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false))
          && (j == findRow(gameBoard, gameBoard.length, i, k+2))){
            zCoordinate = k+2;
            column = i;
            row = j;

          // Horizontal Placement Condition "X[][]X" or "O[][]O" accross 1 z dimension
          } else if((k <= gameBoard.length-4) 
          && (gameBoard[i][j][k+2].equals("[]"))
          && (gameBoard[i][j][k+1].equals("[]"))
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+3])) 
          && !(gameBoard[i][j][k].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, k+2, i))){
            zCoordinate = i;
            column = k+2;
            row = j;
            
          // Horizontal Placement Condition "X[][]X" or "O[][]O" accross multiple z dimensions
          }else if((k <= gameBoard.length-4) 
          && (gameBoard[k+2][j][i].equals("[]"))
          && (gameBoard[k+1][j][i].equals("[]"))
          && (gameBoard[k][j][i].equals(gameBoard[k+3][j][i])) 
          && !(gameBoard[k][j][i].equals("[]"))
          && (winComboFound == false)
          && (defendThree == false)
          && (j == findRow(gameBoard, gameBoard.length, i, k+2))){
            zCoordinate = k+2;
            column = i;
            row = j;

          // Vertical Placement (2 in a row)
          }else if((gameBoard[i][k][j].equals("[]") 
          && (k <= gameBoard.length-3)
          && (gameBoard[i][k+1][j].equals(gameBoard[i][k+2][j])) 
          && !(gameBoard[i][k+1][j].equals("[]")))
          && (winComboFound == false)
          && (defendThree == false)){
            zCoordinate = i;
            column = j;
            row = k;
          } 
          
          //3 in a row
          // Vertical
          if((gameBoard[i][k][j].equals("[]") 
          && (k <= gameBoard.length-4)
          && (gameBoard[i][k+1][j].equals(gameBoard[i][k+2][j])) 
          && (gameBoard[i][k+1][j].equals(gameBoard[i][k+3][j])) 
          && !(gameBoard[i][k+1][j].equals("[]")))
          && (winComboFound == false)){

            // set z, row, and column to the empty slot that will make a connection of 4
            zCoordinate = i;
            column = j;
            row = k;

            if(gameBoard[i][k+1][j].equals("O ")){
              // if this is found, all other possibilities are ignored. the game will be won
              winComboFound = true; 
            } else{
              // A crucial move that once found, will execute and ignore all other moves. Keeps the game alive 
              defendThree = true;  
            }
          
          // Horizontal Placement Condition "[]XXX" or "[]OOO"
          }else if((gameBoard[i][j][k].equals("[]") 
          && (k <= gameBoard.length-4)
          && (gameBoard[i][j][k+1].equals(gameBoard[i][j][k+2])) 
          && (gameBoard[i][j][k+1].equals(gameBoard[i][j][k+3])) 
          && !(gameBoard[i][j][k+1].equals("[]"))
          && (winComboFound == false))
          && (j == findRow(gameBoard, gameBoard.length, k, i))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j][k+1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

          // Horizontal Placement Condition "XXX[]" or "OOO[]"
          }else if((k <= gameBoard.length-4) 
          &&(gameBoard[i][j][k+3].equals("[]")
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+1])) 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+2])) 
          && !(gameBoard[i][j][k].equals("[]"))
          && (winComboFound == false))
          && (j == findRow(gameBoard, gameBoard.length, k+3, i))){
            zCoordinate = i;
            column = k+3;
            row = j;
            if(gameBoard[i][j][k].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }
          
          // Horizontal Placement Condition "X[]XX" or "O[]OO"
          }else if((k <= gameBoard.length-4)
          && (gameBoard[i][j][k+1].equals("[]") 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+2])) 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+3])) 
          && !(gameBoard[i][j][k].equals("[]"))
          && (winComboFound == false))
          && (j == findRow(gameBoard, gameBoard.length, k+1, i))){
            zCoordinate = i;
            column = k+1;
            row = j;
            if(gameBoard[i][j][k].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }
          // Horizontal Placement Condition "XX[]X" or "OO[]O"
          }else if((k <= gameBoard.length-4)
          && (gameBoard[i][j][k+2].equals("[]") 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+1])) 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+3])) 
          && !(gameBoard[i][j][k].equals("[]"))
          && (winComboFound == false))
          && (j == findRow(gameBoard, gameBoard.length, k+2, i))){
            zCoordinate = i;
            column = k+2;
            row = j;
            if(gameBoard[i][j][k].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }
          }else if((gameBoard[k][j][i].equals("[]") 
          && (k <= gameBoard.length-4)
          && (gameBoard[k+1][j][i].equals(gameBoard[k+2][j][i])) 
          && (gameBoard[k+1][j][i].equals(gameBoard[k+3][j][i])) 
          && !(gameBoard[k+1][j][i].equals("[]"))
          && (winComboFound == false))
          && (j == findRow(gameBoard, gameBoard.length, i, k))){
            zCoordinate = k;
            column = i;
            row = j;
            if(gameBoard[k+1][j][i].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }
          // Horizontal Placement Condition "XXX[]" or "OOO[]" Along z dimensions
          }else if((k <= gameBoard.length-4)
          && (gameBoard[k+3][j][i].equals("[]")) 
          && (gameBoard[k][j][i].equals(gameBoard[k+1][j][i])) 
          && (gameBoard[k][j][i].equals(gameBoard[k+2][j][i])) 
          && !(gameBoard[k][j][i].equals("[]"))
          && (winComboFound == false)
          && (j == findRow(gameBoard, gameBoard.length, i, k+3))){
            zCoordinate = k+3;
            column = i;
            row = j;
            if(gameBoard[k][j][i].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }
          // Horizontal Placement Condition "X[]XX" or "O[]OO" Along z dimensions
          }else if((k <= gameBoard.length-4)
          && (gameBoard[k+1][j][i].equals("[]"))
          && (gameBoard[k][j][i].equals(gameBoard[k+2][j][i])) 
          && (gameBoard[k][j][i].equals(gameBoard[k+3][j][i])) 
          && !(gameBoard[k][j][i].equals("[]"))
          && (winComboFound == false)
          && (j == findRow(gameBoard, gameBoard.length, i, k+1))){
            zCoordinate = k+1;
            column = i;
            row = j;
            if(gameBoard[k][j][i].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }
          // Horizontal Placement Condition "XX[]X" or "OO[]O" Along z dimensions
          }else if((k <= gameBoard.length-4)
          && (gameBoard[k+2][j][i].equals("[]") 
          && (gameBoard[k][j][i].equals(gameBoard[k+1][j][i])) 
          && (gameBoard[k][j][i].equals(gameBoard[k+3][j][i])) 
          && !(gameBoard[k][j][i].equals("[]"))
          && (winComboFound == false))
          && (j == findRow(gameBoard, gameBoard.length, i, k+2))){
            zCoordinate = k+2;
            column = i;
            row = j;
            if(gameBoard[k][j][i].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom Left to top right, top left placement accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]"))
          && (j <= gameBoard.length - 4) 
          && (k >= 3)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j+1][k-1].equals(gameBoard[i][j+2][k-2])) 
          && (gameBoard[i][j+1][k-1].equals(gameBoard[i][j+3][k-3])) 
          && !(gameBoard[i][j+1][k-1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j+1][k-1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom Left to top right, second from top left placement accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]"))
          && (j <= gameBoard.length - 3) && (j >= 1)
          && (k >= 2) && (k <= gameBoard.length -2)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j-1][k+1].equals(gameBoard[i][j+1][k-1])) 
          && (gameBoard[i][j-1][k+1].equals(gameBoard[i][j+2][k-2])) 
          && !(gameBoard[i][j-1][k+1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j-1][k+1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom Left to top right, second from bottom right placement accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]"))
          && (j <= gameBoard.length - 2) && (j >= 2)
          && (k >= 1) && (k <= gameBoard.length -3)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j+1][k-1].equals(gameBoard[i][j-1][k+1])) 
          && (gameBoard[i][j+1][k-1].equals(gameBoard[i][j-2][k+2])) 
          && !(gameBoard[i][j+1][k-1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j+1][k-1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom Left to top right, bottom right placement accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]"))
          && (j >= 3) 
          && (k <= gameBoard.length-4)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j-1][k+1].equals(gameBoard[i][j-2][k+2])) 
          && (gameBoard[i][j-1][k+1].equals(gameBoard[i][j-3][k+3])) 
          && !(gameBoard[i][j-1][k+1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j-1][k+1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom right to top left, top left placement accross 1 z dimension
          } else if((gameBoard[i][j][k].equals("[]"))
          && (j <= gameBoard.length - 4) 
          && (k <= gameBoard.length - 4)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j+1][k+1].equals(gameBoard[i][j+2][k+2])) 
          && (gameBoard[i][j+1][k+1].equals(gameBoard[i][j+3][k+3])) 
          && !(gameBoard[i][j+1][k+1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j+1][k+1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom right to top left, second from top left placement accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]"))
          && (j <= gameBoard.length - 3) && (j >= 1)
          && (k >= 1) && (k <= gameBoard.length -3)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j-1][k-1].equals(gameBoard[i][j+1][k+1])) 
          && (gameBoard[i][j-1][k-1].equals(gameBoard[i][j+2][k+2])) 
          && !(gameBoard[i][j-1][k-1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j-1][k-1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom right to top left, second from bottom right placement accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]"))
          && (j <= gameBoard.length - 2) && (j >= 2)
          && (k >= 2) && (k <= gameBoard.length -2)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j+1][k+1].equals(gameBoard[i][j-1][k-1])) 
          && (gameBoard[i][j+1][k+1].equals(gameBoard[i][j-2][k-2])) 
          && !(gameBoard[i][j+1][k+1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j+1][k+1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }

            //Bottom right to top left, bottom right placement accross 1 z dimension
          }else if((gameBoard[i][j][k].equals("[]"))
          && (j >= 3) 
          && (k >= 3)
          && (findRow(gameBoard, gameBoard.length, k, i) == j)
          && (gameBoard[i][j-1][k-1].equals(gameBoard[i][j-2][k-2])) 
          && (gameBoard[i][j-1][k-1].equals(gameBoard[i][j-3][k-3])) 
          && !(gameBoard[i][j-1][k-1].equals("[]"))){
            zCoordinate = i;
            column = k;
            row = j;
            if(gameBoard[i][j-1][k-1].equals("O ")){
              winComboFound = true;
            } else {
              defendThree = true;
            }
          }

        }
      }
    }
    
    // If no good move is found, the code reverts to a random decision
    while(row == -1){
      column = (int)(Math.random() * gameBoardSize + 0);
      zCoordinate = (int)(Math.random() * gameBoardSize + 0);
      row = findRow(gameBoard, gameBoardSize, column, zCoordinate);
    } // makes sure ai doesnt select filled column
    gameBoard[zCoordinate][row][column] = "O ";
    printBoard(gameBoard);
    return gameBoard;
  }
  
  /**
  * checkGameOver 
  * This method takes the gameboard, and checks all possibilities to see if the game is over
  * @author Zaid Omer
  * @param gameBoard A 3D array that holds all the data for pieces played in the game
  * @return String[][][], a 3D string array that holds the value of the gameboard pieces, 
  * with the computer movement
  */
  public static boolean checkGameOver(String[][][] gameBoard) {
    boolean gameOver =  false;
    
    // Vertical, horizontal win check
    for(int i = 0; i < gameBoard.length; i++){
      for(int j = 0; j < gameBoard.length; j++){
        for(int k = 0; k <= gameBoard.length-4; k++){
          if((gameBoard[i][k][j].equals(gameBoard[i][k+1][j])) 
          && (gameBoard[i][k][j].equals(gameBoard[i][k+2][j])) 
          && (gameBoard[i][k][j].equals(gameBoard[i][k+3][j])) 
          && !(gameBoard[i][k][j].equals("[]"))){
            gameOver = true; // vertical win
          }else if((gameBoard[i][j][k].equals(gameBoard[i][j][k+1])) 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+2])) 
          && (gameBoard[i][j][k].equals(gameBoard[i][j][k+3])) 
          && !(gameBoard[i][j][k].equals("[]"))){
            gameOver = true; // horizontal win
          } else if((gameBoard[k][j][i].equals(gameBoard[k+1][j][i])) 
          && (gameBoard[k][j][i].equals(gameBoard[k+2][j][i])) 
          && (gameBoard[k][j][i].equals(gameBoard[k+3][j][i])) 
          && !(gameBoard[k][j][i].equals("[]"))){
            gameOver = true; // horizontal win accross multiple z dimensions
          }
        }
      }
    }
    
    //Diagonal win
    for(int zCoordinate = 0; zCoordinate < gameBoard.length; zCoordinate++){
      for(int row = 0; row < gameBoard.length; row++){
        for(int column = 0; column <= gameBoard.length-4; column++){
          if((row <= gameBoard.length-4) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate][row+1][column+1])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate][row+2][column+2])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate][row+3][column+3])) 
          && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right 
          } else if((zCoordinate <= gameBoard.length-4) 
          &&((row <= gameBoard.length-4) 
          && gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+1][row+1][column+1])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+2][row+2][column+2])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+3][row+3][column+3])) 
          && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right multi-dimensional, multiple heights
          } else if((zCoordinate <= gameBoard.length-4) 
          &&(gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+1][row][column+1])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+2][row][column+2])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+3][row][column+3])) 
          && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right multi-dimensional, 1 row height
          }else if((zCoordinate <= gameBoard.length-4) 
          &&(column >= 3)
          &&(gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+1][row][column-1])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+2][row][column-2])) 
          && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+3][row][column-3])) 
          && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from top right to bottom left multi-dimensional, 1 row height
          }
        }
        for(int column = gameBoard.length-1; column >= 3; column--){
          if((row <= gameBoard.length-4)
           &&(gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate][row+1][column-1]))
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate][row+2][column-2]))
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate][row+3][column-3]))
           && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from bottom left to top right
          } else if((zCoordinate >= 3)
           && (row <= gameBoard.length-4) 
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate-1][row+1][column-1]))
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate-2][row+2][column-2]))
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate-3][row+3][column-3]))
           && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from bottom left to top right multi-dimensional 
          } 
        }
        for(int column = 0; column < gameBoard.length; column++){
          if((zCoordinate <= gameBoard.length-4) 
           &&(row <= gameBoard.length-4)
           &&(gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+1][row+1][column])) 
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+2][row+2][column])) 
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate+3][row+3][column])) 
           && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right multi-dimensional, but along 1 column
          } else if((zCoordinate >= 3) 
           && (row <= gameBoard.length-4)
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate-1][row+1][column]))
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate-2][row+2][column]))
           && (gameBoard[zCoordinate][row][column].equals(gameBoard[zCoordinate-3][row+3][column]))
           && (!(gameBoard[zCoordinate][row][column].equals("[]")))){
            gameOver = true; //from bottom left to top right multi-dimensional, but along 1 column 
          }
        }
      }
    }

    return gameOver;
  }
}