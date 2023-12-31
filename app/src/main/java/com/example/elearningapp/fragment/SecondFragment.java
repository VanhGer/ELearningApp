package com.example.elearningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.Check_another_profile;
import com.example.elearningapp.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View rootView;

    private String mParam2;
    private FirebaseFirestore firestore;

    String userId;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
        linkdatabase();
        return rootView ;
    }

    private void linkdatabase() {
        Check_another_profile check_another_profile = (Check_another_profile) getActivity();
        userId = check_another_profile.getUserId();

        TextView username = rootView .findViewById(R.id.textView85);
        TextView nhapJob = rootView .findViewById(R.id.nhapJob);
        TextView nhapEmail = rootView .findViewById(R.id.nhapEmail);
        TextView nhapPhonenumber = rootView .findViewById(R.id.sodienthoai);

        FirebaseFirestore.getInstance().collection("users")
                .document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {
                        if (document.exists()) {
                            String email = document.getString("email");
                            String job = document.getString("job");
                            String phoneNumber = document.getString("phonenumber");
                            username.setText(document.getString("name"));
                            nhapJob.setText(job);
                            nhapPhonenumber.setText(phoneNumber);
                            nhapEmail.setText(email);
                        }
                    }
                });


    }

}