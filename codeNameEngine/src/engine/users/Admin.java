package engine.users;

public class Admin extends User {
    private String name;

    public Admin(String name) {
        super(name);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
