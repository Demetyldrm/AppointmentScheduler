package yildirim.dbclientapp.model;

public class Users {

    public int userID;
    public String userName;
    public String userPassword;
    /**
     * @param userID  user id
     * @param userName username
     * @param userPassword  user password
     */

    public Users(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * @return userID
     */
    public int getUserID() {

        return userID;
    }

    /**
     * @return userName
     */
    public String getUserName() {

        return userName;
    }

    /**
     * @return userPassword
     */
    public String getUserPassword() {

        return userPassword;
    }
    @Override
    public String toString(){
        return userName;
    }



}

