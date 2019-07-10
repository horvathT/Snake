/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author tibor_horvath
 */
public class HighScoreTableModel extends AbstractTableModel {

    private final ArrayList<User> users;
    private final String[] colName = new String[]{"Name", "Points"};

    public HighScoreTableModel(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int i, int j) {
        User h = users.get(i);
        return (j == 0) ? h.getName() : h.getScore();
    }

    @Override
    public String getColumnName(int i) {
        return colName[i];
    }
}
