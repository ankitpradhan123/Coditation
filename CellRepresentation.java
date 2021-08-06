/** Imagine an infinite two-dimensional orthogonal grid of square cells, each of which is in one of two
possible states, live or dead. Every cell interacts with its eight neighbors, which are the cells that are
directly horizontally, vertically, or diagonally adjacent.

 At each step in time (tick), the following transitions occur:

 1. Any live cell with fewer than two live neighbors dies, as if by loneliness.
2. Any live cell with more than three live neighbors dies, as if by overcrowding.
3. Any live cell with two or three live neighbors lives, unchanged, to the next generation.
4. Any dead cell with exactly three live neighbors comes to life.

 Write a program in the language(s) of your choice with following guidelines:
 1. Accept a user input of new cells (max 100 at every step/tick) to be placed; each cell should have a
 unique name which is to be accepted by the user. This input can be accepted through a CLI or
 HTML element or any other form of user interface
 2. Acceptance of the user input represents a tick and program is expected to calculate the state of
 every cell
 3. The program should output the state of the cells and changes - representation (UI/CLI et al.) of
 the state of the cells can be decided by the developer
 4. The program should provide a way to search by the name of the cell and show the current state of
 the cell
 5. The program should end on termination through appropriate OS specific signals

 Author: Ankit Pradhan
 Email id: ankit.03996@gmail.com

 **/


import java.util.Random;
import java.util.Scanner;

/** class implemention for cell representation
 *
 * Conventions
 * int board[][] //The board represents the 2D representation of the cells
 * board[1][2] = 1; //Means cell at (1,2) is live
 * board[2][4] = 0; //Means cell at (2,4) is dead
 */

public class CellRepresentation {
    int row;
    int col;
    int board[][];
    static Scanner input = new Scanner(System.in);

    /** setting the size of the matrix according to the user input **/
    public CellRepresentation(int row, int col){
        this.row = row;
        this.col = col;
        board = new int[this.row][this.col];
    }

    /** Function to generate random live and dead cells **/
    public void randomLiveCells(){
        Random ran = new Random();
        int rowRandom = ran.nextInt(this.row);
        int colRandom = ran.nextInt(this.col);
        board[rowRandom][colRandom] = 1;
    }

    /** Function to display the live and dead cells. '.' is a dead cell and '*' is a live cell **/
    public void displayBoard(){
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                if(board[i][j]==0){
                    System.out.print('.');
                }
                else
                    System.out.print('*');
            }
            System.out.println();
        }
    }

    /** Function to change the state of a specific cell to live **/
    public void setAlive(int row, int col){
        board[row][col] = 1;
    }

    /** Function to change the state of a specific cell to dead **/
    public void setDead(int row, int col){
        board[row][col] = 0;
    }

    /** Function to generate next generation cells according to the rules:
     * 1. Any live cell with fewer than two live neighbors dies, as if by loneliness.
     * 2. Any live cell with more than three live neighbors dies, as if by overcrowding.
     * 3. Any live cell with two or three live neighbors lives, unchanged, to the next generation.
     * 4. Any dead cell with exactly three live neighbors comes to life.
     */
    public void nextGeneration() {
        int[][] newBoard = new int[row][col];

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                int aliveNeighbours = countAliveNeighbours(i, j);

                if (getState(i, j) == 1) {
                    if (aliveNeighbours < 2) {
                        newBoard[i][j] = 0;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[i][j] = 1;
                    } else if (aliveNeighbours > 3) {
                        newBoard[i][j] = 0;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        newBoard[i][j] = 1;
                    }
                }

            }
        }

        this.board = newBoard;
    }

    /** Function to count live neighbours around a specific cell **/
    public int countAliveNeighbours(int row, int col) {
        int count = 0;

        count += getState(row - 1, col - 1); //Checking the top-left neighbour
        count += getState(row, col - 1); //Checking the center-left neighbour
        count += getState(row + 1, col - 1); //Checking the bottom-left neighbour

        count += getState(row - 1, col);// Checking the top-center neighbour
        count += getState(row + 1, col);// Checking the bottom-center neighbour

        count += getState(row - 1, col + 1);//Checking the top-right neighbour
        count += getState(row, col + 1);//Checking the center-right neighbour
        count += getState(row + 1, col + 1);//Checking the bottom right neighbour

        return count;
    }

    /** Function to get the state of the specific cell. if x and y are out of the index then return 0
     * otherwise return the state of the cell **/
    public int getState(int x, int y) {
        if (x < 0 || x >= row) {
            return 0;
        }

        if (y < 0 || y >= col) {
            return 0;
        }

        return this.board[x][y];
    }


    /**Function to take the user input in the form of row and column to change
     * the state of the cell at that specific postion to live**/
    public void takeLiveInput(int row, int col){


        setAlive(row,col);
        System.out.print("Row No: ");
        int r = input.nextInt();
        r = r-1;
        if(r>=0 && r<this.row) {
            System.out.print("Column No: ");
            int c = input.nextInt();
            c = c-1;
            System.out.println("--------------------------");
            takeLiveInput(r, c);

        }
        else
            return;
    }


    /**Function to take the user input in the form of row and column to change
     * the state of the cell at that specific postion to dead**/
    public void takeDeadInput(int row, int col){


        setDead(row,col);
        System.out.print("Row No: ");
        int r = input.nextInt();
        r = r-1;
        if(r>=0 && r<this.row) {
            System.out.print("Column No: ");
            int c = input.nextInt();
            c = c-1;
            System.out.println("--------------------------");
            takeDeadInput(r, c);

        }
        else
            return;
    }

    /**Execution starts from here**/
    public static void main(String args[]){


        System.out.print("Enter the size of the board");
        System.out.println();
        System.out.print("Row size: ");
        int rowSize = input.nextInt();
        System.out.print("Column size: ");
        int colSize = input.nextInt();

        CellRepresentation cell = new CellRepresentation(rowSize, colSize);
        cell.randomLiveCells();
        cell.randomLiveCells();
        cell.randomLiveCells();
        System.out.println("Random live and dead cells generated");
        System.out.println("--------------------------");
        cell.displayBoard();
        System.out.println("--------------------------");


        System.out.println("Enter the row number and column number to make that cell live(rows from 1 to "+rowSize+" and columns from 1 to "+colSize+")");
        System.out.println("Please Enter any value out of the row size to go to the next step");
        System.out.print("Row No: ");
        int rowLive = input.nextInt();
        rowLive = rowLive-1;

        if(rowLive>=0 && rowLive<rowSize) {
            System.out.print("Column No: ");
            int colLive = input.nextInt();
            colLive = colLive-1;
            System.out.println("--------------------------");
            cell.takeLiveInput(rowLive, colLive);

        }

        System.out.println("--------------------------");
        System.out.println("Enter the row number and column number to make that cell dead(rows from 1 to "+rowSize+" and columns from 1 to "+colSize+")");
        System.out.println("Please Enter any value out of the row size to see the current generation and next generation");
        System.out.print("Row No: ");
        int rowDead = input.nextInt();
        rowDead = rowDead-1;

        if(rowDead>=0 && rowDead<rowSize) {
            System.out.print("Column No: ");
            int colDead = input.nextInt();
            colDead = colDead-1;
            System.out.println("--------------------------");
            cell.takeDeadInput(rowDead, colDead);

        }

        System.out.println("--------------------------");
        System.out.println("Current Generation");
        cell.displayBoard();
        cell.nextGeneration();
        System.out.println("--------------------------");
        System.out.println("Next Generation");
        cell.displayBoard();
    }


}
