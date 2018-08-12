package com.team.mamba.atlas.userInterface.dashBoard.announcements;

import com.team.mamba.atlas.data.model.api.Announcements;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementsViewModel extends BaseViewModel<AnnouncementsNavigator> {

    private AnnouncementsDataModel dataModel;
    private static List<Announcements> announcementsList = new ArrayList<>();


    public void setDataModel(AnnouncementsDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setAnnouncementsList(List<Announcements> announcementsList) {
        AnnouncementsViewModel.announcementsList = announcementsList;
    }

    public List<Announcements> getAnnouncementsList() {
        return announcementsList;
    }

    public void requestAnnouncements(AnnouncementsViewModel viewModel){

        dataModel.requestAnnouncements(viewModel);
    }
}
