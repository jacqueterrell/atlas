package com.team.mamba.atlas.userInterface.welcome.howYouMet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

public class RememberHowYouMetFragment extends Fragment {


    public static RememberHowYouMetFragment newInstance(){

        return new RememberHowYouMetFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.how_you_met_layout,container,false);



        return v;
    }
}
