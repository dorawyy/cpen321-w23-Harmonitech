package com.cpen321.tunematch;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;

public class SpotifyClient extends ApiClient<SpotifyInterface> {
    @Override
    protected String getBaseUrl() {
        return "https://api.spotify.com/";
    }
    private String auth;

    public SpotifyClient(@NonNull String token) {
        super(SpotifyInterface.class);
        this.auth = "Bearer " + token;
    }

    public JsonObject getMe() throws ApiException {
        Call<String> call = api.getUser(auth);
        return call(call).getAsJsonObject();
    }

    public class SpotifyTopResult {
        public List<String> topArtists;
        public List<String> topGenres;
        public SpotifyTopResult(List<String> topArtists, List<String> topGenres) {
            this.topArtists = topArtists;
            this.topGenres = topGenres;
        }
    }
    public SpotifyTopResult getMeTopArtistsAndGenres() throws ApiException {
        Call<String> call = api.getTopArtists(auth);

        // Parse the top artists response
        JsonArray topArtistsArray = call(call).getAsJsonObject().get("items").getAsJsonArray();
        ArrayList<String> artistList = new ArrayList<>();
        Set<String> genreSet = new HashSet<>();
        for (int i = 0; i < topArtistsArray.size(); i++) {
            JsonObject artist = topArtistsArray.get(i).getAsJsonObject();
            artistList.add(artist.get("name").getAsString());

            JsonArray genres = artist.get("genres").getAsJsonArray();
            for (int j = 0; j < genres.size(); j++) {
                genreSet.add(genres.get(j).getAsString());
            }
        }
        List<String> genreList = new ArrayList<>(genreSet);

        return new SpotifyTopResult(artistList, genreList);
    }

    public JsonObject getSong(String query) throws ApiException {
        Call<String> call = api.getSong(auth, query, "track");
//        Log.d("SpotifyClient", "getSong: " + call(call).getAsJsonObject());
        return call(call).getAsJsonObject();
    }
}
