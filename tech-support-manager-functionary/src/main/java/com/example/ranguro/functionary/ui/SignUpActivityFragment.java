package com.example.ranguro.functionary.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.functionary.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignUpActivityFragment extends Fragment {

    private final String LOG_TAG = SignUpActivityFragment.class.getSimpleName();

    public SignUpActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
}
