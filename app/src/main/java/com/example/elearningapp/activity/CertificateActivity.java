package com.example.elearningapp.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class CertificateActivity extends AppCompatActivity {
    private TextView userNameTextView;
    private int REQUEST_PERMISSION_CODE=10;
    private TextView courseNameTextView;
    private TextView a;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
//    private static final String URL_FILE = "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2021/01/agora-best-picture-nature-696x871.jpg?fit=700%2C20000&quality=95&ssl=1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_layout);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        TextView textViewUsername = findViewById(R.id.textView97);
        TextView textViewCourseName = findViewById(R.id.textView98);

        if (currentUser != null) {
            DocumentReference userRef = firestore.collection("users").document(currentUser.getUid());
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String username = documentSnapshot.getString("name");
                    textViewUsername.setText(username); // Hiển thị username vào textViewUsername
                }
            });
        }

        String courseName = getIntent().getStringExtra("courseName");
        textViewCourseName.setText(courseName);
    }

}