package com.team.mamba.atlas.userInterface.dashBoard.settings.network_management;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.NetworkManagementLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections.DescribeConnectionsFragment;
import com.team.mamba.atlas.utils.ChangeFragments;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NetworkManagementFragment extends BaseFragment<NetworkManagementLayoutBinding,NetworkManagementViewModel>
implements NetworkManagementNavigator{


    @Inject
    NetworkManagementViewModel viewModel;

    @Inject
    NetworkManagementDataModel dataModel;


    public static NetworkManagementFragment newInstance(){

        return new NetworkManagementFragment();
    }

    private NetworkManagementLayoutBinding binding;
    private List<UserConnections> userConnections = new ArrayList<>();
    private NetworkManagementAdapter networkManagementAdapter;
    private UserConnections deletedUserConnection;


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.network_management_layout;
    }

    @Override
    public NetworkManagementViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.progressSpinner;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         binding = getViewDataBinding();

         networkManagementAdapter = new NetworkManagementAdapter(userConnections,getViewModel());
         binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
         binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
         binding.recyclerView.setAdapter(networkManagementAdapter);

         showProgressSpinner();
         viewModel.getUsersContacts(getViewModel());
         setUpItemTouchHelper();

         return binding.getRoot();
    }

    @Override
    public void onContactListReceived() {

        userConnections.clear();
        userConnections.addAll(viewModel.getUserConnectionsList());
        networkManagementAdapter.notifyDataSetChanged();

        hideProgressSpinner();
    }

    @Override
    public void handleError(String msg) {

        hideProgressSpinner();
    }

    @Override
    public void onRowClicked(UserConnections connection) {

        if (connection.isOrgBus){


        } else {

            ChangeFragments.addFragmentFadeIn(DescribeConnectionsFragment.newInstance(connection,false,true),getBaseActivity()
                    .getSupportFragmentManager(),"DescribeConnections",null);
        }

    }


    private void setUpItemTouchHelper(){

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {


                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {

                    deletedUserConnection = userConnections.get(position);
                    userConnections.remove(position);
                    networkManagementAdapter.notifyDataSetChanged();
                    viewModel.deleteUserConnection(getViewModel(),deletedUserConnection);

//                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "connection removed", 4000)
//
//                            .setAction("Undo", v -> {
//
//                                userConnections.add(position,deletedUserConnection);
//                                networkManagementAdapter.notifyDataSetChanged();
//                                showRestoredSnackbar();
//                            });
//
//                    View view = snackbar.getView();
//                    view.setBackgroundResource(R.drawable.snackbar_background);
//                    snackbar.setActionTextColor(getResources().getColor(R.color.white));
//
//
//                    snackbar.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX,
                                    float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                try {

                    Bitmap icon;

                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float width = height / 3;
                        Paint paint = new Paint();
                        paint.setColor(Color.parseColor("#D32F2F"));

                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_delete_image);
                        RectF destination = new RectF((float) itemView.getRight() - 2 * width,
                                (float) itemView.getTop() + width, (float) itemView.getRight() - width,
                                (float) itemView.getBottom() - width);

                        c.drawBitmap(icon, null, destination, paint);

                    } else {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }

                } catch (Exception e) {
                    Logger.e(e.getMessage());
                }
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }


    /**
     * Used to show an 'items restored' snackbar
     */
    private void showRestoredSnackbar() {

        Snackbar snackbar = Snackbar.make(binding.getRoot(), "item restored", 2000);
        View v = snackbar.getView();
        v.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.dessert_green));

        snackbar.show();
    }
}
