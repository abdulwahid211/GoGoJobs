package jobapp.mdabdulwahid.jobapp.JSONparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;

/**
 * Created by mdAbdulWahid on 28/01/2016.
 */
// implements interface JSONParser
public class Job_SeekerJSONParser implements JSONParser {

    //
    public Object parseFeed(String content, String jsonChildAttribute) {

        try {
            // de-serialise the JSON data
            JSONObject jsonRootObject = new JSONObject(content);
            // instantiate new object
            Job_seeker jobSeeker = new Job_seeker();
            // get the JSON Object data set of key value pairs
            // helps to hold each set of key value pairs
            JSONArray jsonArray = jsonRootObject.optJSONArray(jsonChildAttribute);
            for (int i = 0; i < jsonArray.length(); i++) {
              // iterate through each set of key value pairs
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // for each key that matches, insert their value
                // to the relevant attributes
                jobSeeker.setId(jsonObject.getString("id"));
                jobSeeker.setUsername(jsonObject.getString("username"));
                jobSeeker.setMobile(jsonObject.getString("mobile"));
                jobSeeker.setTelephone(jsonObject.getString("telephone"));
                jobSeeker.setEmail(jsonObject.getString("email"));
                jobSeeker.setFirstName(jsonObject.getString("firstName"));
                jobSeeker.setLastName(jsonObject.getString("lastName"));
                jobSeeker.setPostCode(jsonObject.getString("postCode"));


            }
            //return the new concrete object
            return jobSeeker;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end RegisterMessage method
}
