package jobapp.mdabdulwahid.jobapp.Profiles;

// importing all android's widgets libraries

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.R;

// importing all the relevant Google Map API libraries


/**
 *
 *
 * The Abstract Search map class is a baseline or parent class, it helps to build certain sub clases that displays the list of objects
 * in the Interactive Google Map fragmenent. The SearchMap class allows the other sub classes to share its Google Map API's attributes
 * that can be used to create a Map activity in Android. Building an abstract Search Map class makes it easier for development to build Map activity
 * that have similar features and helps to save time .
 *
 * The searchMap object has a  methods to help sub clasees calculate the distance between two points of the
 * latitude and longitude return as miles. Also has method to help the user move to a particular area in the map by
 * passing lat and long value in the methods parameter. The Search Map has some abstarct methods to be implemneted by the sub classes
 */

// The abstract searchMap extends AppCompatActivity which is the base class android activities that supports Android Bar feature
// To allow class activities have Bar feature on the screen it must extend AppComPactActivity
// The SearchMap implements GoogleApiClient interface to help support and access Location Services API. The  Location Services API
// can be found in Google Play Service which is the GoogleApiClient
// The ConnectionCallbacks provides service for connection
// OnConnectionFailedListener ensures if the connection from Play service has failed
//LocationListener helps the object detect the user location as it changes
public abstract class InteractiveMap extends AppCompatActivity {

    // All attributes and methods are being protected as it can only accessed within the inheritance
    /// instance object of Google Map that allows to control the actual map fragments
    protected GoogleMap gogoMap;


    protected EditText searchQuery; //search jobs based title or postCode

    // transferPackage object to send values to the web service script
    protected TransferPackage transferPackage;

    // http management class to handle http requests to the sever
    protected HttpTransferData httpTransferData;


    // An ArrayList that holds list of Markers to displays on the map
    // The marker option object defines the Marker
    protected ArrayList<MarkerOptions> markers = null;


    protected Button findPostCode; // Button to direct the user to particular area in the map


    // an abstract methods signatures for sub classes to be implemented

    // an abstract method to help the list of data to be updated in the Google Map
    protected abstract void updateData();

    // an abstract method to run all feature of the google map
    protected abstract void markersWindowActionEvent() throws IOException;

    //an abstract method markers to be displayed
    protected abstract void displayMarkers() throws IOException;

    // button Method to take the user to the requested location
    //  go to the location by entering
    // the postcode or name of the place
    public void findLocation(View view) throws IOException {

        // if the search bar is not empty
        if (searchQuery.getText().toString().length() > 0) {
            // then do the following procedure
            // to access all geolocation feature for the GoGo jobs map
            Geocoder geocoder = new Geocoder(this);

            //using the list object to collect address of that particular vacancy place
            // call the getFromLocationName method pass
            // in the post code or location name and the maximum number of results to be found is 1
            List<Address> Addresslist = geocoder.getFromLocationName(searchQuery.getText().toString(), 1);


            // collect the first result of the address
            android.location.Address add = Addresslist.get(0);
            // collect latitude of the address
            double lat = add.getLatitude();
            // collect the longitude of the address
            double lng = add.getLongitude();

            // go to that current location with the latitude and longitude
            goToLocation(lat, lng, 13);
        }
    }


    //
    // goToLocation method allows the user move to particular zone in the map
    // the method has been adapted from Lynda tutorials on Andorid Google Map
    //  Directs the exact location to the map using the longitude and latitude,
    // also takes the third value for zooming to get closer in certain distance
    protected void goToLocation(double lat, double lng, int zoom) {
        // LatLng object helps to take to get the exact location from the lat and log
        // it is part of the google map gps
        LatLng pointers = new LatLng(lat, lng);
        //  CameraUpdate object helps to modify the camera of the map that allows to move
        // to certain location
        // CameraUpdate takes user  to the exact location of the map
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pointers, zoom);
        // actual gogoMap's camera is updated
        gogoMap.moveCamera(update);
    }



    // the distance method helps to calculate the distance between two points of the
    // latitude and longitude and return as miles.
    // Code has been taken from GeodataSource website that mathematically calculates the distance
    // Reference: https://www.geodatasource.com/developers/java
    // the distance method helps to calculate the distance between two points of the
    // latitude and longitude and return as miles.
    protected static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        //mathematical formula the calculates between the two locations from their longitude and latitude coordinates
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        // return as Miles in double
        return (dist);
    }

    //Other two mathematical methods from GeodataSource that helps to convert decimal degrees to radians and radians to decimal degrees.
    // These two methods are used in the distance function to help calculate distance
    /*::	This method converts decimal degrees to radians						 :*/
    protected static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*::	This method converts radians to decimal degrees						 :*/

    protected static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    // method to help determine if the current map is initialised to the xml fragment map in the application
    //  this code has been taken from Lynda Tutorials on Android Google Map
    protected boolean initialiseGoGoMap() {
        // if the GoGo jobs map is null then make reference to the support fragments
        if (gogoMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            gogoMap = mapFragment.getMap();

        }
/// once the GogoJobs Map has been initialised or is already initialised then return true
        return (gogoMap != null);
    }


    // The displayInfoWindowMarker method helps to display markers InfoWindow and snippet
    // Allows to customise the appearance of the display and layout design
    // This code taken from Hiren Patel Answer From StackOverFlow
    // http://stackoverflow.com/questions/13904651/android-google-maps-v2-how-to-add-marker-with-multiline-snippet/31629308#31629308
    protected void displayInfoWindowMarker() {
        //Setting up the Google Maps InfoWindow Adapter method for the Markers
        // Calling objects interface InfoWindowAdapter() method such as
        // getInfoWindow to display default info window frame
        // getInfoContents allows to customise the info window frame layout
        gogoMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


            // it display a default info window frame by returning as null
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // allows to customise the layout of info window frame
            @Override
            public View getInfoContents(Marker marker) {
                // for each marker
                // collect the current context from this activity
                // getting the context helps to display the widgets
                // on the info window frame
                Context context = getApplicationContext(); //getting the current context

                // the info would be viewed as Linear Layout
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL); // display in a vertical format

                // widget TextView for the job title to display on window frame
                TextView job_title = new TextView(context);
                job_title.setTextColor(Color.BLACK); // color black
                job_title.setTypeface(null, Typeface.BOLD);
                // collect the title string from that marker
                job_title.setText(marker.getTitle());

                // widget Textview for snippet to be displayed
                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.BLACK); // color black
                // collect the snippet string from that marker
                snippet.setText(marker.getSnippet());

                // add the widgets to the Linear Layout View
                info.addView(job_title);
                info.addView(snippet);

                // then update the new content on the info window frame
                return info;
            }
        });
    }


}
