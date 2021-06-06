package com.example.zoom.ui.meetings;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zoom.R;
import com.example.zoom.contacts.ContactAdapter;
import com.example.zoom.ui.meetings.PreviousMeetings.PreviousMeetingsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviousMeetingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousMeetingsFragment extends Fragment {
    RecyclerView recyclerView;
    View mView;
    PreviousMeetingsAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreviousMeetingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreviousMeetingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviousMeetingsFragment newInstance(String param1, String param2) {
        PreviousMeetingsFragment fragment = new PreviousMeetingsFragment();
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
        mView =  inflater.inflate(R.layout.fragment_previous_meetings, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.previous_meetings_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        updateView();

        return mView;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateView() {
        adapter = new PreviousMeetingsAdapter(getContext());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}