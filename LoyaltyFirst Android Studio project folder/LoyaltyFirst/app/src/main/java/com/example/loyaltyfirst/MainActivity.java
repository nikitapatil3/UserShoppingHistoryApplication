package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    String cid="";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText=findViewById(R.id.editText);
        EditText editText2=findViewById(R.id.editText2);
        Button button=findViewById(R.id.button);
        progressBar=findViewById(R.id.progressBar6);
        progressBar.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                String username=editText.getText().toString();
                String password=editText2.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                String url="http://10.0.2.2:8080/loyaltyfirst/login?user="+username+"&pass="+password;
                StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.setVisibility(View.GONE);
                        if(s.trim().equals("No")){
                            Toast.makeText(MainActivity.this,"Invalid Username or Password", Toast.LENGTH_LONG).show();
                        }
                        else{
                            String[] result=s.trim().split(":");
                            cid=result[1];
                            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                            intent.putExtra("cid",cid);
                            startActivity(intent);
                        }
                    }
                },null);
                setRequestPolicy(request);
                queue.add(request);
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
