package com.example.zoom.contacts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private List<Contacts> contacts;
    private Bundle mBundle;
    private Context mContext;
    private Fragment mFragment;
    private ContactDataSource datasource;

    public ContactAdapter (Context context){

        mBundle = new Bundle();
        mContext = context;
        datasource = new ContactDataSource(context);

        try {
            datasource.open();
            this.contacts = datasource.getContacts();


        } catch (Exception e){
        } finally {
            datasource.close();
        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.contact_row, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Contacts contact= contacts.get(position);
        holder.title.setText(contact.getName());
        holder.email.setText(contact.getEmail());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "botard", Toast.LENGTH_LONG).show();
            }
        
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView email;
        public Button btn;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.contactName);
            image = (ImageView) view.findViewById(R.id.contactImage);
            email = (TextView)view.findViewById(R.id.contactEmail);
            btn = (Button) view.findViewById(R.id.inviteToMeetingButton);

            java.lang.String uri = "@drawable/google_contacts";
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
            Drawable res = mContext.getResources().getDrawable(imageResource);
            image.setImageDrawable(res);

        }

    }
}
