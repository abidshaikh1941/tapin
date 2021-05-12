package com.glsica.fireapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
  //  private FirebaseDatabase database;
  //  private DatabaseReference myRef;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        linearLayout=findViewById(R.id.mainLayout);




    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(this,login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser==null)
        {
        sendUserToLogin();
        }

    }


    public void logout(View view) {
        Toast.makeText(this,"logging out..",Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        sendUserToLogin();
    }

    public void openAbout(View view) {
        Intent aboutIntent = new Intent(this,About.class);
        startActivity(aboutIntent);

    }

    public void openAddOrder(View view) {
        Intent orderIntent = new Intent(this,Order.class);
        startActivity(orderIntent);
    }

    public void openHistory(View view) {
        Snackbar.make(linearLayout,"Order's History",Snackbar.LENGTH_SHORT).show();
    }

    public void openTrack(View view) {
        Snackbar.make(linearLayout,"Track Delivery",Snackbar.LENGTH_SHORT).show();
    }

    public void openProfile(View view) {
        Snackbar.make(linearLayout,"User's Profile",Snackbar.LENGTH_SHORT).show();
        Intent profileIntent = new Intent(this,Profile.class);
        startActivity(profileIntent);
    }
}
