package com.example.elearningapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangeUserProfile extends AppCompatActivity {

    private Spinner spinnerSex;
    private ImageView avatar ;
    private EditText editTextUser, editTextBirth, editTextPhoneNumber, editTextJob, editTextLevel;
    private Button buttonSave;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private TextView confirmText;

    private boolean isEditing = false;
    private static final int MY_REQUEST_CODE = 10;
    private Uri selectedImageUri; // Thêm biến này để lưu Uri của ảnh mới



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        avatar = findViewById(R.id.imageView8);
        spinnerSex = findViewById(R.id.editTextText7);
        editTextUser = findViewById(R.id.editTextText);
        editTextBirth = findViewById(R.id.editTextText5);
        editTextPhoneNumber = findViewById(R.id.editTextText6);
        editTextJob = findViewById(R.id.editTextText8);
        editTextLevel = findViewById(R.id.editTextText9);
        buttonSave = findViewById(R.id.Button_update);
        confirmText = findViewById(R.id.textView65);
        buttonSave.setVisibility(View.GONE);
        editTextUser.setEnabled(false);
        editTextBirth.setEnabled(false);
        editTextPhoneNumber.setEnabled(false);
        editTextJob.setEnabled(false);
        editTextLevel.setEnabled(false);
        spinnerSex.setEnabled(false); // Ẩn Spinner khi mở trang
        confirmText.setVisibility(View.VISIBLE);

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
                    String imageUriString = documentSnapshot.getString("image");
                    editTextUser.setText(username);
                    editTextBirth.setText(birth);
                    editTextPhoneNumber.setText(phonenumber);

                    // Chọn giới tính tương ứng trong Spinner
                    int position = spinnerAdapter.getPosition(sex);
                    spinnerSex.setSelection(position);

                    editTextJob.setText(job);
                    editTextLevel.setText(level);

                    if (imageUriString != null) {
                        Uri imageUri = Uri.parse(imageUriString);
                        Picasso.get().load(imageUriString).into(avatar);
                    }

                }
            });
        }

        confirmText.setOnClickListener(view -> ChangeProfile());

        buttonSave.setOnClickListener(view -> saveUserProfile());
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "FloatingActionButton clicked");
                onClickPermission();


            }
        });

    }

    private void ChangeProfile() {
        isEditing = true;
        editTextUser.setEnabled(true);
        editTextBirth.setEnabled(true);
        editTextPhoneNumber.setEnabled(true);
        spinnerSex.setEnabled(true);
        editTextJob.setEnabled(true);
        editTextLevel.setEnabled(true);
        buttonSave.setVisibility(View.VISIBLE);
        confirmText.setVisibility(View.GONE);
    }

    private void saveUserProfile() {
        editTextUser.setEnabled(false);
        editTextBirth.setEnabled(false);
        editTextPhoneNumber.setEnabled(false);
        spinnerSex.setEnabled(false);
        editTextJob.setEnabled(false);
        editTextLevel.setEnabled(false);
        buttonSave.setVisibility(View.GONE);
        confirmText.setVisibility(View.VISIBLE);

        String newUsername = editTextUser.getText().toString();
        String newBirth = editTextBirth.getText().toString();
        String newPhonenumber = editTextPhoneNumber.getText().toString();
        String newSex = spinnerSex.getSelectedItem().toString();
        String newJob = editTextJob.getText().toString();
        String newLevel = editTextLevel.getText().toString();

        if (currentUser != null) {
            DocumentReference userRef = firestore.collection("users").document(currentUser.getUid());
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String currentImageUri = documentSnapshot.getString("image");

                    String currentUsername = documentSnapshot.getString("name");
                    String currentBirth = documentSnapshot.getString("birth");
                    String currentPhoneNumber = documentSnapshot.getString("phonenumber");
                    String currentSex = documentSnapshot.getString("sex");
                    String currentJob = documentSnapshot.getString("job");
                    String currentLevel = documentSnapshot.getString("level");

                    boolean hasChanges = !newUsername.equals(currentUsername) ||
                            !newBirth.equals(currentBirth) || !newPhonenumber.equals(currentPhoneNumber) ||
                            !newSex.equals(currentSex) || !newJob.equals(currentJob) ||
                            !newLevel.equals(currentLevel) || selectedImageUri != null;

                    if (hasChanges) {
                        Map<String, Object> newData = new HashMap<>();
                        if (selectedImageUri != null) {
                            String imageURL = selectedImageUri.toString();
                            newData.put("image", "https://i.imgur.com/BrHbOka.png");
                        } else {
                            newData.put("image", "https://i.imgur.com/BrHbOka.png");
                        }
                        newData.put("name", newUsername);
                        newData.put("birth", newBirth);
                        newData.put("phonenumber", newPhonenumber);
                        newData.put("sex", newSex);
                        newData.put("job", newJob);
                        newData.put("level", newLevel);

                        userRef.update(newData).addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(this, "Không có thay đổi dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void onClickPermission() {
        Log.d(TAG, "OnclickPermission clicked");

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        openGallery();
        return;
//        } else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            openGallery();
//        } else {
//            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
//            requestPermissions(permission, MY_REQUEST_CODE);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "OnrequestPermission clicked");

        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery() {
        Log.d(TAG, "OpenGallery clicked");
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));

    }
    private static final String TAG =Check_another_profile.class.getName();

    private ActivityResultLauncher<Intent> mActivityResultLauncher= registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    Log.e(TAG,"OnActivityResult");
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data =result.getData();
                        if(data ==null) {
                            return;
                        }
                        selectedImageUri = data.getData(); // Lưu Uri của ảnh mới

                        Uri uri =data.getData();
//                                mUri = uri;
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            avatar.setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                    }
                }
            }

    );
}