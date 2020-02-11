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
    private long id;
    //End ID for CRUD
    //Constructor
    Contact(String firstName, String lastName, String phone, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.id = ++id;
    }
    //End constructor
    //Use on object before adding to contactList then writing to file. (Write takes String object) Changing the format of this will crash the app
    public String toContactString(){
        return this.firstName + " " + this.lastName + " | " + this.phone + " | " + this.email;
    }
    //End use on object before adding to contactList then writing to file. (Write takes String object) Changing the format of this will crash the app

}
