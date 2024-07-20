package engine.users;

public class NormalUser extends User {
    private String name;

    public NormalUser(String name) {
        super(name);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
