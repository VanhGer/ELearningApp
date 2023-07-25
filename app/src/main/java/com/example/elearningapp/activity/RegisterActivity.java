package com.example.elearningapp.activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.elearningapp.CreateDatabase;
import com.example.elearningapp.R;
import com.example.elearningapp.databinding.ActivityMainBinding;
import com.example.elearningapp.fragment.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextUser, editTextCfPasword;
    Button buttonRegister;
    FirebaseAuth mAuth;
    TextView Login;
    ImageButton back;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.nhapEmail);
        editTextPassword = findViewById(R.id.password);
        editTextUser =findViewById(R.id.editTextName);
        editTextCfPasword = findViewById(R.id.cfpassword);

        buttonRegister = findViewById(R.id.btn_Register);

        Login = findViewById(R.id.loginNow);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                String email, password, user, cfpassword;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                cfpassword = String.valueOf(editTextCfPasword.getText());
                user = String.valueOf(editTextUser.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập email của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(cfpassword)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(user)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập tên của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Tài khoản đã được tạo.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Đăng ký không thành công",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }
}