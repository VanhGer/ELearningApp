package com.example.elearningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.AboutAppActivity;
import com.example.elearningapp.activity.ChangeUserProfile;
import com.example.elearningapp.activity.DownloadActivity;
import com.example.elearningapp.activity.HelpAndSupportActivity;
import com.example.elearningapp.activity.LoginActivity;
import com.example.elearningapp.activity.NotificationActivity;
import com.example.elearningapp.activity.SettingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth auth;
    FirebaseUser user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        rootView = inflater.inflate(R.layout.fragment_more, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
        clicksetting();
        return rootView ;
    }
    private void clicksetting() {


        //setting
        ConstraintLayout go_to_setting =  rootView.findViewById(R.id.constraintLayout13);
        go_to_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Firebase", "ACV");
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

            }
        });



        //download
        ConstraintLayout go_to_download =  rootView.findViewById(R.id.constraintLayout17);
        go_to_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Firebase", "ACV");
                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                startActivity(intent);
            }
        });


        //profile
        ConstraintLayout go_to_profile =  rootView.findViewById(R.id.constraintLayout18);
        go_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Firebase", "ACV");
                Intent intent = new Intent(getActivity(), ChangeUserProfile.class);
                startActivity(intent);
            }
        });


        // Liên hệ
        ConstraintLayout go_to_help_and_support =  rootView.findViewById(R.id.constraintLayout14);
        go_to_help_and_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Firebase", "ACV");
                Intent intent = new Intent(getActivity(), HelpAndSupportActivity.class);
                startActivity(intent);
            }
        });


        //về chúng tôi
        ConstraintLayout go_to_about_app =  rootView.findViewById(R.id.constraintLayout19);
        go_to_about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Firebase", "ACV");
                Intent intent = new Intent(getActivity(), AboutAppActivity.class);
                startActivity(intent);
            }
        });

        //thông báo
        ConstraintLayout notification =  rootView.findViewById(R.id.constraintLayout21);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Firebase", "ACV");
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        // đăng xuất
        ConstraintLayout logout =  rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });






    }
}