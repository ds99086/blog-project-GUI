package swingcomponent.jsobject;

import java.sql.Timestamp;

import java.util.*;


public class UserList  {

    private static String [] COLUMN_NAMES = {"userID", "username", "hashPassword", "firstName", "lastName",
            "dateOfBirth", "avatarImage", "authToken", "adminstratorLevel", "introduction"};
    private List<User> userList = new ArrayList<User>();
    private List<UserListListener> listeners;


    public UserList(List<User> list) {
        this.userList = list;
        listeners = new ArrayList<UserListListener>();
    }

    public int getUserListSize(){
        ;return userList.size();
    }


    public User get(int rowIndex) {
        return userList.get(rowIndex);
    }

    public void deleteUser( int rowIndex) {
        User targetUser = userList.get(rowIndex);
        targetUser.setHashPassword("");
        targetUser.setFirstName("");
        targetUser.setLastName("");
        targetUser.setDateOfBirth("");
        targetUser.setAvatarImage("/deleteIcon");
        targetUser.setAuthToken("");
        Date date = new Date();
        targetUser.setIntroduction("User was deleted at " + new Timestamp(date.getTime()));
        userToString(targetUser);
        System.out.println(targetUser);

        for(UserListListener listener: listeners) {
            listener.update(targetUser);
        }

    }

    public void userToString(User user) {
        System.out.println("username: " + user.getUsername() +
                "userFirstname: " + user.getFirstName() +
                "user introduction: " + user.getIntroduction() );
    }

    public int getIndexFor(User user) {
        for (int i = 0; i < userList.size(); i++) {
            User oneUser = userList.get(i);
            if (oneUser.getUsername().equals(user.getUsername())){
                return i;
            }
        }
        return -1;
    }

    public void addUserListListener(UserListListener listener) {
        listeners.add(listener);
    }
}


