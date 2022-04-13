package com.example.barberbusiness;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barberbusiness.adapters.AppointmentAdapter;
import com.example.barberbusiness.items.AppointmentItem;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppointmentFragment extends Fragment implements  AppointmentAdapter.OnItemListener {

    private String SQL_URL = "http://192.168.100.6/barbershop-php/getAppointment.php";
    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private List<AppointmentItem> appointmentItemList;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getActivity().getSharedPreferences("UserPrefs" , Context.MODE_PRIVATE);

        SQL_URL += "?barbershopID=" + preferences.getString("id","");

        appointmentItemList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.appointments_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadAppointments();
    }

    private void loadAppointments(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SQL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray appointments = new JSONArray(response);
                    for (int i = 0 ; i < appointments.length() ; i++){
                        JSONObject appointmentObject = appointments.getJSONObject(i);
                        String appointmentID = appointmentObject.getString("appointmentID");
                        String customerName = appointmentObject.getString("customerName");
                        String barberName = appointmentObject.getString("barberName");
                        String serviceAt = appointmentObject.getString("serviceLocation");
                        String dateTime = appointmentObject.getString("date") + " , " + appointmentObject.getString("time");
                        String totalPrice = appointmentObject.getString("totalPrice");
                        String status = appointmentObject.getString("status");
                        String services = appointmentObject.getString("services");
                        String customerAddress = appointmentObject.getString("customerAddress");
                        AppointmentItem appointmentItem = new AppointmentItem(appointmentID,customerName,barberName,totalPrice,dateTime,serviceAt,customerAddress,services,status);
                        appointmentItemList.add(appointmentItem);
                    }

                    adapter = new AppointmentAdapter(getActivity(), appointmentItemList, AppointmentFragment.this::onItemClick);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // this method will execute if there is error
                Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
