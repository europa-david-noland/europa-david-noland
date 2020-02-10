import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
    //contact list field
    private static List<String> contactList = new ArrayList<>();
    //main
    public static void main(String[] args) {
        Contact firstContact = new Contact("Test", "Testerson", "1234567890","test@email.com");
        Contact secondContact = new Contact("Testy", "Testinez", "0987654321","testinez@email.com");


        contactList.add(firstContact.toContactString());
        contactList.add(secondContact.toContactString());
        System.out.println(contactList);

        initContacts();
        writeFile();
    }

    //check for and create initial contacts file
    static void initContacts(){
        String directory = "contacts";
        String filename = "contacts.txt";
        Path contactsDirectory= Paths.get(directory);
//        System.out.println(contactsDirectory.toAbsolutePath());
        Path contactsFile = Paths.get(directory, filename);

        try {
            if (Files.notExists(contactsDirectory)) {
                Files.createDirectories((contactsDirectory));
//                System.out.println("Created directory");
            }
            if (!Files.exists(contactsFile)) {
                Files.createFile(contactsFile);
//                System.out.println("Created file");
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    //generic write method (take list and write)
    static void writeFile(){

        try{
            Path contactsListPath = Paths.get("contacts","contacts.txt");
            Files.write(contactsListPath, contactList);
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public int list(List<String> inputList){

    }
}
