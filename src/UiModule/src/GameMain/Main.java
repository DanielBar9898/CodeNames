package GameMain;
import EnginePackage.*;
import GamePackage.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to Code Names game!");
        EngineImpl engine = new EngineImpl();
        engine.showGameMenu();
        choice = sc.nextInt();
        switch (choice){
            case 1:
                engine.loadXmlFile();
        }
        boolean validFile;
        System.out.println("Please enter your XML file name:");
        String filename = sc.nextLine();
        validFile = engine.loadXmlFile();
        while(!validFile) {
            System.out.println("please enter your XML file name:");
            validFile = engine.loadXmlFile();
            filename = sc.nextLine();
        }

    }
}
