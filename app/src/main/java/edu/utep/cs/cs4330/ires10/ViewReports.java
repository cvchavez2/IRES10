//package edu.utep.cs.cs4330.ires10;
//
//import android.app.Activity;
//import android.location.Address;
//import android.location.Geocoder;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import android.widget.Toast;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.util.Locale;
//import java.util.concurrent.ExecutionException;
//
//public class ViewReports extends AppCompatActivity {
//
//    ListView listView;
//    ReportListManager listManager = new ReportListManager();
//    JSONParser parser = new JSONParser();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_reports);
//        setTitle("View Reports");
//
//        GETRequestToGetReports();
//        listView = findViewById(R.id.listView);
//        CustomAdapter customAdapter = new CustomAdapter(this, listManager.getReports());
//        listView.setAdapter(customAdapter);
//
//    }
//
//    public void GETRequestToGetReports(){
//        try {
//            String jsonString = new MyAsyncTask(this).execute("GET").get();
//            Object obj = parser.parse(jsonString);
//            JSONArray jsonArray = (JSONArray) obj;
//            for(int i = 0; i<jsonArray.size(); i++){
//                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                Long timestampInMillis = Long.parseLong((String)jsonObject.get("timestamp"));
//                String incident = (String) jsonObject.get("incident");
//                double latitude = Double.valueOf((String)jsonObject.get("latitude"));
//                double longitude = Double.valueOf((String)jsonObject.get("longitude"));
//                String description = (String) jsonObject.get("description");
//                Report report = new Report(timestampInMillis, incident, latitude, longitude, description);
//                listManager.addReport(report);
//            }
//            Log.d("UTEPCS", "TEST");
//
//            Log.d("UTEPCS", jsonString);
//        } catch (ExecutionException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class CustomAdapter extends ArrayAdapter<Report> {
//
//        Activity context;
//        List<Report> reportsList;
//
//        public CustomAdapter(Activity context, List<Report>reportsList){
//            super(context, R.layout.report_listview, reportsList);
//            this.context = context;
//            this.reportsList = reportsList;
//        }
//
//        public Report getItem(int position) {
//            return reportsList.get(position);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = context.getLayoutInflater();
//            View row = inflater.inflate(R.layout.report_listview, null, true);
//            Report report = reportsList.get(position);
//
//            TextView textView = row.findViewById(R.id.dateTextV);
//            textView.setText(toDate(report.getDate()));
//            textView = row.findViewById(R.id.incidentTextV);
//            textView.setText(report.getIncident());
//            textView = row.findViewById(R.id.locationTextV);
//            textView.setText(geocoder(report.getLatitude(), report.getLongitude()));
//            textView = row.findViewById(R.id.descriptionTextV);
//            textView.setText(report.getDescription());
//            return row;
//
//        }
//
//        private String geocoder(double latitude, double longitude){
//            try {
//                // Geocoder translates coordinates into a street name
//                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                return addressList.get(0).getAddressLine(0);
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//            return "Could not find address";
//        }
//        private String toDate(long time) {
//            return new SimpleDateFormat("MM/dd/yyyy").format(new Date(time));
//        }
//    }
//
//}
