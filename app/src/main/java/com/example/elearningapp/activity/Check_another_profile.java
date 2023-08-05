package com.example.elearningapp.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Check_another_profile extends AppCompatActivity {
    private Spinner spinnerSex;
    private EditText editTextUser, editTextBirth, editTextPhoneNumber, editTextJob, editTextLevel;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_2);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();


        ImageView imageView = findViewById(R.id.imageView8);
        spinnerSex = findViewById(R.id.editTextText7);
        editTextUser = findViewById(R.id.editTextText);
        editTextBirth = findViewById(R.id.editTextText5);
        editTextPhoneNumber = findViewById(R.id.editTextText6);
        editTextJob = findViewById(R.id.editTextText8);
        editTextLevel = findViewById(R.id.editTextText9);
        editTextUser.setEnabled(false);
        editTextBirth.setEnabled(false);
        editTextPhoneNumber.setEnabled(false);
        editTextJob.setEnabled(false);
        editTextLevel.setEnabled(false);
        spinnerSex.setEnabled(false); // Ẩn Spinner khi mở trang
        // Tạo ArrayAdapter cho Spinner với hai tùy chọn "Nam" và "Nữ"
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Nam", "Nữ"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(spinnerAdapter);
        spinnerSex.setSelection(0);


        // Lấy thông tin người dùng hiện tại từ Firestore và điền vào các trường EditText
        if (currentUser != null) {
            DocumentReference userRef = firestore.collection("users").document(currentUser.getUid());
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String username = documentSnapshot.getString("name");
                    String birth = documentSnapshot.getString("birth");
                    String phonenumber = documentSnapshot.getString("phonenumber");
                    String sex = documentSnapshot.getString("sex");
                    String job = documentSnapshot.getString("job");
                    String level = documentSnapshot.getString("level");
                    String image = documentSnapshot.getString("image");
                    editTextUser.setText(username);
                    editTextBirth.setText(birth);
                    editTextPhoneNumber.setText(phonenumber);

                    // Chọn giới tính tương ứng trong Spinner
                    int position = spinnerAdapter.getPosition(sex);
                    spinnerSex.setSelection(position);

                    editTextJob.setText(job);
                    editTextLevel.setText(level);

                    if (image != null) {
                        Picasso.get().load(image).into(imageView);
                    }

                }
            });
        }


    }

//    private void ChangeProfile() {
//        isEditing = true;
//        editTextUser.setEnabled(true);
//        editTextBirth.setEnabled(true);
//        editTextPhoneNumber.setEnabled(true);
//        spinnerSex.setEnabled(true);
//        editTextJob.setEnabled(true);
//        editTextLevel.setEnabled(true);
//        buttonSave.setVisibility(View.VISIBLE);
//        confirmText.setVisibility(View.GONE);
//    }
//
//    private void saveUserProfile() {
//        editTextUser.setEnabled(false);
//        editTextBirth.setEnabled(false);
//        editTextPhoneNumber.setEnabled(false);
//        spinnerSex.setEnabled(false);
//        editTextJob.setEnabled(false);
//        editTextLevel.setEnabled(false);
//        buttonSave.setVisibility(View.GONE);
//        confirmText.setVisibility(View.VISIBLE);
//
//        String newUsername = editTextUser.getText().toString();
//        String newBirth = editTextBirth.getText().toString();
//        String newPhonenumber = editTextPhoneNumber.getText().toString();
//        String newSex = spinnerSex.getSelectedItem().toString();
//        String newJob = editTextJob.getText().toString();
//        String newLevel = editTextLevel.getText().toString();
//
//        if (currentUser != null) {
//            DocumentReference userRef = firestore.collection("users").document(currentUser.getUid());
//            userRef.get().addOnSuccessListener(documentSnapshot -> {
//                if (documentSnapshot.exists()) {
//                    String currentUsername = documentSnapshot.getString("name");
//                    String currentBirth = documentSnapshot.getString("birth");
//                    String currentPhoneNumber = documentSnapshot.getString("phonenumber");
//                    String currentSex = documentSnapshot.getString("sex");
//                    String currentJob = documentSnapshot.getString("job");
//                    String currentLevel = documentSnapshot.getString("level");
//
//                    if (!newUsername.equals(currentUsername) ||
//                            !newBirth.equals(currentBirth) || !newPhonenumber.equals(currentPhoneNumber) ||
//                            !newSex.equals(currentSex) || !newJob.equals(currentJob) ||
//                            !newLevel.equals(currentLevel)) {
//                        Map<String, Object> newData = new HashMap<>();
//                        newData.put("name", newUsername);
//                        newData.put("birth", newBirth);
//                        newData.put("phonenumber", newPhonenumber);
//                        newData.put("sex", newSex);
//                        newData.put("job", newJob);
//                        newData.put("level", newLevel);
//
//                        userRef.update(newData).addOnSuccessListener(aVoid -> {
//                            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
//                        }).addOnFailureListener(e -> {
//                            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                        });
//                    } else {
//                        Toast.makeText(this, "Không có thay đổi dữ liệu", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//}
}
