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
  int goesFirst;
  String gameBoardSize;
  boolean gameOver = false;
  int intGameBoardSize = 0;
  Select(){
    super("Start Menu");
    this.setSize(900, 400);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    goesFirst = JOptionPane.showConfirmDialog(null, "Do you want to go first?");
    gameBoardSize = JOptionPane.showInputDialog("Please enter the dimension of the gameBoard (one number for all dimensions)");
    intGameBoardSize = Integer.parseInt(gameBoardSize);
    String[][][] gameBoard = new String[intGameBoardSize][intGameBoardSize][intGameBoardSize];
    gameBoard = blankGameBoard(gameBoard, intGameBoardSize);
    String winner = "";
    do{
      if(goesFirst == 0){
        gameBoard = playerChoice(gameBoard, intGameBoardSize);
        winner = " You win!";
        gameOver = checkGameOver(gameBoard);
        if(gameOver==false) {
          gameBoard = comupterChoice(gameBoard, intGameBoardSize);
          winner = " The computer wins!";
          gameOver = checkGameOver(gameBoard);
        }
      } else {
        gameBoard = comupterChoice(gameBoard, intGameBoardSize);
        winner = " The computer wins!";
        gameOver = checkGameOver(gameBoard);
        if(gameOver == false) {
          gameBoard = playerChoice(gameBoard, intGameBoardSize);
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
    int row = 0;
    boolean found = false;
    for(int i = gameBoardSize-1; i > 0; i--){
      if((((gameBoard[zCoordiante][i][column]).equals("[]")) && (found == false))) {
        row = i;
        found = true;
      }
    }
    return row;
  }
  
  public static String[][][] comupterChoice(String[][][] gameBoard, int gameBoardSize) {
    int column;
    int zCoordiante;
    int row; 
    
    // Temporary random coordiantes
    column = (int)(Math.random() * gameBoardSize + 0);
    zCoordiante = (int)(Math.random() * gameBoardSize + 0);
    row = findRow(gameBoard, gameBoardSize, column, zCoordiante);
    gameBoard[zCoordiante][row][column] = "O ";
    printBoard(gameBoard);
    return gameBoard;
  }
  
  public static boolean checkGameOver(String[][][] gameBoard) {
    boolean gameOver =  false;
    
    // Multi-dimensional Diagonal Win
    for(int i = 0; i < gameBoard.length-2; i++) {
      if((gameBoard[i][gameBoard.length-1-i][i].equals(gameBoard[i+1][gameBoard.length-1-(i+1)][i+1])) && (gameBoard[i][i][i] != "[]")){
        gameOver = true;
      }
    }
    
    //Vertical, horixontal win check, accross 1 z dimension
    for(int i = 0; i < gameBoard.length; i++){
      for(int j = 0; j < gameBoard.length; j++){
        for(int k = 0; k <= gameBoard.length-4; k++){
          if((gameBoard[i][k][j].equals(gameBoard[i][k+1][j])) && (gameBoard[i][k][j].equals(gameBoard[i][k+2][j])) && (gameBoard[i][k][j].equals(gameBoard[i][k+3][j])) && !(gameBoard[i][k][j].equals("[]"))){
            gameOver = true; // vertical win
          }else if((gameBoard[i][j][k].equals(gameBoard[i][j][k+1])) && (gameBoard[i][j][k].equals(gameBoard[i][j][k+2])) && (gameBoard[i][j][k].equals(gameBoard[i][j][k+3])) && !(gameBoard[i][j][k].equals("[]"))){
            gameOver = true; // horizontal win
          }

        }
      }
    }
    
    //Diagonal win (accross 1 z dimension)
    for(int zCoordiante = 0; zCoordiante < gameBoard.length; zCoordiante++){
      for(int row = 0; row <= gameBoard.length-4; row++){
        for(int column = 0; column <= gameBoard.length-4; column++){
          if((gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+1][column+1])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+2][column+2])) 
          && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+3][column+3])) 
          && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from top left to bottom right 
          }
        }
        for(int column = gameBoard.length-1; column >= 3; column--){
          if((gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+1][column-1]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+2][column-2]))
           && (gameBoard[zCoordiante][row][column].equals(gameBoard[zCoordiante][row+3][column-3]))
           && (!(gameBoard[zCoordiante][row][column].equals("[]")))){
            gameOver = true; //from bottom left to top right
          }
        }
      }
    }

    return gameOver;
  }
}