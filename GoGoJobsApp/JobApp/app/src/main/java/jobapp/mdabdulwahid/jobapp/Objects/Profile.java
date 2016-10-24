package jobapp.mdabdulwahid.jobapp.Objects;

import android.os.Parcelable;

/**
 * Created by mdAbdulWahid on 18/01/2016.

 * Setting up the abstract profile object as super class
 */
public abstract class Profile  implements Parcelable {

// using the encapsulation feature as protected to protect important members variables
// in the profile object

    protected String id;
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String telephone;
    protected String mobile;
    protected String email;
    protected String postCode;
    protected int access;

    // default constructor
    public Profile(){

    }




    // public getter methods for users details to be accessed within the application

    // public getter method for id
    public String getID() {
        return this.id;
    }

    // public getter method for name
    public String getFirstName() {
        return this.firstName;
    }

    // public getter method for last name
    public String getLastName() {
        return this.lastName;
    }

    // public getter method for Telephone
    public String getTelephone() {
        return this.telephone;
    }

    // public getter method for mobile
    public String getMobile() {
        return this.mobile;
    }
    // public getter method for email
    public String getEmail() {
        return this.email;
    }


    // public setter method for mobile
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    // public setter method for name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // public setter method for Telephone
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    // public setter method for last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    // All setter methods will be protected so it can only be accessed within the inheritance architecture

    //  public setter method for id
    public void setId( String id){
        this.id = id;
    }



    //  public setter method for email
    public void setEmail( String email){
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }
    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

}
