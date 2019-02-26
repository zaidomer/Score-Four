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
    do{
      if(goesFirst == 0){
        gameBoard = playerChoice(gameBoard, intGameBoardSize);
        gameBoard = comupterChoice(gameBoard, intGameBoardSize);
      } else {
        gameBoard = comupterChoice(gameBoard, intGameBoardSize);
        gameBoard = playerChoice(gameBoard, intGameBoardSize);
      }
      gameOver = checkGameOver(gameBoard);
    }while(gameOver == false);
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
    int numConnected = 0;
    for(int i = 0; i < gameBoard.length-1; i++) {
      if((gameBoard[i][i][i].equals(gameBoard[i+1][i+1][i+1])) && gameBoard[i][i][i] != "[]"){
        numConnected++;
        System.out.println(numConnected);
        if(numConnected == 3){
          gameOver = true;
        }
      }
    }
    return gameOver;
  }
}
