package com.example.elearningapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserProfile extends AppCompatActivity {

    private EditText editTextEmail, editTextUser,editTextBirth,editTextPhoneNumber,editTextSex,editTextJob,editTextLevel;
    private Button buttonSave;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
//        editTextEmail = findViewById(R.id.editTextText4);
        editTextUser = findViewById(R.id.editTextText);
        editTextBirth = findViewById(R.id.editTextText5);
        editTextPhoneNumber = findViewById(R.id.editTextText6);
        editTextSex = findViewById(R.id.editTextText7);
        editTextJob = findViewById(R.id.editTextText8);
        editTextLevel = findViewById(R.id.editTextText9);
        buttonSave = findViewById(R.id.Button_update);

        // Lấy thông tin người dùng hiện tại từ Firestore và điền vào các trường EditText
        if (currentUser != null) {
            DocumentReference userRef = firestore.collection("users").document(currentUser.getUid());
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
//                    String email = documentSnapshot.getString("email");
                    String username = documentSnapshot.getString("name");
                    String birth = documentSnapshot.getString("birth");
                    String phonenumber = documentSnapshot.getString("phonenumber");
                    String sex = documentSnapshot.getString("sex");
                    String job = documentSnapshot.getString("job");
                    String level = documentSnapshot.getString("level");
//                    editTextEmail.setText(email);
                    editTextUser.setText(username);
                    editTextBirth.setText(birth);
                    editTextPhoneNumber.setText(phonenumber);
                    editTextSex.setText(sex);
                    editTextJob.setText(job);
                    editTextLevel.setText(level);
                }
            });
        }

        // Xử lý sự kiện khi người dùng nhấp vào nút Lưu
        buttonSave.setOnClickListener(view -> saveUserProfile());
    }

    private void saveUserProfile() {
//        String newEmail = editTextEmail.getText().toString();
        String newUsername = editTextUser.getText().toString();
        String newBirth = editTextBirth.getText().toString();
        String newPhonenumber = editTextPhoneNumber.getText().toString();
        String newSex = editTextSex.getText().toString();
        String newJob = editTextJob.getText().toString();
        String newLevel = editTextLevel.getText().toString();

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (currentUser != null) {
            // Lấy tham chiếu đến tài liệu người dùng hiện tại trong Firestore
            DocumentReference userRef = firestore.collection("users").document(currentUser.getUid());

            // Lấy dữ liệu hiện tại của người dùng từ Firestore
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
//                    String currentEmail = documentSnapshot.getString("email");
                    String currentUsername = documentSnapshot.getString("name");
                    String currentBirth = documentSnapshot.getString("birth");
                    String currentPhoneNumber = documentSnapshot.getString("phoneNumber");
                    String currentSex = documentSnapshot.getString("sex");
                    String currentJob = documentSnapshot.getString("job");
                    String currentLevel = documentSnapshot.getString("level");


                    // Kiểm tra giá trị của newEmail, newUsername, currentEmail và currentUsername
//                    Log.d("Debug", "newEmail: " + newEmail);
//                    Log.d("Debug", "newUsername: " + newUsername);
//                    Log.d("Debug", "currentEmail: " + currentEmail);
//                    Log.d("Debug", "currentUsername: " + currentUsername);

                    // Kiểm tra xem dữ liệu mới có thay đổi so với dữ liệu hiện tại hay không
                    if ( !newUsername.equals(currentUsername) ||
                            !newBirth.equals(currentBirth) || !newPhonenumber.equals(currentPhoneNumber) ||
                            !newSex.equals(currentSex) || !newJob.equals(currentJob) ||
                            !newLevel.equals(currentLevel) ) {
                        // Nếu dữ liệu mới khác dữ liệu hiện tại, thêm trường email và name mới vào Map để cập nhật vào Firestore
                        Map<String, Object> newData = new HashMap<>();
//                        newData.put("email", newEmail);
                        newData.put("name", newUsername);
                        newData.put("birth", newBirth);
                        newData.put("phonenumber", newPhonenumber);
                        newData.put("sex", newSex);
                        newData.put("job", newJob);
                        newData.put("level", newLevel);
                        // Tiến hành cập nhật thông tin người dùng mới vào Firestore
                        userRef.update(newData)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                    // Thành công
                                    // Hiển thị thông báo hoặc thực hiện các hành động khác khi cập nhật thành công
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Cập nhật thất bại ", Toast.LENGTH_SHORT).show();
                                    // Xử lý lỗi nếu việc cập nhật thất bại
                                });
                    } else {
                        // Dữ liệu không thay đổi, không cần thực hiện cập nhật
                        Toast.makeText(this, "Không có thay đổi dữ liệu", Toast.LENGTH_SHORT).show();
                        // Hiển thị thông báo hoặc thực hiện các hành động khác nếu cần
                    }
                }
            });
        }
    }
}
