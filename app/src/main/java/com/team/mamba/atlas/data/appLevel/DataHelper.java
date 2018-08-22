package com.team.mamba.atlas.data.appLevel;

import com.team.mamba.atlas.data.remote.AtlasApiEndPoints;
import com.team.mamba.atlas.data.remote.GooglePlacesApiEndPoints;
import com.team.mamba.atlas.utils.AppSharedPrefs;

public interface DataHelper {

    AppSharedPrefs getSharedPrefs();
    AtlasApiEndPoints getApiEndPoints();
    GooglePlacesApiEndPoints getGooglePlacesApiEndPoint();
}
