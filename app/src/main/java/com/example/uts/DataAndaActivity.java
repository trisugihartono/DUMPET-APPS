package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataAndaActivity extends AppCompatActivity {

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    TextView namalengkap, alamat, email,nama, email2;
    ImageView logo;
    RelativeLayout kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_anda);

        getUsernameLocal();
        kembali = findViewById(R.id.kembali);

        namalengkap = findViewById(R.id.namalengkap);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);
        email2 = findViewById(R.id.email2);
        nama = findViewById(R.id.nama);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                namalengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                nama.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                alamat.setText(dataSnapshot.child("alamat").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                email2.setText(dataSnapshot.child("email").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(DataAndaActivity.this,HomeActivity.class);
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