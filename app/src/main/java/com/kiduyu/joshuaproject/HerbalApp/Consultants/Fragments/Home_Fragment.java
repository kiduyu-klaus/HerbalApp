package com.kiduyu.joshuaproject.HerbalApp.Consultants.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kiduyu.joshuaproject.k_vet.R;

/**
 * Created by Kiduyu klaus
 * on 10/11/2020 03:33 2020
 */
public class Home_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consultant_home, container, false);

        return view;
    }
    }
