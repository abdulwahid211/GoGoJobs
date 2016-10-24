package jobapp.mdabdulwahid.jobapp.JSONparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jobapp.mdabdulwahid.jobapp.Objects.LoginMessage;

/**
 * Created by mdAbdulWahid on 26/01/2016.
 */
public class LoginMessageJSONParser implements JSONParser {

    public Object parseFeed(String content, String jsonChildAttribute) {

        try {
            // de-serialise the JSON data
            JSONObject jsonRootObject = new JSONObject(content);
            // instantiate new object
            LoginMessage loginMessage = new LoginMessage();
            JSONArray jsonArray = jsonRootObject.optJSONArray(jsonChildAttribute);
            // get the JSON Object data set of key value pairs
            // helps to hold each set of key value pairs
            for (int i = 0; i < jsonArray.length(); i++) {
                // iterate through each set of key value pairs
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // for each key that matches, insert their value
                // to the relevant attributes
                loginMessage.setAccess(Integer.parseInt(jsonObject.getString("Access")));
                loginMessage.setMessage(jsonObject.getString("Message"));
                loginMessage.setAccount_type(jsonObject.getString("Account_type"));

            }
            //return the new concrete object
            return loginMessage;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end RegisterMessage method
}
