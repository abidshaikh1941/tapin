package com.glsica.fireapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Profile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private TextView num;
    private TextView a,b,c,gender,dob;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        a=findViewById(R.id.a);
        b=findViewById(R.id.b);
        c=findViewById(R.id.c);
        gender=findViewById(R.id.gender);
        dob=findViewById(R.id.dob);

        num=findViewById(R.id.num_details);
        num.setText(mCurrentUser.getPhoneNumber());
        progressBar=findViewById(R.id.progressBar);

        getUserDetails();
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
                            gender.setText(jsonObject.getString("Gender"));
                            dob.setText(jsonObject.getString("Dob"));
                            //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
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
    }

    public void back(View view) {
        onBackPressed();
    }

    public void openEdit(View view) {
        Intent editIntent = new Intent(this,Test.class);
        startActivity(editIntent);
    }
}
