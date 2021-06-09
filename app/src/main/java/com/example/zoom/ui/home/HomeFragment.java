package com.example.zoom.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.zoom.databinding.FragmentHomeBinding;
import com.example.zoom.ui.schedule.ScheduleFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ImageButton scheduleButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        scheduleButton = binding.scheduleButton;
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleFragment fragment = new ScheduleFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(container.getId(), fragment, fragment.toString());
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}