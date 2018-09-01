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

public class FBAuthActivity extends AppCompatActivity {

    TextView uname, name, email, pass, confPass, goLogin;
    String unameS, nameS, emailS, passS, confPassS;
    Button submit;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbauth);

        auth = FirebaseAuth.getInstance();

        uname = findViewById(R.id.uname);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        confPass = findViewById(R.id.confpass);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unameS = uname.getText().toString();
                emailS = email.getText().toString();
                nameS = name.getText().toString();
                passS = pass.getText().toString();
                confPassS = confPass.getText().toString();
                createUser();
            }
        });
        goLogin = findViewById(R.id.goLogin);

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FBAuthActivity.this, FBAuthLoginActivity.class));
            }
        });

    }

    private void createUser() {
        if (TextUtils.isEmpty(nameS)) {
            name.setError("Please enter name");
            name.requestFocus();
        } else if (TextUtils.isEmpty(emailS)) {
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
        } else if (!passS.equals(confPassS)) {
            confPass.setError("Password & Confirm password doesnot match");
            confPass.requestFocus();
        } else {
            auth.createUserWithEmailAndPassword(emailS, passS).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        Toast.makeText(FBAuthActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(FBAuthActivity.this, "EXPqq ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(FBAuthActivity.this, "EXP "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(FBAuthActivity.this, "FirebaseAuthInvalidCredentialsException", Toast.LENGTH_SHORT).show();
                        }
                        if (task.getException() instanceof FirebaseAuthEmailException) {
                            Toast.makeText(FBAuthActivity.this, "FirebaseAuthEmailException", Toast.LENGTH_SHORT).show();
                        }
                        if (task.getException() instanceof FirebaseAuthActionCodeException) {
                            Toast.makeText(FBAuthActivity.this, "FirebaseAuthActionCodeException", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
