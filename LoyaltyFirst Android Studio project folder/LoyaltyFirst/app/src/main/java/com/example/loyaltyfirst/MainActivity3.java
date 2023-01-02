package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {
    String cid = "";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent=getIntent();
        cid=intent.getStringExtra("cid");
        progressBar=findViewById(R.id.progressBar);
        RequestQueue queue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        String url="http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressBar.setVisibility(View.GONE);
                String[] result=s.trim().split("#");
                initTable(result);
            }
        }, null);
       setRequestPolicy(request);
        queue.add(request);

    }

    public void initTable(String[] result) {
        TableLayout stk = (TableLayout) findViewById(R.id.main_table);
        TableRow tableRow1 = new TableRow(this);
        TextView textView1 = new TextView(this);
        textView1.setText(" TXN Ref              ");
        tableRow1.addView(textView1);

        TextView textView2 = new TextView(this);
        textView2.setText(" Date                 ");
        tableRow1.addView(textView2);

        TextView textView3 = new TextView(this);
        textView3.setText(" Points               ");
        tableRow1.addView(textView3);

        TextView textView4 = new TextView(this);
        textView4.setText(" Total                ");
        tableRow1.addView(textView4);

        stk.addView(tableRow1);

        View line = new View(this);
        line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 3));
        line.setBackgroundColor(Color.rgb(51, 51, 51));

        stk.addView(line);
        for (int itr =0; itr< result.length; itr++){
            String[] row = result[itr].split(",");
            String tref = row[0];
            String date = row[1];
            String tpoints = row[2];
            String amount = row[3];
            TableRow tableRow = new TableRow(this);
            TextView trefView = new TextView(this);
            trefView.setText(" " + tref);
            tableRow.addView(trefView);

            TextView dateView = new TextView(this);
            dateView.setText(" " + date);
            tableRow.addView(dateView);

            TextView pointsView = new TextView(this);
            pointsView.setText(" " + tpoints);
            tableRow.addView(pointsView);

            TextView amountView = new TextView(this);
            amountView.setText(" $" + amount);
            tableRow.addView(amountView);

            stk.addView(tableRow);
        }
        View endline = new View(this);
        endline.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 3));
        endline.setBackgroundColor(Color.rgb(0, 188, 212));
        stk.addView(endline);
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