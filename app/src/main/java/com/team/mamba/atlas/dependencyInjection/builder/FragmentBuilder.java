package com.team.mamba.atlas.dependencyInjection.builder;

import com.team.mamba.atlas.userInterface.dashBoard.announcements.AnnouncementsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users.recycler_view.FindUsersRecycler;
import com.team.mamba.atlas.userInterface.dashBoard.crm.edit_add_note.EditAddNotePageSixFragment;
import com.team.mamba.atlas.userInterface.dashBoard.crm.filter_list.CrmFilterSettingsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.crm.selected_crm.SelectedCrmFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.add_business.AddBusinessFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.add_user.AddUserFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections.DescribeConnectionsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users.FindUsersFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts.SuggestedContactsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.business.BusinessProfileFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.ContactsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.DashBoardModule;
import com.team.mamba.atlas.userInterface.dashBoard.crm.main.CrmFragment;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.UserProfileFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_address_info.EditAddressFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info.EditEducationFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info.EditEmailFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_phone_info.EditPhoneFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history.EditWorkFragment;
import com.team.mamba.atlas.userInterface.welcome.ViewPagerActivityModule;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = ViewPagerActivityModule.class)
    abstract WelcomeFragment bindWelcomeFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract CrmFragment bindBusinessOpportunitiesFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract ContactsFragment bindContactsFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract InfoFragment bindDashBoardHomeFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract AnnouncementsFragment bindNotificationsFragment();

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

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract SelectedCrmFragment bindSelectedCrmFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract EditAddNotePageSixFragment bindEditAddNotePageSixFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract CrmFilterSettingsFragment bindCrmFilterSettingsFragment();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract FindUsersRecycler bindFindUsersRecycler();





}
