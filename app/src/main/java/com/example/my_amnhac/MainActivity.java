package com.example.my_amnhac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.transition.FragmentTransitionSupport;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import com.example.my_amnhac.Adapter.Main_ViewPagerAdapter;
import com.example.my_amnhac.Fragment.Fragment_timkiem;
import com.example.my_amnhac.Fragment.Fragment_trangchu;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Main_ViewPagerAdapter main_viewPagerAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] title = new String[]{"Home","Seach"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        init();
    }

    private void init(){
        main_viewPagerAdapter = new Main_ViewPagerAdapter(this);

        viewPager2.setAdapter(main_viewPagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(title[position]))).attach();
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.icontrangchu);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.iconsearch);
    }

    private void anhxa(){
        tabLayout = findViewById(R.id.myTablayout);
        viewPager2 = findViewById(R.id.myViewpager);
    }
}