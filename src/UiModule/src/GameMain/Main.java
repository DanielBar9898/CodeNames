package GameMain;
import EnginePackage.*;
import GamePackage.Game;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hey, please enter your XML file name:\n");
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        EngineImpl engine = new EngineImpl();
        engine.loadXmlFile(filename);
    }
}
