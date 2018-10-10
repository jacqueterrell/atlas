package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.DirectoryRecyclerViewBinding;

import com.team.mamba.atlas.data.model.api.fireStore.UserConnections;

import java.util.ArrayList;
import java.util.List;

public class DirectoryListRecycler extends Fragment implements DirectoryNavigator{

    private DirectoryRecyclerViewBinding binding;
    private static List<UserConnections> directoryConnections;
    private static ContactsNavigator contactsNavigator;
    private DirectoryListAdapter directoryListAdapter;

    public static DirectoryListRecycler newInstance(ContactsNavigator navigator,List<UserConnections> connectionsList){

        directoryConnections = connectionsList;
        contactsNavigator = navigator;
        return new DirectoryListRecycler();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.directory_recycler_view,container,false);

        directoryListAdapter = new DirectoryListAdapter(directoryConnections,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(directoryListAdapter);

        int count = directoryConnections.size();
        binding.tvDirectoryCount.setText(String.valueOf(count));

        binding.btnCancel.setOnClickListener(view -> {
            getActivity().onBackPressed();
        });

        return binding.getRoot();
    }

    @Override
    public void onRowClicked(UserConnections connections) {

        getActivity().onBackPressed();
        contactsNavigator.onDirectoryRowClicked(connections);
    }
}
