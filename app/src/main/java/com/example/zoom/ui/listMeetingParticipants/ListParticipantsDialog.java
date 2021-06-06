package com.example.zoom.ui.listMeetingParticipants;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;
import com.example.zoom.db.MeetingsDataSource;

public class ListParticipantsDialog extends DialogFragment {
    private View mView;
    private RecyclerView recyclerView;
    private ListParticipantsAdapter participantsAdapter;
    private MeetingsDataSource meetingsDataSource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.list_meeting_participants, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.participants_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        meetingsDataSource = new MeetingsDataSource(getContext());
        meetingsDataSource.open();
    //    folderRecipeDataSource = new FolderRecipeDataSource(getContext());
     //   folderRecipeDataSource.open();

   /*     cancelButton = mView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        saveButton = mView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> folderIds = folderAdapter.getSelectedFolders();
                if(!folderIds.isEmpty()) {
                    int recipeId = toSave.getId();
                    for (Integer folderId : folderIds) {
                        FolderRecipe fr = new FolderRecipe(folderId, recipeId);
                        folderRecipeDataSource.addFolderRecipe(fr);
                    }
                    getDialog().dismiss();


                    Toast.makeText(getContext(), "Recipe saved!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Please select a folder!", Toast.LENGTH_SHORT).show();
            }
        });

        addFolderButton = mView.findViewById(R.id.add_folder_button);
        addFolderButton.setColorFilter(Color.WHITE);
        addFolderButton.setScaleType(ImageView.ScaleType.CENTER);
        addFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folderDataSource.addFolder(new Folder(0, "New Folder", toSave.getImage()));
                updateView();
            }
        });
*/
        updateView();
        return mView;
    }

    @Override
    public void onResume() {
        updateView();
        super.onResume();
    }

    private void updateView() {
        participantsAdapter = new ListParticipantsAdapter(getContext());
        participantsAdapter.setHasStableIds(true);
        recyclerView.setAdapter(participantsAdapter);
        participantsAdapter.notifyDataSetChanged();
    }

}
