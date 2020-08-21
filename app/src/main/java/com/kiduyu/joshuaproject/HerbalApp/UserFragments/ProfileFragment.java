package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kiduyu.joshuaproject.k_vet.R;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_fragment, container, false);




        return layout;
    }
}
