package test;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestSMS {
    public static void main(String[] args) {
        try {
            String url = "https://api.twilio.com/2010-04-01/Accounts/AC7c3327f98229f727c2cf9ac7b6780faf/Messages.json";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method to POST
            con.setRequestMethod("POST");

            // Set the request headers
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Authorization", "Basic QUM3YzMzMjdmOTgyMjlmNzI3YzJjZjlhYzdiNjc4MGZhZjo5NjQwMDMyNWFmOTdlN2Q1ODNmNDMxNDk4ZGE0NTYwYg==");

            // Enable input/output streams
            con.setDoOutput(true);

            // Prepare the request data
            String data = "Body=Hello World!&To=+84349616610&From=+12562902470";
            byte[] postData = data.getBytes(StandardCharsets.UTF_8);

            // Write the request data to the output stream
            try (OutputStream os = con.getOutputStream()) {
                os.write(postData);
            }

            // Get the response code
            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // TODO: Handle the response as needed (read the response body, etc.)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
