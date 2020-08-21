package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kiduyu.joshuaproject.HerbalApp.Adapters.ConsultantAdapter;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.Models.Consultant;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultantsFragment extends Fragment {
    private ConsultantAdapter consultantAdapter;
    public static int confirmation = 0;
    public static boolean isRefreshed;
    private RequestQueue mRequestQueue;
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Consultant> consultantArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.consultant_fragment, container, false);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        recycler = layout.findViewById(R.id.recyclerview_consultant);

        swipeRefreshLayout = layout.findViewById(R.id.consultant_refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        recycler.setLayoutManager(layoutManager);
        recycler.setFocusable(false);
        fetchData();
        Loading.showProgressDialog(getActivity(),true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
                FancyToast.makeText(getActivity(), "Data refreshed!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        });


        return layout;
    }

    private void fetchData() {
        String urlForJsonObject = Constants.Baseurl + "consultant.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                urlForJsonObject,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("Consultant");

                            if (isRefreshed) {
                                consultantArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("title");
                                    String phone = consultant.getString("phone");
                                    String location = consultant.getString("location");
                                    String image = consultant.getString("image");
                                    String date = consultant.getString("date");
                                    Loading.showProgressDialog(getActivity(),false);

                                    consultantArrayList.add(new Consultant(title, phone, "", location, image, date));

                                }

                            } else {
                                consultantArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("title");
                                    String phone = consultant.getString("phone");
                                    String location = consultant.getString("location");
                                    String image = consultant.getString("image");
                                    String date = consultant.getString("date");

                                    consultantArrayList.add(new Consultant(title, phone, "", location, image, date));

                                }
                            }

                            consultantAdapter = new ConsultantAdapter(getActivity(), consultantArrayList);
                            recycler.setAdapter(consultantAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
}

