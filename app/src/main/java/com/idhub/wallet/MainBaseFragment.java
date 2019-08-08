package com.idhub.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class MainBaseFragment extends Fragment {
    //标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
    //在试图未初始化的时候，在lazyoad当中就使用的话，就会有空指针的异常
    private boolean isPrepared;
    //标志当前页面是否可见
    private boolean isVisible;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //懒加载
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    private void onInvisible() {
    }

    private void onVisible() {
        lazyLoad();
    }

    private void lazyLoad() {
        if(!isVisible || !isPrepared){
            return;
        }
        isPrepared = false;
        loadData();
    }
    protected abstract void loadData();
}
