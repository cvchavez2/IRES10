package edu.utep.cs.cs4330.ires10;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

public class MyAsyncTask extends AsyncTask<Object,String,String> {

    private Context context;
    String data = null;
    String url_port = "http://129.108.18.45/sec-report/";

    public MyAsyncTask(Context context) {  // can take other params if needed
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            if (params[0].equals("alert")) {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("latitude", params[1]);
                postDataParams.put("longitude", params[2]);
                postDataParams.put("timestamp", params[3]);

                return HTTPHandler.sendPost(url_port + "/alert", postDataParams);
            }
            else if(params[0].equals("GET")){
                //GET Request
                return data = HTTPHandler.sendGet(url_port + "/reports?category=" + params[1] ); // params[1] contains the report type; to query all reports '/^'
            }
            else {
                // POST Request for all other type of reports
                JSONObject postDataParams = putInJSON(params);
                return HTTPHandler.sendPost(url_port + "/allReports", postDataParams); // params[0] contain the type of report
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            data = s;
//            Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    private JSONObject putInJSON(Object...params){
        JSONObject postDataParams = null;
        try {
            postDataParams = new JSONObject();
            postDataParams.put("category", params[0]);
            postDataParams.put("latitude", params[1]);
            postDataParams.put("longitude", params[2]);
            postDataParams.put("timestamp", params[3]);
            postDataParams.put("incident", params[4]);
            postDataParams.put("description", params[5]);
            return postDataParams;
        } catch (Exception e){
            Toast.makeText(context.getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return postDataParams;
    }

}