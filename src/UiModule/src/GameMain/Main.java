package GameMain;
import EnginePackage.*;
import GamePackage.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean validFile;
        System.out.println("Hey, please enter your XML file name:");
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        EngineImpl engine = new EngineImpl();
        validFile = engine.loadXmlFile(filename);
        while(!validFile) {
            System.out.println("please enter your XML file name:");
            validFile = engine.loadXmlFile(filename);
            filename = sc.nextLine();
        }

    }
}
