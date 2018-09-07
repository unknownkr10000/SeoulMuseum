package com.example.in_301.seoulmuseum;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String no = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        no = intent.getStringExtra("tag_no");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String key = "http://openapi.seoul.go.kr:8088/674c6151497275643831724b6d4151/xml/SearchCulturalFacilitiesDetailService/1/790/";
        String strUrl = key + no;

        DownloadWebpageTask task = new DownloadWebpageTask();
        task.execute(strUrl);
    }

    public class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String)downloadUrl((String)urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        protected void onPostExecute(String result) {

            String name = null;
            String address = null;
            String gpsX = null;
            String gpsY = null;

            boolean bSet_name = false;
            boolean bSet_address = false;
            boolean bSet_gpsX = false;
            boolean bSet_gpsY = false;

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    } else if(eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if (tag_name.equals("FAC_NAME"))
                            bSet_name = true;
                        if (tag_name.equals("ADDR"))
                            bSet_address = true;
                        if (tag_name.equals("X_COORD"))
                            bSet_gpsX = true;
                        if (tag_name.equals("Y_COORD"))
                            bSet_gpsY = true;
                    } else if(eventType == XmlPullParser.TEXT) {
                        if (bSet_name) {
                            name = xpp.getText();
                            bSet_name = false;
                        }
                        if (bSet_address) {
                            address = xpp.getText();
                            bSet_address = false;
                        }
                        if (bSet_gpsX) {
                            gpsX = xpp.getText();
                            bSet_gpsX = false;
                        }
                        if (bSet_gpsY) {
                            gpsY = xpp.getText();
                            bSet_gpsY = false;

                            displayMuseum(gpsX, gpsY, name, address);
                        }
                    } else if(eventType == XmlPullParser.END_TAG) {
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
            }
        }

        private String downloadUrl(String myurl) throws IOException {

            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while((line = bufreader.readLine()) != null) {
                    page += line;
                }

                return page;
            } finally {
                conn.disconnect();
            }
        }

        private void displayMuseum(String gpsX, String gpsY, String name, String address) {
            double latitude;
            double longitude;
            LatLng LOC;

            latitude = Double.parseDouble(gpsX);
            longitude = Double.parseDouble(gpsY);
            LOC = new LatLng(latitude, longitude);
            Marker mk1 = mMap.addMarker(new MarkerOptions()
                    .position(LOC)
                    .title(name)
                    .snippet(address));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
        }
    }
}
