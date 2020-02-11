package com.idhub.wallet.me.information;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.ImagesList.StringsListAdapter;

import java.util.List;


public class FileTypePopupWindow extends PopupWindow {
    public FileTypePopupWindow(Context context, StringsListAdapter.StringClickItem clickItem) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_select_file_type, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        StringsListAdapter stringsListAdapter = new StringsListAdapter(context);
        List<String> types = FileType.getFileTypes(context);
        stringsListAdapter.addAll(types);
        stringsListAdapter.setStringClickItem(clickItem);
        recyclerView.setAdapter(stringsListAdapter);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.wallet_white)));
        setOutsideTouchable(true);
    }
}
