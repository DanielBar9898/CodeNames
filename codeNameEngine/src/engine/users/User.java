package engine.users;

public abstract class User {
    private String userName;


    public User(String userName) {
        this.userName = userName;
    }

    public abstract boolean isAdmin();

    public abstract String getName();
}
