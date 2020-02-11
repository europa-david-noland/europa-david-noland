public class Contact {
    //First name
    private String firstName;
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    //End first name
    //Last name
    private String lastName;
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    //End last name
    //Phone
    private String phone;
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    //End phone
    //Email
    private String email;
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    //End email
    //ID for CRUD
    private static long counter = -1;
    private long id;
    public long getId() {
        return this.id;
    }
    //End ID for CRUD
    //Constructor
    Contact(String firstName, String lastName, String phone, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.id = counter;
        counter++;
    }
    //End constructor
    //Use on object before adding to contactList then writing to file. (Write takes String object) Changing the format of this will crash the app
    public String toContactString() {
        String phoneFormat = "(" + this.phone.substring(0,3) + ")" + this.phone.substring(3, 6) + "-" + this.phone.substring(6);
        String output = String.format("%s %s %-32s %s %-32s %s", this.firstName, this.lastName, "|", phoneFormat,  "|", this.email);
        return this.firstName + " " + this.lastName + " | " + this.phone + " | " + this.email;
    }
    //End use on object before adding to contactList then writing to file. (Write takes String object) Changing the format of this will crash the app

}
