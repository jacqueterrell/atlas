package com.team.mamba.atlas.userInterface.dashBoard;


import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_business.AddBusinessViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_user.AddUserViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.describe_connections.DescribeConnectionsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.find_users.FindUsersViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.suggested_contacts.SuggestedContactsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.ContactsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.businessOpportunities.BusinessOpportunitiesViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.notifications.NotificationsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.UserProfileViewModel;

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
    InfoViewModel providesDashBoardHomeViewModel(){

        return new InfoViewModel();
    }

    @Provides
    NotificationsViewModel providesNotificationsViewModel(){

        return new NotificationsViewModel();
    }

    @Provides
    AddUserViewModel providesAddContactViewModel(){

        return new AddUserViewModel();
    }

    @Provides
    DescribeConnectionsViewModel providesDescribeConnectionsViewModel(){

        return new DescribeConnectionsViewModel();
    }

    @Provides
    AddBusinessViewModel providesAddBusinessViewModel(){

        return new AddBusinessViewModel();
    }

    @Provides
    FindUsersViewModel providesFindUsersViewModel(){

        return new FindUsersViewModel();
    }

    @Provides
    SuggestedContactsViewModel providesSuggestedContactsViewModel(){

        return new SuggestedContactsViewModel();
    }

    @Provides
    UserProfileViewModel providesUserProfileViewModel(){

        return new UserProfileViewModel();
    }
}
