package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {
    String cid="";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        cid=intent.getStringExtra("cid");
        TextView textView4=findViewById(R.id.textView4);
        TextView textView5=findViewById(R.id.textView5);
        RequestQueue queue= Volley.newRequestQueue(this);
        progressBar=findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.VISIBLE);
        String url="http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid="+cid;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressBar.setVisibility(View.GONE);
                String[] result=s.trim().split(",");
                String name=result[0];
                String points=result[1];
                textView4.setText(name);
                textView5.setText(points);
            }
        },null);
        setRequestPolicy(request);
        queue.add(request);

        ImageView imageView=findViewById(R.id.imageView);
        String url2="http://10.0.2.2:8080/loyaltyfirst/images/"+cid+".jpeg";
        ImageRequest request2=new ImageRequest(url2, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        },0,0,null,null);
        queue.add(request2);

        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });

        Button button3=findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MainActivity4.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });

        Button button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MainActivity5.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });

        Button button5=findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MainActivity6.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });

        Button button6=findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    private void setRequestPolicy(StringRequest request) {
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }
}