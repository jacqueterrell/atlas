package com.team.mamba.atlas.userInterface.welcome._viewPagerActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;

public class EmptyFragment extends Fragment {

    public static EmptyFragment newInstance(){

        return new EmptyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.empty_view_layout,container,false);

        return v;    }
}
