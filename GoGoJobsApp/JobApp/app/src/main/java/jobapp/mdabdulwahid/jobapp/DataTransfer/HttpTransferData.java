package jobapp.mdabdulwahid.jobapp.DataTransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This code has been obtained from Lynda Tutorials
 * Reference:
 * https://www.lynda.com/Android-tutorials/Connecting-Android-Apps-RESTful-Web-Services/163757-2.html
 *
 * helps to outputstreams the TransPackages list of map pair values to the webservice
 * and receives the input of stream from the PHP response
 *
 */
public class HttpTransferData {
    private String urlLink;
    // TransferPackage class that contains key and values to be transfer
    // to the web service script PHP
    private TransferPackage transferPackage;

    // constructor for the URL and TransferPackage object
    public HttpTransferData(String _url, TransferPackage transferData) throws Exception {

        this.transferPackage = transferData;

        this.urlLink = _url;

    }

    // function to output the stream of bytes to the web service Script PHP
    // and to receive the inout of stream of bytes from PHP which are returned as values
    // full implementation was taken from Lyndas Tutorial
    public String DataTransfer() {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            // open up the URL connection
            URL url = new URL(this.urlLink);
            //helps to post and receive over the web through HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //to receive input of bytes
            InputStreamReader reciveBytes;
            // to output the stream of bytes to the
            // restful web service script
            OutputStreamWriter sendBytes;
            // Set the connection to POST request
            connection.setRequestMethod("POST");

            // if the connection request is POST
            // then output everything
            if (connection.getRequestMethod().equals("POST")) {
                connection.setDoOutput(true); //enable output
                //initialise the OutStream object
                sendBytes = new OutputStreamWriter(connection.getOutputStream());
                // send the PostData stream of bytes to the world wide web
                sendBytes.write(transferPackage.getEncodedParams());
                sendBytes.flush(); // ensure everything has gone through
            }
            // receiving the input of stream from the PHP web service script
            reciveBytes = new InputStreamReader(connection.getInputStream());
            // reads the values to bytes
            reader = new BufferedReader(reciveBytes);

            String line;
            // converts them into String
            while ((line = reader.readLine()) != null) {
                //append the new line to the string
                sb.append(line + "\n");
            }


        } catch (Exception e) {

        } finally

        {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        // send the String of values to the web service script
        return sb.toString();

    } // end recieve data method


}  // end of class
