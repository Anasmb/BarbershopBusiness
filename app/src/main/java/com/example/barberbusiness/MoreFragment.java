package com.example.barberbusiness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment {

    private LinearLayout myAccountLayout;
    private LinearLayout changeLanguageLayout;
    private LinearLayout customerSupportLayout;
    private LinearLayout logoutLayout;
    private SharedPreferences preferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        preferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        myAccountLayout = view.findViewById(R.id.myAccountLayout);
        myAccountLayout.setOnClickListener(myAccountLayoutListener);

        changeLanguageLayout = view.findViewById(R.id.changeLanguageLayout);
        changeLanguageLayout.setOnClickListener(changeLanguageLayoutListener);

        customerSupportLayout = view.findViewById(R.id.customerSupportLayout);
        customerSupportLayout.setOnClickListener(customerSupportLayoutListener);

        logoutLayout = view.findViewById(R.id.signInLayout);
        logoutLayout.setOnClickListener(logoutLayoutListener);


        return view;
    }

    private View.OnClickListener myAccountLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), MyAccountActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener changeLanguageLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener customerSupportLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String url = "https://api.whatsapp.com/send?phone="+"+966 569333470";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    };

    private View.OnClickListener logoutLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), BarbershopLoginActivity.class);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(intent);
            getActivity().finish();
        }
    };
}
