package sports.mgl.mgl.Admin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import sports.mgl.mgl.BussinessLayer.Controller;
import sports.mgl.mgl.R;


public class AdminActivity extends AppCompatActivity implements GameFragment.Interface  {



    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Controller controller;
    private GameFragment gameFragment;
    private MatchFragment matchFragment;
    private UpdateFragment updateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        gameFragment=new GameFragment();
        adapter.addFragment(gameFragment, "GAME");
        matchFragment=new MatchFragment();
        adapter.addFragment(matchFragment, "MATCH");
        updateFragment=new UpdateFragment();
        adapter.addFragment(updateFragment, "UPDATE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void updateGame() {
        matchFragment.setDropDown();
        updateFragment.setListView();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
