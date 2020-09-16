package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.kiduyu.joshuaproject.HerbalApp.Adapters.HerbsAdapter;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.Models.Consultant;
import com.kiduyu.joshuaproject.HerbalApp.Models.Herb;
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
    EditText search;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Consultant> consultantArrayList = new ArrayList<>();
    ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.consultant_fragment, container, false);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        recycler = layout.findViewById(R.id.recyclerview_consultant);
        pDialog = new ProgressDialog(getActivity());
        swipeRefreshLayout = layout.findViewById(R.id.consultant_refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        search = layout.findViewById(R.id.search_editText_consultant);
        recycler.setLayoutManager(layoutManager);
        recycler.setFocusable(false);
        fetchData();
        //Loading.showProgressDialog(getActivity(),true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
                FancyToast.makeText(getActivity(), "Data refreshed!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return layout;
    }

    private void fetchData() {
        pDialog.setTitle("Fetching Consultants");
        pDialog.setMessage("Please wait, while we are checking the database.");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
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

                                    pDialog.dismiss();
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

                                    pDialog.dismiss();
                                    consultantArrayList.add(new Consultant(title, phone, "", location, image, date));

                                }
                            }

                            consultantAdapter = new ConsultantAdapter(getActivity(), consultantArrayList);
                            recycler.setAdapter(consultantAdapter);

                        } catch (JSONException e) {
                            pDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                volleyError.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    private void filter(String text) {
        ArrayList<Consultant> filteredList = new ArrayList<>();
        for (Consultant item : consultantArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        consultantAdapter = new ConsultantAdapter(getActivity(), filteredList);
        recycler.setAdapter(consultantAdapter);
        consultantAdapter.notifyDataSetChanged();

    }
}

