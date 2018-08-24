package com.team.mamba.atlas.userInterface.welcome.select_business_accounts.admin_accounts;

import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.admin_accounts.AdminAccountsDataModel;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.admin_accounts.AdminAccountsNavigator;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.admin_accounts.AdminAccountsViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@SuppressWarnings("ResultOfMethodCallIgnored")
@Config(constants = BuildConfig.class)
public class AdminAccountsViewModelTest {


    @Mock
    private AdminAccountsNavigator mockNavigator;

    @Mock
    private AdminAccountsDataModel mockDataModel;

    private AdminAccountsViewModel viewModel;
    private AdminAccountsViewModel spyViewModel;



    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        viewModel = new AdminAccountsViewModel();
        spyViewModel = spy(new AdminAccountsViewModel());
        viewModel.setDataModel(mockDataModel);

        doReturn(mockNavigator).when(spyViewModel).getNavigator();
    }

    @Test
    public void getAdminProfileList() {

        //set up
        List<UserProfile> userProfiles = new ArrayList<>();

        //action
        viewModel.setAdminProfileList(userProfiles);

        //assert
        assertEquals(userProfiles,viewModel.getAdminProfileList());
    }

    @Test
    public void getAllAdminProfiles() {

        //action
        viewModel.getAllAdminProfiles(viewModel);

        //assert
        verify(mockDataModel).getAllAdminProfiles(viewModel);
    }
}