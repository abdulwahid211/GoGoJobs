package jobapp.mdabdulwahid.jobapp.Register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.JSONparse.RegisterMessageJSONParser;
import jobapp.mdabdulwahid.jobapp.LoginDetails.Login;
import jobapp.mdabdulwahid.jobapp.R;

/**
 *
 *
 * Register_Employer is sub class of Register that helps to process the Employers registration details
 */
public class Register_Employer extends Register {

    //only available to the employer
    private EditText companyName;
    private EditText firstLineAdress;
    private EditText secondLineAdress;
    private EditText town;

    //onCreate method to initialise all object and display the content
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setTitle("    Registration For Employer");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));
        setContentView(R.layout.register_employer);
        companyName = (EditText) findViewById(R.id.companyName);
        actionBar.setDisplayHomeAsUpEnabled(true); //
        firstLineAdress = (EditText) findViewById(R.id.firstnamelineaddress);
        secondLineAdress = (EditText) findViewById(R.id.secondlineaddress);
        postCode = (EditText) findViewById(R.id.postcode);
        usernameInput = (EditText) findViewById(R.id.jbtitle);
        passwordInput = (EditText) findViewById(R.id.type);
        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        telephone = (EditText) findViewById(R.id.telephone);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        town = (EditText) findViewById(R.id.town);
        registerMessageJSONParser = new RegisterMessageJSONParser();
        transferPackage = new TransferPackage();

        try {
            // set up the webservice request object
            //  allows to post values and receive response
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }

    } // end onCreate method


     //submit button to send all th details of the employer
    public void SubmitButton(View view) {
        SubmitData();
    }

    public void SubmitData() {


        // validation check to ensure the user has filled all required forms
        if (usernameInput.getText().toString().length() > 0 && passwordInput.getText().toString().length() > 0 &&
                firstName.getText().toString().length() > 0 && lastName.getText().toString().length() > 0
                && telephone.getText().toString().length() > 0 && mobile.getText().toString().length() > 0 &&
                email.getText().toString().length() > 0 && postCode.getText().toString().length() > 0) {

            // post all the details to the web service script
            transferPackage.addNewPost("Username", usernameInput.getText().toString());
            transferPackage.addNewPost("Password", passwordInput.getText().toString());
            transferPackage.addNewPost("FirstName", firstName.getText().toString());
            transferPackage.addNewPost("LastName", lastName.getText().toString());
            transferPackage.addNewPost("CompanyName", companyName.getText().toString());
            transferPackage.addNewPost("FirstLineAddress", firstLineAdress.getText().toString());
            transferPackage.addNewPost("LastLineAddress", secondLineAdress.getText().toString());
            transferPackage.addNewPost("PostCode", postCode.getText().toString());
            transferPackage.addNewPost("Town", town.getText().toString());
            transferPackage.addNewPost("Telephone", telephone.getText().toString());
            transferPackage.addNewPost("Mobile", mobile.getText().toString());
            transferPackage.addNewPost("Email", email.getText().toString());
            transferPackage.addNewPost("Type", "Employer");

            // execute the Async task in background to send the details of the employer
            AttemptRegister attemptRegister = new AttemptRegister();
            attemptRegister.execute();//execute background thread
        } else {
            Toast.makeText(getApplicationContext(), "Please fill in all required forms", Toast.LENGTH_SHORT).show();
        }


    }

    //registration message update
    public void updateMessage() {


        String message = registerMessageList.getMessage();
        int access = registerMessageList.getAccess();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        //if the registration is a success for employer
        if (access == 1) {
            //return back to the login page
            Intent i = new Intent(Register_Employer.this, Login.class);
            startActivity(i);
        }


    }

    @Override // method for the action bar return back to the previous activity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }


}
