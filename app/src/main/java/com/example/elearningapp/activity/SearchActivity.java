package com.example.elearningapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elearningapp.R;

public class SearchActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText searchText;
    private ImageButton clearButton;

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
        backButton = (ImageButton) findViewById(R.id.backBtnTopCourse);
        searchText = (EditText) findViewById(R.id.editTextSearch);
        clearButton = (ImageButton) findViewById(R.id.clear_text_btn);
    }

    private void backBtnClick() {

        if (backButton == null) {
            return;
        }
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

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(searchText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void performSearch(String text) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        startActivity(intent);
    }




}