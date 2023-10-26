// Wrote by team member following online tutorial regarding BottomNavigationView usage
package com.cpen321.tunematch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private View view;
    ReduxStore model;
    ApiClient apiClient;

    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(ReduxStore.class);
        apiClient = ((MainActivity) getActivity()).getApiClient();;

        fm = getActivity().getSupportFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_profile, container, false);

        Button friendsListBtn = view.findViewById(R.id.friendsListBtn);
        friendsListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayList<String> friendsNameList = model.friendsNameList();
                Log.d("ProfileFragment", "friendsNameList:"+friendsNameList.toString());        // TODO: crash due to empty friendlist
                ListFragment friendsListFragment = ListFragment.newInstance(friendsNameList, "Friends List");

                ft = fm.beginTransaction();

                ft.replace(R.id.mainFrame, friendsListFragment);
                ft.addToBackStack(null);
                ft.commit();
            }

        });

        Button topArtistBtn = view.findViewById(R.id.topArtistsBtn);
        topArtistBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ProfileFragment", "line80:in run() thread");
                        try {
                            String response = apiClient.doGetRequest("/me?fullProfile=true", true);
                            ArrayList<String> topArtistsList = parseList(response, "topArtists");

                            ListFragment topArtistFragment = ListFragment.newInstance(topArtistsList, "Top Artists");

                            // Begin a fragment transaction
                            ft = fm.beginTransaction();
                            ft.replace(R.id.mainFrame, topArtistFragment);
                            ft.addToBackStack(null);
                            ft.commit();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        });

        Button topGenresBtn = view.findViewById(R.id.topGenresBtn);
        topGenresBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            String response = apiClient.doGetRequest("/me?fullProfile=true", true);
                            ArrayList<String> topGenreList = parseList(response, "topGenres");

                            ListFragment topGenresFragment = ListFragment.newInstance(topGenreList, "Top Genres");

                            // Begin a fragment transaction
                            ft = fm.beginTransaction();
                            ft.replace(R.id.mainFrame, topGenresFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        return view;
    }

    public ArrayList<String> parseList(String response, String key) {
        Log.d("ProfileFragment", "parseList: "+key);

        ArrayList<String> parsedList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String tempList = jsonObject.getString(key).replace("[", "").replace("]","").trim();
            Log.d("ProfileFragment", "tempList:"+tempList);

            for (String item : tempList.split(",")) {
                parsedList.add(item.replace("\"", ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parsedList;
    }
}
