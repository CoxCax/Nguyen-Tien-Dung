package com.example.test5.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test5.R;
import com.example.test5.database.UserDAO;
import com.example.test5.model.UserModel;


public class SignInActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnSignIn;
    TextView tvSignup;
    UserDAO userDB;
    UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignup = findViewById(R.id.tvSignUp);
        userDB = new UserDAO(SignInActivity.this);
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        checkLoginWithDatabase();
    }

    private void checkLoginWithDatabase() {
        btnSignIn.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                edtEmail.setError("Email cannot be empty");
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                edtPassword.setError("Password cannot be empty");
                return;
            }

            userModel = userDB.getInforuser(email, pass);
            if (userModel != null && userModel.getEmail() != null) {
                Intent intent = new Intent(SignInActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME_ACCOUNT", email);
                bundle.putInt("ID_ACCOUNT", userModel.getId());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SignInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
