package jobapp.mdabdulwahid.jobapp.JobSeekerMenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.R;

/**
 *
 */
public class applicationForm extends AppCompatActivity {


    //android widgets and String variables to hold anser from the fields
    // that can be posted to the next activity
    private CheckBox eligCheckYes, eligCheckNo, experYes, experNo;
    private Vacancy vacancy;
    private Job_seeker job_seeker;
    private EditText notice, anderInfo;
    private String eligableAnser;
    private String prevAnser;
    private String noticeAnser;
    private String suitableAnser;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_form);

        // initialising all check boxes from the application form xml
        eligCheckYes = (CheckBox) findViewById(R.id.checkBox);
        eligCheckNo = (CheckBox) findViewById(R.id.checkBox2);
        experYes = (CheckBox) findViewById(R.id.chckBxExperYes);
        experNo = (CheckBox) findViewById(R.id.checkBox3);
        notice = (EditText) findViewById(R.id.noticePeriod);
        anderInfo = (EditText) findViewById(R.id.anserInfo);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //
        actionBar.setTitle("        Application Form");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // receive objects from the previous activity
        job_seeker = (Job_seeker) bundle.getParcelable("JobSeekerDetails");
        vacancy = (Vacancy) bundle.getParcelable("vacancy");
        noticeAnser = notice.getText().toString();
        suitableAnser = anderInfo.getText().toString();


    } // end onCreate method

    // all 4 check boxes methods
    // to capture the users reuqest
    public void eligCheckYes(View v) {
        if (eligCheckYes.isChecked())

        {
            eligableAnser = "YES";

            eligCheckNo.setChecked(false);

        }
    }
    public void eligCheckNo(View v) {
        if (eligCheckNo.isChecked())

        {
            eligableAnser = "NO";

            eligCheckYes.setChecked(false);

        }
    }
    public void experYes(View v) {
        if (experYes.isChecked())

        {
            prevAnser = "YES";

            experNo.setChecked(false);

        }
    }
    public void experNo(View v) {
        if (experNo.isChecked())

        {
            prevAnser = "NO";

            experYes.setChecked(false);

        }
    }


    @Override // method for the action bar return back to the previous activiity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }

    // submit button to send the values to the next activity
    public void SubmitButton(View v) {
        // validation checks if the user has entered everything
        if (notice.getText().toString().length() > 0 && anderInfo.getText().toString().length() > 0 && eligableAnser.length() > 0
                && prevAnser.length() > 0) {
            //intently send all the details to the next activity
            Intent intent = new Intent(applicationForm.this, uploadFile.class);
            intent.putExtra("vacancy", vacancy);
            intent.putExtra("JobSeekerDetails", job_seeker);
            intent.putExtra("eligableUK", eligableAnser);
            intent.putExtra("noticePeriod", notice.getText().toString());
            intent.putExtra("preExperience", prevAnser);
            intent.putExtra("userAnser", anderInfo.getText().toString());
            startActivity(intent);


        } else {
            Toast.makeText(getApplicationContext(), "Please fill in all required forms", Toast.LENGTH_SHORT).show();
        }

    }


}