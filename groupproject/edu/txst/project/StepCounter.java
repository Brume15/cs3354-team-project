package edu.txst.project;

/**
 * Tracks the number of steps taken by the player.
 */
public class StepCounter {
    private int steps;

    /** Initializes the step counter to zero. */
    public StepCounter() {
        steps = 0;
    }

    /**
     * Gets the current step count.
     * @return number of steps
     */
    public int getSteps() {
        return steps;
    }

    /** Increments the step count by one. */
    public void increaseSteps() {
        steps++;
    }

    /** Resets the step count to zero. */
    public void resetSteps() {
        steps = 0;
    }
}
