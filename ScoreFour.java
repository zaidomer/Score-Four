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
    Select(){
        super("Start Menu");
        this.setSize(900, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        goesFirst = JOptionPane.showConfirmDialog(null, "Do you want to go first?");
        gameBoardSize = JOptionPane.showInputDialog("Please enter the dimension of the gameboard (one number for all dimensions)");
        System.out.println(goesFirst);
    }
}