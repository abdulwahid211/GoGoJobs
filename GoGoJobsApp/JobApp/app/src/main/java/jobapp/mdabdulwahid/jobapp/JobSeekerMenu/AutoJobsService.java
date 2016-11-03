package jobapp.mdabdulwahid.jobapp.JobSeekerMenu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

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
import jobapp.mdabdulwahid.jobapp.Profiles.VacancyProfile;
import jobapp.mdabdulwahid.jobapp.R;
import android.app.Notification

// The AutoJobsService inherits Service class to ensure the operation of the class runs on
// on the background
// This service class does not require user interaction, will carry out the searching for infinity until the
// onDestroy method is called
// The AutoJobsService  requires Location Servce API to track down the users current GPS
// Setting up the GoogleAPiClient and Location Service API configuration example was taken from
// Stackoverflow supported by one of the helpers Diego Palomar
// Reference: http://stackoverflow.com/questions/24611977/android-locationclient-class-is-deprecated-but-used-in-documentation


// The class implements GoogleApiClient interface to access the Location Service API
// and LocationListener interface to use onLocationChangd a to track location change from the GPS
//
public class AutoJobsService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Job_seeker job_seeker; // job seeker object
    private NotificationManager notificationPushObject;
    private ArrayList<Vacancy> vacancies; // list of vacancy retrieving from the database
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest currentLocationRequest; //used to set up the location service
    private TransferPackage transferPackage; // transferPackage object to send values to the php script
    private HttpTransferData httpTransferData;
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/retrieveVacancies.php";
    private VacanciesListJSON vacanciesListJSON;
    private ArrayList<String> userChoice;
    private Geocoder geocoder;
    private int mile = 0;
    private ArrayList<Vacancy> uniqueVacancies;
    ScheduledExecutorService scheduleTaskExecutor;

    // must default override method
    // returning nothing
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    // main method to initialise all objects and set up the Location Based service API
    // this method initialise everything before the Background Service execution happens
    //
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Let it continue running until it is stopped.
        Toast.makeText(this, "Start Auto Search", Toast.LENGTH_LONG).show();

        notificationPushObject = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // setting up the API configuration
        // was taken from stackOverflow
        // instantiate mGoogleApiClient Location Service connections
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();// execute the connections

        uniqueVacancies = new ArrayList<Vacancy>();
        vacancies = new ArrayList<Vacancy>();
        vacanciesListJSON = new VacanciesListJSON();
        // to access all geolocation feature for the GoGo jobs map
        geocoder = new Geocoder(this);


        transferPackage = new TransferPackage();

        //if the intent request is not empty from the
        // previous activity
        if (!intent.getExtras().equals(null)) {
            //collect all the content
            Bundle bundle = intent.getExtras();
            //un-parcel all objects from the previous object
            job_seeker = (Job_seeker) bundle.getParcelable("JobSeekerDetails");
            userChoice = bundle.getStringArrayList("jobs_list");
            mile = bundle.getInt("mile");
        }
        // send id to ensure it does not notify the same jobs applied before
        transferPackage.addNewPost("jobSeeker_id", job_seeker.getID());
        try {
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }
        // START_STICKY object helps to determine
        // if the Service is destroyed due to low memory in the android phone
        // the android operating system clears some memory
        // will restart the Service, because that particular issue is set.
        return START_STICKY;
    } // end StartCommand

    // the method destroys the service operation
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect(); // stop the GoogleApiClientAPI
        Toast.makeText(this, "Auto Search Stopped", Toast.LENGTH_LONG).show();
    }

    // method sends the notification on the users screen
    // it would take a vacancy object and unique index number
    // to output on the users screen
    public void showNotification(Vacancy vacancy, int uniqueNotificationIndex) {

        Intent newVacancy = new Intent(this, VacancyProfile.class);
        newVacancy.putExtra("vacancy", vacancy);
        newVacancy.putExtra("JobSeekerDetails", job_seeker);
        // allows to perform intent object request at a certain time
        PendingIntent pending = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), newVacancy, 0);

        // Set the info for the views that show in the notification panel.
        // this Notification panel can be used to customise the content
        // initialise the notification object
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)  // the status icon
                .setTicker("GoGo Job Found")  // the status of the icon
                .setWhen(System.currentTimeMillis())  // the time to be shown in the android device
                .setContentTitle("GoGo Job match")  // the label of the entry notification
                .setContentText("Job Title: " + vacancy.getJob_title())  // the contents of the entry
                .setGroup("GROUP_KEY_MESSAGES")
                .setGroupSummary(true)
                .setContentIntent(pending)  // The intent to send when the entry is clicked
                .build(); // finally build the notification object


        private NotificationCompat.Builder createHeaderBuilder() {
            return new NotificationCompat.Builder(this)
                    .setColor(Color.GREEN) // use the green color (a value is needed! Not a resource)
                    .setContentTitle(getString(R.string.app_name)) // 'GCM Webhooks' in the example text
                    .setSmallIcon(R.drawable.notification_template_icon_bg) // white and transparent logo (the 'C')
                    .setGroupSummary(true) // important: This is a summary, aka header
                    .setGroup("Apple "); // important: Always use the same group,
            // otherwise you'll get multiple notifications
        }

        //  send that notification object on the screen
        notificationPushObject.notify(uniqueNotificationIndex, notification);
    }


    //The onConnected method provide services to connect the Location Service
    // within the method set up the configuration to enable the location service
    @Override
    public void onConnected(Bundle bundle) {
        // the onConnected configuration was taken from stackOverflow
        // instantiate the locationRequest object
        currentLocationRequest = LocationRequest.create();
        // setting up the priority as High Accuracy as this gives more frequent reports
        // on users GPS and when the location changes
        currentLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Update the location after every 10 seconds
        currentLocationRequest.setInterval(10000);

        // finally using location service API update the GoogleMapAPI and locationRequest Object
        // this enables to receive the best quality service locations
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, currentLocationRequest, this);
        // pause the execution if the activity is not being used

       // new RetrieveAllVacancies().execute();
        // First a ScheduledExecutorService is created with 5 threads within
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        // the last parameters specify a inner main runnable thread to
        // execute after every 10 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {

            public void run() {
                new RetrieveAllVacancies().execute();
            }
        }, 0, 10, TimeUnit.SECONDS); // after every 10 seconds


    }


    // overriding implemented method from the GoogleMapAPi interface
    // the method is used if Location Service is delayed or suspended
    @Override
    public void onConnectionSuspended(int i) {

    }

    // overriding implemented method from the GoogleMapAPi interface
    // the method is used if Location Service has failed
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    // the onLocationChanged method detects the users current location via GPS
    // it checks if the location has changed after every 10 seconds( setted up in the
    // onConnnected method)
    @Override
    public void onLocationChanged(Location location) {


        // when location object is updated, after every 10 seconds
        // loop through the list of vacancies
        for (int i = 0; i < vacancies.size(); i++) {

            // collect the postcode of each vacancy
            String postCode = vacancies.get(i).getPostCode();

            // Addresslist is used collect address of that particular vacancy place
            // inside the getFromLocationName method pass
            // in the exact post code and the maximum number of results to be found which is 1
            List<Address> Addresslist = null;
            try {
                Addresslist = geocoder.getFromLocationName(postCode, 1);

            } catch (Exception e) {

            }
            // collect the first result of the address
            android.location.Address add = Addresslist.get(0);
            // collect latitude of the address
            double lat = add.getLatitude();
            // collect the longitude of the address
            double lng = add.getLongitude();
            // if the user has selected the job preference as any tpe
            // just show that notification of that particular vacancy

            if (userChoice.get(0).equals("Any type")) {

                showNotification(vacancies.get(i), i);
            }
            // if not
            else {
                // then go through the list of job choices
                for (int j = 0; j < userChoice.size(); j++) {
                    // if the distance of the user and vacancy is less or equal to the mile
                    // and job choices matches the job sector type
                    if ((int) distance(location.getLatitude(), location.getLongitude(), lat, lng) <= mile
                            && userChoice.get(j).equals(vacancies.get(i).getType())) {

                        // send that push notification on the users screen
                        showNotification(vacancies.get(i), i);

                    }
                }
            }
        } // end  first for loop
    }  // end Location changed


    // the distance method helps to calculate the distance between two points of the
    // latitude and longitude and return as miles.
    // Code has been taken from GeodataSource website that mathematically calculates the distance
    // Reference: https://www.geodatasource.com/developers/java
    protected static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        //mathematical formula the calculates between the two locations from their longitude and latitude coordinates
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        // return as Miles in double
        return (dist);
    }

    public ArrayList<Vacancy>  removeDuplication(ArrayList<Vacancy> vacancies){

        for(int i=0; i<vacancies.size();i++){


            if(!uniqueVacancies.contains(vacancies.get(i))){
                uniqueVacancies.add(vacancies.get(i));
            }
        }



        return uniqueVacancies;
    }

    //Other two mathematical methods from GeodataSource that helps to convert decimal
    // degrees to radians and radians to decimal degrees.
    // These two methods are used in the distance function to help calculate distance
    /*::	This method converts decimal degrees to radians						 :*/
    protected static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*::	This method converts radians to decimal degrees						 :*/

    protected static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    // inner class AsyncTask object to execute the operation in the background thread
    private class RetrieveAllVacancies extends AsyncTask<String, String, String> {

        //override method to set up values before background execution
        @Override
        protected void onPreExecute() {
        }
        // doInBackground to execute the actual operation in the background //test
        @Override
        protected String doInBackground(String... param) {
            // executes request to the php script
            String newContent = httpTransferData.DataTransfer();
            return newContent; // return the new response from the php web service script
        }

        @Override
        protected void onPostExecute(String result) {
            // receive the values from php response
            // the php response would contain JSON data
            // JSON data contains all job detail
            notificationPushObject.cancelAll();
            vacancies = (ArrayList) vacanciesListJSON.parseFeed(result, "vacancyDetail");


            //vacancies =  removeDuplication( vacancies);


        }


    }

}