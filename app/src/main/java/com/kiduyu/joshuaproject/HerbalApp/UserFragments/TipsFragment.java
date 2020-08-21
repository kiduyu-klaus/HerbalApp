package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.os.Bundle;
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
import com.kiduyu.joshuaproject.HerbalApp.Adapters.TipsAdapter;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.Models.Consultant;
import com.kiduyu.joshuaproject.HerbalApp.Models.Tip;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TipsFragment extends Fragment {
    private TipsAdapter tipsAdapter;
    public static int confirmation = 0;
    public static boolean isRefreshed;
    private RequestQueue mRequestQueue;
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Tip> tipArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tips_fragment, container, false);

        mRequestQueue = Volley.newRequestQueue(getActivity());
        recycler = layout.findViewById(R.id.recyclerview_tips);

        swipeRefreshLayout = layout.findViewById(R.id.tip_refresh);

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
                FancyToast.makeText(getActivity(), "Tips refreshed!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        });



        return layout;
    }

    private void fetchData() {
        String urlForJsonObject = Constants.Baseurl + "tips.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                urlForJsonObject,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("Tips");

                            if (isRefreshed) {
                                tipArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("title");
                                    String description = consultant.getString("description");
                                    String image = consultant.getString("image");
                                    Loading.showProgressDialog(getActivity(),false);

                                    tipArrayList.add(new Tip(title, description ,image));

                                }

                            } else {
                                tipArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("title");
                                    String description = consultant.getString("description");
                                    String image = consultant.getString("image");
                                    Loading.showProgressDialog(getActivity(),false);

                                    tipArrayList.add(new Tip(title, description ,image));

                                }
                            }

                            tipsAdapter = new TipsAdapter(getActivity(), tipArrayList);
                            recycler.setAdapter(tipsAdapter);

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
