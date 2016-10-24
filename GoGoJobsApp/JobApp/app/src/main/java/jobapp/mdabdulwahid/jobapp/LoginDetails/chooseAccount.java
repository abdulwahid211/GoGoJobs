package jobapp.mdabdulwahid.jobapp.LoginDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import jobapp.mdabdulwahid.jobapp.R;
import jobapp.mdabdulwahid.jobapp.Register.Register_Employer;
import jobapp.mdabdulwahid.jobapp.Register.Register_Job_seeker;

/**
 *
 * Displaying option to either register as Employer or Job Seeker
 */
public class chooseAccount  extends AppCompatActivity {


    private Button employer;
    private Button jobSeeker;

    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_type);

        employer = (Button) findViewById(R.id.button_emp);
        jobSeeker = (Button) findViewById(R.id.button_jb);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Please choose Account Type");
        actionBar.setDisplayHomeAsUpEnabled(true);
        jobSeeker.setY(70);
        jobSeeker.setX(210);
        employer.setY(170);
        employer.setX(210);

    }

    //register as employer
    public void empButton(View viw){
        Intent i = new Intent(chooseAccount.this, Register_Employer.class );
        startActivity(i);
    }
    // register as job seeker
    public void jbButton(View viw){
        Intent i = new Intent(chooseAccount.this, Register_Job_seeker.class );
        startActivity(i);
    }


    @Override // method for the action bar return back to the previous activity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }



}