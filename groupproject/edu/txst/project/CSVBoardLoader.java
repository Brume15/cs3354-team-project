package edu.txst.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implementation of BoardLoader that parses CSV formatted level files.
 */
public class CSVBoardLoader implements BoardLoader {
    
    /**
     * Reads a CSV file and maps integer values to the Board grid.
     * @param filename path to the CSV file
     * @return populated Board object
     */
    @Override
    public Board load(String filename) {
        Board board = new Board();
        board.setStepCounter();
        String line;
        int row = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null && row < 6) {
                String[] values = line.split(",");
                for (int col = 0; col < values.length && col < 10; col++) {
                    int cellType = Integer.parseInt(values[col].trim());
                    board.setCell(row, col, cellType);
                }
                row++;
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading level: " + e.getMessage());
        }
        return board;
    }
}
