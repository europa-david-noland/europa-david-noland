import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
    //contact list field
    private static Scanner myScanner = new Scanner(System.in);
    private static List<String> contactList = new ArrayList<>();
    //main
    public static void main(String[] args) {
        //Initial tests
        Contact firstContact = new Contact("Test", "Testerson", "1234567890","test@email.com");
        Contact secondContact = new Contact("Testy", "Testinez", "0987654321","testinez@email.com");
        contactList.add(firstContact.toContactString());
        contactList.add(secondContact.toContactString());
        System.out.println(contactList);
        //Initial tests end
        //Create dir and write
        initContacts();
        writeFile();
        //Create dir and write end
        String[] mainMenuArr = new String[] {"Exit.", "View Contacts", "Add a new contact", "Search a contact by first name.", "Search a contact by last name.", "Delete an existing contact."};
        List<String> mainMenuList = new ArrayList<>(Arrays.asList(mainMenuArr));
        int userSelected = Integer.MAX_VALUE;
        do {
            userSelected = selectFromList(mainMenuList);
            switch(userSelected) {
                case 1:
                    userSelected = 1;
                    System.out.println("Thank you for using Contacts Lister. Goodbye");
                    break;
                case 2:
                    viewContactList();
                    break;
                case 3:
                    addContact();
                    break;
                case 4:
                    //Search by first name
                    break;
                case 5:
                    //Search by last name
                    break;
                case 6:
                    //Delete existing contact
                    break;
                default:
                    break;
            }
        } while(userSelected != 1);
    }

    //check for and create initial contacts file
    static void initContacts(){
        String directory = "contacts";
        String filename = "contacts.txt";
        Path contactsDirectory= Paths.get(directory);
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

    public static int selectFromList(List<String> inputList){
        int output = Integer.MAX_VALUE;
        boolean keepLooping = true;
        do {
            int counter = 1;
            for(String option : inputList) {
                System.out.println(counter + ". " + option);
                counter++;
            }
            System.out.print("Select an option by inputting the corresponding integer: \t");
            try {
                output = Integer.valueOf(myScanner.next());
                if (output <= counter && output > 0) {
                    keepLooping = false;
                }
            } catch(Exception e) {
                System.out.println("Invalid input.");
            }
        } while(keepLooping);

        return output;
    }

    public static void addContact() {
        boolean keepLooping = true;
        myScanner.nextLine();
        System.out.println("Input contact first name: \t");
        String firstName = myScanner.nextLine();
        System.out.println("Input contact last name: \t");
        String lastName = myScanner.nextLine();
        System.out.println("Input contact email: \t");
        String email = myScanner.nextLine();
        String phone = "";
        do {
            System.out.println("Input contact ten digit phone number without any special characters: \t");
            try {
                int phoneNumber = Integer.valueOf(myScanner.next());
                if(Integer.toString(phoneNumber).length() == 10) {
                    keepLooping = false;
                    phone = Integer.toString(phoneNumber);
                }
            } catch(Exception e) {
                System.out.println("Invalid input.");
            }
        } while(keepLooping);
        Contact newContact = new Contact(firstName, lastName, phone, email);
        contactList.add(newContact.toContactString());
        writeFile();
    }

    public static void viewContactList() {
        boolean keepLooping = true;

        //viewing function
        for(String eachContact : contactList){
            System.out.println(eachContact);
        }

        do{
            //create functionality that sets keep looping to false
            myScanner.nextLine();
            System.out.println("Input 1 to exit to main menu: \t");
            try {
                int viewListQuit = Integer.valueOf(myScanner.next());
                if(viewListQuit==1){
                    keepLooping = false;
                }
            } catch(Exception e) {

            }

        } while(keepLooping);

    }
}
