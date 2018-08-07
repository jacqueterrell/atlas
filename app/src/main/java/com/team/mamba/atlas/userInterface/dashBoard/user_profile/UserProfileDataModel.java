package com.team.mamba.atlas.userInterface.dashBoard.user_profile;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.data.model.TestProfile;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoViewModel;
import com.team.mamba.atlas.utils.AppConstants;

import junit.framework.Test;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class UserProfileDataModel {


    private AppDataManager dataManager;

    @Inject
    public UserProfileDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }



    public void getUserDetails(UserProfileViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.USERS_COLLECTION)
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        viewModel.setUserProfile(userProfiles.get(0));
                        viewModel.getNavigator().setUserDetails();

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }


    public void getBusinessDetails(UserProfileViewModel viewModel){


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String savedUserId = dataManager.getSharedPrefs().getUserId();
        BusinessProfile profile = new BusinessProfile();


        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .whereEqualTo("id", savedUserId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {


                            try{
                                String code = document.getData().get("code").toString();
                                profile.setCode(code);
                            }catch (Exception e){Logger.e(e.getMessage()); }

                            try{
                                String name = document.getData().get("name").toString();
                                profile.setName(name);
                            }catch (Exception e){Logger.e(e.getMessage()); }


                            try{
                                String contactName = document.getData().get("contactName").toString();
                                profile.setContactName(contactName);
                            }catch (Exception e){Logger.e(e.getMessage()); }


                            try{
                                String email = document.getData().get("email").toString();
                                profile.setEmail(email);
                            }catch (Exception e){Logger.e(e.getMessage()); }

                            try{
                                String phone = document.getData().get("phone").toString();
                                profile.setPhone(phone);
                            }catch (Exception e){Logger.e(e.getMessage()); }


                            try{
                                String fax = document.getData().get("fax").toString();
                                profile.setFax(fax);
                            }catch (Exception e){Logger.e(e.getMessage()); }

                            try{
                                String street = document.getData().get("street").toString();
                                profile.setStreet(street);
                            }catch (Exception e){Logger.e(e.getMessage()); }

                            try{
                                String cityStateZip = document.getData().get("cityStateZip").toString();
                                profile.setCityStateZip(cityStateZip);
                            }catch (Exception e){Logger.e(e.getMessage()); }

                            try{
                                String imageUrl = document.getData().get("imageUrl").toString();
                                profile.setImageUrl(imageUrl);
                            }catch (Exception e){Logger.e(e.getMessage()); }


//                            try{
//                                document.do
//                                Map<String,String> announcements = document.getData().get("cityStateZip");
//                                profile.setCityStateZip(cityStateZip);
//                            }catch (Exception e){Logger.e(e.getMessage()); }
//
//                            try{
//                                String cityStateZip = document.getData().get("cityStateZip").toString();
//                                profile.setCityStateZip(cityStateZip);
//                            }catch (Exception e){Logger.e(e.getMessage()); }
//
//                            try{
//                                String cityStateZip = document.getData().get("cityStateZip").toString();
//                                profile.setCityStateZip(cityStateZip);
//                            }catch (Exception e){Logger.e(e.getMessage()); }
//
//                            try{
//                                String cityStateZip = document.getData().get("cityStateZip").toString();
//                                profile.setCityStateZip(cityStateZip);
//                            }catch (Exception e){Logger.e(e.getMessage()); }
//
//

                        }



//                            List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        viewModel.setBusinessProfile(profile);
                        viewModel.getNavigator().setBusinessProfile();

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }
}
