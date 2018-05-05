package server;

public class User {
    private String userName=null;
    private String mdp=null;
    User(String userName,String motDePasse){
        this.userName=userName;
        mdp=motDePasse;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
