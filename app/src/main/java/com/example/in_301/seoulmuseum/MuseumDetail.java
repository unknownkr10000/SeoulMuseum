package com.example.in_301.seoulmuseum;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MuseumDetail extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);

        final Intent intent = getIntent();

        TextView tvName = (TextView) findViewById(R.id.name);
        TextView tvKind = (TextView) findViewById(R.id.kind);
        TextView tvAddress = (TextView) findViewById(R.id.tv_museumdetail_address);
        final TextView tvTel = (TextView) findViewById(R.id.tv_museumdetail_tel);
        TextView tvHomepage = (TextView) findViewById(R.id.tv_museumdetail_homepage);
        TextView tvCloseday = (TextView) findViewById(R.id.tv_museumdetail_closeday);

        final String no = intent.getStringExtra("tag_no");
        String kind = intent.getStringExtra("tag_kind");
        String name = intent.getStringExtra("tag_name");
        String address = intent.getStringExtra("tag_address");
        final String tel = intent.getStringExtra("tag_tel");
        final String homepage = intent.getStringExtra("tag_homepage");
        String closeday = intent.getStringExtra("tag_closeday");

        tvName.setText(name);
        tvKind.setText(kind);
        tvAddress.setText(address);
        tvTel.setText(tel);
        tvHomepage.setText(homepage);
        tvCloseday.setText(closeday);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number;
                Intent it;
                switch (view.getId()) {
                    case R.id.imageview_museumdetail_link:
                        it = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                        startActivity(it);
                        break;
                    case R.id.textview_museumdetail_link:
                        it = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                        startActivity(it);
                        break;
                    case R.id.imageview_museumdetail_map:
                        it = new Intent(MuseumDetail.this, DetailMapActivity.class);
                        it.putExtra("tag_no", no);
                        startActivity(it);
                        break;
                    case R.id.textview_museumdetail_map:
                        it = new Intent(MuseumDetail.this, DetailMapActivity.class);
                        it.putExtra("tag_no", no);
                        startActivity(it);
                        break;
                    case R.id.imageview_museumdetail_call:
                        number = Uri.parse("tel:" + tel);
                        it = new Intent(Intent.ACTION_DIAL, number);
                        // Intent intent = new Intent(Intent.ACTION_CALL, number);
                        try {
                            startActivity(it);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.textview_museumdetail_call:
                        number = Uri.parse("tel:" + tel);
                        it = new Intent(Intent.ACTION_DIAL, number);
                        // Intent intent = new Intent(Intent.ACTION_CALL, number);
                        try {
                            startActivity(it);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        ImageView ivMap = (ImageView) findViewById(R.id.imageview_museumdetail_map);
        ImageView ivLink = (ImageView) findViewById(R.id.imageview_museumdetail_link);
        ImageView ivCall = (ImageView) findViewById(R.id.imageview_museumdetail_call);
        TextView tvMap = (TextView) findViewById(R.id.textview_museumdetail_map);
        TextView tvLink = (TextView) findViewById(R.id.textview_museumdetail_link);
        TextView tvCall = (TextView) findViewById(R.id.textview_museumdetail_call);
        ivMap.setOnClickListener(onClickListener);
        ivLink.setOnClickListener(onClickListener);
        ivCall.setOnClickListener(onClickListener);
        tvMap.setOnClickListener(onClickListener);
        tvLink.setOnClickListener(onClickListener);
        tvCall.setOnClickListener(onClickListener);

    }
}
