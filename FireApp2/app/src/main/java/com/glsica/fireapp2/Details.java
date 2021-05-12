package com.glsica.fireapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private TextView num,msgTv;
    private EditText a,b,c;     /*a->Firstname,b->lastname,c->Address*/
    private EditText date,month,year;
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
        date=findViewById(R.id.date);
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        msgTv=findViewById(R.id.msg);
        progressBar=findViewById(R.id.progressBar);
        radioGroup=findViewById(R.id.radioGroup);
        num=findViewById(R.id.num_details);
        num.setText(mCurrentUser.getPhoneNumber());

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
                           if(!d.equals("Dob")) {
                               date.setText(""+d.charAt(0) + d.charAt(1));
                                month.setText(""+d.charAt(3) + d.charAt(4));
                                year.setText(""+d.charAt(6) + d.charAt(7) + d.charAt(8) + d.charAt(9));

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

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser==null) {
            Intent loginIntent = new Intent(this,login.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        else{
            registerUser();
        }
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
        else {
            String dob =date.getText().toString()+"-"+month.getText().toString()+"-"+year.getText().toString();
            updateUser(dob);
        }
    }

    /*Set Details of a User in mysql database*/
    private void updateUser(String dob) {


            final String firstnameString = a.getText().toString();
            final String lastnameString = b.getText().toString();
            final String addressString = c.getText().toString();
            final String dobString = dob;
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
                    params.put("Phone", phoneString);
                    return params;
                }
            };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    /*get details of a user from mysql database*/
    private void getDetails() {

        final String phoneString = mCurrentUser.getPhoneNumber().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GETDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (msg.equals("exists")) {
                                //Snackbar.make(linearLayout,"Verified...",Snackbar.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),"Verified" , Toast.LENGTH_SHORT).show();

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


    public void uploadImage(View view) {
        Toast.makeText(this,"Upload Profile",Toast.LENGTH_SHORT).show();
    }
}
