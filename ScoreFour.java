import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JOptionPane;

public class ScoreFour extends JFrame{
  public static void main(String[] args) {
    new Select();
  }
}

class Select extends JFrame {
  int goesFirst = -1;
  int gameBoardSize = 0;
  boolean gameOver = false;
  Select(){
    super("Start Menu");
    this.setSize(900, 400);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    while(goesFirst != 1 && goesFirst != 0){
      goesFirst = JOptionPane.showConfirmDialog(null, "Do you want to go first?");
    }
    
    while(gameBoardSize < 4){
      try{
        gameBoardSize = Integer.parseInt(JOptionPane.showInputDialog("Please enter the dimension of the gameBoard, greater than 3"));
      }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Please enter an integer value.");
      }
    }
    String[][][] gameBoard = new String[gameBoardSize][gameBoardSize][gameBoardSize];
    gameBoard = blankGameBoard(gameBoard, gameBoardSize);
    String winner = "";
    do{
      if(goesFirst == 0){
        gameBoard = playerChoice(gameBoard, gameBoardSize);
        winner = " You win!";
        gameOver = checkGameOver(gameBoard);
        if(gameOver==false) {
          gameBoard = comupterChoice(gameBoard, gameBoardSize);
          winner = " The computer wins!";
          gameOver = checkGameOver(gameBoard);
        }
      } else {
        gameBoard = comupterChoice(gameBoard, gameBoardSize);
        winner = " The computer wins!";
        gameOver = checkGameOver(gameBoard);
        if(gameOver == false) {
          gameBoard = playerChoice(gameBoard, gameBoardSize);
          winner = " You win!";
          gameOver = checkGameOver(gameBoard);
        }
      }
      
    }while(gameOver == false);
    JOptionPane.showMessageDialog(null, "Game is over." + winner);
  } // end of constructor
  
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
  
  public static String[][][] playerChoice(String[][][] gameBoard, int gameBoardSize) {
    int zCoordiante = Integer.parseInt(JOptionPane.showInputDialog("Please enter the row (from a birds eye view) or Z coordiante of your move, between 0 and " + (gameBoardSize-1)));
    int column = Integer.parseInt(JOptionPane.showInputDialog("Please enter the column, between 0 and " + (gameBoardSize-1)));
    int row = findRow(gameBoard, gameBoardSize, column, zCoordiante);
    gameBoard[zCoordiante][row][column] = "X ";
    printBoard(gameBoard);
    return gameBoard;
  }
  
  public static int findRow(String[][][] gameBoard, int gameBoardSize, int column, int zCoordiante) {
    int row = -1;
    boolean found = false;
    for(int i = gameBoardSize-1; i >= 0; i--){
      if((((gameBoard[zCoordiante][i][column]).equals("[]")) && (found == false))) {
        row = i;
        found = true;
      }
    }
    return row;
  }
  
  public static String[][][] comupterChoice(String[][][] gameBoard, int gameBoardSize) {
    int column = 0;
    int zCoordiante = 0;
    int row = -1; 
    boolean winComboFound = false;
    boolean defendThree = false;

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
            zCoordiante = i;
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
            zCoordiante = k+2;
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
            zCoordiante = i;
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
            zCoordiante = k+3;
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
            zCoordiante = i;
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
            zCoordiante = k;
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
            zCoordiante = i;
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
            zCoordiante = k+2;
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
            zCoordiante = i;
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
            zCoordiante = k+2;
            column = i;
            row = j;

          // Vertical Placement (2 in a row)
          }else if((gameBoard[i][k][j].equals("[]") 
          && (k <= gameBoard.length-3)
          && (gameBoard[i][k+1][j].equals(gameBoard[i][k+2][j])) 
          && !(gameBoard[i][k+1][j].equals("[]")))
          && (winComboFound == false)
          && (defendThree == false)){
            zCoordiante = i;
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
            zCoordiante = i;
            column = j;
            row = k;
            if(gameBoard[i][k+1][j].equals("O ")){
              winComboFound = true;
            } else{
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = k;
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
            zCoordiante = k+3;
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
            zCoordiante = k+1;
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
            zCoordiante = k+2;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
            zCoordiante = i;
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
    
    // Temporary random coordiantes
    while(row == -1){
      column = (int)(Math.random() * gameBoardSize + 0);
      zCoordiante = (int)(Math.random() * gameBoardSize + 0);
      row = findRow(gameBoard, gameBoardSize, column, zCoordiante);
    } // makes sure ai doesnt select filled column
    gameBoard[zCoordiante][row][column] = "O ";
    printBoard(gameBoard);
    System.out.println(row);
    return gameBoard;
  }
  
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
    for(int zCoordiante = 0; zCoordiante < gameBoard.length; zCoordiante++){
      for(int row = 0; row < gameBoard.length; row++){
        for(int column = 0; column <= gameBoard.length-4; column++){
          if((row <= gameBoard.length-4) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+1][column+1])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+2][column+2])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+3][column+3])) 
          && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right 
          } else if((zCoordiante <= gameBoard.length-4) 
          &&((row <= gameBoard.length-4) 
          && gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+1][row+1][column+1])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+2][row+2][column+2])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+3][row+3][column+3])) 
          && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right multi-dimensional, multiple heights
          } else if((zCoordiante <= gameBoard.length-4) 
          &&(gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+1][row][column+1])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+2][row][column+2])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+3][row][column+3])) 
          && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right multi-dimensional, 1 row height
          }else if((zCoordiante <= gameBoard.length-4) 
          &&(column >= 3)
          &&(gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+1][row][column-1])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+2][row][column-2])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+3][row][column-3])) 
          && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from top right to bottom left multi-dimensional, 1 row height
          }
        }
        for(int column = gameBoard.length-1; column >= 3; column--){
          if((row <= gameBoard.length-4)
           &&(gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+1][column-1]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+2][column-2]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+3][column-3]))
           && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from bottom left to top right
          } else if((zCoordiante >= 3)
           && (row <= gameBoard.length-4) 
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante-1][row+1][column-1]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante-2][row+2][column-2]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante-3][row+3][column-3]))
           && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from bottom left to top right multi-dimensional 
          } 
        }
        for(int column = 0; column < gameBoard.length; column++){
          if((zCoordiante <= gameBoard.length-4) 
           &&(row <= gameBoard.length-4)
           &&(gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+1][row+1][column])) 
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+2][row+2][column])) 
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante+3][row+3][column])) 
           && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right multi-dimensional, but along 1 column
          } else if((zCoordiante >= 3) 
           && (row <= gameBoard.length-4)
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante-1][row+1][column]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante-2][row+2][column]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante-3][row+3][column]))
           && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from bottom left to top right multi-dimensional, but along 1 column 
          }
        }
      }
    }

    return gameOver;
  }
}