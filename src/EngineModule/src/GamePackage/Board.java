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
    public Board(Board otherBoard) {
        numRows = otherBoard.numRows;
        numCols = otherBoard.numCols;
        numOfBlackWords = otherBoard.numOfBlackWords;
        numOfTotalWords = otherBoard.numOfTotalWords;
        wordsSet = new HashSet<>(otherBoard.getWords());
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

    public void assignWordsToTeams(Team team1,Team team2){
        List<Word> wordList = new ArrayList<>(wordsSet);
        Collections.shuffle(wordList);
        Word w = null;
        int serial = 1;
        for (int i = 0; i < this.numOfBlackWords; i++) {
            wordList.remove(0).setColor(Word.cardColor.BLACK);
        }

        for (int i = 0; i < team1.getWordsToGuess(); i++) {
            w = wordList.remove(0);
            w.setColor(Word.cardColor.TEAM1);
            team1.addWordToGuess(w);
        }

        for (int i = 0; i < team2.getWordsToGuess(); i++) {
            w = wordList.remove(0);
            w.setColor(Word.cardColor.TEAM2);
            team2.addWordToGuess(w);
        }
        for(Word remainingWords : wordList){
            remainingWords.setColor(Word.cardColor.NEUTRAL);
        }
        for(Word word: wordsSet){
            word.setSerialNumber(serial);
            serial++;
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
