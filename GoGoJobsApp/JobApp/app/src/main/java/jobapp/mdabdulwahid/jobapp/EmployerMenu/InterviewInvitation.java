package jobapp.mdabdulwahid.jobapp.EmployerMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.CalendarContract.Events;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.Objects.Employer;
import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;
import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.R;

/**
 * This activity gives the employer a chance to send interview invitation
 * to the applicant
 * It uses an open source library to display interactive time and calendar
 * Reference:
 * https://github.com/wdullaer/MaterialDateTimePicker
 */
public class InterviewInvitation extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {


    private TextView Interviewtime;
    private TextView Interviewdate;
    private TextView duration;
    private TextView firstAddress;
    private TextView secondAddress;
    private TextView postcode;
    private CheckBox defaul_address;
    private TransferPackage transferPackage; // transferPackage object to send values to the php script
    private HttpTransferData httpTransferData;
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/interviewMail.php";
    private int dayOfMonth;
    private int month;
    private int year;
    private int hourOfDay;
    private int minute;
    private Job_seeker job_seeker;
    private Employer employer;
    private Vacancy vacancy;
    private ActionBar actionBar;

    @Override
    //onCreate method to initialise all object and display the content
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inteview_invitation);
        actionBar = getSupportActionBar();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        actionBar.setTitle("      Interview Invitation");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));
        vacancy = (Vacancy) bundle.getParcelable("vacancy");
        employer = (Employer) bundle.getParcelable("employer");
        job_seeker = (Job_seeker) bundle.getParcelable("JobSeekerDetails");
        actionBar.setTitle("      Interview Invitation");
        actionBar.setDisplayHomeAsUpEnabled(true);
        defaul_address = (CheckBox) findViewById(R.id.defff);
        Interviewtime = (EditText) findViewById(R.id.jbtitle);
        Interviewdate = (EditText) findViewById(R.id.type);
        duration = (EditText) findViewById(R.id.duration);
        firstAddress = (EditText) findViewById(R.id.firstaddress);
        secondAddress = (EditText) findViewById(R.id.secondaddress);
        postcode = (EditText) findViewById(R.id.postcode);
        firstAddress.setText(employer.getFirstLineAddress());
        secondAddress.setText(employer.getLastLineAddress());
        postcode.setText(employer.getPostCode());


        transferPackage = new TransferPackage();

        try {
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }


    }  // end onCreate method


    // using the library method MaterialDateTimePicker
    // to set up the interview time
    public void SetInterviewTime(View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                InterviewInvitation.this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                false
        );

        tpd.setThemeDark(false);
        tpd.vibrate(true);
        tpd.dismissOnPause(false);
        tpd.enableSeconds(false);

        tpd.setAccentColor(Color.parseColor("#ffcc7f")); // change color for theme scheme
        tpd.setTitle("Set Interview Time");


        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    } // end setInterview time

    // using the library method MaterialDateTimePicker
    // to set up the interview date
    public void SetInterviewDate(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                InterviewInvitation.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setAccentColor(Color.parseColor("#ffcc7f"));
        dpd.setTitle("Interview Date");
        dpd.show(getFragmentManager(), "Datepickerdialog");
    } // end set Interview date

    @Override
    // helps to set up correct time when the users selects from the user interface
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String am_pm = "";

        // determine if its PM or AM
        if (view.getIsCurrentlyAmOrPm() == 1) {
            am_pm = "pm";
        } else {
            am_pm = "am";
        }
        //collect time format
        String time = hourOfDay + ":" + minute;

        try {
            // set the time format to be in hours and minute
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            // set the actual user interview time
            Interviewtime.setText(new SimpleDateFormat("K:mm").format(dateObj) + am_pm);
        } catch (Exception e) {

        }

        this.hourOfDay = hourOfDay;
        this.minute = minute;

    }

    // using the library method MaterialDateTimePicker
    // to set up the interview date
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        Interviewdate.setText(date);
        this.dayOfMonth = dayOfMonth;
        this.month = monthOfYear;
        this.year = year;

    }


    //method to sync the details on the Google Calendar
    public void bookCalendar() {
        // instantiate calendar begin time
        Calendar interviewTime = Calendar.getInstance();
        // retrieve the Employer chosen time and date
        interviewTime.set(this.year, this.month, this.dayOfMonth, this.hourOfDay, this.minute);
        // use an intent object to insert and sync the detail on the google calendar
        // set up the details to be sync on the calendar
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                        // pass in the calendars time and date
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, interviewTime.getTimeInMillis())
                        //title of the calendar event
                .putExtra(Events.TITLE, "Job Interview for " + vacancy.getJob_title())
                        // description of the event
                .putExtra(Events.DESCRIPTION, "Interview details:" + "\n" + "Employer Name: " +
                        employer.getFirstName() + " " + employer.getLastName() + "\n"
                        + "Interview date: " + Interviewdate.getText().toString()
                        + "\n" + "Interview time: " + Interviewtime.getText().toString()
                        + "\n" + "FirstLine Address: " + firstAddress.getText().toString()
                        + "\n" + "SecondLine Address: " + secondAddress.getText().toString()
                        + "\n" + "Town: " + employer.getTown()
                        + "\n" + "PostCode: " + employer.getPostCode() + "\n" + "Telephone "
                        + employer.getTelephone() + "\n" + employer.getEmail())
                        // location of the event
                .putExtra(Events.EVENT_LOCATION, employer.getPostCode())
                        // Employer availability
                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_FREE)
                        // send the details on the job seeker Calendar to be synchronised
                        // the job seeker would accept the event ot reject
                .putExtra(Intent.EXTRA_EMAIL, job_seeker.getEmail());

        // start the Intent activity
        startActivity(intent);
    } // end book calendar


    // method to determine if the employer wants default address to be displayed
    // using widget check boxes
    public void defaulCheck(View view) {
        if (defaul_address.isChecked()) {
            firstAddress.setText(employer.getFirstLineAddress());
            secondAddress.setText(employer.getLastLineAddress());
            postcode.setText(employer.getPostCode());

        } else {
            //if the employer unchecked the box
            // remove the address
            firstAddress.setText("");
            secondAddress.setText("");
            postcode.setText("");

        }
    } // end default check


    @Override // method for the action bar return back to the previous activiity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }

    // the submit button to post the details of the interview
    public void SubmitButton(View v) {
        // validate if the user has entered all fields
        if (Interviewtime.getText().length() > 0 && Interviewdate.getText().length() > 0 && duration.getText().length() > 0
                && firstAddress.getText().length() > 0 && secondAddress.getText().length() > 0) {

            Toast.makeText(InterviewInvitation.this, "Details are successfully send", Toast.LENGTH_SHORT).show();
            transferPackage.addNewPost("InterviewTime", Interviewtime.getText().toString());
            transferPackage.addNewPost("InterviewDate", Interviewdate.getText().toString());
            transferPackage.addNewPost("duration", duration.getText().toString());
            transferPackage.addNewPost("firstLineAddress", firstAddress.getText().toString());
            transferPackage.addNewPost("secondLineAddress", secondAddress.getText().toString());
            transferPackage.addNewPost("town", employer.getTown());
            transferPackage.addNewPost("postCode", employer.getPostCode());
            transferPackage.addNewPost("telephone", employer.getTelephone());
            transferPackage.addNewPost("Job_seeker_email", job_seeker.getEmail());
            transferPackage.addNewPost("Employer_email", employer.getEmail());
            transferPackage.addNewPost("JobTitle", vacancy.getJob_title());
            transferPackage.addNewPost("Job_seeker_name", job_seeker.getFirstName() + " " + job_seeker.getLastName());
            transferPackage.addNewPost("Employer_name", employer.getFirstName() + " " + employer.getLastName());
            // execute the request in the background
            new SendEmail().execute();
            bookCalendar();//calling method to book the details in the google calendar

        } else {
            Toast.makeText(InterviewInvitation.this, "Please fill all the required fields!", Toast.LENGTH_SHORT).show();
        }


    } // end Submit Button

    // inner class AsyncTast object to send interview details to the web service PHP
    // execute in the background thread
    // PHP script will automate email
    private class SendEmail extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... param) {
            String newContent = httpTransferData.DataTransfer();
            return newContent;
        }

        @Override
        protected void onPostExecute(String result) {

        }


    } // end asyncTask


}
