package com.example.ranguro.functionary.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.functionary.R;

public class NextSignUpActivityFragment extends Fragment {

    private final String LOG_TAG = NextSignUpActivityFragment.class.getSimpleName();

    public NextSignUpActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_next_sign_up, container, false);
    }
}
