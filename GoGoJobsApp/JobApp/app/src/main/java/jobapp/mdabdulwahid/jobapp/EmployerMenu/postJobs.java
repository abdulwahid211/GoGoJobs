package jobapp.mdabdulwahid.jobapp.EmployerMenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.Objects.Employer;
import jobapp.mdabdulwahid.jobapp.R;

/**
 * This activity class is used by the employer to post new jobs on the employment market
 */
public class postJobs extends AppCompatActivity {

    protected TransferPackage transferPackage;
    public HttpTransferData httpTransferData;
    protected String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/postVacancy.php";
    // android widget objects
    protected ActionBar actionBar;
    protected EditText title; // input of the job title
    protected EditText salary; // input of the salary
    protected EditText contract;
    protected EditText postCode;
    protected EditText town;
    protected EditText description;
    // employer details retrieving from the main menu
    protected Employer employer;
    protected EditText responsibility;
    protected Time currentDate;
    protected String type;
    private Spinner dropdown;
    // array of job type categories
    String[] items = new String[]
            {"Accounting", "Admin", "Banking & Finance", "Education", "Sales & Marketing", "Cafe"
                    , "Government", "Bar", "ICT", "Other", "Office", "Media"
                    , "Customer Service", "Restaurant", "Engineering", "Education", "Retail", "Science",
                    "Government", "Health Care", "Hospitality"
                    , "Human Resources", "ICT", "Internships", "Manufactor", "Volunteer", "Any type"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postjob);
        actionBar = getSupportActionBar();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        actionBar.setTitle("          Post New Job");
        // reconstructing the employer object
        employer = (Employer) bundle.getParcelable("employerDetails");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));

        currentDate = new Time(Time.getCurrentTimezone());
        currentDate.setToNow();
        transferPackage = new TransferPackage();

        try {
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }

        // initlising anodroid's text wdigets
        title = (EditText) findViewById(R.id.jbtitle);
        salary = (EditText) findViewById(R.id.firstname);
        contract = (EditText) findViewById(R.id.lastname);
        postCode = (EditText) findViewById(R.id.postcode);
        description = (EditText) findViewById(R.id.description);
        responsibility = (EditText) findViewById(R.id.responsibility);
        dropdown = (Spinner) findViewById(R.id.type);
        town = (EditText) findViewById(R.id.town);

        // the arrayAdpter helps to hold all list of job types categories
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        //set up the adapter to display all job types
        dropdown.setAdapter(adapter);

        // determines if the user has selected an iten from the dropdown menu
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               // if the user has selected an item
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   // then collect that particular item of the job type
                                                   // from the dropdown menu
                                                   type = (String) parent.getItemAtPosition(position);
                                               }

                                               //must override method if the user has not selected anything
                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {

                                               }
                                           }
        ); // drop down
    } // end on create method

    // method for the button to post the details of the vacancy
    public void PostButton(View view) {
        // intialise new postJob AsyncTask to execute the submission
        postNewJob postNewJOb = new postNewJob();

        // validation checks to see all forms has been filled before submission
        // ensures edit text form's string value is greater than 1
        if (title.getText().toString().length() > 1 && salary.getText().toString().length() > 1 && contract.getText().toString().length() > 1 && description.getText().toString().length() > 1
                && postCode.getText().toString().length() > 1 && responsibility.getText().toString().length() > 1 && town.getText().toString().length() > 1) {
            postDetails();
            postNewJOb.execute(); // send the details to the php scripts

            // once all details has been sent successfully, clear all text fields
            title.setText("");
            salary.setText("");
            contract.setText("");
            description.setText("");
            postCode.setText("");
            town.setText("");
            responsibility.setText("");
            type = "";

        } else {

            Toast.makeText(getApplicationContext(), "Please fill all the required fields!", Toast.LENGTH_SHORT).show();

        }


    } // end button method

    /// method contains all new vacancy details to be posted to the PHP script
    public void postDetails() {
        transferPackage.addNewPost("title", title.getText().toString());
        transferPackage.addNewPost("salary", salary.getText().toString());
        transferPackage.addNewPost("contract", contract.getText().toString());
        transferPackage.addNewPost("employer_id", employer.getID());
        transferPackage.addNewPost("descriptions", description.getText().toString());
        transferPackage.addNewPost("postCode", postCode.getText().toString());
        transferPackage.addNewPost("responsibility", responsibility.getText().toString());
        transferPackage.addNewPost("posted", currentDate.monthDay + "/" + currentDate.month + "/" + currentDate.year);
        transferPackage.addNewPost("type", type);
        transferPackage.addNewPost("city", town.getText().toString());
        transferPackage.addNewPost("companyName", employer.getCompanyName());
        transferPackage.addNewPost("employer_username", employer.getUsername());

    }


    // inner class AsyncTast object to execute the new vacancy details
    // in the background thread
    protected class postNewJob extends AsyncTask<String, String, String> {

        //override method to set up values before background execution
        @Override
        protected void onPreExecute() {

        }

        // doInBackground to execute the actual operation in the background
        @Override
        protected String doInBackground(String... param) {
            // executes request to the php script
            String newContent = httpTransferData.DataTransfer();
            return newContent; // return the new response from the php web service script
        }

        //method recieves values from the doInBackground after the execution
        @Override
        protected void onPostExecute(String result) {
            //display sucess message after background execution
            Toast.makeText(getApplicationContext(), "New vacancy has been posted!", Toast.LENGTH_SHORT).show();

        }

    }


} // end class