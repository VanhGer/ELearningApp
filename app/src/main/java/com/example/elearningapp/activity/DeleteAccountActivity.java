package com.example.elearningapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mAuth = FirebaseAuth.getInstance();

        Button deleteAccountButton = findViewById(R.id.btn_DeleteAccount);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa tài khoản");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản? Mọi dữ liệu sẽ bị mất và không thể khôi phục.");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteAccount();
            }
        });
        builder.setNegativeButton("Hủy", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void deleteAccount() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                // Xóa tài khoản thành công
                                Toast.makeText(DeleteAccountActivity.this, "Tài khoản đã được xóa",
                                        Toast.LENGTH_SHORT).show();
                                deleteUserDataFromFirestore(user.getUid());
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
        } else {
            // Người dùng chưa đăng nhập hoặc đã đăng xuất
            Toast.makeText(DeleteAccountActivity.this, "Bạn cần đăng nhập để xóa tài khoản",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUserDataFromFirestore(String userId) {
        FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firestore", "Dữ liệu đã được xóa thành công từ Firestore");
                        } else {
                            Log.e("Firestore", "Xóa dữ liệu không thành công từ Firestore: " + task.getException());
                        }
                    }
                });
    }
}
