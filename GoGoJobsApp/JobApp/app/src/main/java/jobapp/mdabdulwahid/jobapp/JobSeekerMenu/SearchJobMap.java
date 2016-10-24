

package jobapp.mdabdulwahid.jobapp.JobSeekerMenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
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
import jobapp.mdabdulwahid.jobapp.JSONparse.VacanciesListJSON;
import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.Profiles.InteractiveMap;
import jobapp.mdabdulwahid.jobapp.Profiles.VacancyProfile;
import jobapp.mdabdulwahid.jobapp.R;
import jobapp.mdabdulwahid.jobapp.arrayAdaptors.VacancyAdapter;


/**
 * The SearchJobMap is a concrete class that extends from the SearchMap.
 * SearchJobMap helps the job seeker display available vacancies from
 * the Server's database. It displays the list of vacancies in an list view format and can display
 * them in an interactive map.
 */
// Once the SearchJobMap extends SearchMap it  allows this subclass access the Google Map API objects that is stated in the class. It
// can
public class SearchJobMap extends InteractiveMap {

    // securing the all attributes as private as it can only accessed in within the class
    private Job_seeker job_seeker; // job seeker object

    private ArrayList<Vacancy> vacancies; // list of vacancy retrieving from the database
    // A custom Vacancy List JSON object that helps convert string of json text into Vacancy object values
    private VacanciesListJSON vacanciesListJSON;
    // Andorid  ListView object that will display the ArrayList of vacancies in a list format
    private ListView VacancyListView;
    // url link to request the list of vacancies from the php script
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/retrieveVacancies.php";
    //Andorid Adapter that helps the list view to be viewed and access the data item
    private VacancyAdapter adapter;
    // An widget action bar to feature in the user interface
    private ActionBar actionBar;

    // An Android View Object that holds reference of the Google Map Fragment
    // having the actual reference of the Map fragments helps to control the visability of the Google Map
    // this helps when the user wants switch the map view off
    private View Mapfragment;

    private Button MapButton; // Button display list of jobs in the Map
    private Button listButton; //Button display list of jobs in the list view format
    // to pause refresh update when activity has not started
    private boolean paused = false;


    @Override
    // Initilaise all objects and Android widgets in the activity
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // display the content from the XML
        setContentView(R.layout.searchlist);

        // recieving object intent content form the previous activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        actionBar = getSupportActionBar();
        actionBar.setTitle("              Search Jobs");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));

        // reconstructing the job seeker objects
        // getting the reference from the previous activity
        job_seeker = (Job_seeker) bundle.getParcelable("JobSeekerDetails");


        // initialsing all objects
        vacancies = new ArrayList<Vacancy>();
        vacanciesListJSON = new VacanciesListJSON();
        markers = new ArrayList<MarkerOptions>();
        actionBar = getSupportActionBar();
        transferPackage = new TransferPackage();
        // send the job seeker id to prevent previous
        // jobs that has been already applied
        transferPackage.addNewPost("jobSeeker_id", job_seeker.getID());

        // initialising all android widgets
        searchQuery = (EditText) findViewById(R.id.search_jobs);
        VacancyListView = (ListView) findViewById(R.id.list);
        VacancyListView.setVisibility(View.VISIBLE);
        Mapfragment = (View) findViewById(R.id.map);
        Mapfragment.setVisibility(View.GONE);
        MapButton = (Button) findViewById(R.id.mapButton);
        listButton = (Button) findViewById(R.id.listButton);
        findPostCode = (Button) findViewById(R.id.postcode_button);

        // initialise the Google map object
        initialiseGoGoMap();


        //setting up the x and y axis to position the android widgets
        findPostCode.setY(8);
        findPostCode.setX(800);
        searchQuery.setY(8);
        MapButton.setY(1349);
        MapButton.setX(570);
        listButton.setX(14);
        listButton.setY(1349);
        VacancyListView.setY(150);
        Mapfragment.setY(150);

        listButton.setTextColor(Color
                .rgb(255, 255, 255));

        findPostCode.setVisibility(View.INVISIBLE);

        try {
            // set up the webservice request object
            //  allows to post values and receive response
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }

        // this helps to detect if the user has selected particular item from
        // the list
        // setOnItemClickListener listens to the user response from the list
        VacancyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //onItemClick helps to retrieve the position from the list
                // which is selected
                // select that particular item form the list
                Vacancy vacancy = vacancies.get(position);
                // view that vacancy object in the next profile activity
                Intent intent = new Intent(SearchJobMap.this, VacancyProfile.class);
                // pass along with objects to the next activity
                intent.putExtra("vacancy", vacancy);
                intent.putExtra("JobSeekerDetails", job_seeker);
                startActivity(intent);
            }
        });


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
                            new RetrieveAllVacancies().execute();

                        }
                    }

                }); // end runONUIThread method
            }
        }, 0, 5, TimeUnit.SECONDS); // after every 5 seconds


    } //  end onCreate method

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

    //button method to display the interactive map and switch off list view
    public void MapView(View v) {
        // show the button to search for locations through postcode
        findPostCode.setVisibility(View.VISIBLE);
        //Set the text hint to postCode
        searchQuery.setHint("  Please enter the post code");
        // turn off the visibility for the list format
        VacancyListView.setVisibility(View.INVISIBLE);
        // display the google map
        // by turning on the visibility for the map fragments
        Mapfragment.setVisibility(View.VISIBLE);

        // change the color of text buttons
        MapButton.setTextColor(Color
                .rgb(255, 255, 255));
        listButton.setTextColor(Color
                .rgb(0, 0, 0));
    }

    //button method to display the list of vacancies and switch off map format
    public void ListView(View view) {

        // turn of the button for postcode
        findPostCode.setVisibility(View.INVISIBLE);
        // turn on the visibility for the list format
        VacancyListView.setVisibility(View.VISIBLE);
        //Set the text hint to search job based on title
        searchQuery.setHint("  Please enter title of the job");
        // turn off the visibility for the map fragments
        Mapfragment.setVisibility(View.INVISIBLE);

        // change the color of text buttons
        listButton.setTextColor(Color
                .rgb(255, 255, 255));
        MapButton.setTextColor(Color
                .rgb(0, 0, 0));
    }


    //  implementing the abstract markersWindowActionEvent() method from the Parent class SearchMap.
    // the markersWindowActionEvent() method ensures all the list of vacancies are being displayed on the Google Map
    // also it ensures the Markers info window is clickable that leads the user to Job description profile
    // the method throws an IOException if there any errors related to the markers
    public void markersWindowActionEvent() throws IOException {


        // activating the Google Map object's  setOnInfoWindowCLickListener adapter method on
        // inside the method parameter initialise  onInfoWindowClickerListener()
        // within the new onInfoWindowClickerListener() it has second method onInfoWindowClick
        gogoMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            // onInfoWindowClick allows the Marker to have event action
            // setting up the event action allows to send the user to an new activity which is job description page
            public void onInfoWindowClick(Marker marker) {

                // once the user presses a marker it would
                //iterate through list of vacancies
                for (int i = 0; i < vacancies.size(); i++) {
                    // if that particular markers title matches vacancy version
                    if (vacancies.get(i).getJob_title().equals(marker.getTitle())) {
                        // then collect that vacancy
                        Vacancy vacancy = vacancies.get(i);

                        // intent the user to a new activity which is the vacancy profile
                        Intent intent = new Intent(SearchJobMap.this, VacancyProfile.class);
                        intent.putExtra("vacancy", vacancy); // send reference of the chosen vacancy
                        intent.putExtra("JobSeekerDetails", job_seeker); // send reference of the job seeker
                        startActivity(intent); // start new activity
                        break;
                    }

                } // end loop


            } // end onInfoWindowClick method
        });


    }


    //method is called in the AsynTask to update the list
    public void updateData() {

        // update the vacancy list in all methods of markers
        try {
            displayMarkers();
            displayInfoWindowMarker();
            markersWindowActionEvent();
        } catch (Exception e) {

        }

        // initialise the list adapter with XML layout and the vacancies list to be displayed
        adapter = new VacancyAdapter(SearchJobMap.this, R.layout.items_job, vacancies);
        VacancyListView.setAdapter(adapter);  //set the listView object to match adapter list

        // EditText searchQuery object can be used to look up jobs in the list of vacancies
        // it uses TextWatcher() to filter the query in the list
        // this code has been adapted from
        // Reference: http://www.javased.com/?api=android.text.TextWatcher
        searchQuery.addTextChangedListener(new TextWatcher() {
            // all methods from TextWatcher() must be override
            // before text change
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            // after text change
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            //during text change
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // during the text change it would use character of the word
                // to be filter in the list adapter
                // the adapter uses VacancyAdapter to do the filtering
                adapter.getFilter().filter(cs.toString());

            }
        });

    } // end upDate list


    // to display all vacancies objects on the google map
    public void displayMarkers() throws IOException {


        // display an icon of the the user current location
        gogoMap.setMyLocationEnabled(true);
        // to access all geolocation feature for the GoGo jobs map
        // the Geocode object helps convert post codes or street name
        // into latitude and longitude coordinates
        Geocoder geocoder = new Geocoder(this);


        // initialising all markers
        for (int k = 0; k < vacancies.size(); k++) {
            markers.add(new MarkerOptions());
        }


        // iterate for each vacancy in the list
        for (int i = 0; i < vacancies.size(); i++) {


            // collect the postcode of that job
            String postCode = vacancies.get(i).getPostCode();

            // Addresslist is used collect address of that particular vacancy place
            // inside the getFromLocationName method pass
            // in the exact post code and the maximum number of results to be found which is 1
            List<android.location.Address> Addresslist = geocoder.getFromLocationName(postCode, 1);


            // collect the first result of the address
            android.location.Address add = Addresslist.get(0);
            // collect latitude of the address
            double lat = add.getLatitude();
            // collect the longitude of the address
            double lng = add.getLongitude();
            // add that job title to that marker
            markers.get(i).title(vacancies.get(i).getJob_title());

            // set up the Markers info snipped to be displayed
            markers.get(i).snippet(vacancies.get(i).getCompanyName() + "\n"
                    + vacancies.get(i).getSalary() + ", " + vacancies.get(i).getPostCode() + "\n"
                    + "Posted: " + vacancies.get(i).getJobPosted()
                    + "\n" + "Job Type: " + vacancies.get(i).getType() + "\n" + "Distance: "
                    + (int) distance(gogoMap.getMyLocation().getLatitude(), gogoMap.getMyLocation().getLongitude(), lat, lng) + " Mile");

            // update the new poistion of the marker based on new latitude and longitude
            markers.get(i).position(new LatLng(lat, lng));


        }
        // add all the markers to the google interactive map
        for (int i = 0; i < vacancies.size(); i++) {
            gogoMap.addMarker(markers.get(i));
        }


    } // end display Markers


    // inner class AsyncTask object to execute the operation in the background thread
    private class RetrieveAllVacancies extends AsyncTask<String, String, String> {
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
            // JSON data contains all vacancies detail
            vacancies = (ArrayList) vacanciesListJSON.parseFeed(result, "vacancyDetail");
            //update the list of vacancies
            updateData();


        }


    }


}