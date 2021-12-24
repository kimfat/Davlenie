package com.example.davlenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button nextButn;
    public int status = 0;
    public String ans = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButn = (Button) findViewById(R.id.nextButton);
        EditText m1 = (EditText)findViewById(R.id.m1);
        EditText m2 = (EditText)findViewById(R.id.m2);
        RequestQueue queue = Volley.newRequestQueue(this);
        nextButn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "http://abashin.ru/cgi-bin/ru/tests/burnout";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(MainActivity.this, "Запрос выполнен!",Toast.LENGTH_LONG).show();
                                        ans = response;

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                        ans = error.toString();
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("m2", m2.getText().toString());
                                params.put("m1", m1.getText().toString());
                                params.put("sex", "1");
                                params.put("year", "2000");
                                params.put("month", "11");
                                params.put("day", "27");
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String>  params = new HashMap<String, String>();

                                Integer k = 40 + m2.length() + m1.length();
                                params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
                                params.put("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                params.put("Content-Length", k.toString());

                                return params;
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                                String utf8String = null;
                                try {
                                    utf8String = new String(response.data, "UTF-8");
                                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));

                                } catch (UnsupportedEncodingException e) {
                                    return Response.error(new ParseError(e));
                                }
                            }
                        };
                        queue.add(stringRequest);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                intent.putExtra("lname", ans);
                                startActivity(intent);
                            }
                        }, 2000);
                    }

                }
        );

    }

}