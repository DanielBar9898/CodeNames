package engine.GamePackage;

public class Player {
    public enum Role {
        GUESSER,
        DEFINER
    }

    private String name;
    private int serialGameNumber;
    private Role role;
    private String team;

    public Player(String name, Role role, int serialGameNumber, String team) {
        this.name = name;
        this.serialGameNumber=serialGameNumber;
        this.role = role;
        this.team = team;

    }

    public String getTeam() {
        return team;
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
