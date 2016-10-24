
package jobapp.mdabdulwahid.jobapp.EmployerMenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.JSONparse.Job_SeekerListJSON;
import jobapp.mdabdulwahid.jobapp.Objects.Employer;
import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.Profiles.CandidateProfile;
import jobapp.mdabdulwahid.jobapp.Profiles.InteractiveMap;
import jobapp.mdabdulwahid.jobapp.R;

/**
 * display an interactive Map of applicants who applied to the particular jobs
 *
 * ListCandidatesMap is a sub class of InteractiveMap
 * it inherits the map and location based service proprieties
 * to allow list of applicants to be displayed on the map activity
 */
public class listCandidatesMap extends InteractiveMap {



    private ArrayList<Job_seeker> job_seekers;
    private Job_SeekerListJSON job_seekerJSONParserList;
    // An widget action bar to feature in the user interface
    private ActionBar actionBar;
    private Vacancy vacancy;
    private Employer employer;
    private TextView no_candidate;
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/retrieveAllCandidate.php";
    // to pause refresh update when activity has not started
    private boolean paused = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_map);



        actionBar = getSupportActionBar();
        actionBar.setTitle("         Candidates Location");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));
        actionBar.setDisplayHomeAsUpEnabled(true); //
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        transferPackage = new TransferPackage();
        job_seekerJSONParserList = new Job_SeekerListJSON();
        vacancy = (Vacancy) bundle.getParcelable("vacancy");
        employer = (Employer) bundle.getParcelable("employer");
        // initialise the Google map object
        initialiseGoGoMap();


        transferPackage.addNewPost("vacancy_id", vacancy.getID());

        markers = new ArrayList<MarkerOptions>();
        job_seekers = new ArrayList<Job_seeker>();
        no_candidate = (TextView) findViewById(R.id.candidate);
        findPostCode = (Button) findViewById(R.id.postcode_button);
        searchQuery = (EditText) findViewById(R.id.search_jobs);
        //setting up the x and y axis to position the android widgets
        findPostCode.setY(8);
        findPostCode.setX(800);

        try {
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }



             /*This schedules a runnable task every second*/
        // Reference: http://stackoverflow.com/questions/23978400/how-to-update-ui-from-asynctask
        // ScheduledExecutorService is the worker thread that executes the task asynchronously
        // ScheduledExecutorService helps to execute the AsyncTask after every 5 seconds
        // ScheduledExecutorService is very useful as it allows to execute repeatedly with a
        // fixed interval of time in between each thread execution
        // this ensure the list of jobs are up-to-date and refreshed after 5 every seconds

        // First a ScheduledExecutorService is created with 5 threads within
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // the last parameters specify a inner main runnable thread to
        // execute after every 10 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // pause the execution if the activity is not being used
                        if (paused) {
                            // execute to retrieve all job seekers who applied from the database
                            new RetrieveAllJobSeekers().execute();
                        }
                    }

                }); // end runONUIThread method
            }
        }, 0, 5, TimeUnit.SECONDS); // after every 5 seconds


    } // end onCreate Method


    @Override // method for the action bar return back to the previous activity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }

    // start activity
    @Override
    protected void onStart() {
        super.onStart();

    }

    // pause the activity
    @Override
    protected void onPause() {
        super.onPause();
        // pause the refresh update
        paused = false;
    }

    // resume the activity
    @Override
    protected void onResume() {
        super.onResume();
        // begin refreshing the list
        paused = true;
    }




    // method to update the content on the map
    @Override
    protected void updateData() {


        if (job_seekers.size() > 0) {
            // update the vacancy list
            try {
                // initialise all markers method
                displayMarkers(); // calling the method to display the markers first
                displayInfoWindowMarker();
                markersWindowActionEvent();
            } catch (Exception e) {

            }

            no_candidate.setText("Number of Candidate: " + job_seekers.size());
        }
    }


    // the markersWindowActionEvent() method ensures all the list of job seekers are being displayed on the Google Map
    // also it ensures the Markers info window is clickable that leads the user to applicant profile
    // the method throws an IOException if there any errors related to the markers
    public void markersWindowActionEvent() throws IOException {


        // activating the Google Map Info Window InfoWindow adapter CLick listener method on
        // inside the method parameter initialising new Info window listener
        // within the new Info window clicker listener it has method onInfoWindowClick
        gogoMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            // onInfoWindowClick allows the Marker to have event action
            // setting up the event action allows to send the user to an new activity which is job description page
            public void onInfoWindowClick(Marker marker) {

                // once the user presses a marker it would
                //iterate through list of vacancies
                for (int i = 0; i < job_seekers.size(); i++) {
                    // if that particular markers title matches vacancy version
                    if ((job_seekers.get(i).getFirstName() + " " + job_seekers.get(i).getLastName()).equals(marker.getTitle())) {
                        // then collect that job seeker object
                        Job_seeker job_seeker = job_seekers.get(i);

                        // intent the user to a new activity which is the vacancy profile
                        Intent intent = new Intent(listCandidatesMap.this, CandidateProfile.class);
                        intent.putExtra("vacancy", vacancy); // send reference of the chosen applicant
                        intent.putExtra("JobSeekerDetails", job_seeker); // send reference of the applicant
                        intent.putExtra("employer", employer); // send reference of the employer
                        // pass these object to the next activity
                        startActivity(intent); // start new activity
                        break;
                    }

                } // end loop


            } // end onInfoWindowClick method
        });


    } // end runGoogleMaps


    // display all markers on the interactive google map
   protected void displayMarkers() throws IOException {

        // display an icon of the the user current location
        gogoMap.setMyLocationEnabled(true);
        // to access all geolocation feature for the GoGo jobs map
        // the Geocode object helps convert post codes or street name
        // into latitude and longitude coordinates
        Geocoder geocoder = new Geocoder(this);


        // initialising all markers
        for (int k = 0; k < job_seekers.size(); k++) {
            markers.add(new MarkerOptions());
        }

        // iterate for each job seeker in the list
        for (int i = 0; i < job_seekers.size(); i++) {
            //collect the postcode
            String postCode = job_seekers.get(i).getPostCode();


            //list to collect address of that particular vacancy place
            // inside the get from location name method pass
            // in the extact post code and the maximum number of results to be found is 1
            List<Address> Addresslist = geocoder.getFromLocationName(postCode, 1);


            // collect the first result of the address
            android.location.Address add = Addresslist.get(0);
            // collect latitude of the address
            double lat = add.getLatitude();
            // collect the longitude of the address
            double lng = add.getLongitude();

            // add that candidates  name to that marker's title
            markers.get(i).title(job_seekers.get(i).getFirstName() + " " + job_seekers.get(i).getLastName());
            // set up the Markers info snipped to be displayed
            markers.get(i).snippet("Postcode: " + job_seekers.get(i).getPostCode()
                    + "\n"+(int) distance(gogoMap.getMyLocation().getLatitude(),
                    gogoMap.getMyLocation().getLongitude(), lat, lng) + " Mile");
                    // update the new position of the marker based on new latitude and longitude
            markers.get(i).position(new LatLng(lat, lng));


        }

        // add all the markers to the google interactive map
        for (int i = 0; i < job_seekers.size(); i++) {
            gogoMap.addMarker(markers.get(i));
        }
    } // end


    // inner class AsyncTast object to run in the background thread
    // to retrieve applicants who applied to that particular job
    private class RetrieveAllJobSeekers extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
        }

        // execute the actual request in the background thread
        @Override
        protected String doInBackground(String... param) {
            String newContent = httpTransferData.DataTransfer();
            return newContent;
        }

        @Override
        protected void onPostExecute(String result) {
            // remove all white spaces
            String removeWhiteSpaces = result.replaceAll("\\s+", "");
           //if the JSON data of the job seeker does contain
            if(removeWhiteSpaces.length()>0) {
                // update on the map
                job_seekers = (ArrayList) job_seekerJSONParserList.parseFeed(removeWhiteSpaces, "JobSeekerDetail");
                updateData();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Applicants applied yet", Toast.LENGTH_SHORT).show();
            }


        }


    }


}