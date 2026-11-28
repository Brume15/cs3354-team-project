package edu.txst.project;

/**
 * Handles core game logic, player movement, win conditions, and interactions.
 */
public class GameEngine {
    private Board board;
    private int playerRow;
    private int playerCol;
    private int exitRow;
    private int exitCol;

    private static final int FLOOR = 0;
    private static final int WALL = 1;
    private static final int COIN = 2;
    private static final int EXIT = 5;
    private static final int PLAYER = 6;

    /**
     * Constructs a new GameEngine for the given board.
     * @param board the game board
     */
    public GameEngine(Board board) {
        this.board = board;
        findPlayer();
        findExit();
    }

    /**
     * Checks if the player has reached the exit.
     * @return true if player wins, false otherwise
     */
    public boolean playerWins() {
        return playerRow == exitRow && playerCol == exitCol;
    }

    private void findPlayer() {
        // Fixed: Loop up to 6 rows (board has 6 rows)
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 10; c++) {
                if (board.getCell(r, c) == PLAYER) {
                    playerRow = r;
                    playerCol = c;
                    return;
                }
            }
        }
    }

    private void findExit() {
        // Fixed: Loop up to 6 rows
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 10; c++) {
                if (board.getCell(r, c) == EXIT) {
                    exitRow = r;
                    exitCol = c;
                    return;
                }
            }
        }
    }

    /**
     * Attempts to move the player.
     * @param dRow change in row (-1, 0, 1)
     * @param dCol change in column (-1, 0, 1)
     */
    public void movePlayer(int dRow, int dCol) {
        int targetRow = playerRow + dRow;
        int targetCol = playerCol + dCol;
        int targetCell = board.getCell(targetRow, targetCol);

        if (targetCell == WALL || targetCell == -1) {
            return; // Blocked by wall or out of bounds
        }

        // Fixed: Increment step counter on every valid move
        board.stepCounter.increaseSteps();

        // Fixed: Increment coin counter if moving onto a coin
        if (targetCell == COIN) {
            board.coins++;
        }

        board.setCell(playerRow, playerCol, FLOOR);
        playerRow = targetRow;
        playerCol = targetCol;
        board.setCell(playerRow, playerCol, PLAYER);
    }
    
    /**
     * Gets the current step count.
     * @return number of steps
     */
    public int getSteps() {
        return board.stepCounter.getSteps();
    }
    
    /**
     * Gets the current coin count.
     * @return number of coins collected
     */
    public int getCoins() {
        return board.coins;
    }
}
