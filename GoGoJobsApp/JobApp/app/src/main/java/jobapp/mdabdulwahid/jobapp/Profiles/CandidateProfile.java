package jobapp.mdabdulwahid.jobapp.Profiles;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.EmployerMenu.InterviewInvitation;
import jobapp.mdabdulwahid.jobapp.JSONparse.ApplicationJSONParser;
import jobapp.mdabdulwahid.jobapp.Objects.ApplicationForm;
import jobapp.mdabdulwahid.jobapp.Objects.Employer;
import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.R;

/**
 *
 *
 * This activity class is used to see applicants profile details and documents can be downloaded
 */
public class CandidateProfile extends AppCompatActivity {


    private ActionBar actionBar;
    private TextView candidate_answer;
    private TextView eligable;
    private TextView experience;
    private TextView noticePeriod;
    private TextView telephone;
    private TextView mobile;
    private TextView email;
    private TransferPackage transferPackage; // transferPackage object to send values to the php script
    private HttpTransferData httpTransferData; // http management class to handle http requests to the sever
    private Job_seeker job_seeker;
    private Vacancy vacancy;
    private ApplicationForm applicationForm;
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/retrieveApplicationForm.php";
    private static String file_url = "http://doc.gold.ac.uk/~ma301ma/finalProject/downloadFile.php";
    private RetrieveApplicationForm retrieveApplicationForm;
    private Employer employer;
    private ApplicationJSONParser applicationJSONParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_profile);

        transferPackage = new TransferPackage();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        job_seeker = (Job_seeker) bundle.getParcelable("JobSeekerDetails");
        vacancy = (Vacancy) bundle.getParcelable("vacancy");
        employer = (Employer) bundle.getParcelable("employer");
        //setting for action bar
        actionBar = getSupportActionBar();
        actionBar.setTitle(job_seeker.getFirstName() + " " + job_seeker.getLastName());
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));
        //initialise all objects
        retrieveApplicationForm = new RetrieveApplicationForm();
        applicationForm = new ApplicationForm();
        applicationJSONParser = new ApplicationJSONParser();
        transferPackage.addNewPost("jobseeker_id", job_seeker.getID());
        transferPackage.addNewPost("vacancy_id", vacancy.getID());
        candidate_answer = (TextView) findViewById(R.id.candidate_firstanswer);
        eligable = (TextView) findViewById(R.id.textView9);
        experience = (TextView) findViewById(R.id.textView10);
        telephone = (TextView) findViewById(R.id.textView12);
        mobile = (TextView) findViewById(R.id.textView13);
        email = (TextView) findViewById(R.id.textView14);
        noticePeriod = (TextView) findViewById(R.id.textView16);


        telephone.setText("Telephone: " + job_seeker.getTelephone());
        mobile.setText("Mobile: " + job_seeker.getMobile());
        email.setText("Email: " + job_seeker.getEmail());


        try {
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }

        retrieveApplicationForm.execute();


    } // end onCreate method


    @Override // method for the action bar return back to the previous activity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }

    //retrieve all the application form details
    public void updateInfo() {
        eligable.setText(applicationForm.getEligableUK());
        experience.setText(applicationForm.getPreExperience());
        candidate_answer.setText(applicationForm.getAnswer());
        noticePeriod.setText(applicationForm.getNoticePeriod());
    }

    //button method to download CV
    public void DownloadCV(View view) {
        Toast.makeText(CandidateProfile.this, "Downloading CV", Toast.LENGTH_SHORT).show();
        new DownloadFileAsync().execute(file_url + "?jb_id=" + job_seeker.getID() + "&vacancy_id=" + vacancy.getID() + "&fileType=CV");
    }

    //button method to download Cover letter
    public void DownloadCoverLetter(View view) {
        Toast.makeText(CandidateProfile.this, "Downloading Cover letter", Toast.LENGTH_SHORT).show();
        new DownloadFileAsync().execute(file_url + "?jb_id=" + job_seeker.getID() + "&vacancy_id=" + vacancy.getID() + "&fileType=Cover_Letter");
    }
    //button method go to interview invitation page
    public void Invite_Interview(View view) {

        Intent intent = new Intent(CandidateProfile.this, InterviewInvitation.class);
        intent.putExtra("vacancy", vacancy);
        intent.putExtra("employer", employer);
        intent.putExtra("JobSeekerDetails", job_seeker);
        startActivity(intent);

    }


    // inner class AsyncTask object to execute the operation in the background thread
    private class RetrieveApplicationForm extends AsyncTask<String, String, String> {
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
            // receive the values from php response
            // the php response would contain JSON data
            // JSON data contains applicationForm detail

            applicationForm = (ApplicationForm) applicationJSONParser.parseFeed(result, "application_form");
            //update the info
            updateInfo();
        }


    } // end Retrieve Application Form

    // inner class to run the background thread in the candidateProfile
    // this AsyncTask was taken from Android tutorial site Hive
    // Reference: http://www.androidhive.info/2012/04/android-downloading-file-by-showing-progress-bar/
    class DownloadFileAsync extends AsyncTask<String, String, String> {
        String filename = null;


        // executing method  before the doInBackground
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        // execute the doInBackground to download th file
        @Override
        protected String doInBackground(String... f_url) {

            int count;
            try {
                // URL to download the content from the PHP scripts
                URL url = new URL(f_url[0]);
                // to download the bytes of file from the database
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                conection.connect();

                // the content of the file
                filename = conection.getHeaderField("Content-Disposition");


                // download the file from the php file with approx 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // create the folder storage location of the file to be stored
                File folder = new File(Environment.getExternalStorageDirectory() + "/GoGoJobs");

                // if the folder the doesn't exist in the internal storage
                if (!folder.exists()) {
                    // then make a directory folder
                    folder.mkdir();
                }


                // Output stream the bytes of the file to the external storage device folder GoGoJobs
                OutputStream output = new FileOutputStream("/storage/emulated/0/GoGoJobs/" + filename);
                // maximum number of bytes
                byte data[] = new byte[1024];


                while ((count = input.read(data)) != -1) {
                    // writing data to file
                    // executing the output
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();


                input.close();


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }


            return null;
        }


        /// when the downloading the file executing has been finished
        // ensure the file can be opened with any PDF application
        // show the list of PDF application to open with
        @Override
        protected void onPostExecute(String file_url) {
            //location of the file to open with the PDF launcher
            File file = new File("/storage/emulated/0/GoGoJobs/" + filename);
            // Intent to perform action the execution to open with PDF
            Intent target = new Intent(Intent.ACTION_VIEW);
            //collect the url data and application type to open with
            target.setDataAndType(Uri.fromFile(file), "application/pdf");
            // to not keep the activity history saved
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            // perform the action to open the file with optional of PDF applications
            Intent intent = Intent.createChooser(target, "Open File");
            try {
                // perform the activity to open the file with PDF launcher
                startActivity(intent);
            } catch (ActivityNotFoundException e) {

            }
        }

    } // end download file class


}