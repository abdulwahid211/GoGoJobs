package jobapp.mdabdulwahid.jobapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdAbdulWahid on 19/01/2016.
 */



// vacancy object created by the employer to give out details to job seekers
public class Vacancy implements Parcelable {

    private String id;
    private String employer_id;
    private String companyName;
    private String responsibilies;
    private String job_title;
    private String job_description;
    private String Salary;
    private String contract;
    private String postCode;
    private String city;
    private String jobPosted;
    private String type;
    public Vacancy() {

    }

  public   Vacancy(String title){
        this.job_title = title;
    }

    // public getter methods for vaccancy details to be accessed within the application

    // public getter method for vacancy id

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setResponsibilies(String responsibilies) {
        this.responsibilies = responsibilies;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getResponsibilies() {
        return responsibilies;
    }

    public String getID() {
        return this.id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // public getter method for job title
    public String getJob_title() {
        return this.job_title;
    }

    // public getter method for job description
    public String getJobDescription() {
        return this.job_description;
    }

    // public getter method for salary
    public String getSalary() {
        return this.Salary;
    }

    // public getter method for contract
    public String getContract() {
        return this.contract;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getJobPosted() {
        return jobPosted;
    }

    public void setJobPosted(String jobPosted) {
        this.jobPosted = jobPosted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmployer_id(String employer_id) {
        this.employer_id = employer_id;
    }
    // public setter methods for vacancy details if any changes require from the employer

    // public setter method for id
    public void setID(String id) {
        this.id = id;
    }

    // public setter method for job title
    public void setJob_title(String title) {
        this.job_title = title;
    }

    // public setter method for job description
    public void setJobDescription(String job_description) {
        this.job_description = job_description;
    }

    // public setter method for salary
    public void setSalary(String sal) {
        this.Salary = sal;
    }

    // public setter method for contract
    public void setContract(String con) {
        this.contract = con;
    }


    // override the asbtract methods: writeToParcel and describeContents
    @Override

    // flattens all object of this class into a Parecel
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(postCode);
        dest.writeString(city);
        dest.writeString(jobPosted);
        dest.writeString(employer_id);
        dest.writeString(type);
        dest.writeString(job_title);
        dest.writeString(job_description);
        dest.writeString(responsibilies);
        dest.writeString(companyName);
        dest.writeString(Salary);
        dest.writeString(contract);

    }

    // default override  method used for to return special case scenarios
    // it is not used in the application as it return default  value by zero
    @Override
    public int describeContents() {
        return 0;
    }

    // helps to reconstruct/de-parcel the object again after reading from the parcel data
    public Vacancy(Parcel reconstruct) {
        id = reconstruct.readString();
        postCode = reconstruct.readString();
        city = reconstruct.readString();
        jobPosted = reconstruct.readString();
        employer_id = reconstruct.readString();
        type = reconstruct.readString();
        job_title = reconstruct.readString();
        job_description = reconstruct.readString();
        responsibilies = reconstruct.readString();
        companyName = reconstruct.readString();
        Salary = reconstruct.readString();
        contract = reconstruct.readString();

    }


    // once the class has been declared Parcelable, it needs to implement static field CREATOR to effectly
    // unparcel or deserialise by the operating system.
    public static final Parcelable.Creator<Vacancy> CREATOR = new Parcelable.Creator<Vacancy>() {

        @Override
        // this method creates a new instance from the Parcelable class and instantiates from the given parcel data
        // that was previously written by writeToParcel method
        public Vacancy createFromParcel(Parcel parcel) {
            return new Vacancy(parcel);
        }

        @Override
        // create a new array of this parcelable object
        public Vacancy[] newArray(int size) {
            return new Vacancy[size];
        }
    };


}
