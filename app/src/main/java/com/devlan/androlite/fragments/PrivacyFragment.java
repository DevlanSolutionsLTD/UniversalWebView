package com.devlan.androlite.fragments;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devlan.androlite.activity.MainActivity;
import com.devlan.androlite.R;

import java.io.IOException;
import java.io.InputStream;

public class PrivacyFragment extends Fragment {
    private TextView textview_privacy_policy;
    private String str;


    public PrivacyFragment() {
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_privacy, container, false);

        textview_privacy_policy = view.findViewById(R.id.textview_privacy_policy);

        getActionBar().setTitle("Privacy Policy");
        try {
            InputStream is = getContext().getAssets().open("privarcypolicy.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            str = new String(buffer);

            Log.d("text", str);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textview_privacy_policy.setText(Html.fromHtml(str));

    return view;
    }

    private ActionBar getActionBar() {
        return ((MainActivity) getActivity()).getSupportActionBar();
    }


}
