package edu.txst.project;

/** Simple executable regression tests for the trap feature; no JUnit needed. */
public class TrapTileTest {
    /**
     * Runs the trap tests, throwing an error if a check fails.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        // Check a trap above, below, left, and right of the player.
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            Board board = emptyBoard();
            board.setCell(2, 2, 6);
            int row = 2 + direction[0];
            int col = 2 + direction[1];
            board.setCell(row, col, 4);
            GameEngine engine = new GameEngine(board);
            // The penalty must add to the existing total, not replace it.
            board.stepCounter.increaseSteps();
            engine.movePlayer(direction[0], direction[1]);
            check(engine.getSteps() == 4, "First trap attempt must add exactly 3");
            check(board.getCell(2, 2) == 6, "Player must stay in place");
            check(board.getCell(row, col) == 4, "Trap must remain on the board");
            engine.movePlayer(direction[0], direction[1]);
            check(engine.getSteps() == 7, "Second attempt must add another 3");
            check(board.getCell(2, 2) == 6, "Repeated attempt must not move player");
            check(board.getCell(row, col) == 4, "Repeated attempt must preserve trap");
        }

        Board board = emptyBoard();
        board.setCell(0, 0, 6);
        board.setCell(0, 1, 1);
        GameEngine engine = new GameEngine(board);
        engine.movePlayer(-1, 0);
        engine.movePlayer(0, -1);
        engine.movePlayer(0, 1);
        check(engine.getSteps() == 0, "Walls and edges must not charge a trap penalty");
        check(board.getCell(0, 0) == 6, "Walls and edges must block movement");
        engine.movePlayer(1, 0);
        check(board.getCell(1, 0) == 6, "Normal floor movement must still work");
        check(board.getCell(0, 0) == 0, "Previous player cell must become floor");

        Board original = new CSVBoardLoader().load("tests/levels/trap-demo.csv");
        Board current = original.clone();
        engine = new GameEngine(current);
        engine.movePlayer(0, 1);
        engine.movePlayer(0, 1);
        check(engine.getSteps() == 6, "CSV trap must charge 6 after two attempts");
        check(current.getCell(1, 1) == 6, "CSV player must stay in place");
        check(current.getCell(1, 2) == 4, "CSV trap must remain active");
        engine.movePlayer(1, 0);
        engine.movePlayer(0, 1);
        engine.movePlayer(0, 1);
        engine.movePlayer(-1, 0);
        check(current.getCell(1, 3) == 6, "Player must be able to go around trap");
        check(current.getCell(1, 2) == 4, "Going around must preserve trap");
        check(original.stepCounter.getSteps() == 0, "Original board must stay unchanged");
        current = original.clone(); // Same reset mechanism used by MazeGUI.
        engine = new GameEngine(current);
        check(engine.getSteps() == 0, "Reset must clear penalty steps");
        check(current.getCell(1, 1) == 6, "Reset must restore player");
        check(current.getCell(1, 2) == 4, "Reset must preserve trap");
        engine.movePlayer(0, 1);
        check(engine.getSteps() == 3, "Trap must work after reset");
        System.out.println("PASS: four directions, repeated traps, existing steps, walls, edges,");
        System.out.println("floor movement, CSV loading, detour, original board, and reset.");
    }

    private static Board emptyBoard() {
        Board board = new Board();
        board.setStepCounter();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 10; col++) {
                board.setCell(row, col, 0);
            }
        }
        board.setCell(4, 9, 5);
        return board;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
