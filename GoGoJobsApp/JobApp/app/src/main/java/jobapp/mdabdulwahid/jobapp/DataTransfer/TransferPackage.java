package jobapp.mdabdulwahid.jobapp.DataTransfer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * This code has been obtained from Lynda Tutorials
 * Reference:
 * https://www.lynda.com/Android-tutorials/Connecting-Android-Apps-RESTful-Web-Services/163757-2.html
 *
 *
 * Allows to add values in hashMap to be posted to the webservice script php
 * encodes the values to be send over world wide web
 *
 */
public class TransferPackage {


    // contains key which carries a value to be posted to the web server
    private HashMap<String, String> postData = new HashMap<String, String>();


    // public void method to add new key and value
    public void addNewPost(String key, String value) {
        postData.put(key, value);
    }

    // functions ensures collection of postData
    // that values are URLEncoded
    // this implementation was taken from Lynda Tutorial
    // it separates each item by adding ampersand
    // when key and value are added
    // it would send String of a list of postData
    public String getEncodedParams() {

        // allows String value to be appended
        StringBuilder sb = new StringBuilder();
        // loop through the postData hashMap
        // enhanced loop
        // for each key
        for (String key : postData.keySet()) {
            String value = null;
            try {
                // helps to encode the value into bytes for unsafe characters
                // using UTF-8 as the encoding scheme helps to convert String of characters
                // into hex code
                // hence it helps prevents from cross site attack, SQL
                // injection and may direct our web application into some unpredicted output
                // Reference:
                // http://www.mkyong.com/java/how-to-encode-a-url-string-or-form-parameter-in-java/
                value = URLEncoder.encode(postData.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //if the string contains a value add a ampersand
            // so the php script can break them down
            if (sb.length() > 0) {
                sb.append("&");
            }
            //then key to the value
            sb.append(key + "=" + value);
        } // end loop
        // send the String of list
        // to be posted to the PHP script
        return sb.toString();
    }
}
