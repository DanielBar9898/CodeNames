package engine.GamePackage;

public class Player {
    public enum Role {
        GUESSER,
        DEFINER
    }

    private String name;
    private int serialGameNumber;
    private Role role;

    public Player(String name, Role role, int serialGameNumber) {
        this.name = name;
        this.serialGameNumber=serialGameNumber;
        this.role = role;

    }


    public String getName() {
        return name;
    }

    public int getSerialGameNumber() {
        return serialGameNumber;
    }

    public Role getRole() {
        return role;
    }

}
