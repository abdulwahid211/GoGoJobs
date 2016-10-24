package jobapp.mdabdulwahid.jobapp.JobSeekerMenu;

import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TabHost;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.JSONparse.Job_SeekerJSONParser;
import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.R;

/**

 *
 *
 * If the JobSeeker user has logged in successfully
 * load up all relevant activities and interfaces
 * will display tab activities for search jobs on the map and auto seacrhing
 * Some of the code for setting up tab activities has been adapted
 * from
 * http://www.technotalkative.com/android-tab-bar-example-1/
 */
public class Job_seekerMenu  extends TabActivity {
    // main jobSeeker object that will be passed through all applicant's activities
    private TransferPackage transferPackage; // transferPackage object to send values to the php script
    private HttpTransferData httpTransferData; // http management class to handle http requests to the sever
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/retrieveJobSeeker.php";
    private Job_seeker job_seeker;
    private String jobSeekerUserName;
    private Job_SeekerJSONParser job_seekerJSONParser;

    TabHost tabHost;
    TabHost.TabSpec spec;

    //onCreate method to initialise all object and display the content
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobseeker_main_menu);


        job_seeker = new Job_seeker();
        transferPackage = new TransferPackage();
        job_seekerJSONParser = new Job_SeekerJSONParser();

        try {
            // set up the webservice request object
            //  allows to post values and receive response
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        //collect username and password from the login page
        jobSeekerUserName = bundle.getString("username");




        transferPackage.addNewPost("Username", jobSeekerUserName);

        // execute the to retrieve job seeker details
        // to be runned in the background thread
        new RetrieveJobSeekerDetails().execute();
        //get the current tab activity
        tabHost = getTabHost();


    }

    //job seeker is parcelled to the two tab activities
    public void ParcelableObjectsTabs() {
        // initialise two intent objects
        // to send job seeker objects to other two activities
        Intent intent[] = new Intent[2];
        // post current username and password to retrieve employer's detail

        // first tab to display searchJobMap
        intent[0] = new Intent().setClass(Job_seekerMenu.this, SearchJobMap.class);
        intent[0].putExtra("JobSeekerDetails", job_seeker);
        spec = tabHost.newTabSpec("Search Jobs").setIndicator("Search Jobs")
                .setContent(intent[0]);
        tabHost.addTab(spec);

        // second tab to display the auto job searching
        intent[1] = new Intent().setClass(Job_seekerMenu.this, autoJobs.class);
        intent[1].putExtra("JobSeekerDetails", job_seeker);
        spec = tabHost.newTabSpec("Auto Search").setIndicator("Auto Search")
                .setContent(intent[1]);
        tabHost.addTab(spec);

    }



    // inner class AsyncTask object to execute the operation in the background thread
    private class RetrieveJobSeekerDetails extends AsyncTask<String, String, String> {

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
            // JSON data contains all jobseekers detail

            job_seeker = (Job_seeker) job_seekerJSONParser.parseFeed(result, "JobSeekerDetail");
            // update jobSeeker object to be send to the other two activities
            ParcelableObjectsTabs();



        }


    }





}