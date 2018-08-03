package com.team.mamba.atlas.userInterface.dashBoard;


import com.team.mamba.atlas.userInterface.dashBoard.contacts.ContactsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.businessOpportunities.BusinessOpportunitiesViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.info.DashBoardHomeViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.notifications.NotificationsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.settings.SettingsViewModel;
import dagger.Module;
import dagger.Provides;

@Module
public class DashBoardModule {

    @Provides
    BusinessOpportunitiesViewModel providesBusinessOpportunitiesViewModel(){

        return new BusinessOpportunitiesViewModel();
    }

    @Provides
    DashBoardActivityViewModel providesDashBoardActivityViewModel(){

        return new DashBoardActivityViewModel();
    }

    @Provides
    ContactsViewModel providesContactsViewModel(){

        return new ContactsViewModel();
    }

    @Provides
    DashBoardHomeViewModel providesDashBoardHomeViewModel(){

        return new DashBoardHomeViewModel();
    }

    @Provides
    NotificationsViewModel providesNotificationsViewModel(){

        return new NotificationsViewModel();
    }

    @Provides
    SettingsViewModel providesSettingsViewModel(){

        return new SettingsViewModel();
    }
}
