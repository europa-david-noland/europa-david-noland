import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static Scanner myScanner = new Scanner(System.in);
    private static List<String> contactList = new ArrayList<>();
    private static List<Contact> contactObjList = new ArrayList<>();
    private static List<String> repeatAction = new ArrayList<>();
    private static List<String> mainMenuList = new ArrayList<>();
    private static List<String> crudParamOptions = new ArrayList<>();

    public static void main(String[] args) {
        initContacts();
        //MAIN MENU, REPEAT ACTION AND CRUD PARAM LISTS INIT
        mainMenuList = Arrays.asList("Exit.", "View Contacts.", "Add a new contact.", "Search a contact.", "Delete an existing contact.");
        repeatAction = Arrays.asList("Continue.", "Repeat previous action.");
        crudParamOptions = Arrays.asList("By First Name", "By Last Name", "By Phone", "By Email");
        //END MAIN MENU, REPEAT ACTION AND CRUD PARAM LISTS INIT
        //
        //TURN CONTACTS.TXT TO STRING LIST.
        try {
            contactList = fileToList();
        } catch (Exception e) {
            Contact firstContact = new Contact("Alice", "Smith", "1234567890", "jSMITH@email.com");
            Contact secondContact = new Contact("Alice", "Holmes", "0987654321", "aliceisholmes@email.com");
            contactList.add(firstContact.toContactString());
            contactList.add(secondContact.toContactString());
            writeFile();
        }
        if(contactList.size() <= 0) {
            Contact firstContact = new Contact("Alice", "Smith", "1234567890", "jSMITH@email.com");
            Contact secondContact = new Contact("Alice", "Holmes", "0987654321", "aliceisholmes@email.com");
            contactList.add(firstContact.toContactString());
            contactList.add(secondContact.toContactString());
            writeFile();
        }
        //TURN CONTACTS.TXT TO STRING LIST.
        //
        //CONVERT STRING LIST TO LIST OF CONTACT OBJS
        fileToContactObjs();
        //CONVERT STRING LIST TO LIST OF CONTACT OBJS
        //
        //RUN PROGRAM
        int userSelected = Integer.MAX_VALUE;
        System.out.println("\n\nWelcome to The Contacts App.");
        System.out.println("With this app you can manage your contacts list.\n\n");
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
                    search();
                    break;
                case 5:
                    //Delete existing contact
                    deleteContactFromFile();
                    break;
                default:
                    break;
            }
        } while(userSelected != 1);
        //END RUN PROGRAM
    }
    //METHODS THAT INTERACT WITH FILE DIRECTLY
    //
    //CREATE DIR AND FILE IF ABSENT
    static void initContacts(){
        String directory = "contacts";
        String filename = "contacts.txt";
        Path contactsDirectory= Paths.get(directory);
        Path contactsFile = Paths.get(directory, filename);
        try {
            if (Files.notExists(contactsDirectory)) {
                Files.createDirectories((contactsDirectory));
                System.out.println("Created directory");
            }
            if (!Files.exists(contactsFile)) {
                Files.createFile(contactsFile);
                System.out.println("Created file");
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    //END CREATE DIR AND FILE IF ABSENT
    //
    //CONVERT FILE CONTENTS TO STRING LIST
    static List<String> fileToList(){
        List<String> contacts = null;
        try {
            Path contactsListPath = Paths.get("contacts","contacts.txt");
            contacts = Files.readAllLines(contactsListPath);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return contacts;
    }
    //END CONVERT FILE CONTENTS TO STRING LIST
    //
    //WRITE STRING LIST TO CONTACTS.TXT
    static void writeFile(){
        try{
            Path contactsListPath = Paths.get("contacts","contacts.txt");
            Files.write(contactsListPath, contactList);
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
        //refreshes list of Contact objs
        fileToContactObjs();
    }
    //END WRITE STRING LIST TO CONTACTS.TXT
    //
    //STRING LIST TO CONTACT OBJ LIST
    public static void fileToContactObjs() {
        contactObjList.clear();
        List<String> myList = fileToList();
        String strList = String.join( ",", myList);
        String[] strArr = strList.split(",");
        for(String contact : strArr) {
            contact = contact.replace("|", "&");
            String[] contactElems = contact.split("&");
            String[] nameArr = contactElems[0].split(" ");
            //danger code. comment out for demo
            contactElems[1] = contactElems[1].replace("(", "");
            contactElems[1] = contactElems[1].replace(")", "");
            contactElems[1] = contactElems[1].replace("-", "");
            //
            //ArrayIndexOutOfBoundsException below.
            Contact newContact = new Contact(nameArr[0].trim(), nameArr[1].trim(), contactElems[1], contactElems[2]);
            contactObjList.add(newContact);
        }
    }
    //END STRING LIST TO CONTACT OBJ LIST
    //
    //END METHODS THAT INTERACT WITH FILE DIRECTLY
    //
    //DISPLAY THE LIST THAT IS INPUT. USER SELECTS AN INT THAT IS RETURNED
    public static int selectFromList(List<String> inputList){
        int output = Integer.MAX_VALUE;
        boolean keepLooping = true;
        do {
            int counter = 1;
//            System.out.printf("*----------------------------------------*\n|%40s|","");
            System.out.printf("**-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-****-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-**\n||%82s||","");

            for(String option : inputList) {
                System.out.printf("\n||\t" + counter + ". " +  "%-76s ||" +"", option);
                counter++;
            }
            System.out.printf("\n||%82s||","");
//            System.out.printf("\n*----------------------------------------*\n","");
            System.out.printf("\n**=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=****-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-**\n","");

            System.out.println("Select an option by inputting the corresponding integer: \t");
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
    //END DISPLAY THE LIST THAT IS INPUT. USER SELECTS AN INT THAT IS RETURNED
    //
    //PRINT CONTACTS FROM STRING LIST OF CONTACTS
    public static void viewContactList() {
        boolean keepLooping = true;
        do{
            //viewing function
            System.out.printf("Name%-15s  Phone%-9s Email\n","","","");
            System.out.println("______________________________________________________________________________________");
            for(String eachContact : contactList){
                System.out.println(eachContact);
            }
            //create functionality that sets keep looping to false
            myScanner.nextLine();
            try {
                int userSelect = selectFromList(repeatAction);
                if(userSelect == 1){
                    keepLooping = false;
                }
            } catch(Exception ignored) {}
        } while(keepLooping);
    }
    //END PRINT CONTACTS FROM STRING LIST OF CONTACTS
    //
    //CREATE CONTACT OBJ ADD STRING TO LIST. WRITE FILE(WRITE FILE UPDATES OBJ LIST)
    public static void addContact() {
        boolean isBadEmail = true;
        boolean keepLooping = true;
        myScanner.nextLine();
        System.out.println("Input contact first name: \t");
        String firstName = myScanner.nextLine();
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        System.out.println("Input contact last name: \t");
        String lastName = myScanner.nextLine();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        String email = "";
        do {
            System.out.println("Input contact email: \t");
            email = myScanner.nextLine();
            if(email.contains("@") && email.contains(".")) {
                isBadEmail = false;
            } else {
                System.out.println("Please input a proper email address.");
            }
        } while (isBadEmail);
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
        System.out.println("You added: " + newContact.toContactString());
        writeFile();
    }
    //END CREATE CONTACT OBJ ADD STRING TO LIST. WRITE FILE(WRITE FILE UPDATES OBJ LIST)
    //
    //MAIN SEARCH
    public static void search() {
        int userSelected = selectFromList(crudParamOptions);
        switch (userSelected){
            case 1:
                searchFirstName();
                break;
            case 2:
                searchLastName();
                break;
            case 3:
                searchPhone();
                break;
            case 4:
                searchEmail();
                break;
            default:
                break;
        }
    }
    //END MAIN SEARCH
    //
    //SEARCH METHODS
    public static Map<String, Long> searchFirstName(){
        boolean keepLooping = true;
        Map<String, Long> outputMap;
        do{
            outputMap = new HashMap<>();
            myScanner.nextLine();
            System.out.println("\nEnter your search string: \n");
            String searchTerm = myScanner.next();
            myScanner.nextLine();

            for(Contact result : contactObjList){
                String firstName = result.getFirstName();
                if(firstName.toLowerCase().contains(searchTerm.toLowerCase())){
                    outputMap.put(result.toContactString(), result.getId());
                }
            }
            if(outputMap.size() == 0){
                System.out.println("\nNo results found.\n");
            } else {
                System.out.println("\nHere are your search results:\n");
            }
            for(Map.Entry<String, Long> entry : outputMap.entrySet()){
                System.out.println(entry.getKey());
            }
            System.out.println("\n");
            int userSelect = selectFromList(repeatAction);
            if (userSelect==1){
                keepLooping=false;
            }
        } while(keepLooping);
        return outputMap;
    }
    public static Map<String, Long> searchLastName(){
        boolean keepLooping = true;
        Map<String, Long> outputMap;
        do{
            outputMap = new HashMap<>();
            myScanner.nextLine();
            System.out.println("\nEnter your search string: \n");
            String searchTerm = myScanner.next();
            myScanner.nextLine();
            for(Contact result : contactObjList){
                String lastName = result.getLastName();
                if(lastName.toLowerCase().contains(searchTerm.toLowerCase())){
                    outputMap.put(result.toContactString(), result.getId());
                }
            }
            if(outputMap.size() == 0){
                System.out.println("\nNo results found.\n");
            } else {
                System.out.println("\nHere are your search results:\n");
            }
            for(Map.Entry<String, Long> entry : outputMap.entrySet()){
                System.out.println(entry.getKey());
            }
            System.out.println("\n");
            int userSelect = selectFromList(repeatAction);
            if (userSelect==1){
                keepLooping=false;
            }
        } while(keepLooping);
        return outputMap;
    }
    public static Map<String, Long> searchPhone(){
        boolean keepLooping = true;
        Map<String, Long> outputMap;
        do{
            outputMap = new HashMap<>();
            myScanner.nextLine();
            System.out.println("\nEnter your search string: \n");
            String searchTerm = myScanner.next();
            myScanner.nextLine();

            for(Contact result : contactObjList){
                String phone = result.getPhone();
                if(phone.toLowerCase().contains(searchTerm.toLowerCase())){
                    outputMap.put(result.toContactString(), result.getId());
                }
            }
            if(outputMap.size() == 0){
                System.out.println("\nNo results found.\n");
            } else {
                System.out.println("\nHere are your search results:\n");
            }
            for(Map.Entry<String, Long> entry : outputMap.entrySet()){
                System.out.println(entry.getKey());
            }
            System.out.println("\n");
            int userSelect = selectFromList(repeatAction);
            if (userSelect==1){
                keepLooping=false;
            }
        } while(keepLooping);
        return outputMap;
    }
    public static Map<String, Long> searchEmail(){
        boolean keepLooping = true;
        Map<String, Long> outputMap;
        do{
            outputMap = new HashMap<>();
            myScanner.nextLine();
            System.out.println("\nEnter your search string: \n");
            String searchTerm = myScanner.next();
            myScanner.nextLine();

            for(Contact result : contactObjList){
                String email = result.getEmail();
                if(email.toLowerCase().contains(searchTerm.toLowerCase())){
                    outputMap.put(result.toContactString(), result.getId());
                }
            }
            if(outputMap.size() == 0){
                System.out.println("\nNo results found.\n");
            } else {
                System.out.println("\nHere are your search results:\n");
            }
            for(Map.Entry<String, Long> entry : outputMap.entrySet()){
                System.out.println(entry.getKey());
            }
            System.out.println("\n");
            int userSelect = selectFromList(repeatAction);
            if (userSelect==1){
                keepLooping=false;
            }
        } while(keepLooping);
        return outputMap;
    }
    //END SEARCH METHODS
    //
    //DELETE CONTACT MAIN
    public static void deleteContactFromFile() {
        boolean keepLooping = true;
        do{
            myScanner.nextLine();
            int userSelected = selectFromList(crudParamOptions);
            switch(userSelected) {
                case 1:
                    //By first name
                    Map<String, Long> firstMap = searchFirstName();
                    List<String> firstStrList = new ArrayList<>(firstMap.keySet());
                    System.out.println("Which would you like to delete?");
                    int firstSelection = selectFromList(firstStrList);
                    int indexFirst = firstSelection - 1;
                    long firstDeleteId = firstMap.get(firstStrList.get(indexFirst));
                    deleteContactObj(firstDeleteId);
                    writeFile();
                    break;
                case 2:
                    //By last name
                    Map<String, Long> lastMap = searchLastName();
                    List<String> lastStrList = new ArrayList<>(lastMap.keySet());
                    System.out.println("Which would you like to delete?");
                    int lastSelection = selectFromList(lastStrList);
                    int indexLast = lastSelection - 1;
                    long lastDeleteId = lastMap.get(lastStrList.get(indexLast));
                    deleteContactObj(lastDeleteId);
                    writeFile();
                    break;
                case 3:
                    //By phone
                    Map<String, Long> phoneMap = searchPhone();
                    List<String> phoneStrList = new ArrayList<>(phoneMap.keySet());
                    System.out.println("Which would you like to delete?");
                    int phoneSelection = selectFromList(phoneStrList);
                    int indexPhone = phoneSelection - 1;
                    long phoneDeleteId = phoneMap.get(phoneStrList.get(indexPhone));
                    deleteContactObj(phoneDeleteId);
                    writeFile();
                    break;
                case 4:
                    //By email
                    Map<String, Long> emailMap = searchEmail();
                    List<String> emailStrList = new ArrayList<>(emailMap.keySet());
                    System.out.println("Which would you like to delete?");
                    int emailSelection = selectFromList(emailStrList);
                    int indexEmail = emailSelection - 1;
                    long emailDeleteId = emailMap.get(emailStrList.get(indexEmail));
                    deleteContactObj(emailDeleteId);
                    writeFile();
                    break;
                default:
                    break;
            }
            int userContinue = selectFromList(repeatAction);
            if (userContinue == 1){
                keepLooping=false;
            }
        } while(keepLooping);
    }
    //END DELETE MAIN
    //DELETE CONTACT OBJ (CALLED IN DELETE MAIN)
    public static void deleteContactObj(long id) {
        System.out.println(id);
        contactList.clear();
        Contact deleteMe = null;
        for(Contact contact : contactObjList) {
            if(contact.getId() == id) {
                deleteMe = contact;
            }
        }
        System.out.println("You deleted: " + deleteMe.toContactString());
        contactObjList.remove(deleteMe);
        for(Contact contactObj : contactObjList) {
            contactList.add(contactObj.toContactString());
        }
    }
    //END DELETE CONTACT OBJ (CALLED IN DELETE MAIN)
}
