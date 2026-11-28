package edu.txst.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * The main graphical user interface for the Maze game. 
 * Handles rendering, user input, and menu interactions.
 */
public class MazeGUI extends JFrame {
    private Board originalBoard;
    private Board currentBoard;
    private GameEngine engine;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private JMenuItem resetItem;

    /** Constructs the main GUI frame and initializes components. */
    public MazeGUI() {
        setTitle("16-Bit Maze");
        setSize(640, 480); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initMenu();
        infoPanel = new InfoPanel();
        gamePanel = new GamePanel();
        add(infoPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (engine == null) return;
                
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> engine.movePlayer(-1, 0);
                    case KeyEvent.VK_DOWN -> engine.movePlayer(1, 0);
                    case KeyEvent.VK_LEFT -> engine.movePlayer(0, -1);
                    case KeyEvent.VK_RIGHT -> engine.movePlayer(0, 1);
                }
                
                // Fixed: Update UI labels with actual engine values
                infoPanel.setInfoSteps(engine.getSteps());
                infoPanel.setInfoCoins(engine.getCoins());
                gamePanel.repaint();

                if (engine.playerWins()) {
                    // Fixed: Correct score calculation formula
                    int steps = engine.getSteps();
                    int coins = engine.getCoins();
                    int score = (steps * -1) + (coins * 5);
                    
                    JOptionPane.showMessageDialog(MazeGUI.this,
                            "Congratulations! You found the exit.\nYour score: " + score,
                            "Level Complete", JOptionPane.INFORMATION_MESSAGE);
                    engine = null;
                    resetItem.setEnabled(false);
                }
            }
        });
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem openItem = new JMenuItem("Open");
        resetItem = new JMenuItem("Reset");
        resetItem.setEnabled(false); 
        openItem.addActionListener(e -> openFile());
        resetItem.addActionListener(e -> resetGame());
        gameMenu.add(openItem);
        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            CSVBoardLoader loader = new CSVBoardLoader();
            originalBoard = loader.load(selectedFile.getAbsolutePath());
            currentBoard = originalBoard.clone();
            engine = new GameEngine(currentBoard);
            resetItem.setEnabled(true);
            gamePanel.setBoard(currentBoard);
            infoPanel.setInfoSteps(0);
            infoPanel.setInfoCoins(0);
            gamePanel.repaint();
        }
    }

    private void resetGame() {
        if (originalBoard != null) {
            currentBoard = originalBoard.clone();
            engine = new GameEngine(currentBoard);
            gamePanel.setBoard(currentBoard);
            infoPanel.setInfoSteps(0);
            infoPanel.setInfoCoins(0);
            gamePanel.repaint();
        }
    }

    private class InfoPanel extends JPanel {
        private JLabel infoSteps;
        private JLabel infoCoins;

        public InfoPanel() {
            this.setLayout(new FlowLayout());
            this.add(new JLabel("Steps: "));
            infoSteps = new JLabel("0");
            this.add(infoSteps);
            this.add(new JLabel("Coins: "));
            infoCoins = new JLabel("0");
            this.add(infoCoins);
        }

        public void setInfoSteps(int remainingSteps) {
            this.infoSteps.setText(Integer.toString(remainingSteps));
        }

        public int getInfoSteps() {
            return Integer.parseInt(this.infoSteps.getText());
        }

        public void setInfoCoins(int infoCoins) {
            this.infoCoins.setText(Integer.toString(infoCoins));
        }

        public int getInfoCoins() {
            return Integer.parseInt(this.infoCoins.getText());
        }
    }

    private class GamePanel extends JPanel {
        private Board board;
        private final int TILE_SIZE = 64; 

        public void setBoard(Board board) {
            this.board = board;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (board == null) return;
            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 10; c++) {
                    int cell = board.getCell(r, c);
                    drawTile(g, cell, c * TILE_SIZE, r * TILE_SIZE);
                }
            }
        }

        private void drawTile(Graphics g, int type, int x, int y) {
            switch (type) {
                case 0 -> g.setColor(Color.LIGHT_GRAY); // Floor
                case 1 -> g.setColor(Color.DARK_GRAY);  // Wall
                case 2 -> g.setColor(Color.YELLOW);     // Coin
                case 5 -> g.setColor(Color.MAGENTA);    // Exit
                case 6 -> g.setColor(Color.BLUE);       // Player
                default -> g.setColor(Color.BLACK);
            }
            g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, TILE_SIZE, TILE_SIZE); // Grid lines
        }
    }

    /**
     * Main entry point for the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MazeGUI().setVisible(true));
    }
}
