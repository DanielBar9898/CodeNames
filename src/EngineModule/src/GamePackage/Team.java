package GamePackage;

public class Team {

    private Guesser guesser;
    private Hinter hinter;
    int wordsToGuess,wordsGuessed ;
    protected String teamName;

    public Team(String teamName,int wordsToGuess,Word.cardColor teamColor) {
        this.teamName = teamName;
        this.wordsToGuess = wordsToGuess;
        wordsGuessed = 0;
        guesser = new Guesser(teamColor);
        hinter = new Hinter(teamColor);
    }

    public int getWordsToGuess() {
        return wordsToGuess;
    }
    public String toString(){
        return "";
    }

    public void showTeamWordsState(){
        System.out.println("So far the team guessed correctly"+wordsGuessed+"/"+wordsToGuess+"words");
    }

    public Guesser getGuesser() {
        return guesser;
    }

    public Hinter getHinter() {
        return hinter;
    }
    public void printTeamTurn(){
        System.out.println("Its team: "+teamName.toString()+" turn");
    }

    public void guessedRight(){
        wordsGuessed++;
    }
    public String getTeamName(){
        return teamName;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamName.equals(team.teamName);
    }

    @Override
    public int hashCode() {
        return teamName.hashCode();
    }
}
