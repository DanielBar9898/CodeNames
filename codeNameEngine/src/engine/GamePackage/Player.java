package engine.GamePackage;

import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return serialGameNumber == player.serialGameNumber &&
                Objects.equals(name, player.name) &&
                role == player.role &&
                Objects.equals(teamOfPlayer, player.teamOfPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, serialGameNumber, role, teamOfPlayer);
    }
}
