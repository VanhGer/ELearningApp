package com.example.elearningapp.activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

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
                    // Nếu textbox nhập email trống, hiển thị thông báo lỗi
                    Toast.makeText(Forget1Activity.this, "Hãy nhập email của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem email có tồn tại trên Firebase hay không
                auth.fetchSignInMethodsForEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean isEmailExists = !task.getResult().getSignInMethods().isEmpty();
                                if (isEmailExists) {
                                    // Nếu email tồn tại trên Firebase, thực hiện yêu cầu đặt lại mật khẩu
                                    resetPassword(emailAddress);
                                } else {
                                    // Nếu email không tồn tại trên Firebase, hiển thị thông báo lỗi
                                    Toast.makeText(Forget1Activity.this, "Email không tồn tại trên hệ thống", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
