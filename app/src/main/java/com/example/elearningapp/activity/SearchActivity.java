package com.example.elearningapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    private Button backButton;
    private EditText searchText;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText editSearchText = findViewById(R.id.editTextSearch);
        editSearchText.requestFocus();

        getViews();

        clearButton.setVisibility(View.INVISIBLE);

        backBtnClick();
        searchTextManager();
        clearBtnClick();
    }

    private void getViews() {
        backButton = (Button) findViewById(R.id.back_text_btn);
        searchText = (EditText) findViewById(R.id.editTextSearch);
        clearButton = (Button) findViewById(R.id.clear_text_btn);
    }

    private void backBtnClick() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void clearBtnClick() {
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText.setText("");
            }
        });
    }

    private void searchTextManager() {
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchText.getText().toString().matches("")){
                    clearButton.setVisibility(View.INVISIBLE);
                } else {
                    clearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }




}