package com.team.mamba.atlas.data.remote;

import com.team.mamba.atlas.data.model.api.googlePlaces.GoogleLocations;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GooglePlacesApiEndPoints {

    @GET("place/autocomplete/json")
    Single<GoogleLocations> requestGoogleAutoComplete(@QueryMap Map<String, String> queryMap);
}
