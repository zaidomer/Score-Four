/** 
 * this template can be used for a start menu
 * for your final project
 * get inputs - implement zaids code for x and z
 * make menu
 * draw added pieces
 **/


//Imports
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;

class StartingFrameO extends JFrame { 
  static JFrame frame;
  static int panelNumber;
  static int row;
  static int column;
  static boolean played = false;
  static BufferedImage overlayGame;
  static int sizeBoxPixels;
  static int arraySize;
  static JButton buttonsMain[][];
  static JButton buttonsSide[][][];
  static GraphicsPanel canvas; 
  static JPanel buttonForm;
  static JPanel viewForm[] = new JPanel[4];; 
  static boolean computerTurn = true;
  static int layer[][];
  static int layersUnderButtons;
  static String gameBoard [][][];
  //Main method starts this application
  public static void main(String[] args) { 
    //Setup frame
    JFrame frame = new JFrame("Score Four");
    frame.setResizable(false);
    frame.setSize(1440,900);
    frame.setVisible(true);
    //Graphics Panel
    canvas = new GraphicsPanel();
    canvas.setLayout(null);
    
    //Icon
    ImageIcon img = new ImageIcon("coin.png");
    frame.setIconImage(img.getImage());
    //Set array
    while(arraySize < 4){
      try{
        arraySize = Integer.parseInt(JOptionPane.showInputDialog("Please enter the dimension of the gameBoard, greater than 3"));
      }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Please enter an integer value.");
      }
    }
    sizeBoxPixels = arraySize*50;
    
    //Initalize gameboard
    gameBoard = new String[arraySize][arraySize][arraySize];
    for(int i = 0; i < arraySize; i++){
      for(int j = 0; j < arraySize; j++){
        for(int k = 0; k < arraySize; k++){
          gameBoard[i][j][k] = "[]";
        }
      }
    }
    
    buttonForm = new JPanel();
    buttonForm.setLayout(new GridLayout(arraySize,arraySize));
    //Set up external panels
    for(int k = 0; k < 4; k++)
    {
      viewForm[k] = new JPanel();
      viewForm[k].setLayout(new GridLayout(arraySize,arraySize)); 
    }
    //Button sizer
    buttonsMain = new JButton[arraySize][arraySize];
    buttonsSide = new JButton[arraySize][arraySize][4];
    //Setup height array tracker
    layer = new int[arraySize][arraySize];
    //Create button field
    for(int f = 0; f < 4; f++) 
    {
      for(int i = 0; i < arraySize; i++) 
      {
        for(int u = 0; u < arraySize; u++) 
        {
          buttonsMain[i][u] = new JButton();
          buttonsSide[i][u][f] = new JButton();
          layer[i][u] = 0;
        }
      }
    }
    //Draw Buttons
    for(int i = 0; i < arraySize; i++) 
    {
       for(int u = 0; u < arraySize; u++) 
       {
         buttonForm.add(buttonsMain[i][u]);
         buttonsMain[i][u].setOpaque(true);
         buttonsMain[i][u].setContentAreaFilled(false);
         buttonsMain[i][u].setBorderPainted(true);
         buttonsMain[i][u].addActionListener(new buttonListener());
       }
    }
          
    for (int j = 0; j < 4; j++)
    {
      for(int i = 0; i < arraySize; i++) 
      {
        for(int u = 0; u < arraySize; u++) 
        {
          viewForm[j].add(buttonsSide[i][u][j]);
          buttonsSide[i][u][j].setOpaque(true);
          buttonsSide[i][u][j].setContentAreaFilled(false);
          buttonsSide[i][u][j].setBorderPainted(true);
        }
      }
    }
    //Set boundaries
    buttonForm.setBounds(556,300,320,360);
    viewForm[0].setBounds(30,65,247,282);
    viewForm[1].setBounds(30,568,247,282);
    viewForm[2].setBounds(1136,65,247,282);
    viewForm[3].setBounds(1136,568,247,282);
    buttonForm.setVisible(true);
    canvas.add(buttonForm);
    for (int d = 0; d < 4; d++)
    {
      canvas.add(viewForm[d]);
      viewForm[d].setVisible(true);
    }
    //Load overlay image
    try 
    {                
      overlayGame = ImageIO.read(new File("OverlayGame.png"));
    } catch (IOException ex){} 
    frame.add(canvas);
    frame.setVisible(true);
  }
  //Button Class
  static class buttonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    { 
      if (!computerTurn) {
         comupterChoice(gameBoard, arraySize);
      } else {
      canvas.repaint();
      for (int i = 0; i < arraySize; i++) 
      {
        for (int u = 0; u < arraySize; u++) 
        {
          if(event.getSource()== buttonsMain[i][u]) //gameButtons[i][j] was clicked
          {
            row = i;
            column = u;
            played = true;
            layersUnderButtons = layer[row][column];
            layer[row][column] = layer[row][column] + 1;
            if (arraySize <= layer[row][column])
            {
              buttonsMain[row][u].setEnabled(false);
            } else {
              gameBoard[row][layer[row][column]][column] = "X ";
            }
            canvas.repaint();
          }
        }
      }
      computerTurn = !computerTurn;
      canvas.repaint();
      System.out.println("Row" + (1+row)+ "Column" + (1+column));
      }
      if (checkGameOver(gameBoard)) {
        JOptionPane.showMessageDialog(null, "Game over!");
        System.exit(0);
      }
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
    
    public static void comupterChoice(String[][][] gameBoard, int gameBoardSize) {
    column = 0;
    int zCoordiante = 0;
    row = -1; 
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
    canvas.repaint();
    while(row == -1 || layer[zCoordiante][column] >= 4){
      column = (int)(Math.random() * gameBoardSize + 0);
      zCoordiante = (int)(Math.random() * gameBoardSize + 0);
      row = findRow(gameBoard, gameBoardSize, column, zCoordiante);
    } // makes sure ai doesnt select filled column
    //System.out.println(layer[zCoordiante][column]);
       played = true;
       row = zCoordiante;
       layersUnderButtons = layer[row][column];
       layer[row][column] = layer[row][column] + 1;
       if (arraySize <= layer[row][column])
       {
         buttonsMain[row][column].setEnabled(false);
       } else {
          gameBoard[zCoordiante][layer[zCoordiante][column]][column] = "O ";
       }
       canvas.repaint();
       computerTurn = !computerTurn;
       System.out.println("Row" + (1+row)+ "Column" + (1+column));
    }
  }
   static class GraphicsPanel extends JPanel 
  {
    int counter = 0;
    public GraphicsPanel()
    {
      setFocusable(true);
      requestFocusInWindow();
    }
    public void paintComponent(Graphics g) 
    { 
      super.paintComponent(g);

      //Draw background image
      g.drawImage(overlayGame,0,0,this);
      //Get layer number of the button
      int x = layersUnderButtons;
      //Draw chip + layer it is on MAIN PANEL
      if (played==true && computerTurn == true)
      {
        buttonsMain[row][column].setIcon(new ImageIcon("chip/chipCOMP" + (x+1) + ".png"));
      }
      else if (played==true && computerTurn == false)
      {
        buttonsMain[row][column].setIcon(new ImageIcon("chip/chipPLAYER" + (x+1) + ".png"));
      }
      //FRONT PANEL
      if (played==true && row == arraySize-1 && computerTurn == true)
      {
        //buttonsSide[bot>top][column][0]
        buttonsSide[row-x][column][0].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && row == arraySize-1)
      {
        buttonsSide[row-x][column][0].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
      //WEST PANEL
      if (played==true && column == 0 && computerTurn == true)
      {
        //buttonsSide[array-column=row][column][0]
        buttonsSide[(arraySize-1)-column-x][row][1].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && column == 0)
      {
        buttonsSide[(arraySize-1)-column-x][row][1].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
      //BACK PANEL
      //buttonsSide[array-row=row][column][0]
      if (played==true && row == 0 && computerTurn == true)
      {
        buttonsSide[(arraySize-1)-row-x][(arraySize-1)-column][2].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && row == 0)
      {
        buttonsSide[(arraySize-1)-row-x][(arraySize-1)-column][2].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
      //EAST PANEL
      if (played==true && column == arraySize-1 && computerTurn == true)
      {
        buttonsSide[column-x][(arraySize-1)-row][3].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && column == arraySize-1)
      {
        buttonsSide[column-x][(arraySize-1)-row][3].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
    }
  }
}
