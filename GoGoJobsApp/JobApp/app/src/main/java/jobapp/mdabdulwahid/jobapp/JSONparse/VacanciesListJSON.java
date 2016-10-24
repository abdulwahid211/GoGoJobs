package jobapp.mdabdulwahid.jobapp.JSONparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jobapp.mdabdulwahid.jobapp.Objects.Vacancy;

/**
 * Created by mdAbdulWahid on 03/02/2016.
 */
public class VacanciesListJSON implements JSONParser {

    public Object parseFeed(String content, String jsonChildAttribute) {

        ArrayList<Vacancy> vacancies = new ArrayList<Vacancy>();
        int i = 0;
        try {
            // de-serialise the JSON data
            JSONObject jsonRootObject = new JSONObject(content);

            JSONArray jsonArray = jsonRootObject.optJSONArray(jsonChildAttribute);
            for (; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // instantiate new object
                Vacancy vacancy = new Vacancy();
                // get the JSON Object data set of key value pairs
                // helps to hold each set of key value pairs
                // iterate through each set of key value pairs
                vacancy.setID(jsonObject.getString("id"));
                vacancy.setJob_title(jsonObject.getString("title"));
                vacancy.setSalary(jsonObject.getString("salary"));
                vacancy.setContract(jsonObject.getString("contract"));
                vacancy.setEmployer_id(jsonObject.getString("employer_id"));
                vacancy.setJobDescription(jsonObject.getString("descriptions"));
                vacancy.setResponsibilies(jsonObject.getString("resposibilities"));
                vacancy.setCompanyName(jsonObject.getString("companyName"));
                vacancy.setPostCode(jsonObject.getString("postCode"));
                vacancy.setJobPosted(jsonObject.getString("posted"));
                vacancy.setCity(jsonObject.getString("city"));
                vacancy.setType(jsonObject.getString("type"));

                vacancies.add(vacancy);

            } // end loop


            //return the new concrete list object
            return  vacancies;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end RegisterMessage method
}

