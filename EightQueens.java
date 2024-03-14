//Sahithra Kesavan
//Period 4
//package eightqueens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EightQueens extends JFrame {

    GraphicsPanel[][] board = new GraphicsPanel[8][8];
    JPanel centerPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JLabel countLabel = new JLabel("0 out of 92");
    JButton solveButton = new JButton("Solve");
    int current = 0;
    EightQueen eq = new EightQueen();

    public EightQueens() {
        initGUI();
        setTitle("Eight Queens");
        pack();	// tell the layout manager to organize the components optimally
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initGUI() {
        centerPanel.setLayout(new GridLayout(8, 8));
        eq.solve();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i % 2 == 0) {
                    board[i][j] = new GraphicsPanel(i, j, j % 2);
                } else {
                    board[i][j] = new GraphicsPanel(i, j, (j + 1) % 2);
                }
                centerPanel.add(board[i][j]);
            }
        }
        add(centerPanel, BorderLayout.CENTER);
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                solveButtonActionPerformed(ae);
            }
        });
        bottomPanel.add(solveButton);
        bottomPanel.add(countLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void solveButtonActionPerformed(ActionEvent e) {
        boolean[][] temp = eq.list.get(current % 92);
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                if (temp[i][j]) {
                    board[i][j].isVisible = true;
                } else {
                    board[i][j].isVisible = false;
                }
            }
        }
        countLabel.setText("" + (current++ % 92 + 1) + " out of 92");
        repaint();
    }

    public static void main(String[] args) {
        new EightQueens();
    }
}

class GraphicsPanel extends JPanel {

    public int r, c, colorSelection;
    int SIZE = 50;
    Color light = new Color(255, 248, 220);
    Color dark = new Color(222, 184, 135);
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 42);
    String queen = "\u265B";
    public boolean isVisible;

    public GraphicsPanel(int r, int c, int colorSelection) {
        this.r = r;
        this.c = c;
        this.colorSelection = colorSelection;
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g2D);
        g2D.setFont(font);
        if (colorSelection == 0) {
            g2D.setColor(light);
            g2D.fillRect(0, 0, SIZE, SIZE);
        } else {
            g2D.setColor(dark);
            g2D.fillRect(0, 0, SIZE, SIZE);
        }
        if (isVisible) {
            FontMetrics fm = g.getFontMetrics(font);
            int x = (SIZE - fm.stringWidth(queen)) / 2;
            int y = ((SIZE - fm.getHeight()) / 2) + fm.getAscent();
            g2D.setColor(Color.BLACK);
            g2D.drawString(queen, x, y);
        }
        g2D.dispose();
    }

    public Dimension getPreferredSize() {
        Dimension size = new Dimension(SIZE, SIZE);
        return size;
    }
}

class EightQueen {

    public boolean chessBoard[][];
    public ArrayList<boolean[][]> list = new ArrayList<>();

    public EightQueen() {
        chessBoard = new boolean[8][8];
    }

   
    public void solve() {
        recursiveSolve(0);
    }

    // recursive helper method that solves Eight Queens problem
    public void recursiveSolve(int row) {
        // problem solved
        if (row == 8) {
            list.add(copy());
        } 
        else 
        {
            // loop thru rows(or cols) and check if it's a valid move
            // if yes then update spot to true and make a recursive call plus 1
            // be sure to change the spot back to false afterwards
        	   // loop thru rows(or cols) and check if it's a valid move
            for (int col = 0; col < 8; col++)  //going through the columns on the board
            {
                if (isValid(row, col)) //if the spot on the board is empty....
                {
                    chessBoard[row][col] = true;  // placing  the queen
                    recursiveSolve(row + 1);      // recursively move to the next row and on and on..
                    chessBoard[row][col] = false; // backtracking and removing the queen
                }
            
            
        }
    }
    }

    // determines if location is valid
    public boolean isValid(int row, int column) {
        // loop to check rows/cols
        // check for a queen in the same row/col and diagonal as the target location
        // For diagonals, use Math.abs as described in the instructions
    	for (int i = 0; i < row; i++) { //Checking if there's a queen in the same column
            if (chessBoard[i][column]) 
            {
                return false;
            }

           
            int leftDiagonal = column - Math.abs(row - i);  // Calculating the position of the upper left diagonal place
            if (leftDiagonal >= 0 && chessBoard[i][leftDiagonal]) //Checking if there's a queen in the upper left diagonal spot
            {
            	
                return false;
            }

            
            int rightDiagonal = column + Math.abs(row - i);// Calculating the position of the upper right diagonal place
            if (rightDiagonal < 8 && chessBoard[i][rightDiagonal]) //Checking if there's a queen in the upper right diagonal spot
            {
                return false;
            }
        }
        return true;   
        }

    public boolean[][] copy() {
        boolean[][] temp = new boolean[8][8];
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                temp[i][j] = chessBoard[i][j];
            }
        }
        return temp;
    }

    public void printBoard() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.printf("%c ", chessBoard[i][j] ? 'Q' : '_');
            }
            System.out.println();
        }
        System.out.println();
    }
}
