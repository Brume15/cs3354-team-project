package edu.txst.project;

public class GameEngine {
	private Board board;
	private int playerRow;
	private int playerCol;
	private int exitRow;
	private int exitCol;

	// Cell Type Constants
	private static final int FLOOR = 0;
	private static final int WALL = 1;
	private static final int COIN = 2;
	/** Cell code for a reusable trap that blocks movement. */
	private static final int TRAP = 4;
	/** Number of steps charged for each attempt to enter a trap. */
	private static final int TRAP_PENALTY = 3;
	private static final int EXIT = 5;
	private static final int PLAYER = 6;

	public GameEngine(Board board) {
		this.board = board;
		findPlayer();
		findExit();
	}

	public boolean playerWins() {
		return false;
	}

	private void findPlayer() {
		for (int r = 0; r < 5; r++) {
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
		for (int r = 0; r < 5; r++) {
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
	 *
	 * @return the number of steps recorded on the current board
	 */
	public int getSteps() {
		return board.stepCounter.getSteps();
	}

	/**
	 * Attempts to move the player. A trap adds exactly three steps without
	 * moving the player or removing the trap.
	 * 
	 * @param dRow Change in row (-1, 0, 1)
	 * @param dCol Change in column (-1, 0, 1)
	 */
	public void movePlayer(int dRow, int dCol) {
		int targetRow = playerRow + dRow;
		int targetCol = playerCol + dCol;
		int targetCell = board.getCell(targetRow, targetCol);

		// Check for Walls or Out of Bounds
		if (targetCell == WALL || targetCell == -1) {
			return; // Movement blocked
		}

		if (targetCell == TRAP) {
			for (int i = 0; i < TRAP_PENALTY; i++) {
				board.stepCounter.increaseSteps();
			}
			return; // Stop before changing either cell or counting a normal move.
		}

		// Move the Player
		// Current position becomes Floor (or Goal if player was standing on one)
		// Note: For simplicity, this engine assumes player replaces the cell.
		// If you want "Player on Goal", you'd add a 6th constant.
		board.setCell(playerRow, playerCol, FLOOR);

		playerRow = targetRow;
		playerCol = targetCol;
		board.setCell(playerRow, playerCol, PLAYER);

	}
}
