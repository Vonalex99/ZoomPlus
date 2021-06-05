package com.example.zoom.contacts;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;

public class ContactFragment extends Fragment {
    private View mView;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        mView = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.contactReciclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        updateView();

        return mView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateView() {
        contactAdapter = new ContactAdapter(getContext());
        contactAdapter.setHasStableIds(true);
        recyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }

}
