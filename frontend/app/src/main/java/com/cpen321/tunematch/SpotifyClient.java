package com.cpen321.tunematch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class SpotifyClient extends ApiClient<SpotifyInterface> {
    @Override
    protected String getBaseUrl() {
        return "https://api.spotify.com/v1";
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
            artistList.add(artist.get("name").toString());

            String genres = artist.get("genres").toString().replace("[", "").replace("]", "").trim();
            for (String g : genres.split(",")) {
                genreSet.add(g.replace("\"", ""));
            }
        }
        List<String> genreList = new ArrayList<>(genreSet);

        return new SpotifyTopResult(artistList, genreList);
    }

    public JsonObject getSong(String query) throws ApiException {
        Call<String> call = api.getSong(auth, query, "track");
        return call(call).getAsJsonObject();
    }
}
