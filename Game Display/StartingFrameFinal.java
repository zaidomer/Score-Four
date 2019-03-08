/* Selection Sort method
 * @desc displays button clicks, is ONLY a basic input/output display
 * @author Charles Ahn
 * @version March 03th 2019
 */


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

class StartingFrameFinal extends JFrame { 
  //Declare Variables
  static final int ZERO = 0;
  static final int EXTERNALPANELS = 4;
  static final int REPEAT = 4;
  static final int COUNTERSTART = 0;
  static final int COORDINATEX = 0;
  static final int COORDINATEY = 0;
  static int panelNumber;
  static int row;
  static int column;
  static int sizeBoxPixels;
  static int arraySize;
  static int layer[][];
  static int layersUnderButtons;
  static BufferedImage overlayGame;
  static JButton buttonsMain[][];
  static JButton buttonsSide[][][];
  static JFrame frame;
  static GraphicsPanel canvas; 
  static JPanel buttonForm;
  static JPanel viewForm[] = new JPanel[4];
  static boolean played = false;
  static boolean computerTurn = false;
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
    //Icon for frame
    ImageIcon img = new ImageIcon("coin.png");
    frame.setIconImage(img.getImage());
    //Setup array
    arraySize = 10;
    sizeBoxPixels = arraySize*50;
    buttonForm = new JPanel();
    buttonForm.setLayout(new GridLayout(arraySize,arraySize));
    //Set up external panels
    for(int k = COUNTERSTART; k < EXTERNALPANELS; k++)
    {
      viewForm[k] = new JPanel();
      viewForm[k].setLayout(new GridLayout(arraySize,arraySize)); 
    }
    //Setup height array tracker
    layer = new int[arraySize][arraySize];
    buttonDrawer();
    //Load overlay image
    try 
    {                
      overlayGame = ImageIO.read(new File("OverlayGame.png"));
    } catch (IOException ex){} 
    frame.add(canvas);
    frame.setVisible(true);
  }
  public static void buttonDrawer()
  {
    //Button sizer
    buttonsMain = new JButton[arraySize][arraySize];
    buttonsSide = new JButton[arraySize][arraySize][4];
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
  }
  /* Button Listener Class
   * @desc sort words through  array from list of ten numbers
   * @author Charles Ahn
   * @version February 12th 2019
   * @param buttons
   * @return where clicks came from
   */
  static class buttonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    { 
      //Loop through all the buttons
      for (int i = COUNTERSTART; i < arraySize; i++) 
      {
        for (int u = COUNTERSTART; u < arraySize; u++) 
        {
          //Find where button was clicked on the grid
          if(event.getSource()== buttonsMain[i][u])
          {
            row = i;
            column = u;
            played = true;
            //
            layersUnderButtons = layer[row][column];
            layer[row][column] = layer[row][column] + 1;
            //Find where max buttons are layed
            if (arraySize == layer[row][column]/REPEAT)
            {
              buttonsMain[i][u].setEnabled(false);
            }
            canvas.repaint(); 
          }
        }
      }
      canvas.repaint();
      System.out.println("Row" + (1+row)+ "Column" + (1+column));
      System.out.println(played);
    }
  }//Button Listener Class
  /* Graphics Panel
   * @desc draw images on the buttons
   * @author Charles Ahn
   * @version March 3rd 2019
   */
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
      g.drawImage(overlayGame,COORDINATEX,COORDINATEY,this);
      //Get layer number of the button
      int x = layersUnderButtons/REPEAT;
      //Drawing chip on the main panel
      if (played==true && computerTurn == true)
      {
        buttonsMain[row][column].setIcon(new ImageIcon("chip/chipCOMP" + (x+1) + ".png"));
      }
      else if (played==true && computerTurn == false)
      {
        buttonsMain[row][column].setIcon(new ImageIcon("chip/chipPLAYER" + (x+1) + ".png"));
      }
      //North Panel
      if (played==true && row == arraySize-1 && computerTurn == true)
      {
        buttonsSide[row-x][column][ZERO].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && row == arraySize-1)
      {
        buttonsSide[row-x][column][ZERO].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
      //West Panel
      if (played==true && column == ZERO && computerTurn == true)
      {
        buttonsSide[(arraySize-1)-column-x][row][1].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && column == ZERO)
      {
        buttonsSide[(arraySize-1)-column-x][row][1].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
      //South display
      if (played==true && row == ZERO && computerTurn == true)
      {
        buttonsSide[(arraySize-1)-row-x][(arraySize-1)-column][2].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && row == ZERO)
      {
        buttonsSide[(arraySize-1)-row-x][(arraySize-1)-column][2].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
      //East panel
      if (played==true && column == arraySize-1 && computerTurn == true)
      {
        buttonsSide[column-x][(arraySize-1)-row][3].setIcon(new ImageIcon("chip/chipCOMP0.png"));
      }
      else if (played==true && computerTurn == false && column == arraySize-1)
      {
        buttonsSide[column-x][(arraySize-1)-row][3].setIcon(new ImageIcon("chip/chipPLAYER0.png"));
      }
    }//Paint Component
  }//Graphics Class
}//StartingFrameFinal Class
