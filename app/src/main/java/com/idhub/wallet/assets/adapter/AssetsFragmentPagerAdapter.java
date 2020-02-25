package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.idhub.wallet.assets.AssetsChannelFragmentFactory;
import com.idhub.wallet.main.MainChannelFragmentFactory;

public class AssetsFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mItems;
    private Fragment mCurrentFragment;
    private Context mContext;

    public AssetsFragmentPagerAdapter(FragmentManager fm, Context context, String[] strings) {
        super(fm);
        mContext = context;
        mItems = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return AssetsChannelFragmentFactory.createChannelFragment(mContext,mItems[position]);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mItems[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
