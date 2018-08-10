package com.team.mamba.atlas.userInterface.dashBoard;


import com.team.mamba.atlas.userInterface.dashBoard.Crm.CrmViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.Crm.selected_crm.SelectedCrmViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_business.AddBusinessViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_user.AddUserViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.describe_connections.DescribeConnectionsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.find_users.FindUsersViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.suggested_contacts.SuggestedContactsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.business.BusinessProfileViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.ContactsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.notifications.NotificationsViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.UserProfileViewModel;

import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_address_info.EditAddressViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info.EditEducationViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info.EditEmailViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_phone_info.EditPhoneViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history.EditWorkViewModel;
import dagger.Module;
import dagger.Provides;

@Module
public class DashBoardModule {

    @Provides
    CrmViewModel providesBusinessOpportunitiesViewModel(){

        return new CrmViewModel();
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

    @Provides
    BusinessProfileViewModel providesBusinessProfileViewModel(){

        return new BusinessProfileViewModel();
    }

    @Provides
    EditAddressViewModel providesEditEducationViewModel(){

        return new EditAddressViewModel();
    }

    @Provides
    EditEducationViewModel providesEditEducationViewModell(){

        return new EditEducationViewModel();
    }

    @Provides
    EditEmailViewModel providesEditEmailViewModel(){

        return new EditEmailViewModel();
    }

    @Provides
    EditPhoneViewModel providesEditPhoneViewModel(){

        return new EditPhoneViewModel();
    }

    @Provides
    EditWorkViewModel providesEditWorkViewModel(){

        return new EditWorkViewModel();
    }

    @Provides
    SelectedCrmViewModel providesSelectedCrmViewModel(){

        return new SelectedCrmViewModel();
    }
}
