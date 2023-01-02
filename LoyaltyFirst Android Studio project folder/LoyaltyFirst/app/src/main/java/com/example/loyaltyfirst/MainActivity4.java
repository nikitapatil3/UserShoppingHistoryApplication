package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    String cid = "";
    Spinner spinner;
    ArrayList<String> transaction_refs;
    RequestQueue queue;
    TextView textView9;
    TextView textView10;
    String[] result;
    TableLayout stk;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent=getIntent();
        cid=intent.getStringExtra("cid");
        textView9=findViewById(R.id.textView9);
        textView10=findViewById(R.id.textView10);
        stk = findViewById(R.id.tableLayout);
        textView9.setText("");
        textView10.setText("");
        progressBar=findViewById(R.id.progressBar3);
        queue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        String url="http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressBar.setVisibility(View.GONE);
                result=s.trim().split("#");
                initSpinner(result);
            }
        }, null);
        setRequestPolicy(request);
        queue.add(request);
    }

    public void initSpinner(String[] result) {
        spinner = findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        transaction_refs = new ArrayList<String>();
        transaction_refs.add("Select");
        for(int itr=0; itr< result.length; itr++) {
            transaction_refs.add(result[itr].split(",")[0]);
        }
        spinner.setAdapter(new ArrayAdapter<String>(MainActivity4.this, android.R.layout.simple_spinner_dropdown_item, transaction_refs));
    }

    public void initTable(String[] result) {
        TableRow tableRow1 = new TableRow(this);
        TextView textView1 = new TextView(this);
        textView1.setText(" Prod.Name              ");
        tableRow1.addView(textView1);

        TextView textView2 = new TextView(this);
        textView2.setText(" Quantity                 ");
        tableRow1.addView(textView2);

        TextView textView3 = new TextView(this);
        textView3.setText(" Points               ");
        tableRow1.addView(textView3);

        stk.addView(tableRow1);

        for (int itr =0; itr< result.length; itr++){
            String[] row = result[itr].split(",");
            String prodName = row[2];
            String pPoints = row[3];
            String quantity = row[4];
            TableRow tableRow = new TableRow(this);
            TextView prodNameView = new TextView(this);
            prodNameView.setText(" " + prodName);
            tableRow.addView(prodNameView);

            TextView quantityView = new TextView(this);
            quantityView.setText(" " + quantity);
            tableRow.addView(quantityView);

            TextView pPointsView = new TextView(this);
            pPointsView.setText(" " + pPoints);
            tableRow.addView(pPointsView);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        textView9.setText("");
        textView10.setText("");
        stk.removeAllViewsInLayout();
        if (!"Select".equals(transaction_refs.get(i))) {
            progressBar.setVisibility(View.VISIBLE);
            String transactionDetailsUrl = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?tref=" + transaction_refs.get(i);
            StringRequest request1 = new StringRequest(Request.Method.GET, transactionDetailsUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    progressBar.setVisibility(View.GONE);
                    String[] result = s.trim().split("#");
                    String[] row = result[0].split(",");
                    String tDate = row[0];
                    String tPoints = row[1];
                    textView9.setText(tDate);
                    textView10.setText(tPoints + " Points");
                    initTable(result);
                }
            }, null);
            setRequestPolicy(request1);
            queue.add(request1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}