package edu.txst.project;

/**
 * Interface for loading a Board from an external file source.
 */
public interface BoardLoader {
    /**
     * Loads a board from the specified file path.
     * @param filename path to the level file
     * @return populated Board object
     */
    Board load(String filename);
}
