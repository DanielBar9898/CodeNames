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
        Collections.shuffle(wordList);
    }
    public void printHiddenBoard() {
        numRows=wordsSet.size()/numCols;
        List<Word> wordList = new ArrayList<>(wordsSet);
        int itr=0;
        boolean wordsLine=true;
        System.out.println("--------------------------------------------------------------------------");
        for (int i = 0; i < numRows; i++) {
            System.out.print("| ");
            for (int j = 0; j < numCols; j++) {
                Word currWord= wordList.get(itr);
                if (currWord != null) {
                    if(wordsLine) {
                        System.out.print(currWord.toString());
                        System.out.print("          ");
                        System.out.print(" | ");
                        wordsLine=false;
                    }
                    else {
                        System.out.print("(" + currWord.getSerialNumber()+ ")");
                        System.out.print(currWord.getCharFound());
                        System.out.print("("+currWord.getCharColor()+")");
                        System.out.print(" | ");
                        wordsLine=true;
                    }
                }

                System.out.print(" | ");
                itr++;
            }
            if(!wordsLine) {
                itr=itr-numCols;
            }
            System.out.println();
            System.out.println("--------------------------------------------------------------------------");
        }
    }
    public void printVisibleBoard() {
        List<Word> wordList = new ArrayList<>(wordsSet);
        int itr=0;
        boolean wordsLine=true;
        System.out.println("--------------------------------------------------------------------------");
        for (int i = 0; i < numRows; i++) {
            System.out.print("| ");
            for (int j = 0; j < numCols; j++) {
                Word currWord= wordList.get(itr);
                if (currWord != null) {
                    if(wordsLine) {
                        System.out.print(currWord.toString());
                        System.out.print("          ");
                        System.out.print(" | ");
                        wordsLine=false;
                    }
                    else {
                        System.out.print("(" + currWord.getSerialNumber()+ ")");
                        if(currWord.isFound()) {
                            System.out.print(currWord.getCharFound());
                            System.out.print("(" + currWord.getCharColor() + ")");
                        }
                        System.out.print(" | ");
                        wordsLine=true;
                    }
                }

                System.out.print(" | ");
                itr++;
            }
            if(!wordsLine) {
                itr=itr-numCols;
            }
            System.out.println();
            System.out.println("--------------------------------------------------------------------------");
        }
    }
    /*
    public void printHiddenBoard() {
        List<Word> wordList = new ArrayList<>(wordsSet);

        final int numWordsInLine= 5;
        int wordInLine =0;
        int wordLength;
        for(int i=0;i<numRows;i++){
            System.out.print("|");

            for(int j=0;j<numCols;j++){
              while(wordInLine %numWordsInLine!=0){
                  String currWord= wordList.get(wordInLine).toString();
                  wordLength=currWord.length();
                  System.out.println("     ");
                  System.out.print(currWord);
                  for(in)

                  System.out.println();
              }
                System.out.println();
            }
        }
for (Word word : wordsSet) {
    System.out.println(word);
}

    }

     */

    public void assignWordsToTeams(Team team1,Team team2){
        List<Word> wordList = new ArrayList<>(wordsSet);
        Collections.shuffle(wordList);
        Word w = null;
        for (int i = 0; i < this.numOfBlackWords; i++) {
            wordList.remove(0).setColor(Word.cardColor.BLACK);
        }

        for (int i = 0; i < team1.getWordsToGuess(); i++) {
            wordList.remove(0).setColor(Word.cardColor.TEAM1);
        }
        for (int i = 0; i < team1.getWordsToGuess(); i++) {
            w = wordList.remove(0);
            w.setColor(Word.cardColor.TEAM1);
            team1.addWordToGuess(w);
        }

        for (int i = 0; i < team2.getWordsToGuess(); i++) {
            wordList.remove(0).setColor(Word.cardColor.TEAM2);
        }
        for (int i = 0; i < team2.getWordsToGuess(); i++) {
            w = wordList.remove(0);
            w.setColor(Word.cardColor.TEAM2);
            team2.addWordToGuess(w);
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
