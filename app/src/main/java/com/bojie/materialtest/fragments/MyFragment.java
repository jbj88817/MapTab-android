package com.bojie.materialtest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bojie.materialtest.R;
import com.bojie.materialtest.network.VolleySingleton;


public class MyFragment extends Fragment {

    private TextView mTextView;

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        mTextView = (TextView) layout.findViewById(R.id.position);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTextView.setText("The Page Slected is " + bundle.getInt("position"));
        }

        // Volley code
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, "http://php.net", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), "RESPONSE" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "ERROR" + error, Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

        return layout;
    }
}
