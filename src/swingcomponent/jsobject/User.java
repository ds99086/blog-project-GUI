package swingcomponent.jsobject;

public class User {

private int userID;
private String username;
private String hashPassword;
private String firstName;
private String lastName;
private String dateOfBirth;
private String avatarImage;
private String authToken;
private int adminstratorLevel;
private String introduction;

//    int userID, String username, String hashPassword, String firstName, String lastName,
//    String dateOfBirth, String avatarImage, String authToken, int adminstratorLevel, String introduction
    public User() {
        this.userID = userID;
        this.username = username;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.avatarImage = avatarImage;
        this.authToken = authToken;
        this.adminstratorLevel = adminstratorLevel;
        this.introduction = introduction;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getHashPassword() {
        return hashPassword;
    }
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatarImage() {
        return avatarImage;
    }
    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public int getAdminstratorLevel() {
        return adminstratorLevel;
    }
    public void setAdminstratorLevel(int adminstratorLevel) {
        this.adminstratorLevel = adminstratorLevel;
    }

    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
