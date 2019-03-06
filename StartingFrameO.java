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
  static boolean computerTurn = false;
  static int layer[][];
  static int layersUnderButtons;
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
    arraySize = 4;
    sizeBoxPixels = arraySize*50;
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
    for (int j = 0; j < 4; j++)
    {
      for(int i = 0; i < arraySize; i++) 
      {
        for(int u = 0; u < arraySize; u++) 
        {
          buttonForm.add(buttonsMain[i][u]);
          buttonsMain[i][u].setOpaque(true);
          buttonsMain[i][u].setContentAreaFilled(false);
          buttonsMain[i][u].setBorderPainted(true);
          buttonsMain[i][u].addActionListener(new buttonListener());
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
            if (arraySize == layer[row][column]/4)
            {
              buttonsMain[i][u].setEnabled(false);
            }
          }
        }
      }
      canvas.repaint();
      System.out.println("Row" + (1+row)+ "Column" + (1+column));
      System.out.println(played);
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
      canvas.repaint(); 
      //Draw background image
      g.drawImage(overlayGame,0,0,this);
      //Get layer number of the button
      int x = layersUnderButtons/4;
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
