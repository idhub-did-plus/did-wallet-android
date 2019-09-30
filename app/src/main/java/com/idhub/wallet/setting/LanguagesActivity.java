package com.idhub.wallet.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.LocaleSharpreferences;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletNodeSelectedObservable;
import com.idhub.wallet.utils.LocalUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LanguagesActivity extends AppCompatActivity {

    private NodeSettingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_languages);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, LanguagesActivity.class);
        context.startActivity(intent);
    }
    private void initView() {
        Map<String, String> languagesMap = LanguagesManager.languages;
        List<String> languages = new ArrayList<>(languagesMap.values());

        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_language));
        titleLayout.setFirstTextAndClickCallBack(getString(R.string.wallet_save), () -> {
            //保存语言
            String node = mAdapter.getNode();
            for (String s : languagesMap.keySet()) {
                if (node.equals(languagesMap.get(s))) {
                    LocaleSharpreferences.getInstance().setLocalLanguage(s);
                    Intent intent = new Intent(LanguagesActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    startActivity(intent);
                }
            }

            finish();
        });
        String language = LocaleSharpreferences.getInstance().getLocalLanguage();
        RecyclerView recyclerView = findViewById(R.id.language_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String selectPath = languagesMap.get(language);
        mAdapter = new NodeSettingAdapter(this, selectPath);
        recyclerView.setAdapter(mAdapter);
        mAdapter.addDatas(languages);
    }
}
