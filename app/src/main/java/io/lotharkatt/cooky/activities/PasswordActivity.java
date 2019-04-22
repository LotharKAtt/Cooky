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
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    private Button buttonResetPassword;
    private EditText editTextForgottenPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        auth = FirebaseAuth.getInstance();

        editTextForgottenPassword = (EditText) findViewById(R.id.editEmailForgottenPassword);
        buttonResetPassword = (Button) findViewById(R.id.buttonResetPassword);
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(editTextForgottenPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PasswordActivity.this, "Your password has been successfully reset, check your mailbox.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(PasswordActivity.this, "Something wrong happen please try it again.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });

    }
}
