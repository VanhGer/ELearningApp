package com.example.elearningapp.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;

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
//    private static final String URL_FILE = "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2021/01/agora-best-picture-nature-696x871.jpg?fit=700%2C20000&quality=95&ssl=1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate);

        userNameTextView = findViewById(R.id.textView8);
//        userNameTextView.setText(URL_FILE);
//        courseNameTextView = findViewById(R.id.textView12);
        a = findViewById(R.id.taixuong);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        String name = "John Doe"; // Tên người học
        String courseTitle = "Introduction to Programming"; // Tiêu đề khóa học
        String completionDate = "August 6, 2023"; // Ngày hoàn thành

        createCertificate(name, courseTitle, completionDate);
    }

    private void createCertificate(String name, String courseTitle, String completionDate) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Certificate of Completion");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("This is to certify that");
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(name);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("has successfully completed the course:");
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(courseTitle);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("on");
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(completionDate);
            contentStream.endText();
            contentStream.close();

            document.save(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "certificate.pdf"));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

