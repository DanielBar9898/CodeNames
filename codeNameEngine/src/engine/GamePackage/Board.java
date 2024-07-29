package engine.GamePackage;

import engine.JAXBGenerated2.*;
import java.util.*;


public class Board {

    private Set<Word> wordsSet;
    int numRows;
    int numCols;
    int numOfBlackWords;
    int numOfRegularWords;


    public Board(ECNBoard board) {
        ECNLayout e = board.getECNLayout();
        numRows = e.getRows();
        numCols = e.getColumns();
        numOfBlackWords = board.getBlackCardsCount();
        numOfRegularWords = board.getCardsCount();
        wordsSet = new HashSet<>();
    }
    public Board(Board otherBoard) {
        numRows = otherBoard.numRows;
        numCols = otherBoard.numCols;
        numOfBlackWords = otherBoard.numOfBlackWords;
        numOfRegularWords = otherBoard.numOfRegularWords;
        wordsSet = new HashSet<>(otherBoard.getWords());
    }
    public Board(int numRows, int numCols, int numOfBlackWords, int numOfRegularWords, Set<Word> words) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numOfBlackWords = numOfBlackWords;
        this.numOfRegularWords = numOfRegularWords;
        this.wordsSet = words;
    }
    public void addWordsToBoard(Set<Word> wordSet) {
        List<Word> wordList = new ArrayList<>(wordSet);
        Random random = new Random();
        // Ensure numWords does not exceed the size of the given word set
        int numOfTotalWords = numOfBlackWords+ numOfRegularWords;

        // Add random words to the board
        while(wordsSet.size() < numOfTotalWords) {
            int randomIndex = random.nextInt(wordList.size());
            Word randomWord = wordList.get(randomIndex);
            wordsSet.add(randomWord);
        }
    }
    public int getNumOfRegularWords(){
        return numOfRegularWords;
    }


    public void printTheBoard(boolean Hidden) {

        List<Word> shuffleWordsSet = new ArrayList<>(wordsSet);
        int itrWords=0;
        boolean wordsLine=true;
        System.out.println("----------------------------------------------------------------------------------");
        for (int i = 0; i < numRows*2; i++) {
            System.out.print("| ");
            if (wordsLine) {
                for (int j = 0; j < numCols; j++) {
                    Word currWord = shuffleWordsSet.get(itrWords);
                    if (currWord != null) {
                        printWord(currWord);
                        itrWords++;
                    }
                }
                wordsLine = false;
            }
            else {
                for (int j = 0; j < numCols; j++) {
                    Word currWord = shuffleWordsSet.get(itrWords);
                    if (currWord != null) {
                        if(Hidden) {
                            currWord.found();
                            if(currWord.isFound()) {
                                printInfoVisibleBoard(currWord);
                            }
                            else {
                                printInfoHiddenBoard(currWord);
                            }

                        }
                        else{
                            printInfoVisibleBoard(currWord);}
                        itrWords++;
                    }
                }
                wordsLine = true;

            }
            if(!wordsLine) {
                itrWords=itrWords-numCols;
            }
            else{
                System.out.print("\n----------------------------------------------------------------------------------");
            }
            System.out.println();
        }

    }
    public void printInfoVisibleBoard(Word currWord){
        int charCount= calculateChars(currWord.getSerialNumber(),currWord.getCharColor());
        System.out.print("(" + currWord.getSerialNumber()+ ")");
        System.out.print(currWord.getCharFound());
        System.out.print("(" + currWord.getCharColor() + ")");
        int numSpaces=(15-charCount);
        String spaces = String.format("%" + numSpaces + "s", "");
        // Print the string containing 25 spaces
        System.out.print(spaces);
        System.out.print("|");
    }
    public void printInfoHiddenBoard(Word currWord){
        int charCount=2;
        System.out.print("(" + currWord.getSerialNumber()+ ")");
        if(currWord.getSerialNumber()<10){
            charCount++;}
        else {
            charCount=charCount+2;}
        if(currWord.isFound()){
            System.out.print("V");
            charCount++;
        }
        int numSpaces = (15 - charCount);
        String spaces = String.format("%" + numSpaces + "s", "");
        // Print the string containing 25 spaces
        System.out.print(spaces);
        System.out.print("|");
    }

    public void printWord(Word currWord){
        System.out.print(currWord.toString());
        // Create a string with 25 spaces using String.format
        int numSpaces=(15-(currWord.toString().length()));
        String spaces = String.format("%" + numSpaces + "s", "");
        // Print the string containing 25 spaces
        System.out.print(spaces);
        System.out.print("|");
    }
    public synchronized void assignWordsToTeams(List<Team> teams){
        List<Word> wordList = new ArrayList<>(wordsSet);
        Collections.shuffle(wordList);
        Word w = null;
        int serial = 1;
        for (int i = 0; i < this.numOfBlackWords; i++) {
            wordList.remove(0).setWordType("Black");
        }
        for(Team team : teams){
            for(int i =0;i<team.getWordsToGuess();i++){
                w = wordList.remove(0);
                w.setWordType(team.getTeamName());
                team.addWordToGuess(w);
            }
        }
        for(Word remainingWords : wordList){
            remainingWords.setWordType("Neutral");
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
        return numOfRegularWords;
    }
    public Word getWordBySerialNumber(int serialNumber) {
        for (Word word : wordsSet) {
            if (word.getSerialNumber() == serialNumber) {
                return word;
            }
        }
        return null;
    }

    public int calculateChars(int serNum,String color) {
        int counterChars = 5; // num of chars like "("..}
        if (serNum < 10)
            counterChars++;
        else
            counterChars = counterChars + 2;

        switch (color) {
            case "T1":
                counterChars = counterChars + 2;
                break;
            case "T2":
                counterChars = counterChars + 2;
                break;
            case "N":
                counterChars++;
                break;
            case "Black":
                counterChars = counterChars + 5;
                break;
        }

        return counterChars;
    }

    public Set<Word> getBlackWords(){
        Set<Word> blackWords = new HashSet<>(numOfBlackWords);
        for(Word word : wordsSet){
            if(word.getWordType().equalsIgnoreCase("Black")){
                blackWords.add(word);
            }
        }
        return blackWords;
    }
}