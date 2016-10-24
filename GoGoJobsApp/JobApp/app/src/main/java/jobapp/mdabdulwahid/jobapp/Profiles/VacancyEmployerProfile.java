package jobapp.mdabdulwahid.jobapp.Profiles;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jobapp.mdabdulwahid.jobapp.EmployerMenu.listCandidatesMap;
import jobapp.mdabdulwahid.jobapp.Objects.Employer;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.R;

/**
 *
 * VacancyEmployerProfile displays the description of the vacancy profile
 * from the employer
 */
public class VacancyEmployerProfile extends AppCompatActivity {

    private TextView titleDes;
    private TextView answerDes;
    private TextView titleRes;
    private TextView anserRes;
    private TextView titleFac;
    private TextView anserFac;
    private Intent intent;
    private Bundle bundle;
    private Vacancy vacancy;
    private Employer employer;
    private ActionBar actionBar;
    private Button button;

    //onCreate method to initialise all object and display the content
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_description);

        //initialise all widgets
        titleDes = (TextView) findViewById(R.id.des);
        answerDes = (TextView) findViewById(R.id.ansr_description);
        titleRes = (TextView) findViewById(R.id.title_responsibilities);
        anserRes = (TextView) findViewById(R.id.ansr_responsibilities);
        titleFac = (TextView) findViewById(R.id.title_facts);
        anserFac = (TextView) findViewById(R.id.answer_facts);
        button = (Button) findViewById(R.id.apply);

        intent = getIntent();
        bundle = intent.getExtras();

        actionBar = getSupportActionBar();

        vacancy = (Vacancy) bundle.getParcelable("vacancy");
        employer = (Employer) bundle.getParcelable("employer");

        //setting up the action bar
        actionBar.setDisplayHomeAsUpEnabled(true); //
        actionBar.setTitle("        " + vacancy.getJob_title());
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));
        answerDes.setText(vacancy.getJobDescription());
        anserRes.setText(vacancy.getResponsibilies());

        anserFac.setText("Contract: " + vacancy.getContract() + "\n" + "Salary:" + vacancy.getSalary() + "\n"
                + "Date Posted: " + vacancy.getJobPosted());
        //setting up the X and Y axis for widgets
        titleDes.setY(5);
        answerDes.setY(75);
        titleRes.setY(560);
        anserRes.setY(650);
        anserFac.setY(1380);
        titleFac.setY(1300);
        button.setY(1620);
        button.setText("Show All Applicants");
    }


    //button method to take the user to the next map activity
    //which they can view applicants who applied to the particular vacancy
    public void applyButton(View v) {
        Intent intent = new Intent(VacancyEmployerProfile.this, listCandidatesMap.class);
        intent.putExtra("vacancy", vacancy);
        intent.putExtra("employer", employer);
        startActivity(intent);


    }

    @Override // method for the action bar return back to the previous activiity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }

}
