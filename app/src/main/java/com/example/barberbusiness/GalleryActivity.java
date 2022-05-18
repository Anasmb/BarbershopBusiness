package com.example.barberbusiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barberbusiness.adapters.FeedbackAdapter;
import com.example.barberbusiness.adapters.GalleryAdapter;
import com.example.barberbusiness.items.FeedbackItem;
import com.example.barberbusiness.items.GalleryItem;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private String SQL_URL = "http://188.54.243.108/barbershop-php/images/getImages.php";
    private GridView gridView;
    private GalleryAdapter adapter;
    private List<GalleryItem> galleryItemList;
    private ImageView backBtn,addImageBtn;
    private SharedPreferences preferences;
    private String shopID;
    private int SELECT_IMAGE = 1;
    private Bitmap bitmap;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        galleryItemList = new ArrayList<>();

        shopID = preferences.getString("id","");
        SQL_URL += "?barbershopID=" + shopID;

        backBtn = findViewById(R.id.gallery_backButton);
        backBtn.setOnClickListener(backBtnListener);
        addImageBtn = findViewById(R.id.addImageButton);
        addImageBtn.setOnClickListener(addImageListener);
        gridView = findViewById(R.id.galleryGridView);
        gridView.setOnItemLongClickListener(gridItemListener);
        progressBar = findViewById(R.id.galleryProgress);

        loadImages();
    }

    private View.OnClickListener backBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener addImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Image"),SELECT_IMAGE);
        }
    };

    private AdapterView.OnItemLongClickListener gridItemListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            deleteImageFromDB(galleryItemList.get(position).getImageID());
            galleryItemList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Image removed successfully" , Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) { // choose image from gallery
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){

            Uri filePath = data.getData();
            String encodedImg;

            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,60,stream);
                byte[] imageBytes = stream.toByteArray();
                encodedImg = Base64.encodeToString(imageBytes,Base64.DEFAULT);

                saveImageToDB(encodedImg);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    private void saveImageToDB(String encodedImg){

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[2];
                field[0] = "barbershopID";
                field[1] = "image";

                String[] data = new String[2];
                data[0] = preferences.getString("id","");
                data[1] = encodedImg;

                PutData putData = new PutData("http://188.54.243.108/barbershop-php/images/addImage.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.d("php", result);
                        loadImages();
                    }
                }

            }
        });

    }

    private void loadImages(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SQL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("php", response);
                galleryItemList.clear();
                adapter = new GalleryAdapter(getApplicationContext(),galleryItemList);
                gridView.setAdapter(adapter);
                try {
                    JSONArray images = new JSONArray(response);
                    for (int i = 0 ; i < images.length() ; i++){
                        JSONObject ImageObject = images.getJSONObject(i);
                        int imageID = ImageObject.getInt("ImageID");
                        String image = ImageObject.getString("Image");
                        GalleryItem galleryItem = new GalleryItem(imageID,image);
                        galleryItemList.add(galleryItem);
                    }
                    adapter = new GalleryAdapter(getApplicationContext(),galleryItemList);
                    gridView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // this method will execute if there is error
                Log.d("php", "onErrorResponse: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void deleteImageFromDB(int imageID){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[2];
                field[0] = "barbershopID";
                field[1] = "imageID";

                String[] data = new String[2];
                data[0] = preferences.getString("id","");
                data[1] = String.valueOf(imageID);

                PutData putData = new PutData("http://188.54.243.108/barbershop-php/images/deleteImage.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.d("php", result);
                    }
                }

            }
        });
    }
}