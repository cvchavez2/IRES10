package edu.utep.cs.cs4330.ires10;


import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportListFragment extends Fragment {

    ListView listView;
    ReportListManager listManager = new ReportListManager();
    JSONParser parser = new JSONParser();

    public ReportListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_list, container, false);
        GETRequestToGetReports();
        listView = (ListView)view.findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), listManager.getReports());
        listView.setAdapter(customAdapter);
        // Inflate the layout for this fragment
        return view;
    }

    public void GETRequestToGetReports(){
        try {
            String jsonString = new MyAsyncTask(getActivity()).execute("GET").get();
            Object obj = parser.parse(jsonString);
            JSONArray jsonArray = (JSONArray) obj;
            for(int i = 0; i<jsonArray.size(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Long timestampInMillis = Long.parseLong((String)jsonObject.get("timestamp"));
                String incident = (String) jsonObject.get("incident");
                double latitude = Double.valueOf((String)jsonObject.get("latitude"));
                double longitude = Double.valueOf((String)jsonObject.get("longitude"));
                String description = (String) jsonObject.get("description");
                Report report = new Report(timestampInMillis, incident, latitude, longitude, description);
                listManager.addReport(report);
            }
            Log.d("UTEPCS", "TEST");

            Log.d("UTEPCS", jsonString);
        } catch (ExecutionException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static class CustomAdapter extends ArrayAdapter<Report> {

        Activity context;
        List<Report> reportsList;

        public CustomAdapter(Activity context, List<Report>reportsList){
            super(context, R.layout.report_listview, reportsList);
            this.context = context;
            this.reportsList = reportsList;
        }

        public Report getItem(int position) {
            return reportsList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View row = inflater.inflate(R.layout.report_listview, null, true);
            Report report = reportsList.get(position);

            TextView textView = row.findViewById(R.id.dateTextV);
            textView.setText(toDate(report.getDate()));
            textView = row.findViewById(R.id.incidentTextV);
            textView.setText(report.getIncident());
            textView = row.findViewById(R.id.locationTextV);
            textView.setText(geocoder(report.getLatitude(), report.getLongitude()));
            textView = row.findViewById(R.id.descriptionTextV);
            textView.setText(report.getDescription());
            return row;

        }

        private String geocoder(double latitude, double longitude){
            try {
                // Geocoder translates coordinates into a street name
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                return addressList.get(0).getAddressLine(0);
            }catch(Exception e) {
                e.printStackTrace();
            }
            return "Could not find address";
        }
        private String toDate(long time) {
            return new SimpleDateFormat("MM/dd/yyyy").format(new Date(time));
        }
    }
}
