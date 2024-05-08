package JAXB;

import GamePackage.Game;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class SchemaBasedJAXBMain {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "JAXBGenerated";

    public static void main(String[] args) {
        try{
            InputStream inputStream = new FileInputStream(new File("C:\\Users\\97252\\Desktop\\Java Exercises\\CodeNames-v1.xsd"));
            Game game = deserializeGame(inputStream);
        }
        catch(JAXBException | FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private static Game deserializeGame(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (Game) unmarshaller.unmarshal(inputStream);
    }
}
