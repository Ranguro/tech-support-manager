package com.example.ranguro.technicalsupportmanager.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.technicalsupportmanager.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignInFragment extends Fragment {

    private final String LOG_TAG = SignInFragment.class.getSimpleName();


    public SignInFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
}
