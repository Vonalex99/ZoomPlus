package com.example.zoom.ui.meetings;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zoom.R;

public class PreviousMeetingsFragment extends Fragment {
    RecyclerView recyclerView;
    View mView;
    PreviousMeetingsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_previous_meetings, container, false);

        recyclerView = (RecyclerView) mView.findViewById(R.id.previous_meetings_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        updateView(container, getActivity());


        return mView;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateView(ViewGroup container, FragmentActivity activity) {
        adapter = new PreviousMeetingsAdapter(getContext(), container, activity);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}