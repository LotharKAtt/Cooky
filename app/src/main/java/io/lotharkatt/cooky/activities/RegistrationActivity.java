package io.lotharkatt.cooky.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.lotharkatt.cooky.R;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private Button buttonRegistration;
    private EditText editTextRegPassword, editTextRegEmail, editTextRegConfirmPassword;
    private FirebaseAuth auth;

    public RegistrationActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();

        editTextRegEmail = (EditText) findViewById(R.id.editTextRegEmail);
        editTextRegPassword = (EditText) findViewById(R.id.editTextRegEmail);
        editTextRegConfirmPassword = (EditText) findViewById(R.id.editTextRegConfirmPassword);


        buttonRegistration = (Button) findViewById(R.id.buttonRegister);
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextRegPassword.getText().toString() == editTextRegConfirmPassword.getText().toString()) {
                    Toast.makeText(RegistrationActivity.this, "Passwords are different", Toast.LENGTH_LONG).show();

                } else {
                    auth.createUserWithEmailAndPassword(editTextRegEmail.getText().toString(), editTextRegPassword.getText().toString())
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }


            }
        });
    }
}
