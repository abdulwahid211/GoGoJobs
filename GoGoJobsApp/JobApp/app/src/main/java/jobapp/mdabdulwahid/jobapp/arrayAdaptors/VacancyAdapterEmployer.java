
package jobapp.mdabdulwahid.jobapp.arrayAdaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.R;

/*
 *
 *
 *
 * Building custom array adaptor that helps to design own items features to be displayed on an list view appearance
 * It is specifically designed for Vacancy object to be displayed in list view format
 *
 *  the code has been adapted from Lynda Tutorials
 *  Reference:
 *  http://www.lynda.com/Android-tutorials/Displaying-web-service-data-ListActivity/163757/179779-4.html
 *  can be found from the tutorial video example: Displaying web service data in a ListActivity
 */

// extending the ArrayAdapter as getView method can be used to display each vacancy object in the
// list view
public class VacancyAdapterEmployer extends ArrayAdapter<Vacancy> implements Filterable {


    private Context context; // reference from the main the class
    private ArrayList<Vacancy> vacancies; // list of jobs from the employer



    //  adapter constructor
    public VacancyAdapterEmployer(Context context, int resource, ArrayList<Vacancy> _list) {
        super(context, resource, _list);
        this.context = context;
        this.vacancies = _list;
    }

    @Override
    // the getView method helps each item to be displayed in the list
    // position variable within the method parameter helps to View particular object
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // view objects recollects the features of items that needs to be displayed in an list view
        View view = inflater.inflate(R.layout.items_job_employer, parent, false);

        //Display that particular vacancy object
        Vacancy vacancy = vacancies.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(vacancy.getJob_title());
        // date
        TextView date = (TextView) view.findViewById(R.id.dateposted);
        date.setText("Date Created: "+vacancy.getJobPosted());
        // job type
        TextView type = (TextView) view.findViewById(R.id.jobtype);
        type.setText("Sector Type: "+vacancy.getType());


        return view;
    }

}

















