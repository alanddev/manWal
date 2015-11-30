package com.alanddev.manwal.ui;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.MessageQueue;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CategoryAdapter;
import com.alanddev.manwal.controller.CategoryController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        categoryController = new CategoryController(getApplicationContext());
        categoryController.open();
        List<Model> categories = categoryController.getAll();
        categoryController.close();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),getExCategory(categories),getInCategory(categories));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static List<Category> cateExs;
        private static List<Category> cateIns;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,List<Category> lstExCates,List<Category> lstInCates) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            cateExs = lstExCates;
            cateIns = lstInCates;
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_category, container, false);
            ListView lstCategory = (ListView)rootView.findViewById(R.id.lstcategory);
            final Integer sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            CategoryAdapter adapter;
            if(sectionNumber== Constant.EXPENSE_TYPE+1){
                adapter = new CategoryAdapter(getActivity().getApplicationContext(),cateExs);
            }else{
                adapter = new CategoryAdapter(getActivity().getApplicationContext(),cateIns);
            }
            lstCategory.setAdapter(adapter);
            lstCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Category category;
                    if(sectionNumber==Constant.EXPENSE_TYPE+1){
                        category = cateExs.get(position);
                    }else{
                        category = cateIns.get(position);
                    }
                    Intent intent=new Intent();
                    intent.putExtra(MwSQLiteHelper.COLUMN_CATE_ID, category.getId());
                    intent.putExtra(MwSQLiteHelper.COLUMN_CATE_NAME, category.getName());
                    intent.putExtra(MwSQLiteHelper.COLUMN_CATE_IMG, category.getImage());
                    getActivity().setResult(Constant.PICK_CATEGORY, intent);
                    getActivity().finish();//finishing activity
                }
            });
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Category> lstExCates;
        private List<Category> lstInCates;

        public SectionsPagerAdapter(FragmentManager fm,List<Category> lstExCates,List<Category> lstInCates) {
            super(fm);
            this.lstExCates = lstExCates;
            this.lstInCates = lstInCates;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,lstExCates,lstInCates);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Expense";
                case 1:
                    return "Income";
            }
            return null;
        }

    }

    private List<Category> getExCategory(List<Model> categories){
        List<Category> lstCategories = new ArrayList<Category>();
        for(int i=0;i<categories.size();i++){
            Category cate = (Category)categories.get(i);
            if(cate.getType()==0){
                lstCategories.add(cate);
            }
        }
        return lstCategories;
    }
    private List<Category> getInCategory(List<Model> categories){
        List<Category> lstCategories = new ArrayList<Category>();
        for(int i=0;i<categories.size();i++){
            Category cate = (Category)categories.get(i);
            if(cate.getType()==1){
                lstCategories.add(cate);
            }
        }
        return lstCategories;
    }

}
