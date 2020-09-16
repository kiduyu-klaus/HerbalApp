package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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

public class HerbsFragment extends Fragment {
    private HerbsAdapter herbsAdapter;
    public static int confirmation = 0;
    public static boolean isRefreshed;
    EditText search;
    ProgressDialog pDialog;
    private RequestQueue mRequestQueue;
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout,recyclerly;
    private ArrayList<Herb> herbArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.herbs_fragment, container, false);
        pDialog = new ProgressDialog(getActivity());
        linearLayout=layout.findViewById(R.id.lyt_not_found);
        recyclerly=layout.findViewById(R.id.ly_recycle);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        search = layout.findViewById(R.id.search_editText_herbs);


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


        recycler = layout.findViewById(R.id.recyclerview_herbs);

        swipeRefreshLayout = layout.findViewById(R.id.consultant_refresh_herbs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

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


        return layout;
    }

    private void filter(String text) {
        ArrayList<Herb> filteredList = new ArrayList<>();
        for (Herb item : herbArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        herbsAdapter = new HerbsAdapter(getActivity(), filteredList);
        recycler.setAdapter(herbsAdapter);
        herbsAdapter.notifyDataSetChanged();

    }

    private void fetchData() {
        pDialog.setTitle("Fetching Herbs");
        pDialog.setMessage("Please wait, while we are checking the database.");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        String urlForJsonObject = Constants.Baseurl + "herbs.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                urlForJsonObject,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("Herbs");

                            if (isRefreshed) {
                                herbArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("title");
                                    String description = consultant.getString("description");
                                    String disease = consultant.getString("disease");
                                    String image = consultant.getString("image");

                                    pDialog.dismiss();
                                    herbArrayList.add(new Herb(title, description, disease, image));

                                }

                            } else {
                                herbArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("title");
                                    String description = consultant.getString("description");
                                    String disease = consultant.getString("disease");
                                    String image = consultant.getString("image");
                                    pDialog.dismiss();
                                    herbArrayList.add(new Herb(title, description, disease, image));

                                }
                            }

                            herbsAdapter = new HerbsAdapter(getActivity(), herbArrayList);
                            recycler.setAdapter(herbsAdapter);

                        } catch (JSONException e) {
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
}
