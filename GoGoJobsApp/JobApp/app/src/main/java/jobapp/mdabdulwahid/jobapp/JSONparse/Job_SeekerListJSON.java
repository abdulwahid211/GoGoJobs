package jobapp.mdabdulwahid.jobapp.JSONparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jobapp.mdabdulwahid.jobapp.Objects.Job_seeker;

/**
 * Created by mdAbdulWahid on 13/02/2016.
 */
public class Job_SeekerListJSON  implements JSONParser {

    public Object parseFeed(String content, String jsonChildAttribute) {

        ArrayList<Job_seeker> Job_seekers = new ArrayList<Job_seeker>();
        int i = 0;
        try {
            // de-serialise the JSON data
            JSONObject jsonRootObject = new JSONObject(content);

            JSONArray jsonArray = jsonRootObject.optJSONArray(jsonChildAttribute);
            // get the JSON Object data set of key value pairs
            // helps to hold each set of key value pairs
            for (; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Job_seeker jobSeeker = new Job_seeker();
                // instantiate new object
                // for each key that matches, insert their value
                // to the relevant attributes
                jobSeeker.setId(jsonObject.getString("id"));
                jobSeeker.setUsername(jsonObject.getString("username"));
                jobSeeker.setFirstName(jsonObject.getString("firstName"));
                jobSeeker.setLastName(jsonObject.getString("lastName"));
                jobSeeker.setTelephone(jsonObject.getString("telephone"));
                jobSeeker.setMobile(jsonObject.getString("mobile"));
                jobSeeker.setEmail(jsonObject.getString("email"));
                jobSeeker.setPostCode(jsonObject.getString("postCode"));
                // add that particular object to the list
                Job_seekers.add(jobSeeker);

            } // end loop


            //return the new concrete list object
            return  Job_seekers;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end RegisterMessage method
}

