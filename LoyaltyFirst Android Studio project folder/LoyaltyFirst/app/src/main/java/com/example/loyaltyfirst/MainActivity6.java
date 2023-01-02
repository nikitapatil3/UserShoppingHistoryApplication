package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    String cid = "";
    Spinner spinner;
    Button button;
    ArrayList<String> transaction_refs;
    RequestQueue queue;
    TextView textView15;
    TextView textView16;
    TextView textView17;
    String[] result;
    String familyId;
    String percentagePoints;
    String totalNoOfPoints;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Intent intent=getIntent();
        cid=intent.getStringExtra("cid");
        textView15=findViewById(R.id.textView15);
        textView16=findViewById(R.id.textView16);
        textView17=findViewById(R.id.textView17);
        button = findViewById(R.id.button7);
        textView15.setText("");
        textView16.setText("");
        textView17.setText("");
        button.setVisibility(View.INVISIBLE);
        progressBar=findViewById(R.id.progressBar2);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int npoints = (Integer.valueOf(totalNoOfPoints) * Integer.valueOf(percentagePoints))/100;
                progressBar.setVisibility(View.VISIBLE);
                String url="http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?fid="+familyId + "&cid="+cid+"&npoints="+npoints;
                StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),s.trim(),Toast.LENGTH_LONG).show();
                    }
                }, null);
                setRequestPolicy(request);
                queue.add(request);
            }
        });
    }

    public void initSpinner(String[] result) {
        spinner = findViewById(R.id.spinner3);
        spinner.setOnItemSelectedListener(this);
        transaction_refs = new ArrayList<String>();
        transaction_refs.add("Select");
        for(int itr=0; itr< result.length; itr++) {
            transaction_refs.add(result[itr].split(",")[0]);
        }
        spinner.setAdapter(new ArrayAdapter<String>(MainActivity6.this, android.R.layout.simple_spinner_dropdown_item, transaction_refs));
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
        textView15.setText("");
        textView16.setText("");
        textView17.setText("");
        button.setVisibility(View.INVISIBLE);
        if (!"Select".equals(transaction_refs.get(i))) {
            progressBar.setVisibility(View.VISIBLE);
            String transactionDetailsUrl = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid=" + cid +"&tref=" + transaction_refs.get(i);
            StringRequest request1 = new StringRequest(Request.Method.GET, transactionDetailsUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    progressBar.setVisibility(View.GONE);
                    String[] result = s.trim().split(",");
                    familyId = result[0];
                    percentagePoints = result[1];
                    totalNoOfPoints = result[2];
                    textView15.setText("TXN Points:    " + totalNoOfPoints);
                    textView16.setText("Family ID:    " + familyId);
                    textView17.setText("Family Percent:    "+percentagePoints);
                    button.setVisibility(View.VISIBLE);
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