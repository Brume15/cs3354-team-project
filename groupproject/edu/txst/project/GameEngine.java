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
    /** Cell code for a reusable trap that blocks movement. */
    private static final int TRAP = 4;
    /** Number of steps charged for each attempt to enter a trap. */
    private static final int TRAP_PENALTY = 3;
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
     * Returns the step total, including trap penalties.
     * @return the number of steps recorded on the current board
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

    /**
     * Attempts to move the player. A trap adds exactly three steps without
     * moving the player or removing the trap.
     * @param dRow Change in row (-1, 0, 1)
     * @param dCol Change in column (-1, 0, 1)
     */
    public void movePlayer(int dRow, int dCol) {
        int targetRow = playerRow + dRow;
        int targetCol = playerCol + dCol;
        int targetCell = board.getCell(targetRow, targetCol);

        if (targetCell == WALL || targetCell == -1) {
            return; // Blocked by wall or out of bounds
        }

        // Trap logic: adds 3 steps and blocks movement
        if (targetCell == TRAP) {
            for (int i = 0; i < TRAP_PENALTY; i++) {
                board.stepCounter.increaseSteps();
            }
            return; // Stop before changing either cell or counting a normal move.
        }

        // Normal move: increment steps and collect coin if present
        board.stepCounter.increaseSteps();
        if (targetCell == COIN) {
            board.coins++;
        }

        board.setCell(playerRow, playerCol, FLOOR);
        playerRow = targetRow;
        playerCol = targetCol;
        board.setCell(playerRow, playerCol, PLAYER);
    }
}
