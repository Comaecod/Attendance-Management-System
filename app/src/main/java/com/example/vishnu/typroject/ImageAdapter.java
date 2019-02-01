package com.example.vishnu.typroject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<UploadImages> mUploads;
    private onItemClickListener mListener;

    public ImageAdapter(Context context, List<UploadImages> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.images_cardview_custom, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        UploadImages uploadImages = mUploads.get(position);
        holder.textViewName.setText(uploadImages.getmName());
        Picasso.with(mContext)
                .load(uploadImages.getmImageUrl())
                .placeholder(R.drawable.image_loading)
                .fit()
                .centerCrop()
                .into(holder.imageViewName);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public TextView textViewName;
        public ImageView imageViewName;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_upload);
            imageViewName = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null) {
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");

            MenuItem deleteItem = contextMenu.add(Menu.NONE,1,1,"Delete Item");

            deleteItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener != null) {
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION) {
                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
//        void onWhateverClick(int position);
        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;

    }
}
