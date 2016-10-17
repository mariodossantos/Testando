package com.weebly.taggtracker.tagtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Barbara on 17/10/2016.
 *
 * Serve para trocar as páginas da tab
 */

public class Pager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int numTabs;

        //Constructor to the class 
        public Pager(FragmentManager fm, int numTabs) {
            super(fm);
            //Initializing tab count
            this.numTabs= numTabs;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs 
            switch (position) {
                case 0:
                    tela_checklists tab1 = new tela_checklists();
                    return tab1;
                case 1:
                    tela_tags tab2 = new tela_tags();
                    return tab2;
                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs 
        @Override
        public int getCount() {
            return numTabs;
        }
    
}
