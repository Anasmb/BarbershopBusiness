package com.example.barberbusiness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SalonFragment extends Fragment {

    private LinearLayout barbersLayout ;
    private LinearLayout serviceLayout ;
    private LinearLayout timeLayout ;
    private TextView shopName;
    private TextView shopAddress;
    private ImageView barbershopImage;
    int SELECT_IMAGE = 1;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_salon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        barbersLayout = view.findViewById(R.id.barbersLayout);
        barbersLayout.setOnClickListener(barbersLayoutClick);
        serviceLayout = view.findViewById(R.id.serviceLayout);
        serviceLayout.setOnClickListener(serviceLayoutClick);
        timeLayout = view.findViewById(R.id.timeLayout);
        timeLayout.setOnClickListener(timeLayoutClick);
        barbershopImage = view.findViewById(R.id.barbershopImage);
        barbershopImage.setOnClickListener(barbershopImageClicked);

        shopName = view.findViewById(R.id.shopNameText);
        shopName.setText(preferences.getString("shopName" , ""));
        shopAddress = view.findViewById(R.id.shopAddressText);

        String address[] = preferences.getString("address" , "").split("/"); //address with the coordinates
        shopAddress.setText(address[0]);  // set the address only without the coordinates

        attachBarbershopImage(preferences.getString("image", ""));

    }


    View.OnClickListener barbersLayoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(),BarbersActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener serviceLayoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(),ServiceActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener timeLayoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(),TimeActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener barbershopImageClicked = new View.OnClickListener() { // SELECT BARBERSHOP IMAGE LOGO
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Title"),SELECT_IMAGE);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) { // choose image from gallery
        super.onActivityResult(requestCode, resultCode, data);
        String image_str = null;

        if(requestCode == 1){
            Uri uri = data.getData();
            barbershopImage.setImageURI(uri);
            byte[] byteArray = null;
            String image = null;

            try {
                  Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri);
                 ByteArrayOutputStream outStreamArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStreamArray);
                byteArray = outStreamArray.toByteArray();
                 image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }

            saveImageToDB(image);
        }
    }

    private void saveImageToDB(String image_str){

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[2];
                field[0] = "BarbershopID";
                field[1] = "Image";

                String[] data = new String[2];
                data[0] = "1";
                data[1] = image_str;

                PutData putData = new PutData("http://192.168.100.6/barbershop-php/saveShopImage.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.d("php", result);
                    }
                }

            }
        });

    }

   private void attachBarbershopImage(String b64){ //TODO FINISH IMAGE ISSUE
       byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
      Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
       barbershopImage.setImageBitmap(bitmap);

       Log.d("debug", "attachBarbershopImage: " + b64);
   }


}