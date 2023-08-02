package com.example.elearningapp.activity;

import androidx.annotation.NonNull;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elearningapp.interfaces.CourseClickHelper;
import com.example.elearningapp.interfaces.LessonClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.adapter.TopCourseAdapter;
import com.example.elearningapp.object.CourseListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.model.Document;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements CourseClickHelper {

    private ImageButton backButton;
    private EditText searchText;
    private ImageButton clearButton;

    FirebaseUser currentUser;

    TopCourseAdapter searchCourseAdapter;

    RecyclerView searchResultRecyclerView;

    List<CourseListItem> courseListItemList = new ArrayList<>();

    LinearLayout recentSearch;

    TextView searchResultTitle;

    TextView resetSearch;

    LayoutInflater factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        init();

        searchText.requestFocus();
        searchText.onEditorAction(EditorInfo.IME_ACTION_SEARCH);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchCourseAdapter = new TopCourseAdapter(this, courseListItemList, this);
        searchResultRecyclerView.setAdapter(searchCourseAdapter);


        clearButton.setVisibility(View.INVISIBLE);

        backBtnClick();
        searchTextManager();
        clearBtnClick();
        resetSearchClick();

        String hardStringSearch = getIntent().getStringExtra("hardStringSearch");
        searchText.setText(hardStringSearch);

    }

    private void init() {
        factory = LayoutInflater.from(this);
        backButton = (ImageButton) findViewById(R.id.backBtnTopCourse);
        searchText = (EditText) findViewById(R.id.editTextSearch);
        clearButton = (ImageButton) findViewById(R.id.clear_text_btn);
        searchResultRecyclerView = findViewById(R.id.searchListRecycler);
        recentSearch = findViewById(R.id.recentSearch);
        searchResultTitle = findViewById(R.id.searchResultTitle);
        resetSearch = findViewById(R.id.resetSearch);
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

    private void resetSearchClick() {

        if (resetSearch == null) {
            return;
        }
        resetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("users").
                        document(currentUser.getUid()).collection("searches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot: task.getResult()) {
                                        FirebaseFirestore.getInstance().collection("users").
                                                document(currentUser.getUid()).collection("searches").document(documentSnapshot.getId()).delete();
                                    }
                                    recentSearch.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
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
                    recentSearchGet();
                    recentSearch.setVisibility(View.VISIBLE);
                    resetSearch.setVisibility(View.VISIBLE);
                    searchResultRecyclerView.setVisibility(View.INVISIBLE);
                    searchResultTitle.setText("Tìm kiếm trước đó");

                } else {
                    textSearch(searchText.getText().toString());
                    recentSearch.setVisibility(View.INVISIBLE);
                    clearButton.setVisibility(View.VISIBLE);
                    resetSearch.setVisibility(View.INVISIBLE);
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

    private void recentSearchGet() {
         recentSearch.removeAllViews();
         FirebaseFirestore.getInstance().collection("users").
                document(currentUser.getUid()).collection("searches").orderBy("timestamp", Query.Direction.DESCENDING).limit(10).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot documentSnapshot: value.getDocuments()) {
                            View newView = factory.inflate(R.layout.recent_search_item, null);
                            TextView text = newView.findViewById(R.id.recentSearchText);
                            text.setText(documentSnapshot.getString("name").toString());
                            recentSearch.addView(newView);
                        }
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
                                            document.getId(),
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
//        Intent intent = new Intent(this, SearchResultActivity.class);
//        startActivity(intent);
        searchText.clearFocus();
        searchText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        updateSearchCount();
        Map<String, Object> data = new HashMap<>();
        data.put("name", text);
        data.put("timestamp", FieldValue.serverTimestamp());
        FirebaseFirestore.getInstance().collection("users").
                document(currentUser.getUid()).collection("searches").add(data);

    }

    @Override
    public void onItemClick(String id) {
        Intent overallActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
        overallActivity.putExtra("courseId", id);
        startActivity(overallActivity);
    }

    public void updateSearchCount() {
        String doc = searchText.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query docRef = db.collection("topsearches").whereEqualTo("name", doc);
        ListenerRegistration registration = docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {

            boolean doneUpdate = false;
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.getMetadata().hasPendingWrites()) {
                    return;
                }
                if (!value.isEmpty()) {
                    for (DocumentSnapshot document : value.getDocuments()) {
                        int oldCnt = document.getDouble("count").intValue();
                        Log.v("Search", "+1");
                        db.collection("topsearches").document(document.getId()).update("count", oldCnt + 1);
                        doneUpdate = true;
                    }
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("name", doc);
                    data.put("count", 1);
                    db.collection("topsearches").add(data);
                }
            }
        });

        Log.v("Search", searchText.getText().toString());
    }

}