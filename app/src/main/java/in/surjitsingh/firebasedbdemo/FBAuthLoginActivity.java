package in.surjitsingh.firebasedbdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class FBAuthLoginActivity extends AppCompatActivity {

    TextView email, pass, goReg;
    String emailS, passS;
    Button submit;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbauthlogin);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailS = email.getText().toString();
                passS = pass.getText().toString();
                loginUser();
            }
        });
        goReg = findViewById(R.id.goReg);
        goReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( FBAuthLoginActivity.this, FBAuthActivity.class));
            }
        });

    }

    private void loginUser() {
        if (TextUtils.isEmpty(emailS)) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailS).matches()) {
            email.setError("Enter valid email");
            email.requestFocus();
        } else if (TextUtils.isEmpty(passS)) {
            pass.setError("Please enter password");
            pass.requestFocus();
        } else if (passS.length() < 6) {
            pass.setError("Password should have minimum 6 character");
            pass.requestFocus();
        } else {
            auth.signInWithEmailAndPassword(emailS,passS).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        Toast.makeText(FBAuthLoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(FBAuthLoginActivity.this, "EXPqq ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(FBAuthLoginActivity.this, "EXP "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(FBAuthLoginActivity.this, "FirebaseAuthInvalidCredentialsException", Toast.LENGTH_SHORT).show();
                        }
                        if (task.getException() instanceof FirebaseAuthEmailException) {
                            Toast.makeText(FBAuthLoginActivity.this, "FirebaseAuthEmailException", Toast.LENGTH_SHORT).show();
                        }
                        if (task.getException() instanceof FirebaseAuthActionCodeException) {
                            Toast.makeText(FBAuthLoginActivity.this, "FirebaseAuthActionCodeException", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
