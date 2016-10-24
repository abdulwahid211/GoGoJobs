package jobapp.mdabdulwahid.jobapp.Register;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.JSONparse.RegisterMessageJSONParser;
import jobapp.mdabdulwahid.jobapp.Objects.RegisterMessage;

/**
 *
 * Abstract Register class allow sub registration classes to inherit and access common attributes and methods
 */
public abstract class Register extends AppCompatActivity {
    protected final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/Registration.php";
    protected EditText firstName; //
    protected EditText lastName; //
    protected EditText telephone;
    protected EditText mobile;
    protected ActionBar actionBar;
    protected EditText email;
    protected EditText usernameInput;
    protected TransferPackage transferPackage;
    protected HttpTransferData httpTransferData;
    protected EditText passwordInput;
    protected RegisterMessage registerMessageList;
    protected RegisterMessageJSONParser registerMessageJSONParser;
    protected EditText postCode; //


    //getter method for HttpTransferData
    public HttpTransferData getHttpTransferData() {
        return httpTransferData;
    }



    // abstract method for submit button
    public abstract void SubmitButton(View view);
    // abstract method for SubmitData
    public abstract void SubmitData();
    // abstract method for updateMessage
    public abstract void updateMessage();



    //getter methods
    public EditText getEmail() {
        return email;
    }
    public EditText getMobile() {
        return mobile;
    }
    public EditText getTelephone() {
        return telephone;
    }


    // inner class AsyncTask object to execute the operation in the background thread
    protected class AttemptRegister extends AsyncTask<String, String, String> {





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
            // JSON data contains all registation detail
            registerMessageList = (RegisterMessage)registerMessageJSONParser.parseFeed(result,"RegJb");
            //update the message
            updateMessage();

        }

    }


}
