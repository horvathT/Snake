/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author tibor_horvath
 */
public class GamePanel extends JPanel {

    private final DataManipulator dm = new DataManipulator();
    private final int FPS = 8;
    public static final int COL_COUNT = 25;
    public static final int ROW_COUNT = 25;
    public static final int TILE_SIZE = 20;
    public static final Color BACKGROUND = new Color(245, 222, 179);

    private boolean isGameOver = false;
    private boolean isPaused = false;

    private int seconds = 0;
    private int fruitsEaten = 0;
    private Timer fpsTimer;
    private Timer elapsedTime;

    private final TileType tiles[][];
    public Snake snake;
    public ScorePanel scorePanel;

    public GamePanel() {

        this.tiles = new TileType[ROW_COUNT][COL_COUNT];
        setPreferredSize(new Dimension(COL_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE));
        setBackground(BACKGROUND);

        initGamePanel();
        initGameTimer();
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    private void initGameTimer() {
        fpsTimer = new Timer(1000 / FPS, new NewFrameListener());
        fpsTimer.start();

        ActionListener clock = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increaseSeconds();
            }
        };

        elapsedTime = new Timer(1000, clock);
        elapsedTime.start();
    }

    private void initGamePanel() {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                tiles[i][j] = TileType.EMPTY;
            }
        }
        createBlocks();
        createFruit();
        this.snake = new Snake(this);
        this.fruitsEaten = 0;
        this.seconds = 0;
        this.scorePanel = new ScorePanel(this);
    }

    private void createBlocks() {
        int i = 0;
        Random r = new Random();
        while (i < 10) {
            int x = r.nextInt(ROW_COUNT);
            int y = r.nextInt(COL_COUNT);
            if (getTile(x, y) == TileType.EMPTY && (((x<10 || x>15) || (y<10 || y>15)))) {
                setTile(x, y, TileType.BLOCK);
                i++;
            }
        }
    }

    private void createFruit() {
        Random r = new Random();
        int x = r.nextInt(ROW_COUNT);
        int y = r.nextInt(COL_COUNT);
        if (getTile(x, y) == TileType.EMPTY) {
            setTile(x, y, TileType.FRUIT);
        } else {
            createFruit();
        }
        /*
        int i = 0;
        while (i < 1) {
            Random r = new Random();
            int x = r.nextInt(ROW_COUNT);
            int y = r.nextInt(COL_COUNT);
            if (getTile(x, y) == null) {
                setTile(x, y, TileType.FRUIT);
                i++;
            }
        }
         */
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < COL_COUNT; x++) {
            for (int y = 0; y < ROW_COUNT; y++) {
                TileType type = getTile(x, y);
                if (type != null) {
                    drawTile(x * TILE_SIZE, y * TILE_SIZE, type, g);
                }
            }
        }
    }

    private void drawTile(int x, int y, TileType type, Graphics g) {
        switch (type) {
            case FRUIT:
                g.setColor(Color.RED);
                g.fillOval(x, y, TILE_SIZE, TILE_SIZE);
                break;
            case BODY:
                g.setColor(Color.GREEN);
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                break;
            case BLOCK:
                g.setColor(Color.GRAY);
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                break;
            case HEAD:
                g.setColor(Color.GREEN.darker().darker());
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        }
    }

    public void setTile(Point point, TileType type) {
        setTile(point.x, point.y, type);
    }

    public void setTile(int x, int y, TileType type) {
        tiles[x][y] = type;
    }

    public TileType getTile(int x, int y) {
        return tiles[x][y];
    }

    private void updateGame() {

        TileType collision = snake.updateSnake();

        if (null != collision) {
            switch (collision) {
                case FRUIT:
                    fruitsEaten++;
                    createFruit();
                    break;
                case BODY:
                    isGameOver = true;
                    break;
                case BLOCK:
                    isGameOver = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void increaseSeconds() {
        seconds++;
    }

    public String secondsToString() {

        int mins = (seconds - (seconds % 60)) / 60;
        int secs = seconds % 60;
        String tmp = ((mins < 10) ? "0" + mins : mins) + ":" + ((secs < 10) ? "0" + secs : secs);
        return tmp;
    }
    
    public int getFruitsEaten() {
        return fruitsEaten;
    }
    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!isPaused) {
                scorePanel.updateScore();
                scorePanel.updateElapsedTime(secondsToString());
                updateGame();
                repaint();
            }
            if (isGameOver) {
                fpsTimer.stop();
                String input = JOptionPane.showInputDialog("Enter your name: ");
                if (input != null) {
                    dm.insertData(input, fruitsEaten);
                }
            }
        }

    }

    

}
