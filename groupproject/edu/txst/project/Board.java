package edu.txst.project;

/**
 * Represents the 2D grid-based game board and tracks game state like coins.
 */
public class Board implements Cloneable {
    private final int COLUMNS = 10;
    private final int ROWS = 6;
    private Integer[][] grid;
    public StepCounter stepCounter;
    public int coins = 0; // Track collected coins

    /** Initializes an empty 6x10 grid. */
    public Board() {
        this.grid = new Integer[ROWS][COLUMNS];
    }

    /** Instantiates the step counter for this board. */
    public void setStepCounter() {
        stepCounter = new StepCounter();
    }

    /**
     * Sets the value of a specific cell.
     * @param row row index
     * @param col column index
     * @param value cell type integer
     */
    public void setCell(int row, int col, int value) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
            grid[row][col] = value;
        }
    }

    /**
     * Gets the value of a specific cell.
     * @param row row index
     * @param col column index
     * @return cell type integer, or -1 if out of bounds
     */
    public int getCell(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
            return grid[row][col];
        }
        return -1;
    }

    /**
     * Creates a deep copy of the board, including coin count.
     * @return cloned Board object
     */
    @Override
    public Board clone() {
        Board copy = new Board();
        copy.setStepCounter();
        copy.coins = this.coins;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                copy.setCell(r, c, this.grid[r][c]);
            }
        }
        return copy;
    }
}
