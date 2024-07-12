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
                            /*currWord.found();*/
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
                        // Create a string with 25 spaces using String.format
                        String spaces = String.format("%" + (20-currWord.toString().length()) + "s", "");
                        // Print the string containing 25 spaces
                        System.out.println(spaces);
                        System.out.print("|");
                        wordsLine=false;
                    }
                    else {
                        int charCount= calculateChars(currWord.getSerialNumber(),currWord.getCharColor());
                        System.out.print("(" + currWord.getSerialNumber()+ ")");
                        if(currWord.isFound()) {
                            System.out.print(currWord.getCharFound());
                            System.out.print("(" + currWord.getCharColor() + ")");
                            String spaces = String.format("%" + (20-charCount) + "s", "");
                            // Print the string containing 25 spaces
                            System.out.println(spaces);
                            System.out.print("|");
                        }
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
}


