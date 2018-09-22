package com.team.mamba.atlas.userInterface.welcome._viewPager.upToDate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.UpToDateLayoutBinding;

public class UpToDateFragment extends Fragment{


    public static UpToDateFragment newInstance(){

        return new UpToDateFragment();
    }

    private UpToDateLayoutBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.up_to_date_layout,container,false);

        binding.cvImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return binding.getRoot();
    }
}
