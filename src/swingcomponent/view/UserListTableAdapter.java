package swingcomponent.view;

import swingcomponent.jsobject.User;
import swingcomponent.jsobject.UserList;
import swingcomponent.jsobject.UserListListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class UserListTableAdapter extends AbstractTableModel implements UserListListener {

    private static String [] COLUMN_NAMES = {"userID", "username", "hashPassword", "firstName", "lastName",
            "dateOfBirth", "avatarImage", "authToken", "adminstratorLevel", "introduction"};


    private UserList userList;

    public UserListTableAdapter(UserList userList) {
        this.userList = userList;
    }

    @Override
    public int getRowCount() {
        return userList.getUserListSize();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = userList.get(rowIndex);
        Object result = null;
        switch(columnIndex) {
            case 0 :
                result = user.getUserID();
                break;
            case 1:
                result = user.getUsername();
                break;
            case 2:
                result = user.getHashPassword();
                break;
            case 3:
                result = user.getFirstName();
                break;
            case 4:
                result = user.getLastName();
                break;
            case 5:
                result = user.getDateOfBirth();
                break;
            case 6:
                result = user.getAvatarImage();
                break;
            case 7:
                result = user.getAuthToken();
                break;
            case 8:
                result = user.getAdminstratorLevel();
                break;
            case 9:
                result = user.getIntroduction();
                break;
        }
        return result;

    }


    @Override
    public void update(User user) {
        int rowIndex = userList.getIndexFor(user);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}
