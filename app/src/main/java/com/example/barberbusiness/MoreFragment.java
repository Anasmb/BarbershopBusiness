package com.example.barberbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment {

    private LinearLayout myAccountLayout;
    private LinearLayout changePasswordLayout;
    private LinearLayout changeLanguageLayout;
    private LinearLayout customerSupportLayout;
    private LinearLayout logoutLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        myAccountLayout = view.findViewById(R.id.myAccountLayout);
        myAccountLayout.setOnClickListener(myAccountLayoutListener);

        changePasswordLayout = view.findViewById(R.id.changePasswordLayout);
        changePasswordLayout.setOnClickListener(changePasswordLayoutListener);

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

    private View.OnClickListener changePasswordLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener changeLanguageLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO FINISH IF NECESSARY
        }
    };

    private View.OnClickListener customerSupportLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO MAKE THIS GO TO WHATSAPP AND MESSAGE ADMIN
        }
    };

    private View.OnClickListener logoutLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), BarbershopLoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    };
}
