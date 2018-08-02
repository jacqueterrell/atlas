package com.team.mamba.atlas.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.team.mamba.atlas.R;

/**
 * Use this class to change or show your fragments
 */

public class ChangeFragments {

    public static boolean isUnitTesting = false;


    public static void addFragment(Fragment fragmentId, FragmentManager fragmentManager, String tag, Bundle args){


        if (isUnitTesting){//Robolectric cannot handle fragment animations (gets stuck in infinite loop)

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragmentId.setArguments(args);
            transaction.add(R.id.fragment_container, fragmentId, tag);
            transaction.addToBackStack(null);

            transaction.commit();

            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentId.setArguments(args);
        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right);
        transaction.add(R.id.fragment_container, fragmentId, tag);
        transaction.addToBackStack(null);

        transaction.commit();
    }


    public static void replaceFragment(Fragment fragmentId, FragmentManager fragmentManager, String tag, Bundle args){


        if (isUnitTesting){//Robolectric cannot handle fragment animations (gets stuck in infinite loop)

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragmentId.setArguments(args);
            transaction.replace(R.id.fragment_container, fragmentId, tag);
            transaction.addToBackStack(null);

            transaction.commit();

            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentId.setArguments(args);
        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, fragmentId, tag);
        transaction.addToBackStack(null);

        transaction.commit();
    }


    public static void addFragmentRemoveFromBackstack(Fragment fragmentId, FragmentManager fragmentManager, String tag, Bundle args){


        if (isUnitTesting){//Robolectric cannot handle fragment animations (gets stuck in infinite loop)

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragmentId.setArguments(args);
            transaction.add(R.id.fragment_container, fragmentId,tag);
            transaction.commit();

            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentId.setArguments(args);
        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right);
        transaction.add(R.id.fragment_container, fragmentId,tag);
        transaction.commit();

    }
}
