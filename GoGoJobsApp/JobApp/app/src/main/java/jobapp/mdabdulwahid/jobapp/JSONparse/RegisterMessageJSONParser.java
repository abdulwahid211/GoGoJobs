package jobapp.mdabdulwahid.jobapp.JSONparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jobapp.mdabdulwahid.jobapp.Objects.RegisterMessage;

/**
 * Created by mdAbdulWahid on 26/01/2016.
 */
public class RegisterMessageJSONParser implements JSONParser {

    public  Object parseFeed(String content, String jsonChildAttribute) {

        try {
            // de-serialise the JSON data
            JSONObject jsonRootObject = new JSONObject(content);
            // instantiate new object
            RegisterMessage registerMessage = new RegisterMessage();
            // get the JSON Object data set of key value pairs
            // helps to hold each set of key value pairs
            JSONArray jsonArray = jsonRootObject.optJSONArray(jsonChildAttribute);
            for (int i = 0; i < jsonArray.length(); i++) {
                // iterate through each set of key value pairs
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // for each key that matches, insert their value
                // to the relevant attributes
                registerMessage.setAccess(Integer.parseInt(jsonObject.getString("Access")));
                registerMessage.setMessage(jsonObject.getString("Message"));

            }
            //return the new concrete object
            return registerMessage;



        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end RegisterMessage method
}
