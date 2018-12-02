package com.konselingperkawinan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Samuel JL on 07-Apr-18.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;
            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;
            case 2:
                ModulsFragment modulsFragment = new ModulsFragment();
                return modulsFragment;
            case 3:
                FaqsFragment faqsFragment = new FaqsFragment();
                return faqsFragment;
            default:
                return null;
        }

       // return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position)
    {
        switch(position) {
            case 0: return "KONTAK";
            case 1: return "CHATS";
            case 2: return "MODUL";
            case 3: return "FAQ";
            default: return null;
        }
    }
}
