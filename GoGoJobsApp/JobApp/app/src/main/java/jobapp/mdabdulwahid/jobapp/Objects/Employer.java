package jobapp.mdabdulwahid.jobapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdAbdulWahid on 18/01/2016.
 */

// employer inheriting from the profile superclass
// implementing abstract methods for modiying the vacancies object
public class Employer extends Profile {


    private String companyName;
    private String FirstLineAddress; // employer firstLine Address
    private String LastLineAddress; // employers last line address
    private String town; // employers town


    // default constructor
    public Employer() {
    }



    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getFirstLineAddress() {
        return FirstLineAddress;
    }

    public void setFirstLineAddress(String firstLineAddress) {
        FirstLineAddress = firstLineAddress;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLastLineAddress() {
        return LastLineAddress;
    }

    public void setLastLineAddress(String lastLineAddress) {
        LastLineAddress = lastLineAddress;
    }


    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }



    // override the asbtract methods: writeToParcel and describeContents
    @Override

    // flattens all object of this class into a Parecel
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeString(telephone);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(companyName);
        dest.writeString(FirstLineAddress);
        dest.writeString(LastLineAddress);
        dest.writeString(town);
        dest.writeString(postCode);
    }
    // default override  method used for to return special case scenarios
    // it is not used in the application as it return default  value by zero
    @Override
    public int describeContents() {
        return 0;
    }

    // helps to reconstruct/de-parcel the object again after reading from the parcel data
    public Employer(Parcel reconstruct) {
        id = reconstruct.readString();
        firstName = reconstruct.readString();
        lastName = reconstruct.readString();
        username = reconstruct.readString();
        telephone = reconstruct.readString();
        mobile = reconstruct.readString();
        email = reconstruct.readString();
        companyName = reconstruct.readString();
        FirstLineAddress = reconstruct.readString();
        LastLineAddress   = reconstruct.readString();
        town = reconstruct.readString();
        postCode = reconstruct.readString();

    }


    // once the class has been declared Parcelable, it needs to implement static field CREATOR to effectly
    // unparcel or deserialise by the operating system.
    public static final Parcelable.Creator<Employer> CREATOR = new Parcelable.Creator<Employer>() {

        @Override
        // this method creates a new instance from the Parcelable class and instantiates from the given parcel data
        // that was previously written by writeToParcel method
        public Employer createFromParcel(Parcel parcel) {
            return new Employer(parcel);
        }

        @Override
        // create a new array of this parcelable object
        public Employer[] newArray(int size) {
            return new Employer[size];
        }
    };


} // end employer class
