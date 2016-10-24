
package jobapp.mdabdulwahid.jobapp.arrayAdaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;
import jobapp.mdabdulwahid.jobapp.R;

/*
 * Building custom array adaptor that helps to design own items features to be displayed on an list view appearance
 * It is specifically designed for Vacancy object to be displayed in list view format
 *
 *  the code has been adapted from Lynda Tutorials
 *  http://www.lynda.com/Android-tutorials/Displaying-web-service-data-ListActivity/163757/179779-4.html
 *  can be found from the tutorial video example: Displaying web service data in a ListActivity
 */

// extending the ArrayAdapter as getView method can be used to display each vacancy object in the
// list view
// implementing Filterable interface that allows lists of jobs to be searched
// in EditText
public class VacancyAdapter extends ArrayAdapter<Vacancy> implements Filterable {


    private Context context; // reference from the main the class
    public ArrayList<Vacancy> vacancies; // list of jobs
    public ArrayList<Vacancy> originalList;
    private VacancyFilter filter; // filter object


    //  adapter constructor
    public VacancyAdapter(Context context, int resource, ArrayList<Vacancy> _list) {
        super(context, resource, _list);
        this.context = context;
        vacancies = new ArrayList<Vacancy>();
        vacancies.addAll(_list);
        originalList = new ArrayList<Vacancy>();
        originalList.addAll(_list);
    }

   // ensure the actual filter object is initialised
    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new VacancyFilter();
        }
        return filter;
    }

    @Override
    // the getView method helps each item to be displayed in the list
    // position variable within the method parameter helps to View particular object
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // view objects recollects the features of items that needs to be displayed in an list view
        View view = inflater.inflate(R.layout.items_job, parent, false);

        //Display that particular vacancy object
        Vacancy vacancy = vacancies.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView compnayName = (TextView) view.findViewById(R.id.comapanyname);
        TextView salPostCode = (TextView) view.findViewById(R.id.salpostcode);
        TextView datePosted = (TextView) view.findViewById(R.id.datePosted);
        title.setText(vacancy.getJob_title());
        compnayName.setText(vacancy.getCompanyName());
        salPostCode.setText(vacancy.getSalary()+", "+vacancy.getPostCode());
        datePosted.setText("Posted: "+vacancy.getJobPosted());



        return view;
    }
    //VacancyFilter class is custom filter object that checks if the user's word request
    // matches against in the list view
    // The Filter object is android API that allows the Filter operation asynchronously
    // this Filter class has been adapted from mysamplecode web site
    // Reference:
    //http://www.mysamplecode.com/2012/07/android-listview-custom-layout-filter.html
    private class VacancyFilter extends Filter
    {

        // performs the filtering asynchronously
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            //convert the characters from the search query into lower case
            constraint = constraint.toString().toLowerCase();
            // initialise new filter results
            FilterResults result = new FilterResults();
            //if the character does contain value at least
            if(constraint != null && constraint.toString().length() > 0)
            {
                // new list empty of vacancies
                ArrayList<Vacancy> filteredItems = new ArrayList<Vacancy>();
               // go through the original list
                for(int i = 0, l = originalList.size(); i < l; i++)
                {
                   Vacancy vacancy = originalList.get(i);
                    //if that job title matches against  the query
                    if(vacancy.getJob_title().toString().toLowerCase().contains(constraint))
                        // add that new vacancy to the list
                        filteredItems.add(vacancy);
                }
                //once the iteration has completed
                // update the values
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        //once the filtering has been finished
        //update the results
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            vacancies = (ArrayList<Vacancy>)results.values;
            notifyDataSetChanged(); // update the list
            clear(); // clear all old list
            for(int i = 0, l = vacancies.size(); i < l; i++)
                add(vacancies.get(i)); // add new ones
            notifyDataSetInvalidated();
        }
    } // end vacancy class




}






















