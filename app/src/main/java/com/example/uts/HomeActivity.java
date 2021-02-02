package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    RelativeLayout btn_sign_out, dataanda;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    TextView txt_username, saldo;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUsernameLocal();
        btn_sign_out = findViewById(R.id.btn_sign_out);
        dataanda = findViewById(R.id.dataanda);

        txt_username = findViewById(R.id.txt_username);
        logo = findViewById(R.id.logo);
        saldo = findViewById(R.id.saldo);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txt_username.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                saldo.setText("Rp. " + dataSnapshot.child("user_balance").getValue().toString());
//                Picasso.with(HomeActivity.this)
//                        .load(dataSnapshot.child("url_photo_profile")
//                                .getValue().toString()).centerCrop().fit()
//                        .into(logo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                Intent gotohome = new Intent(HomeActivity.this,SignInActivity.class);
                startActivity(gotohome);
                finish();
            }
        });

        dataanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(HomeActivity.this,DataAndaActivity.class);
                startActivity(gotohome);
                finish();
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}