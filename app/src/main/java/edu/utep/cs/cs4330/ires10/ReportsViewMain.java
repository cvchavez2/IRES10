    package edu.utep.cs.cs4330.ires10;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReportsViewMain extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_view_main);
        setTitle("View Reports");

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        TabItem tabChats = findViewById(R.id.tabList);
        TabItem tabStatus = findViewById(R.id.tabMap);
        viewPager = findViewById(R.id.viewPager);


        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        Or you can use the code below after the deprecated setOnTabSelectedListener
//        tabLayout.addOnTabSelectedListener(
//                new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
}
