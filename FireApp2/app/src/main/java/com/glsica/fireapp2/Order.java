package com.glsica.fireapp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Order extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private EditText Description,Comment_Src,Comment_Dest,Receiver_Name,Receiver_Phone,Receiver_Address;
    private RadioGroup radioGroup;
    private RadioButton weightButton;
    private     AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        Description=findViewById(R.id.Description);
        Comment_Src=findViewById(R.id.Comment_Src);
        Comment_Dest=findViewById(R.id.Comment_Dest);
        Receiver_Name=findViewById(R.id.Receiver_Name);
        Receiver_Phone=findViewById(R.id.Receiver_Phone);
        Receiver_Address=findViewById(R.id.Receiver_Address);
        radioGroup=findViewById(R.id.radioGroup);
        builder= new AlertDialog.Builder(this);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void addOrder(View view) {
        if (Description.getText().toString().isEmpty())
        {
            Description.setError("Describe Product");
        }
        else if(Receiver_Name.getText().toString().isEmpty())
        {
            Receiver_Name.setError("Enter Name");
        }
        else if(Receiver_Phone.getText().toString().isEmpty())
        {
            Receiver_Phone.setError("Enter Name");
        }
        else if(Receiver_Address.getText().toString().isEmpty())
        {
            Receiver_Address.setError("Enter Name");
        }
        else {
            
            addOrderUser();
        }
    }
    private void addOrderUser()
    {
        final String phoneString = mCurrentUser.getPhoneNumber();
        final String descriptionString = Description.getText().toString();
        final String csrcString = Comment_Src.getText().toString();
        final String cdestString = Comment_Dest.getText().toString();
        final String addressString = Receiver_Address.getText().toString();
        final String rphoneString = "+91"+Receiver_Phone.getText().toString();
        final String nameString = Receiver_Name.getText().toString();
         weightButton=findViewById(radioGroup.getCheckedRadioButtonId());
        final String weightString = weightButton.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADDORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if(jsonObject.getString("order").equals("true"))
                            {

                                //Setting message manually and performing action on button click
                                builder.setMessage("Order Successfully Placed")
                                        .setCancelable(false)
                                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                                        onBackPressed();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                //Setting the title manually
                                alert.setTitle("Order Status");
                                alert.show();
                            }
                            //Snackbar.make(linearLayout,"Verified...",Snackbar.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();
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
                params.put("Weight",weightString);
                params.put("Description", descriptionString);
                params.put("Comment_Src", csrcString);
                params.put("Receiver_Name", nameString);
                params.put("Receiver_Phone", rphoneString);
                params.put("Receiver_Address", addressString);
                params.put("Comment_Dest",cdestString);
                params.put("Phone",phoneString);

                return params;
            }
        };


        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
