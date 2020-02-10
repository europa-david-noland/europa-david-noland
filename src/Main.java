import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
    //contact list field
    private static Scanner myScanner = new Scanner(System.in);
    private static List<String> contactList = new ArrayList<>();
    private static List<Contact> contactObjList = new ArrayList<>();
    private static List<String> repeatAction = new ArrayList<>();

    //main
    public static void main(String[] args) {
        initContacts();
        //Initial tests
        List<String> currentList = fileToList();
        if(currentList.size()<2) {
            Contact firstContact = new Contact("Test", "Testerson", "1234567890", "test@email.com");
            Contact secondContact = new Contact("Testy", "Testinez", "0987654321", "testinez@email.com");
            contactList.add(firstContact.toContactString());
            contactList.add(secondContact.toContactString());
            System.out.println(contactList);

            writeFile();
        }
        //Initial tests end

        //insert sorcery method
        fileToContactObjs();
        System.out.println(contactList.toString());
        //main menu
        String[] mainMenuArr = new String[] {"Exit.", "View Contacts", "Add a new contact", "Search a contact by first name.", "Search a contact by last name.", "Delete an existing contact."};
        List<String> mainMenuList = new ArrayList<>(Arrays.asList(mainMenuArr));

        repeatAction.add("Exit to main menu.");
        repeatAction.add("Repeat previous action");

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
                    searchFirstName();
                    break;
                case 5:
                    //Search by last name
                    searchLastName();
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

    //read file
    static List<String> fileToList(){
        List<String> contacts = null;

        try {
            Path contactsListPath = Paths.get("contacts","contacts.txt");
            contacts = Files.readAllLines(contactsListPath);
            //from class example
//            for (String line : contacts) {
//                System.out.println(line);
//            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        return contacts;
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
                long phoneNumber = Long.valueOf(myScanner.next());
                if(Long.toString(phoneNumber).length() == 10) {
                    keepLooping = false;
                    phone = Long.toString(phoneNumber);
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

    public static void fileToContactObjs() {
        List<String> myList = fileToList();
        String strList = String.join( ",", myList);
        String[] strArr = strList.split(",");
        for(String contact : strArr) {
            contact = contact.replace("|", "&");
            String[] contactElems = contact.split("&");
            String[] nameArr = contactElems[0].split(" ");
            Contact newContact = new Contact(nameArr[0].trim(), nameArr[1].trim(), contactElems[1], contactElems[2]);
            contactObjList.add(newContact);
            contactList.add(newContact.toContactString());
        }
    }

    public static void searchFirstName(){
        boolean keepLooping = true;
        do{
            List<String> bucket = new ArrayList<>();

            myScanner.nextLine();
            System.out.println("\nEnter your search string: \n");
            String searchTerm = myScanner.next();
            myScanner.nextLine();

            for(Contact result : contactObjList){
                String firstName = result.getfirstName();
                if(firstName.toLowerCase().contains(searchTerm.toLowerCase())){
                    bucket.add(result.toContactString());
                }
            }

            if(bucket.size()==0){
                System.out.println("\nNo results found.\n");
            } else {
                System.out.println("\nHere are your search results:\n");
            }

            for(String contents: bucket){
                System.out.println(contents);
            }
            System.out.println("\n");

            int userSelect = selectFromList(repeatAction);
            if (userSelect==1){
                keepLooping=false;
            }
        }
        while(keepLooping);

    }
    public static void searchLastName(){
        boolean keepLooping = true;
        do{
            List<String> bucket = new ArrayList<>();

            myScanner.nextLine();
            System.out.println("\nEnter your search string: \n");
            String searchTerm = myScanner.next();
            myScanner.nextLine();

            for(Contact result : contactObjList){
                String lastName = result.getlastName();
                if(lastName.toLowerCase().contains(searchTerm.toLowerCase())){
                    bucket.add(result.toContactString());
                }
            }

            if(bucket.size()==0){
                System.out.println("\nNo results found.\n");
            } else {
                System.out.println("\nHere are your search results:\n");
            }

            for(String contents: bucket){
                System.out.println(contents);
            }
            System.out.println("\n");

            int userSelect = selectFromList(repeatAction);
            if (userSelect==1){
                keepLooping=false;
            }
        }
        while(keepLooping);

    }
}
