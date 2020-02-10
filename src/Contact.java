public class Contact {
    //first name
    private String firstName;

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }
    //last name
    private String lastName;

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }


    //phone
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //constructor
    Contact(String firstName, String lastName, String phone, String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=phone;
        this.email=email;
    }

    //to contact string
    public String toContactString(){
        return this.firstName + " " + this.lastName + " | " + this.phone + " | " + this.email;
    }

}
