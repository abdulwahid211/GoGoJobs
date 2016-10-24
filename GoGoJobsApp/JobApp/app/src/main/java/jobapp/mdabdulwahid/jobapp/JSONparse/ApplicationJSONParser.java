package jobapp.mdabdulwahid.jobapp.JSONparse;

/**
 * Created by mdAbdulWahid on 02/02/2016.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jobapp.mdabdulwahid.jobapp.Objects.ApplicationForm;

/**
 * Created by mdAbdulWahid on 28/01/2016.
 */
public class ApplicationJSONParser implements JSONParser {

    public Object parseFeed(String content, String jsonChildAttribute) {

        try {
            // de-serialise the JSON data
            JSONObject jsonRootObject = new JSONObject(content);
            // instantiate new object
            ApplicationForm applicationForm = new ApplicationForm();
            JSONArray jsonArray = jsonRootObject.optJSONArray(jsonChildAttribute);
            // get the JSON Object data set of key value pairs
            // helps to hold each set of key value pairs
            for (int i = 0; i < jsonArray.length(); i++) {
                // iterate through each set of key value pairs
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // for each key that matches, insert their value
                // to the relevant attributes
                applicationForm.setEligableUK(jsonObject.getString("eligableUK"));
                applicationForm.setNoticePeriod(jsonObject.getString("noticePeriod"));
                applicationForm.setPreExperience(jsonObject.getString("preExperience"));
                applicationForm.setAnswer(jsonObject.getString("userAnser"));


            }

            return applicationForm;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end RegisterMessage method
}

