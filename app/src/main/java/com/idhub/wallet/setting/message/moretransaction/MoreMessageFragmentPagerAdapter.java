package com.idhub.wallet.setting.message.moretransaction;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idhub.wallet.R;
import com.idhub.wallet.setting.message.MessageChannelFragmentFactory;

public class MoreMessageFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mItems;
    private Fragment mCurrentFragment;
    private Context mContext;

    public MoreMessageFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
//        mItems = new String[]{context.getResources().getString(R.string.wallet_ETH), context.getResources().getString(R.string.wallet_ERC20),context.getString(R.string.wallet_ST)};
        mItems = new String[]{context.getResources().getString(R.string.wallet_ETH), context.getResources().getString(R.string.wallet_ERC20)};
    }

    @Override
    public Fragment getItem(int position) {
        return MessageChannelFragmentFactory.createChannelFragment(mContext,mItems[position]);
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
