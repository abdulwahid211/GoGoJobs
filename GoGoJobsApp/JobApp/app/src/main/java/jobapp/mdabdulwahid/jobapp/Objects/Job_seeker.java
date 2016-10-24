package jobapp.mdabdulwahid.jobapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdAbdulWahid on 18/01/2016.
 * Reference:
 * http://developer.android.com/reference/android/os/Parcelable.Creator.html
 */

/// Job seeker implements Parcelable interface as it helps the object to be parceled which can
//be easier to pass object reference via one activity to another
public class Job_seeker extends Profile{


    // default constructor
    public Job_seeker() {
    }




    // flattens all object of Job seeker into a Parcel
    public void writeToParcel(Parcel dest, int flags) {
        // Parcel dest ensures which objects elements should to be written
        // this ensures all object elements are serialised
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeString(telephone);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(postCode);
    }

    // default override  method used for to return special case scenarios
    // it is not used in the application as it return default  value by zero
    @Override
    public int describeContents() {
        return 0; // return nothing
    }

    // helps to reconstruct/de-parcel the object again after
    // reading from the parcel data
    public Job_seeker(Parcel reconstruct) {
        id = reconstruct.readString();
        firstName = reconstruct.readString();
        lastName = reconstruct.readString();
        username = reconstruct.readString();
        telephone = reconstruct.readString();
        mobile = reconstruct.readString();
        email = reconstruct.readString();
        postCode = reconstruct.readString();

    }


    // once the class has been declared Parcelable, it needs to implement static field CREATOR to effectevely
    // unparcel or deserialise by the operating system.
    public static final Parcelable.Creator<Job_seeker> CREATOR = new Parcelable.Creator<Job_seeker>() {

        @Override
        // this method creates a new instance from the Parcelable class and instantiates from the given parcel data
        // that was previously written by writeToParcel method
        public Job_seeker createFromParcel(Parcel parcel) {
            return new Job_seeker(parcel);
        }

        @Override
        // create a new array of this parcelable object
        public Job_seeker[] newArray(int size) {
            return new Job_seeker[size];
        }
    };






}
