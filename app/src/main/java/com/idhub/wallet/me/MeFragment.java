package com.idhub.wallet.me;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.me.adapter.LevelAdapter;
import com.idhub.wallet.me.model.MeLevelEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends MainBaseFragment {


    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_profile));
        titleLayout.setBackImgVisible(View.INVISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.rv_level);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LevelAdapter adapter = new LevelAdapter(getContext());
        recyclerView.setAdapter(adapter);
        MeLevelEntity meLevelEntity = new MeLevelEntity();
        meLevelEntity.level = "1";
        MeLevelEntity meLevelEntity1 = new MeLevelEntity();
        meLevelEntity1.level = "2";
        MeLevelEntity meLevelEntity2 = new MeLevelEntity();
        meLevelEntity2.level = "3";
        MeLevelEntity meLevelEntity3 = new MeLevelEntity();
        meLevelEntity3.level = "4";
        MeLevelEntity meLevelEntity4 = new MeLevelEntity();
        meLevelEntity4.level = "5";
        List<MeLevelEntity> meLevelEntities = new ArrayList<>();
        meLevelEntities.add(meLevelEntity);
        meLevelEntities.add(meLevelEntity1);
        meLevelEntities.add(meLevelEntity2);
        meLevelEntities.add(meLevelEntity3);
        meLevelEntities.add(meLevelEntity4);
        adapter.addAll(meLevelEntities);
    }

    @Override
    protected void loadData() {

    }
}
