package ua.lviv.iot.phoenix.noq_cashier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq_cashier.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    public void onStart() {
        super.onStart();
        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, BaseActivity.class));
            //Useful.onAuthSuccess(this, mAuth.getCurrentUser());
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);


        Button mSignInButton = findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(this);

        mEmailField = findViewById(R.id.login_email);
        mPasswordField = findViewById(R.id.login_password);

        mPasswordField.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mSignInButton.performClick();
                return true;
            }
            return false;
        });
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (@NonNull Task<AuthResult> task) -> {
                    Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, BaseActivity.class));
                        finish();
                        return;
                    }
                    Toast.makeText(this, "Sign In Failed:"
                            +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private boolean validateForm() {
        boolean result = false;
        mEmailField.setError(null);
        mPasswordField.setError(null);
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
        } else if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
        } else {
            result = true;
        }

        return result;
    }


    @Override
    public void onClick(View v) {
        signIn();
    }
}