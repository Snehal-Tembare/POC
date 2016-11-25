package com.example.synerzip.poc;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class ActionBarActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private String TAG="ActionBarActivity";

    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mViewPager= (ViewPager) findViewById(R.id.view_pager);
        mTabLayout= (TabLayout) findViewById(R.id.tablayout);

        mTabLayout.addTab(mTabLayout.newTab().setText("Latest"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Top Rated"));
        mTabLayout.addTab(mTabLayout.newTab().setText("NearBy"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        MyAdapter adapter=new MyAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());


        mViewPager.setAdapter(adapter);
        mTabLayout.setOnTabSelectedListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.v(TAG,"onTabSelected");
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.v(TAG,"onTabUnselected");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.v(TAG,"onTabReselected");
    }

      public class MyAdapter extends FragmentStatePagerAdapter{
        int tabCount;
          private String[] tabs=new String[]{"Latest","Top Rated","NearBy"};

        public MyAdapter(FragmentManager fm,int tabCount){
            super(fm);
            this.tabCount=tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new LatestFragment();
                case 1:return new TopFragment();
                case 2:return new NearByFragment();
                default:
                     return null;

            }

        }

        @Override
        public int getCount() {
            return tabCount;
        }

          @Override
          public CharSequence getPageTitle(int position) {
              return tabs[position];
          }
      }


}
