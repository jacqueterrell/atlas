package com.team.mamba.atlas.userInterface.dashBoard.announcements;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.Announcements;
import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class AnnouncementsDataModel {

    private AppDataManager dataManager;

    @Inject
    public AnnouncementsDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void requestAnnouncements(AnnouncementsViewModel viewModel){

        getAllBusinesses(viewModel);
    }


    private void getAllBusinesses(AnnouncementsViewModel viewModel){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> announcementIdList = new ArrayList<>();
        String userId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects(BusinessProfile.class);

                        for (BusinessProfile profile : businessProfiles){

                            if (profile.getContacts().containsKey(userId)){

                                for (Map.Entry<String,String> entry : profile.getAnnouncements().entrySet()){

                                    String id = entry.getKey();

                                    announcementIdList.add(id);
                                }

                            }

                        }

                        getAllAnnouncements(viewModel,announcementIdList);


                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task.getException().getMessage());
                    }

                });
    }

    private void getAllAnnouncements(AnnouncementsViewModel viewModel,List<String> announcementIdList){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Announcements> userAnnouncements = new ArrayList<>();

        db.collection(AppConstants.ANNOUNCEMENTS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<Announcements> announcementsList = task.getResult().toObjects(Announcements.class);

                        for (Announcements entry : announcementsList){

                            for (String id : announcementIdList){

                                if (id.equals(entry.getId())){

                                    userAnnouncements.add(entry);
                                }
                            }
                        }

                        viewModel.setAnnouncementsList(userAnnouncements);
                        viewModel.getNavigator().onAnnouncementsReturned();

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task.getException().getMessage());

                    }

                });
    }
}
