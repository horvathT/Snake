/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author tibor_horvath
 */
public final class SnakeFrame extends JFrame {

    private GamePanel gamePanel;

    public SnakeFrame() {
        super("Snake");
        frameSetup();

        this.gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.SOUTH);
        add(gamePanel.scorePanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Highscore") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataManipulator dm = new DataManipulator();
                HighScoreWindow highScoreWindow = new HighScoreWindow(dm.getData(), SnakeFrame.this);
            }
        });
        JMenuItem menuNewGame = new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();

            }
        });
        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(menuHighScores);
        menu.add(menuNewGame);
        menu.add(menuGameExit);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        gamePanel.snake.addDirection(Direction.UP);
                        break;

                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        gamePanel.snake.addDirection(Direction.DOWN);
                        break;

                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        gamePanel.snake.addDirection(Direction.LEFT);
                        break;

                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        gamePanel.snake.addDirection(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_P:
                        gamePanel.setIsPaused(!gamePanel.getIsPaused());
                        break;
                    case KeyEvent.VK_R:
                        reset();
                        break;
                }
            }

        });
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void frameSetup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        URL url = SnakeFrame.class.getClassLoader().getResource("res/icon.jpg");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

    }

    public void reset() {

        this.gamePanel = new GamePanel();

        add(gamePanel, BorderLayout.SOUTH);
        add(gamePanel.scorePanel, BorderLayout.NORTH);

       // pack();
    }
}
