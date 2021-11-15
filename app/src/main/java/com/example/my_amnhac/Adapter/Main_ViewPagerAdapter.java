package com.example.my_amnhac.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.my_amnhac.Fragment.Fragment_timkiem;
import com.example.my_amnhac.Fragment.Fragment_trangchu;

import java.util.ArrayList;

public class Main_ViewPagerAdapter extends FragmentStateAdapter {

    private String[] title = new String[]{"Home","Seach"};

    public Main_ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return new Fragment_trangchu();
            case 1 :
                return new Fragment_timkiem();
        }
        return new Fragment_trangchu();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Nullable
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Home";
                break;
            case 2:
                title = "Seach";
                break;
        }
        return title;
    }
}
