package engine.GamePackage;

public class Player {
    public enum Role {
        GUESSER,
        DEFINER
    }

    private String name;
    private int serialGameNumber;
    private Role role;
    private String teamOfPlayer;

    public Player(String name, Role role, int serialGameNumber, String teamNameOfPlayer) {
        this.name = name;
        this.serialGameNumber = serialGameNumber;
        this.role = role;
        this.teamOfPlayer = teamNameOfPlayer;
    }

    public String getTeamOfPlayer() {
        return teamOfPlayer;
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
