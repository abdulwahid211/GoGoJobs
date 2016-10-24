

package jobapp.mdabdulwahid.jobapp.JobSeekerMenu;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.R;

/**
 * uploadFile calss allows to upload file to the backend database, it first retrieves URL file
 * from android phone aided by an open source Android library called aFileChooser
 * Reference: https://github.com/iPaulPro/aFileChooser
 * Then the files would be uploaded by UploadFileAsync to be send to the PHP script
 */
public class uploadFile extends AppCompatActivity {

    private TransferPackage transferPackage;
    private HttpTransferData httpTransferData;
    private int serverResponseCode = 0;
    private Button cv;
    private Button coverLetter;

    private Vacancy vacancy;
    private Job_seeker job_seeker;

    private static final int REQUEST_CODE = 1000; // onActivityResult request
    private static final int REQUEST_CODE2 = 2000; // onActivityResult request
    // code

    private String job_seekerID = "";
    private String vacancyID = "";
    private String filePathCV = "";
    private String filePathCoverLetter = "";
    private String eligableUK = "";
    private String noticePeriod = "";
    private String preExperience = "";
    private String userAnser = "";
    private ActionBar actionBar;
    private UploadFileAsync uploadCV;// AsyncTask to upload CV
    private UploadFileAsync uploadCoverLetter; // AsyncTask to upload Cover letter
    private submitApplicationForm submitApplicationForm;// AsyncTask to upload subApplication forms details from the previous
    // activity
    protected Time currentDate;
    private TextView title;
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/application_form.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uplod_file);


        cv = (Button) findViewById(R.id.cvButton);
        coverLetter = (Button) findViewById(R.id.coverButton);
        title = (TextView) findViewById(R.id.messagge);


        // setting X and Y axis of android widgets
        cv.setY(420);
        cv.setX(70);
        coverLetter.setX(70);
        coverLetter.setY(750);
        title.setX(70);
        title.setY(120);
        // setting up the configuration for the action bar
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //
        actionBar.setTitle("         Upload Files");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //retrieving all object values from the previous activity
        job_seeker = (Job_seeker) bundle.getParcelable("JobSeekerDetails");
        vacancy = (Vacancy) bundle.getParcelable("vacancy");
        transferPackage = new TransferPackage();
        eligableUK = bundle.getString("eligableUK");
        noticePeriod = bundle.getString("noticePeriod");
        preExperience = bundle.getString("preExperience");
        userAnser = bundle.getString("userAnser");

        currentDate = new Time(Time.getCurrentTimezone());
        currentDate.setToNow();
        // details of the application form to be posted
        transferPackage.addNewPost("job_seekerId", job_seeker.getID());
        transferPackage.addNewPost("vacancy_id", vacancy.getID());
        transferPackage.addNewPost("noticePeriod", noticePeriod);
        transferPackage.addNewPost("eligableUK", eligableUK);
        transferPackage.addNewPost("preExperience", preExperience);
        transferPackage.addNewPost("userAnser", userAnser);
        transferPackage.addNewPost("dateApplied", currentDate.monthDay + "/" + currentDate.month + "/" + currentDate.year);

        //obtaining of both ids
        job_seekerID = job_seeker.getID();
        vacancyID = vacancy.getID();

        //initialising AsynTasks for CV and Cover Letter
        uploadCV = new UploadFileAsync("cv");
        uploadCoverLetter = new UploadFileAsync("coverletter");


        try {
            // set up the webservice request object
            //  allows to post values and receive response
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }


        submitApplicationForm = new submitApplicationForm();

    }

    //button method to upload the CV
    public void UploadCV(View v) {
        // send the relevant request code
        showChooser(REQUEST_CODE);

    }

    //button method to upload the Cover letter
    public void UploadCoverLetter(View v) {
        // send the relevant request code
        showChooser(REQUEST_CODE2);
    }

    //button method to submit everything
    public void SubmitButton(View v) {
        // check if the user has retrieves both URL path for the files
        if (filePathCV.length() > 0 && filePathCoverLetter.length() > 0) {
            // execute to upload CV to the server
            uploadCV.execute(filePathCV);
            // execute to upload CoverLetter to the server
            uploadCoverLetter.execute(filePathCoverLetter);
            submitApplicationForm.execute();

            // once the file has been submitted return the user back to the job search menu
            Intent intent = new Intent(uploadFile.this, Job_seekerMenu.class);
            intent.putExtra("username", job_seeker.getUsername());
            Toast.makeText(uploadFile.this, "Files has been uploaded successfully", Toast.LENGTH_SHORT).show();
            startActivity(intent);


        } // end if
        else {
            Toast.makeText(uploadFile.this, "You must upload both CV and Cover Letter", Toast.LENGTH_SHORT).show();
        }
    }

    //calling request method aFileChooser library
    // passing the request code
    private void showChooser(int REQUEST_CODE) {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "GoGo Jobs");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    // using the method aFileChooser library to retrieve all URL file paths
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // using two switch cases to fetch files URL path
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();

                        try {
                            // Get the CV file path from the URI
                            final String CVpath = FileUtils.getPath(this, uri);
                            filePathCV = CVpath;
                        } catch (Exception e) {
                            // error
                        }
                    }
                }
                break;
            case REQUEST_CODE2:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();

                        try {
                            // Get the cover letter file path from the URI
                            final String CoverLetterpath = FileUtils.getPath(this, uri);
                            filePathCoverLetter = CoverLetterpath;
                        } catch (Exception e) {
                            // error
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override // method for the action bar return back to the previous activiity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }


    // inner class AsyncTask object to execute the operation in the background thread
    private class submitApplicationForm extends AsyncTask<String, String, String> {

        //override method to set up values before background execution
        @Override
        protected void onPreExecute() {

        }

        // doInBackground to execute the actual operation in the background
        @Override
        protected String doInBackground(String... param) {
            // executes request to the php script of the applicationFormDetails
            String newContent = httpTransferData.DataTransfer();
            return newContent; // return the new response from the php web service script
        }

        @Override
        protected void onPostExecute(String result) {

        }

    }

    // UploadFileAsync allows the files to be send to the PHP script
    // it is executed in the background
    // This AsyncTask was taken from StackOverFlow
    // Reference:
    // http://stackoverflow.com/questions/25398200/uploading-file-in-php-server-from-android-device
    private class UploadFileAsync extends AsyncTask<String, Void, String> {

        String fileType;

        public UploadFileAsync(String type) {
            fileType = type;
        }

        //override method to set up values before background execution
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String sourceFileUri = params[0];
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                File sourceFile = new File(sourceFileUri);

                //if the URL source file does exist
                if (sourceFile.isFile()) {

                    try {
                        // url link of the php script
                        // passing the id's via get request
                        String upLoadServerUri = "http://doc.gold.ac.uk/~ma301ma/finalProject/uploadToServer.php?jobID=" + job_seekerID +
                                "&vacID=" + vacancyID;

                        // open a URL connection to the Servlet
                        //takes the input of the source file URL
                        // it is converted into bytes
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL of the web service script
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");//to be posted in method
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);

                        conn.setRequestProperty(fileType, sourceFileUri);

                        //initialise the output Stream of the bytes
                        dos = new DataOutputStream(conn.getOutputStream());

                        //output the content of the file to the PHP
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"" + fileType + "\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form of buytes
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        // sorts out the bytes
                        while (bytesRead > 0) {
                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);
                        }

                        // finally output all file bytes
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn
                                .getResponseMessage();

                        // once the file has been uploaded successfully
                        if (serverResponseCode == 200) {
                            // display a success message
                            Toast.makeText(uploadFile.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // close all the streams of the file
                        fileInputStream.close();
                        dos.flush();
                        dos.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } // End else block
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }


    } // end aysncTask


}