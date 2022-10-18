package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Sudoku2 extends JFrame {

    final int ROWS = 9;
    final int COLUMNS = 9;

    //mainContainer holds panels, each panel holds a grid
    public Container mainContainer;
    public JPanel[][] panels;
    public JButton[][] grid; //names the grid of buttons


    public Sudoku2(){ //constructor

        mainContainer = getContentPane(); // gets the Container of the window or frame

        this.mainContainer.setLayout(new GridLayout(3,3));
        this.panels = new JPanel[3][3]; // allocate the size of the panels
        this.grid = new JButton[ROWS][COLUMNS]; //allocate the size of grid

        this.setTitle("Sudoku"); //sets title

        ButtonHandler buttonHandler = new ButtonHandler(); //buttonHandler onject for actionListener

        //creating each panel and adding to main container
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                panels[i][j] = new JPanel();
                panels[i][j].setLayout(new GridLayout(3,3));
                panels[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
                panels[i][j].setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
                mainContainer.add(panels[i][j]);

            }
        }

        //creating each grid and adding to panels
        for(int x = 0; x < ROWS; x++){
            for(int y = 0; y < COLUMNS; y++){
                this.grid[x][y] = new JButton(); //creates new button
                grid[x][y].setFont(new Font("Arial", Font.BOLD, 25));
                grid[x][y].setForeground(Color.BLUE);
                this.grid[x][y].setHorizontalAlignment(JButton.CENTER);
                this.grid[x][y].addActionListener(buttonHandler);
                int panelRow = x/3;
                int panelCol = y/3;
                this.panels[panelRow][panelCol].add(grid[x][y]);
            }
        }


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); //sets appropriate size for frame
        this.setVisible(true); //makes frame visible
        this.setSize(800,800);
        this.setLocationRelativeTo(null);

    }

    // reads x,y, and the value of the numbers from data file and adds to Sudoku layout
    static void loadGrid(String filename, JButton[][] grid) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line = br.readLine();

        while(line != null) {
            String[] parts = line.split(",");
            int y = Integer.parseInt(parts[0]);
            int x = Integer.parseInt(parts[1]);
            String value = parts[2];

            grid[x][y].setText(value);
            grid[x][y].setFont(new Font("Arial", Font.BOLD, 25));
            //makes numbers from text file uneditable by user
            grid[x][y].setEnabled(false);

            line = br.readLine();
        }

        br.close();

    }

    //method that checks if the game is solved
    public boolean isSolved(){
        ArrayList<String> values = new ArrayList<String>();
        // checking all the rows
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                String val = grid[i][j].getText();
                if(val == "" || values.contains(val)){
                    return false;
                }
                else{
                    values.add(val);
                }
            }
            values.clear();
        }
        // checking all columns
        for(int j = 0; j < COLUMNS; j++){
            for(int i = 0; i < ROWS; i++){
                String val = grid[i][j].getText();
                if(val == "" || values.contains(val)){
                    return false;
                }
                else{
                    values.add(val);
                }
            }
            values.clear();
        }
        // checking each panel
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                for (Component c : panels[i][j].getComponents()) {
                    if (c instanceof JButton) {
                        String val = ((JButton)c).getText();
                        if(val == "" || values.contains(val)){
                            return false;
                        }
                        else{
                            values.add(val);
                        }
                    }
                }
                values.clear();
            }
        }


        return true;
    }

    //changes value of button when clicked
    public void performClick(int row, int column) {
        String value = grid[row][column].getText();
        switch (value) {
            case "":
                grid[row][column].setText("1");
                break;

            case "9":
                grid[row][column].setText("");
                break;

            default:
                int valInt = Integer.parseInt(value) + 1;
                grid[row][column].setText(Integer.toString(valInt));
                break;

        }

        //dialog box if the puzzle is solved
        if(isSolved()){
            JOptionPane.showMessageDialog(this, "Solved!");
        }
    }

    //actionListener finds which button was clicked and # of times
    private class ButtonHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLUMNS; j++){
                    if(source == grid[i][j]){
                        performClick(i,j);

                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Sudoku2 s = new Sudoku2();//makes new ButtonGrid with 2 parameters
        try {
            loadGrid("SudokuEasy.txt", s.grid);
        } catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

}







