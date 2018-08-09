package com.team.mamba.atlas.dependencyInjection.builder;

import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_business.AddBusinessFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_user.AddUserFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.describe_connections.DescribeConnectionsFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.find_users.FindUsersFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.suggested_contacts.SuggestedContactsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.business_profile.BusinessProfileFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.ContactsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.DashBoardModule;
import com.team.mamba.atlas.userInterface.dashBoard.businessOpportunities.BusinessOpportunitiesFragment;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoFragment;
import com.team.mamba.atlas.userInterface.dashBoard.notifications.NotificationsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.UserProfileFragment;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.edit_address_info.EditAddressFragment;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.edit_education_info.EditEducationFragment;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.edit_email_info.EditEmailFragment;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.edit_phone_info.EditPhoneFragment;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.edit_work_history.EditWorkFragment;
import com.team.mamba.atlas.userInterface.welcome.ViewPagerActivityModule;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = ViewPagerActivityModule.class)
    abstract WelcomeFragment bindWelcomeFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract BusinessOpportunitiesFragment bindBusinessOpportunitiesFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract ContactsFragment bindContactsFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract InfoFragment bindDashBoardHomeFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract NotificationsFragment bindNotificationsFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract AddUserFragment bindAddContactsFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract DescribeConnectionsFragment bindDescribeConnectionsFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract AddBusinessFragment bindAddBusinessFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract FindUsersFragment bindFindUsersFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract SuggestedContactsFragment bindSuggestedContactsFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract UserProfileFragment bindUserProfileFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract BusinessProfileFragment bindBusinessProfileFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract EditAddressFragment bindEditAddressFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract EditEducationFragment bindEditEducationFragment();


    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract EditEmailFragment bindEditEmailFragment();


    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract EditPhoneFragment bindEditPhoneFragment();


    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract EditWorkFragment bindEditWorkFragment();






}
