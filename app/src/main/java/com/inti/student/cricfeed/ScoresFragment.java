package com.inti.student.cricfeed;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.inti.student.cricfeed.Adapter.ScoresAdapter;
import com.inti.student.cricfeed.Common.HTTPDataHandler;
import com.inti.student.cricfeed.Model.RSSObject;

public class ScoresFragment extends Fragment {
    RecyclerView recyclerView;
    RSSObject rssObject;

    //source url
    private final String RSS_url="http://static.cricinfo.com/rss/livescores.xml";
    private final String RSS_to_Json = "https://api.rss2json.com/v1/api.json?rss_url=";
    private final String API_key = "&api_key=rtwj3derm2hiwkfqgajwjszfemdoveerapx1kula&count=50";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scores, null);

        //set to vertical list
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadRSS();

        return v;
    }

    private void loadRSS(){
        //asynctask used to load data and convert in background
        AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                //convert Json to Java object
                rssObject = new Gson().fromJson(s,RSSObject.class);
                ScoresAdapter adapter = new ScoresAdapter(rssObject,getActivity().getBaseContext());
                //set adapter to recyclerview
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        StringBuilder url_get_data = new StringBuilder(RSS_to_Json);
        url_get_data.append(RSS_url).append(API_key);
        //call async on the url string
        loadRSSAsync.execute(url_get_data.toString());
    }
}