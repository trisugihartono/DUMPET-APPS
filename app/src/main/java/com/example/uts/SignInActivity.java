package com.example.uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    TextView register;
    Button btnSignin;

    EditText username2, password2;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        register = findViewById(R.id.register);
        btnSignin = findViewById(R.id.signin);
        username2 = findViewById(R.id.username);
        password2 = findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregister = new Intent(SignInActivity.this, RegisterOneActivity.class);
                startActivity(gotoregister);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnSignin.setEnabled(false);
                btnSignin.setText("Loading ...");

                final String username = username2.getText().toString();
                final String password = password2.getText().toString();

                if(username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    btnSignin.setEnabled(true);
                    btnSignin.setText("SIGN IN");
                }
                else {
                    if(password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                        btnSignin.setEnabled(true);
                        btnSignin.setText("SIGN IN");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(username);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    if(password.equals(passwordFromFirebase)){
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, username2.getText().toString());
                                        editor.apply();


                                        Intent gotohome = new Intent(SignInActivity.this,HomeActivity.class);
                                        startActivity(gotohome);

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_SHORT).show();
                                        btnSignin.setEnabled(true);
                                        btnSignin.setText("SIGN IN");
                                    }


                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Username tidak ada!", Toast.LENGTH_SHORT).show();
                                    btnSignin.setEnabled(true);
                                    btnSignin.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

    }
}