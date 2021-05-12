package com.glsica.fireapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private EditText date,month,year,l1,l2,l3,l4;
    private TextView num,msgTv,bool;
    private EditText a,b,c;     /*a->Firstname,b->lastname,c->Address*/
    private ImageView verified;

    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    private RadioButton genderRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        a=findViewById(R.id.a);
        b=findViewById(R.id.b);
        c=findViewById(R.id.c);
        bool=findViewById(R.id.bool);
        date=findViewById(R.id.date);
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        msgTv=findViewById(R.id.msg);
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);
        verified=findViewById(R.id.verified);
        progressBar=findViewById(R.id.progressBar);
        radioGroup=findViewById(R.id.radioGroup);
        num=findViewById(R.id.num_details);
        num.setText(mCurrentUser.getPhoneNumber());


    }

    public void update(View view) {
        String fn=a.getText().toString();
        String ln=b.getText().toString();
        if (fn.isEmpty())
        {
            a.setError("Enter Firstname");
        }
        else if (ln.isEmpty())
        {
            b.setError("Enter Lastname");
        }
        else if(c.getText().toString().isEmpty())
        {
            c.setError("Enter Address");
        }
        else if (date.getText().toString().isEmpty()|month.getText().toString().isEmpty()|year.getText().toString().isEmpty() )
        {
            year.setError("Invalid Date");
        }
        else if (l1.getText().toString().isEmpty()|l2.getText().toString().isEmpty()|l3.getText().toString().isEmpty()|l4.getText().toString().isEmpty())
        {
            if(bool.getText().equals("false")) {
                l4.setError("Invalid License");
            }
            else {
                if (bool.getText().equals("true")) {
                    String dob = date.getText().toString() + "-" + month.getText().toString() + "-" + year.getText().toString();
                    String license = l1.getHint().toString().toUpperCase() + "-" + l2.getHint().toString() + "-" + l3.getHint().toString() + "-" + l4.getHint().toString();
                    updateDriver(dob, license);
                }
            }
        }
        else {
            String dob =date.getText().toString()+"-"+month.getText().toString()+"-"+year.getText().toString();
            String license =l1.getText().toString().toUpperCase()+"-"+l2.getText().toString()+"-"+l3.getText().toString()+"-"+l4.getText().toString();
            updateDriver(dob,license);
        }
    }

    /*Set Details of a User in mysql database*/
    private void updateDriver(String dob,String license) {


        final String firstnameString = a.getText().toString();
        final String lastnameString = b.getText().toString();
        final String addressString = c.getText().toString();
        final String dobString = dob;
        final String licenseString = license;
        genderRadioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        final String genderString = genderRadioButton.getText().toString();
        final String phoneString = mCurrentUser.getPhoneNumber();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATEDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            //Snackbar.make(linearLayout,"Verified...",Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"Details Updated" , Toast.LENGTH_SHORT).show();
                            sendUserToHome();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Firstname", firstnameString);
                params.put("Lastname", lastnameString);
                params.put("Address", addressString);
                params.put("Gender", genderString);
                params.put("Dob", dobString);
                params.put("License", licenseString);
                params.put("Phone", phoneString);
                return params;
            }
        };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void sendUserToHome()
    {
        Intent homeIntent = new Intent(this,MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser==null) {
            Intent loginIntent = new Intent(this,Login.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        else{
            registerUser();
        }
    }

    /*Register user in mysql database*/
    private void registerUser() {

        final String fbString = mCurrentUser.getUid().trim();
        final String phoneString = mCurrentUser.getPhoneNumber().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (msg.equals("exists")) {
                                //Snackbar.make(linearLayout,"Verified...",Snackbar.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.VISIBLE);
                                msgTv.setText("Fetching details");

                                getUserDetails();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Fb_Id", fbString);
                params.put("Phone", phoneString);
                return params;
            }
        };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    /*Get User Details from mysql*/
    private void getUserDetails() {


        final String phoneString = mCurrentUser.getPhoneNumber().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GETDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //String msg = jsonObject.getString("message");
                            a.setText(jsonObject.getString("Firstname"));
                            b.setText(jsonObject.getString("Lastname"));
                            c.setText(jsonObject.getString("Address"));
                            String d=jsonObject.getString("Dob");
                            String l=jsonObject.getString("License");
                            if(!d.equals("Dob")) {
                                date.setText(""+d.charAt(0) + d.charAt(1));
                                month.setText(""+d.charAt(3) + d.charAt(4));
                                year.setText(""+d.charAt(6) + d.charAt(7) + d.charAt(8) + d.charAt(9));

                            }
                            if(!l.equals("License")) {

                                if (jsonObject.getString("Verified").equals("1")) {
                                    bool.setText("true");
                                  l1.setEnabled(false);
                                    l2.setEnabled(false);
                                    l3.setEnabled(false);
                                    l4.setEnabled(false);
                                  verified.setVisibility(View.VISIBLE);
                                    l1.setHint("" + l.charAt(0) + l.charAt(1));
                                    l2.setHint("" + l.charAt(3) + l.charAt(4));
                                    l3.setHint("" + l.charAt(6) + l.charAt(7) + l.charAt(8) + l.charAt(9));
                                    l4.setHint("" + l.charAt(11) + l.charAt(12) + l.charAt(13) + l.charAt(14) + l.charAt(15) + l.charAt(16) + l.charAt(17));
                                    l1.setHintTextColor(Color.parseColor("#000000"));
                                    l2.setHintTextColor(Color.parseColor("#000000"));
                                    l3.setHintTextColor(Color.parseColor("#000000"));
                                    l4.setHintTextColor(Color.parseColor("#000000"));
                                }
                                else
                                {
                                    l1.setText(""+l.charAt(0) + l.charAt(1));
                                    l2.setText(""+l.charAt(3) + l.charAt(4));
                                    l3.setText(""+l.charAt(6) + l.charAt(7) + l.charAt(8) + l.charAt(9));
                                    l4.setText(""+ l.charAt(11) + l.charAt(12) + l.charAt(13) + l.charAt(14) + l.charAt(15) + l.charAt(16)+l.charAt(17));
                                }
                            }

                            progressBar.setVisibility(View.INVISIBLE);
                            msgTv.setText("Welcome Back!!");
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Phone", phoneString);
                return params;
            }
        };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}
