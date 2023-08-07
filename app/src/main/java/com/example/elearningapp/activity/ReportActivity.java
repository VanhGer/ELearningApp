package com.example.elearningapp.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ReportActivity extends AppCompatActivity {

    private EditText reasonEditText;
    private RadioGroup reasonRadioGroup;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ImageButton back1;
    private String courseId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        courseId = getIntent().getStringExtra("courseId");
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        reasonEditText = findViewById(R.id.reasonEditText);
        reasonRadioGroup = findViewById(R.id.reasonRadioGroup);
        back1 = findViewById(R.id.back1);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onReportButtonClick(View view) {
        // Kiểm tra người dùng đã đăng nhập chưa
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // Nếu chưa đăng nhập, yêu cầu người dùng đăng nhập hoặc xử lý lỗi tùy ý
            Toast.makeText(this, "Vui lòng đăng nhập để gửi báo cáo.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy nội dung báo cáo từ EditText
        String reportReason = reasonEditText.getText().toString().trim();

        // Kiểm tra xem EditText có dữ liệu không
        if (reportReason.isEmpty()) {
            // Nếu không có dữ liệu, hiển thị thông báo lỗi
            reasonEditText.setError("Vui lòng nhập lí do báo cáo.");
            return;
        }

        // Kiểm tra xem người dùng đã chọn RadioButton trong RadioGroup hay chưa
        int selectedRadioButtonId = reasonRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            // Nếu người dùng không chọn RadioButton, hiển thị thông báo lỗi
            Toast.makeText(this, "Vui lòng chọn lý do báo cáo.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy email của người dùng từ ID của họ
        String userEmail = user.getEmail();

        // Lấy userId của người dùng
        userId = user.getUid();

        // Gửi dữ liệu lên Firestore
        // Tạo một map để lưu trữ dữ liệu báo cáo cùng với email, userId và nội dung RadioButton
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("email", userEmail);
        reportData.put("userId", userId);
        reportData.put("courseId", courseId);
        reportData.put("reason", reportReason);

        // Lấy nội dung của RadioButton được chọn
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        if (selectedRadioButton != null) {
            String reportReasonDetail = selectedRadioButton.getText().toString().trim();
            reportData.put("reasonDetail", reportReasonDetail);
        }

        // Gửi dữ liệu lên collection "reports"
        db.collection("reports").add(reportData)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Gửi thành công, hiển thị thông báo thành công
                            Toast.makeText(ReportActivity.this, "Gửi báo cáo thành công", Toast.LENGTH_SHORT).show();
                            // Chuyển hướng về MainActivity
                            finish();
                        } else {
                            // Gửi thất bại, hiển thị thông báo lỗi
                            Toast.makeText(ReportActivity.this, "Gửi báo cáo thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
