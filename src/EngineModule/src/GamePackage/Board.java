package GamePackage;

import JAXBGenerated.ECNBoard;
import JAXBGenerated.ECNLayout;

import java.util.*;


public class Board {

    private Set<Word> wordsSet;
    int numRows;
    int numCols;
    int numOfBlackWords;
    int numOfTotalWords;

    public Board(ECNBoard board) {
        ECNLayout e = board.getECNLayout();
        numRows = e.getRows();
        numCols = e.getColumns();
        numOfBlackWords = board.getBlackCardsCount();
        numOfTotalWords = board.getCardsCount()+numOfBlackWords;
        wordsSet = new HashSet<>(numOfTotalWords);
    }

    public void addWordsToBoard(Set<Word> wordSet) {
        List<Word> wordList = new ArrayList<>(wordSet);
        Random random = new Random();
        // Ensure numWords does not exceed the size of the given word set
        numOfTotalWords = Math.min(numOfTotalWords, wordSet.size());

        // Add random words to the board
        while(wordsSet.size() < numOfTotalWords) {
            int randomIndex = random.nextInt(wordList.size());
            Word randomWord = wordList.get(randomIndex);
            wordsSet.add(randomWord);
        }
    }

    public void assignWordsToTeams(int numTeam1,int numTeam2){
        List<Word> wordList = new ArrayList<>(wordsSet);
        Collections.shuffle(wordList);
        for (int i = 0; i < this.numOfBlackWords; i++) {
            wordList.remove(0).setColor(Word.cardColor.BLACK);
        }

        for (int i = 0; i < numTeam1; i++) {
            wordList.remove(0).setColor(Word.cardColor.TEAM1);
        }

        for (int i = 0; i < numTeam2; i++) {
            wordList.remove(0).setColor(Word.cardColor.TEAM2);
        }
        for(Word remainingWords : wordList){
            remainingWords.setColor(Word.cardColor.NEUTRAL);
        }
    }

    public Set<Word> getWords() {
        return wordsSet;
    }

    public int getNumOfBlackWords(){
        return numOfBlackWords;
    }

    public int getNumRows(){
        return numRows;
    }
    public int getNumCols(){
        return numCols;
    }
    public int getNumOfWords(){
        return numOfTotalWords;
    }
    public Word getWordBySerialNumber(int serialNumber) {
        for (Word word : wordsSet) {
            if (word.getSerialNumber() == serialNumber) {
                return word;
            }
        }
        return null;
    }

}
