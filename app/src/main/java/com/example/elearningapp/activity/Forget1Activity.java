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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget1Activity extends AppCompatActivity {

    EditText email;
    Button resetPasswordButton;
    ImageButton back;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget1);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextEmail);
        resetPasswordButton = findViewById(R.id.buttonSendEmail);
        back = findViewById(R.id.imageButton2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = email.getText().toString().trim();

                if (emailAddress.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), Forget2Activity.class);
                    startActivity(intent);
                    finish();

                } else {
                    resetPassword(emailAddress);
                }
            }
        });
    }

    private void resetPassword(String emailAddress) {
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), Forget2Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Forget1Activity.this, "Lỗi: Không thể gửi yêu cầu đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
