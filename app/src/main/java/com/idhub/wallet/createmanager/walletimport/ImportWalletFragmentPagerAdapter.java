package com.idhub.wallet.createmanager.walletimport;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idhub.wallet.R;

public class ImportWalletFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mItems;
    private Fragment mCurrentFragment;
    private Context mContext;

    public ImportWalletFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mItems = new String[]{context.getResources().getString(R.string.wallet_keystore), context.getResources().getString(R.string.wallet_mnemonic),context.getResources().getString(R.string.wallet_private_key)};
    }

    @Override
    public Fragment getItem(int position) {
        return ImportWalletFragmentFactory.createChannelFragment(mContext,mItems[position]);
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
