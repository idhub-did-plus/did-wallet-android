package com.idhub.wallet.setting.message.idhub;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.greendao.IdHubMessageDbManager;
import com.idhub.base.greendao.entity.IdHubMessageEntity;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdHubMessageFragment extends Fragment implements AsyncOperationListener {


    private IdHubMessageAdapter mIdHubMessageAdapter;

    public IdHubMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_id_hub_message, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        new IdHubMessageDbManager().queryAll(this);
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.idhub_message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIdHubMessageAdapter = new IdHubMessageAdapter(getContext());
        recyclerView.setAdapter(mIdHubMessageAdapter);
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        List<IdHubMessageEntity> idHubMessageEntities = (List<IdHubMessageEntity>) operation.getResult();
        if (idHubMessageEntities != null && idHubMessageEntities.size() > 0) {
            mIdHubMessageAdapter.addDatas(idHubMessageEntities);
        }
    }
}
