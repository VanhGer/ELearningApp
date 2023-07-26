package com.example.elearningapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.TopCourseAdapter;
import com.example.elearningapp.object.CourseListItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText searchText;
    private ImageButton clearButton;

    TopCourseAdapter searchCourseAdapter;

    RecyclerView searchResultRecyclerView;

    List<CourseListItem> courseListItemList = new ArrayList<>();

    ConstraintLayout recentSearch;

    TextView searchResultTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        init();

        EditText editSearchText = findViewById(R.id.editTextSearch);

        editSearchText.requestFocus();


        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchCourseAdapter = new TopCourseAdapter(getApplicationContext(), courseListItemList);
        searchResultRecyclerView.setAdapter(searchCourseAdapter);



        clearButton.setVisibility(View.INVISIBLE);

        backBtnClick();
        searchTextManager();
        clearBtnClick();

        String hardStringSearch = getIntent().getStringExtra("hardStringSearch");
        editSearchText.setText(hardStringSearch);

    }

    private void init() {
        backButton = (ImageButton) findViewById(R.id.backBtnTopCourse);
        searchText = (EditText) findViewById(R.id.editTextSearch);
        clearButton = (ImageButton) findViewById(R.id.clear_text_btn);
        searchResultRecyclerView = findViewById(R.id.searchListRecycler);
        recentSearch = findViewById(R.id.recentSearch);
        searchResultTitle = findViewById(R.id.searchResultTitle);
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
                    recentSearch.setVisibility(View.VISIBLE);
                    searchResultRecyclerView.setVisibility(View.INVISIBLE);
                    searchResultTitle.setText("Tìm kiếm trước đó");

                } else {
                    textSearch(searchText.getText().toString());
                    recentSearch.setVisibility(View.INVISIBLE);
                    clearButton.setVisibility(View.VISIBLE);
                    searchResultRecyclerView.setVisibility(View.VISIBLE);
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

    private void textSearch(String str) {

        FirebaseFirestore.getInstance().collection("courses")
                .orderBy("name")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<CourseListItem> courseListFull = new ArrayList<>();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            courseListFull.add(
                                    new CourseListItem(
                                            document.getString("image"),
                                            document.getString("name")
                                            , "Bui Tuan Dung", document.getString("description"),
                                            document.getDouble("students").intValue(), document.getDouble("star")));
                            Log.v("Firebase", document.getString("name"));
                        }

                        courseListItemList.clear();
                        for (CourseListItem courseListItem: courseListFull) {
                            if (courseListItem.getName().toLowerCase().contains(str.toLowerCase())){
                                courseListItemList.add(courseListItem);
                            }
                        }

                        searchResultTitle.setText("Có tất cả " + courseListItemList.size() + " kết quả được tìm thấy");

                        searchCourseAdapter.notifyDataSetChanged();
                    }
                });


    }

    private void performSearch(String text) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        startActivity(intent);
    }

}