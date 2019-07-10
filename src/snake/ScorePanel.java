/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tibor_horvath
 */
public class ScorePanel extends JPanel {

    private GamePanel gamePanel;
    private JLabel elapsedTime;
    //private int seconds = 0;
    private JLabel score;

    public ScorePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLayout(new FlowLayout());

        this.score = new JLabel("Fruits Eaten: ");
        add(score);

        this.elapsedTime = new JLabel("00:00");
        add(elapsedTime);
    }

    public void updateScore() {
        score.setText("Fruits eaten: " + gamePanel.getFruitsEaten() + "     ");
    }

    public void updateElapsedTime(String time) {
        elapsedTime.setText(time);
    }
}
