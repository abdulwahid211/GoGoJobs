
package jobapp.mdabdulwahid.jobapp.EmployerMenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.JSONparse.VacanciesListJSON;
import jobapp.mdabdulwahid.jobapp.Objects.Employer;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.Profiles.VacancyEmployerProfile;
import jobapp.mdabdulwahid.jobapp.R;
import jobapp.mdabdulwahid.jobapp.arrayAdaptors.VacancyAdapterEmployer;

/**
 * This activity displays list of vacancies set by the employer
 * it retrieves all the list of vacancies and updates the list after every 5 seconds
 */
public class MyVacancies extends AppCompatActivity {


    protected ArrayList<Vacancy> vacancies; // list of vacancy retrieving from the database
    protected VacanciesListJSON vacanciesListJSON;
    private TransferPackage transferPackage; // transferPackage object to send values to the php script
    private HttpTransferData httpTransferData; // http management class to handle http requests to the sever
    public ListView mainListView;
    VacancyAdapterEmployer adapter;
    RetrieveAllVacancies retrieveAllVacancies;
    boolean Paused = true;
    // An widget action bar to feature in the user interface
    private ActionBar actionBar;
    private String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/retrieveVacanciesEmployer.php";
    protected TextView textView;
    protected Employer employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacancylistemployer);
        vacancies = new ArrayList<Vacancy>();
        actionBar = getSupportActionBar();
        actionBar.setTitle("                       My Jobs");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));
        mainListView = (ListView) findViewById(R.id.list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        textView = (TextView) findViewById(R.id.textView3);
        employer = (Employer) bundle.getParcelable("employerDetails");
        transferPackage = new TransferPackage();
        transferPackage.addNewPost("employer_Id", employer.getID());
        try {
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }
        vacanciesListJSON = new VacanciesListJSON();
        retrieveAllVacancies = new RetrieveAllVacancies();

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
                        if (Paused) {
                            // TODO Auto-generated method stub
                            new RetrieveAllVacancies().execute();

                        }
                    }

                }); // end runONUIThread method
            }
        }, 0, 5, TimeUnit.SECONDS); // after every 10 seconds


        // this helps to detect if the user has selected particular item from
        // the list
        // setOnItemClickListener listens to the user response from the list
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //onItemClick helps to retrieve the position from the list
            // which is selected
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // select that particular item form the list
                Vacancy vacancy = vacancies.get(position);
                // view that vacancy object in the next profile activity
                Intent intent = new Intent(MyVacancies.this, VacancyEmployerProfile.class);
                // pass along with objects to the next activity
                intent.putExtra("vacancy", vacancy);
                intent.putExtra("employer", employer);
                startActivity(intent);
            }
        });


    }

    // resume the activity
    @Override
    protected void onResume() {
        super.onResume();
        Paused = true;

    }

    // pause the activity
    @Override
    protected void onPause() {
        super.onPause();
        Paused = false;

    }

    // update the data on the list
    public void updateList() {
        adapter = new VacancyAdapterEmployer(MyVacancies.this, R.layout.vacancy_items, vacancies);
        adapter.notifyDataSetChanged();
        mainListView.setAdapter(adapter);
    }


    // inner class AsyncTast object
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

        @Override
        protected void onPostExecute(String result) {
            // remove all whitespaces
            String phpResult = result.replaceAll("\\s+", "");
            // if vacancy does exist
            if (!phpResult.equals("")) {
                // update the details
                textView.setVisibility(View.INVISIBLE);
                vacancies = (ArrayList) vacanciesListJSON.parseFeed(result, "vacancyDetail");
            }
            else{
                textView.setVisibility(View.VISIBLE);
            }


            // finally update the list as well
            updateList();


        }


    }
}