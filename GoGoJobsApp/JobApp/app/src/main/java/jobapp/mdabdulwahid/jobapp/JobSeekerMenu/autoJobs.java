package jobapp.mdabdulwahid.jobapp.JobSeekerMenu;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.R;


/**
 *
 * The autoJobs activity provides the interface for the job seeker to
 * choose particular jobs type to be searched in the background Service
 * The autoJobs would activate Service activity in the background when the user presses
 * findJob button method
 */
public class autoJobs extends AppCompatActivity {


    protected Job_seeker job_seeker; // job seeker object
    protected ActionBar actionBar;
    //24  widget check boxes
    protected CheckBox accounting;
    protected CheckBox Admin;
    protected CheckBox BankingFinance;
    protected CheckBox office;
    protected CheckBox Media;
    protected CheckBox customer_service;
    protected CheckBox restarants;
    protected CheckBox engineering;
    protected CheckBox education;
    protected CheckBox retail;
    protected CheckBox science;
    protected CheckBox government;
    protected CheckBox health_care;
    protected CheckBox hospitality;
    protected CheckBox hr;
    protected CheckBox ict;
    protected CheckBox internships;
    protected CheckBox manufactor;
    protected CheckBox volunteer;
    protected CheckBox cafe;
    protected CheckBox sales_marketing;
    protected CheckBox bar_;
    protected CheckBox other;
    protected CheckBox Anytype;
    private Spinner dropdown;
    protected int mile = 0;
    protected ArrayList<String> jobChoice;
    //array of mile items
    String[] items = new String[]{"2 Mile", "4 Mile", "6 Mile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_jobs);
        actionBar = getSupportActionBar();
        Intent intentObject = getIntent();
        Bundle bundle = intentObject.getExtras();
        // reconstructing the job seeker objects
        job_seeker = (Job_seeker) bundle.getParcelable("JobSeekerDetails");
        actionBar.setTitle("GoGo Jobs Auto Search");
        jobChoice = new ArrayList<String>();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));

        // initialise allrelevant check boxes
        accounting = (CheckBox) findViewById(R.id.checkBox5);
        Admin = (CheckBox) findViewById(R.id.checkBox7);
        BankingFinance = (CheckBox) findViewById(R.id.checkBox9);
        office = (CheckBox) findViewById(R.id.checkBox11);
        Media = (CheckBox) findViewById(R.id.checkBox13);
        customer_service = (CheckBox) findViewById(R.id.checkBox15);
        restarants = (CheckBox) findViewById(R.id.checkBox17);
        engineering = (CheckBox) findViewById(R.id.checkBox19);
        education = (CheckBox) findViewById(R.id.checkBox20);
        retail = (CheckBox) findViewById(R.id.checkBox23);
        science = (CheckBox) findViewById(R.id.checkBox25);
        government = (CheckBox) findViewById(R.id.checkBox6);
        health_care = (CheckBox) findViewById(R.id.checkBox8);
        hospitality = (CheckBox) findViewById(R.id.checkBox10);
        hr = (CheckBox) findViewById(R.id.checkBox12);
        ict = (CheckBox) findViewById(R.id.checkBox14);
        internships = (CheckBox) findViewById(R.id.checkBox16);
        manufactor = (CheckBox) findViewById(R.id.checkBox18);
        volunteer = (CheckBox) findViewById(R.id.checkBox21);
        cafe = (CheckBox) findViewById(R.id.checkBox22);
        sales_marketing = (CheckBox) findViewById(R.id.checkBox24);
        bar_ = (CheckBox) findViewById(R.id.checkBox26);
        other = (CheckBox) findViewById(R.id.checkBox28);
        Anytype = (CheckBox) findViewById(R.id.checkBox27);
        dropdown = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);


        dropdown.setAdapter(adapter);

       // determines if the user has selected an item from the dropdown menu
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                               // if the user has selected an item
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   String value = (String) parent.getItemAtPosition(position);
                                                   // then collect that particular item of the mile
                                                   // from the dropdown menu
                                                   mile = Integer.valueOf(value.substring(0, 1));
                                               }
                                               //must override method if the user has not selected anything
                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {

                                               }
                                           }


        ); // drop down

        // there are 24 checkboxes activating setOnClickListener() method on
        // the method setOnClickListener() allows to detect the action event
        // inside on every method of  setOnClickListener() it has inner function
        // onClick(View v) which mainly detects perform action on click
        // when the user performs a click it would add a value to the  ArrayList
        // hence these values are then passed to the next activity
        accounting.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (accounting.isChecked()) {
                    jobChoice.add("Accounting");
                }
            }
        });


        Admin.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Admin.isChecked()) {
                    jobChoice.add("Admin");
                }
            }
        });


        BankingFinance.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (BankingFinance.isChecked()) {
                    jobChoice.add("Banking & Finance");
                }
            }
        });


        office.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (office.isChecked()) {
                    jobChoice.add("Office");
                }
            }
        });


        Media.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Media.isChecked()) {
                    jobChoice.add("Media");
                }
            }
        });

        customer_service.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (customer_service.isChecked()) {
                    jobChoice.add("Customer Service");
                }
            }
        });

        restarants.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (restarants.isChecked()) {
                    jobChoice.add("Restaurant");
                }
            }
        });


        engineering.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (engineering.isChecked()) {
                    jobChoice.add("Engineering");
                }
            }
        });

        education.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (education.isChecked()) {
                    jobChoice.add("Education");
                }
            }
        });

        retail.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (retail.isChecked()) {
                    jobChoice.add("Retail");
                }
            }
        });


        science.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (science.isChecked()) {
                    jobChoice.add("Science");
                }
            }
        });

        government.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (government.isChecked()) {
                    jobChoice.add("Government");
                }
            }
        });

        health_care.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (health_care.isChecked()) {
                    jobChoice.add("Health Care");
                }
            }
        });

        hospitality.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (hospitality.isChecked()) {
                    jobChoice.add("Hospitality");
                }
            }
        });

        hr.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (hr.isChecked()) {
                    jobChoice.add("Human Resources");
                }
            }
        });


        ict.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (ict.isChecked()) {
                    jobChoice.add("ICT");
                }
            }
        });

        internships.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (internships.isChecked()) {
                    jobChoice.add("Internships");
                }
            }
        });

        manufactor.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (manufactor.isChecked()) {
                    jobChoice.add("Manufactor");
                }
            }
        });

        volunteer.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (volunteer.isChecked()) {
                    jobChoice.add("Volunteer");
                }
            }
        });

        cafe.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (cafe.isChecked()) {
                    jobChoice.add("Cafe");
                }
            }
        });

        sales_marketing.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (sales_marketing.isChecked()) {
                    jobChoice.add("Sales & Marketing");
                }
            }
        });

        bar_.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (bar_.isChecked()) {
                    jobChoice.add("Bar");
                }
            }
        });

        other.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (other.isChecked()) {
                    jobChoice.add("Other");
                }
            }
        });


        Anytype.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Anytype.isChecked()) {
                    jobChoice.clear();
                    jobChoice.add("Any type");
                    uncheckAllJobs();
                }
            }
        });

    } // end conCreate method


    public void clear(View view) {


        jobChoice.clear();
        uncheckAllJobs();
        Anytype.setChecked(false);
    }


    // Method to start the AutoJobsService
    public void findJob(View view) {

        // when the user has selected at least a job type and mile
        if (jobChoice.size() > 0 && mile > 0) {
            // begin the Service operation
            // pass an intent object values to the next service activity
            Intent intent = new Intent(getBaseContext(), AutoJobsService.class);
            // send an Arraylist of job types
            intent.putStringArrayListExtra("jobs_list", jobChoice);
            // send an mile
            intent.putExtra("mile", mile);
            // send an job seeker object
            intent.putExtra("JobSeekerDetails", job_seeker);
            // set the action bar title
            actionBar.setTitle("Auto Job Search (Processing)");
            // begin the service operation
            startService(intent);
        }
        // if the user has not pressed anything at all, display a message on the screen
        else {
            Toast.makeText(this, "Please fill all the required fields!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to stop the Auto Jobs service
    public void stopJob(View view) {
        jobChoice.clear(); // clear the arrayList
        uncheckAllJobs(); // clear all checkboxes
        Anytype.setChecked(false); // uncheckbox anytype

        actionBar.setTitle("Auto Job Search (Stopped)"); //
        // stop the auto job service,
        // send an intent object to disable service activity
        stopService(new Intent(getBaseContext(), AutoJobsService.class));
    }

    // method to uncheck all boxes from the interface
    public void uncheckAllJobs() {
        accounting.setChecked(false);
        Admin.setChecked(false);
        BankingFinance.setChecked(false);
        office.setChecked(false);
        Media.setChecked(false);
        customer_service.setChecked(false);
        restarants.setChecked(false);
        engineering.setChecked(false);
        education.setChecked(false);
        retail.setChecked(false);
        science.setChecked(false);
        government.setChecked(false);
        health_care.setChecked(false);
        hospitality.setChecked(false);
        hr.setChecked(false);
        ict.setChecked(false);
        internships.setChecked(false);
        manufactor.setChecked(false);
        volunteer.setChecked(false);
        cafe.setChecked(false);
        sales_marketing.setChecked(false);
        bar_.setChecked(false);
        other.setChecked(false);

    }


}
