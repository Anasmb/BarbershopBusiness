package com.example.barberbusiness.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.barberbusiness.items.FeedbackItem;
import com.example.barberbusiness.items.GalleryItem;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private List<GalleryItem> galleryItemList;

    public GalleryAdapter(Context mContext, List<GalleryItem> galleryItemList) {
        this.mContext = mContext;
        this.galleryItemList = galleryItemList;
    }

    @Override
    public int getCount() {
        return galleryItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ImageView imageView = new ImageView(mContext);
        byte[] imageAsBytes = Base64.decode(galleryItemList.get(position).getImage().getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(340,350));

        return imageView;
    }
}
