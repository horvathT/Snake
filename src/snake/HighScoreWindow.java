/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 *
 * @author tibor_horvath
 */
public class HighScoreWindow extends JDialog {

    private final JTable table;

    public HighScoreWindow(ArrayList<User> users, JFrame parent) {
        super(parent, true);
        table = new JTable(new HighScoreTableModel(users));
        table.setFillsViewportHeight(true);

        add(new JScrollPane(table));
        setSize(400, 400);
        setTitle("Top 10");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
