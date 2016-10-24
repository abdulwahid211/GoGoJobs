package jobapp.mdabdulwahid.jobapp.JSONparse;

/**
 * Created by mdAbdulWahid on 02/02/2016.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jobapp.mdabdulwahid.jobapp.Objects.Employer;

/**
 * Created by mdAbdulWahid on 28/01/2016.
 */
public class EmployerJSONParser implements JSONParser {

    public Object parseFeed(String content, String jsonChildAttribute) {

        try {

            // de-serialise the JSON data
            JSONObject jsonRootObject = new JSONObject(content);
            // instantiate new object
            Employer employer = new Employer();
            // get the JSON Object data set of key value pairs
            // helps to hold each set of key value pairs
            JSONArray jsonArray = jsonRootObject.optJSONArray(jsonChildAttribute);
            for (int i = 0; i < jsonArray.length(); i++) {
                // iterate through each set of key value pairs
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // for each key that matches, insert their value
                // to the relevant attributes
                employer.setId(jsonObject.getString("id"));
                employer.setUsername(jsonObject.getString("username"));
                employer.setCompanyName(jsonObject.getString("companyName"));
                employer.setFirstName(jsonObject.getString("firstName"));
                employer.setLastName(jsonObject.getString("secondName"));
                employer.setMobile(jsonObject.getString("mobile"));
                employer.setTelephone(jsonObject.getString("telephone"));
                employer.setEmail(jsonObject.getString("email"));
                employer.setFirstLineAddress(jsonObject.getString("firstLineAddress"));
                employer.setLastLineAddress(jsonObject.getString("lastLineAddress"));
                employer.setTown(jsonObject.getString("town"));
                employer.setPostCode(jsonObject.getString("postCode"));


            }
            //return the new concrete object
            return employer;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end RegisterMessage method
}

