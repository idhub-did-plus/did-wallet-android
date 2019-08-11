package com.idhub.wallet.wallet.export;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.idhub.wallet.MainChannelFragmentFactory;
import com.idhub.wallet.R;

public class ExportWalletFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mItems;
    private Fragment mCurrentFragment;
    private Context mContext;

    public ExportWalletFragmentPagerAdapter(FragmentManager fm, Context context,String[] items) {
        super(fm);
        mContext = context;
        if (items.length > 0) {
            mItems = items;
        } else {
            mItems = new String[]{context.getResources().getString(R.string.wallet_keystore), context.getResources().getString(R.string.wallet_QR_code)};
        }
    }

    @Override
    public Fragment getItem(int position) {
        return ExportChannelFragmentFactory.createChannelFragment(mContext,mItems[position]);
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
