package ua.lviv.iot.phoenix.noq_cashier.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.models.Cafe;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText emailEdit, nameEdit, locatonEdit, iconEdit;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_hiden);

        emailEdit = findViewById(R.id.enter_email);
        locatonEdit = findViewById(R.id.enter_location);
        nameEdit = findViewById(R.id.enter_name);
        iconEdit = findViewById(R.id.enter_icon);

        Button signUpButton = findViewById(R.id.sign_up);
        signUpButton.setOnClickListener(this);

        iconEdit.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signUpButton.performClick();
                return true;
            }
            return false;
        });
    }
    private void signUp() {
        Log.d(TAG, "signUp");
        Task<AuthResult> res = mAuth.createUserWithEmailAndPassword(
                emailEdit.getText().toString(), emailEdit.getText().toString());
        res.addOnCompleteListener(SignUpActivity.this, (@NonNull Task<AuthResult> task) -> {
            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
            if (!task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "Sign Up Failed:"
                        +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            user = task.getResult().getUser();
            user = (user != null) ? user : mAuth.getCurrentUser();
            user.updatePassword(user.getUid());
            Cafe cafe = new Cafe(nameEdit.getText().toString(), locatonEdit.getText().toString(),
                    emailEdit.getText().toString(), iconEdit.getText().toString(), user.getUid());
            new Useful(this).onAuthSuccess( user, cafe);
            finish();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        signUp();
    }
}
