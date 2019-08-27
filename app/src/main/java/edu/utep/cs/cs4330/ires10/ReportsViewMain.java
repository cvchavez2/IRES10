    package edu.utep.cs.cs4330.ires10;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

    public class ReportsViewMain extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_view_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_child_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("View Reports");

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        TabItem tabChats = findViewById(R.id.tabList);
        TabItem tabStatus = findViewById(R.id.tabMap);
        viewPager = findViewById(R.id.viewPager);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//        Or you can use the code below after the deprecated setOnTabSelectedListener
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
        public boolean onCreateOptionsMenu(Menu menu) {
            //Create options menu
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.reportsviewmain, menu);
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            //respond to menu item selection
            switch (item.getItemId()) {
                case R.id.about:
//                startActivity(new Intent(this, About.class));
                    break;
                case R.id.help:
//                startActivity(new Intent(this, Help.class));
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
}
