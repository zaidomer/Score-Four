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
                //playerChoice();
                //comupterChoice();
            } else {
                //comupterChoice();
                //playerChoice();
            }
            gameOver = checkGameOver();
        }while(gameOver == false);
    } // end of constructor

    public static String[][][] blankGameBoard(String[][][] gameBoard, int gameBoardSize){
        for(int i = 0; i < gameBoardSize; i++){
            for(int j = 0; j < gameBoardSize; j++){
                for(int k = 0; k < gameBoardSize; k++){
                    gameBoard[i][j][k] = "[]";
                    System.out.print(gameBoard[i][j][k]);
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
        return gameBoard;
    }

    public static void playerChoice() {
        System.out.println("hi");
    }

    public static void comupterChoice() {
        System.out.println("hi");
    }
    
    public static boolean checkGameOver() {
        return false;
    }
}