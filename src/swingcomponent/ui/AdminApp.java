package swingcomponent.ui;

import swingcomponent.jsobject.User;
import swingcomponent.jsobject.UserList;
import swingcomponent.jsobject.UserListListener;

import swingcomponent.view.UserListTableAdapter;
import swingcomponent.web.API;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.awt.Color.white;
import static javax.swing.JOptionPane.showMessageDialog;

public class AdminApp extends JFrame {

    private JButton btnLogIn;
    private JButton btnLogOut;

    private JTextArea txtID;
    private JPasswordField txtPassword;

    private JButton btnDeleteUser;
    private JTable tableUserInfo;
    private UserList userList = null;
    private AbstractTableModel userDetailTable;

    private int deleteRow;
    private int deleteUserID;

    public AdminApp() {

        initComponents();

        btnLogIn.addActionListener(this::handleLogInBtnClick);

        btnLogOut.addActionListener(this::handleLogOutBtnClick);

        btnDeleteUser.addActionListener(this::handleDeleteBtnClick);

    }

    private void initComponents() {
        //set up the window
        JFrame frame = new JFrame("Blog Admin Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        TODO Table user info?
//        set layout type: group layout
        JPanel panel = new JPanel();
        //FlowLayout experimentLayout = new FlowLayout();
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);


        //assign variables
        JLabel lblID;
        JLabel lblPassword;
        panel.add(lblID = new JLabel("User ID:"));
        panel.add(txtID = new JTextArea());
        panel.add(lblPassword = new JLabel("Password:"));
        panel.add(txtPassword = new JPasswordField());

        panel.add(btnLogIn = new JButton("Log in"));
        panel.add(btnLogOut = new JButton("Log out"));
        panel.add(btnDeleteUser = new JButton("Delete this user"));

        txtID.setColumns(35);
        txtPassword.setColumns(35);
        txtPassword.setEchoChar('*');
        btnLogOut.setEnabled(false);
        btnDeleteUser.setEnabled(false);

        userDetailTable = new UserListTableAdapter(userList);
        JScrollPane scrollPane;
        panel.add(scrollPane = new JScrollPane());
        panel.add(tableUserInfo = new JTable());
        scrollPane.setViewportView(tableUserInfo);
        //tableUserInfo.setModel(userDetailTable);

        tableUserInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnDeleteUser.setEnabled(true);
                JTable table = (JTable) e.getSource();
                deleteRow = table.rowAtPoint(e.getPoint());

                deleteUserID = (int)(tableUserInfo.getValueAt(deleteRow, 0));
                System.out.println(deleteUserID);
            }
        });

        tableUserInfo.setBackground(white);

        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void handleDeleteBtnClick(ActionEvent e) {
        new DeleteUserSwingWorker(deleteUserID).execute();
    }

    private class DeleteUserSwingWorker extends SwingWorker<String, Void> {
        private int deleteUserID ;

        private DeleteUserSwingWorker(int deleteUserID){
            btnDeleteUser.setEnabled(false);
            this.deleteUserID = deleteUserID;
        }

        @Override
        protected String doInBackground() throws Exception {
            return API.getInstance().getResultAfterDeleteUser(deleteUserID);
        }

        @Override
        protected void done() {

            try {
                String response = get();
                //System.out.println(response);
                if (response.equals("User Deleted")) {
                    userList.deleteUser(deleteRow);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleLogOutBtnClick(ActionEvent e) {
        if (userList.getUserListSize() > 0 ) {
            List<User> newList = new ArrayList<User>();
            UserList newUserList = new UserList(newList);
            userDetailTable = new UserListTableAdapter(newUserList);
            tableUserInfo.setModel( userDetailTable);
        }

        if (!btnLogIn.isEnabled()) {
            btnLogIn.setEnabled(true);
        }
        if (btnLogOut.isEnabled()) {
            btnLogOut.setEnabled(false);
        }
    }

    private void handleLogInBtnClick(ActionEvent e) {
        new CheckUserCredentialsSwingWorker().execute();
    }

    private class CheckUserCredentialsSwingWorker extends SwingWorker<List, Void> {

        private final String username;
        private final String password;

        public CheckUserCredentialsSwingWorker() {
            btnLogIn.setEnabled(false);
            this.username = txtID.getText();
            this.password = txtPassword.getText();
        }

        @Override
        protected List doInBackground() throws Exception {

            return API.getInstance().getUserCredential(username, password);
        }

        @Override
        protected void done() {
            try {
                List response = get();

                if (response == null) {
                    btnLogIn.setEnabled(true);
                } else {
                    btnLogOut.setEnabled(true);
                    showMessageDialog(null, "Welcome Admin");
                    userList = new UserList(response);
                    userDetailTable = new UserListTableAdapter(userList);
                    tableUserInfo.setModel(userDetailTable);
                    userList.addUserListListener((UserListListener) userDetailTable);
                }

            } catch (InterruptedException | ExecutionException e) {
                showMessageDialog(null, "Login Failed!");
                btnLogIn.setEnabled(true);
                e.printStackTrace();
            }

        }
    }

}
