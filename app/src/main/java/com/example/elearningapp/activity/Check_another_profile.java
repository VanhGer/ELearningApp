package com.example.elearningapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.FragmentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Check_another_profile extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter adapter;

    ShapeableImageView avatar;

    TextView name;
    String userId;
    ImageView verified;

    TextView about;

    AppCompatButton followBtn;

    ImageButton backBtn;
    ImageButton shareBtn;
    TextView following;
    TextView follower;
    AppCompatButton editBtn;

    public String getUserId() {
        return userId;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_prf);
        userId = getIntent().getStringExtra("userId");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        avatar = findViewById(R.id.imageView24);
        name = findViewById(R.id.textView80);
        verified = findViewById(R.id.imageView28);
        about = findViewById(R.id.textView81);
        followBtn = findViewById(R.id.button2);
        backBtn = findViewById(R.id.imageButton10);
        shareBtn = findViewById(R.id.imageButton11);
        follower = findViewById(R.id.textView98);
        following = findViewById(R.id.textView99);
        editBtn = findViewById(R.id.buttonEdit);

        String currentId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (currentId.equals(userId)) {
            editBtn.setVisibility(View.VISIBLE);
            followBtn.setVisibility(View.GONE);
        } else {
            followBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.GONE);
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ChangeUserProfile.class);
                intent.putExtra("Click", "OK");
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://coursecloud.io/user/" + userId);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        FirebaseFirestore.getInstance().collection("users")
                .document(userId).collection("following").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        following.setText(value.size()+"");
//                        Log.v("Following", value.size()+"");
                    }
                });

        FirebaseFirestore.getInstance().collection("users")
                .document(userId).collection("follower").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        follower.setText(value.size()+"");
                    }
                });

        FirebaseFirestore.getInstance().collection("users")
                .document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Picasso.get().load(value.getString("image")).into(avatar);
                        if (value.getBoolean("verified") != null) {
                            verified.setVisibility(View.VISIBLE);
                        }
                        about.setText(value.getString("about"));
                        name.setText(value.getString("name"));
                    }
                });

        FirebaseFirestore.getInstance().collection("users")
                .document(currentId).collection("following")
                .document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            followBtn.setText("Đã theo dõi");
                            followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.md_white_1000));
                            followBtn.setTextColor(getResources().getColorStateList(R.color.md_blue_500));
                        }
                        followBtn.setClickable(true);
                    }
                });

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followBtn.setClickable(false);
                if (followBtn.getText().toString().equals("Theo dõi")) {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("timestamp", System.currentTimeMillis());
                    FirebaseFirestore.getInstance().collection("users")
                            .document(currentId).collection("following")
                            .document(userId).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseFirestore.getInstance().collection("users")
                                            .document(userId).collection("follower")
                                            .document(currentId).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    followBtn.setText("Đã theo dõi");
                                                    followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.md_white_1000));
                                                    followBtn.setTextColor(getResources().getColorStateList(R.color.md_blue_500));
                                                    followBtn.setClickable(true);
                                                }
                                            });
                                }
                            });


                } else {
                    FirebaseFirestore.getInstance().collection("users")
                            .document(currentId).collection("following")
                            .document(userId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseFirestore.getInstance().collection("users")
                                            .document(userId).collection("follower")
                                            .document(currentId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    followBtn.setText("Theo dõi");
                                                    followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.md_blue_300));
                                                    followBtn.setTextColor(getResources().getColorStateList(R.color.md_white_1000));
                                                    followBtn.setClickable(true);
                                                }
                                            });
                                }
                            });
                }

            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new FragmentAdapter(fragmentManager, getLifecycle(), userId);
        viewPager2.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }}