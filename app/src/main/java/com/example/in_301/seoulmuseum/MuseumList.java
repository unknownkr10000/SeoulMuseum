package com.example.in_301.seoulmuseum;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MuseumList extends AppCompatActivity {

    private ArrayList<MuseumListItem> data = null;
    MuseumListAdapter museumListAdapter;
    String name = "";
    String kind = "";
    String address = "";

    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_list);

        listView = (ListView) findViewById(R.id.listview);
        museumListAdapter = new MuseumListAdapter();

        data = new ArrayList<>();

        String strUrl = "http://openapi.seoul.go.kr:8088/674c6151497275643831724b6d4151/xml/SearchCulturalFacilitiesDetailService/1/790/";
        DownloadWebpageTask task = new DownloadWebpageTask();
        task.execute(strUrl);
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String)downloadUrl((String)urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        protected void onPostExecute(String result) {

            String no = null;
            String name = null;
            String kind = null;
            String address = null;
            String tel = null;
            String homepage = null;
            String closeday = null;
            String gpsX = null;
            String gpsY = null;
            String desc = null;

            boolean bSet_no = false;
            boolean bSet_name = false;
            boolean bSet_kind = false;
            boolean bSet_address = false;
            boolean bSet_tel = false;
            boolean bSet_homepage = false;
            boolean bSet_closeday = false;
            boolean bSet_gpsX = false;
            boolean bSet_gpsY = false;
            boolean bSet_desc = false;

            final MuseumListAdapter museumListAdapter = new MuseumListAdapter();

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
                        if (tag_name.equals("FAC_CODE"))
                            bSet_no = true;
                        if (tag_name.equals("FAC_NAME"))
                            bSet_name = true;
                        if (tag_name.equals("CODENAME"))
                            bSet_kind = true;
                        if (tag_name.equals("ADDR"))
                            bSet_address = true;
                        if (tag_name.equals("PHNE"))
                            bSet_tel = true;
                        if (tag_name.equals("HOMEPAGE"))
                            bSet_homepage = true;
                        if (tag_name.equals("CLOSEDAY"))
                            bSet_closeday = true;
                    } else if(eventType == XmlPullParser.TEXT) {
                        if (bSet_no) {
                            no = xpp.getText();
                            bSet_no = false;
                        }
                        if (bSet_name) {
                            name = xpp.getText();
                            bSet_name = false;
                        }
                        if (bSet_kind) {
                            kind = xpp.getText();
                            bSet_kind = false;
                        }
                        if (bSet_address) {
                            address = xpp.getText();
                            bSet_address = false;
                        }
                        if (bSet_tel) {
                            tel = xpp.getText();
                            bSet_tel = false;
                        }
                        if (bSet_homepage) {
                            homepage = xpp.getText();
                            bSet_homepage = false;
                        }
                        if (bSet_closeday) {
                            closeday = xpp.getText();
                            bSet_closeday = false;
                            museumListAdapter.addItem(no, name, kind, address, tel, homepage, closeday);
                            Log.v("test", no + " " + name + " " + kind + " " + address + " " + tel);
                        }
                    } else if(eventType == XmlPullParser.END_TAG) {
                        listView.setAdapter(museumListAdapter);

                        // listView의 ItemClickListener
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            /**
                             * ListView의 Item을 Click 할 때 수행할 동작
                             * @param parent 클릭이 발생한 AdapterView.
                             * @param view 클릭 한 AdapterView 내의 View(Adapter에 의해 제공되는 View).
                             * @param position 클릭 한 Item의 position
                             * @param id 클릭 된 Item의 Id
                             */
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // adapter.getItem(position)의 return 값은 Object 형
                                // 실제 Item의 자료형은 CustomDTO 형이기 때문에
                                // 형변환을 시켜야 getResId() 메소드를 호출할 수 있습니다.
                                String tNo = ((MuseumListItem)museumListAdapter.getItem(position)).getNo();
                                String tName = ((MuseumListItem)museumListAdapter.getItem(position)).getName();
                                String tKind = ((MuseumListItem)museumListAdapter.getItem(position)).getKind();
                                String tAddress = ((MuseumListItem)museumListAdapter.getItem(position)).getAddress();
                                String tTel = ((MuseumListItem)museumListAdapter.getItem(position)).getTel();
                                String tHompage = ((MuseumListItem)museumListAdapter.getItem(position)).getHomepage();
                                String tCloseday = ((MuseumListItem)museumListAdapter.getItem(position)).getCloseday();

                                // new Intent(현재 Activity의 Context, 시작할 Activity 클래스)
                                Intent intent = new Intent(MuseumList.this, MuseumDetail.class);
                                // putExtra(key, value)
                                intent.putExtra("tag_no", tNo);
                                intent.putExtra("tag_name", tName);
                                intent.putExtra("tag_kind", tKind);
                                intent.putExtra("tag_address", tAddress);
                                intent.putExtra("tag_tel", tTel);
                                intent.putExtra("tag_homepage", tHompage);
                                intent.putExtra("tag_closeday", tCloseday);
                                startActivity(intent);
                            }
                        });
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
    }
}

