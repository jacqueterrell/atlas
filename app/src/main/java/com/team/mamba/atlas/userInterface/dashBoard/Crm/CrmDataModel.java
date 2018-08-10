package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoViewModel;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class CrmDataModel {

    private AppDataManager dataManager;

    @Inject
    public CrmDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void getAllOpportunities(CrmViewModel viewModel){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.BUS_NOTES_COLLECTION)
                .whereEqualTo("authorID",dataManager.getSharedPrefs().getUserId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<CrmNotes> crmNotes = task.getResult().toObjects(CrmNotes.class);

                        Collections.sort(crmNotes,(o1,o2) -> Long.compare(o2.getAdjustedTimeStamp(), o1.getAdjustedTimeStamp()));

                        viewModel.setCrmNotesList(crmNotes);
                        viewModel.getNavigator().onCrmDataReturned();

                    } else {

                        Logger.e(task.getException().getMessage());
                    }

                });
    }



}
