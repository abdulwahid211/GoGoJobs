package jobapp.mdabdulwahid.jobapp.EmployerMenu;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.JSONparse.EmployerJSONParser;
import jobapp.mdabdulwahid.jobapp.Objects.Employer;
import jobapp.mdabdulwahid.jobapp.R;

/**

 *
 *
 * If the Employer user has logged in successful
 * load up all relevant activities and interfaces
 * will display tab activities for post jobs and myVacancies
 * Some of the code for setting up tab activities has been adapted
 * from
 * http://www.technotalkative.com/android-tab-bar-example-1/
 */
public class EmployerMenu extends TabActivity {

    // main employer object that will be passed through all employer's activities
    private Employer employer;
    private EmployerJSONParser employerJSONParser;
    public TransferPackage transferPackage; // transferPackage object to send values to the php script
    private HttpTransferData httpTransferData; // http management class to handle http requests to the sever
    private String employerUserName;
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/retrieveEmployer.php";

    TabHost tabHost;
    TabHost.TabSpec spec;

    //onCreate method to initialise all object and display the content
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_main_menu);
        employer = new Employer();
        transferPackage = new TransferPackage();
        employerJSONParser = new EmployerJSONParser();
        try {
            // set up the webservice request object
          //  allows to post values and receive response
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        //collect username  from the previous login page
        employerUserName = bundle.getString("username");

        transferPackage.addNewPost("Username", employerUserName);
       
        // execute the to retrieve employer details
        // to be runned in the background thread
        new RetrieveEmployerDetails().execute();

        //get the current tab activity
        tabHost = getTabHost();
    }


    //employer is parcelled to the two tab activities
    public void ParcelableObjectsTabs() {

        // initialise two intent objects
        // to send employer objects to other two activities
        Intent intent[] = new Intent[2];

        // first tab to display Employers Vacancies
        intent[0] = new Intent().setClass(EmployerMenu.this, MyVacancies.class);
        intent[0].putExtra("employerDetails", employer);
        spec = tabHost.newTabSpec("Second").setIndicator("Posted Vacancies")
                .setContent(intent[0]);
        tabHost.addTab(spec);

        // second tab to display post jobs
        intent[1] = new Intent().setClass(EmployerMenu.this, postJobs.class);
        intent[1].putExtra("employerDetails", employer);
        spec = tabHost.newTabSpec("Third").setIndicator("Post Job")
                .setContent(intent[1]);
        tabHost.addTab(spec);

        // set the color of the text for each tabs activities
        TabHost tabhost = getTabHost();
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#5A2322"));
        }

    }

    // inner class AsyncTask object to execute the operation in the background thread
    private class RetrieveEmployerDetails extends AsyncTask<String, String, String> {

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

        //method receives values from the doInBackground after the execution
        @Override
        protected void onPostExecute(String result) {
            // receive the values from php response
            // the php response would contain JSON data
            // JSON data contains all employers detail
            employer = (Employer) employerJSONParser.parseFeed(result, "EmployerDetail");
            // update Employer object to be send to the other two activities
            ParcelableObjectsTabs();

        }


    }


} // end class