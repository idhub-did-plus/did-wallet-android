package com.idhub.wallet.me.information;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.SelectUploadFileTypeDialogFragment;
import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.wallet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_BOOTOM_VIEW = 1;
    private final int ITEM_OTHER_VIEW = 2;
    private Context mContext;
    private LayoutInflater inflater;
    private List<UploadFileEntity> uploadFileEntities;

    public UploadFileAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        uploadFileEntities = new ArrayList<>();
    }

    public List<UploadFileEntity> getDatas() {
        return uploadFileEntities;
    }

    public void setDataItem(UploadFileEntity uploadFileEntity){
        notifyItemChanged(uploadFileEntities.size() - 1, uploadFileEntity);
    }
    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        if (position == itemCount - 1) {
            return ITEM_BOOTOM_VIEW;
        } else {
            return ITEM_OTHER_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == ITEM_BOOTOM_VIEW) {
            viewHolder = new UploadFileAdapterBottomViewHolder(inflater.inflate(R.layout.wallet_upload_list_bootom_item, parent, false));
        } else {
            viewHolder = new UploadFileAdapterOtherViewHolder(inflater.inflate(R.layout.wallet_upload_list_item, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UploadFileAdapterBottomViewHolder) {

        } else if (holder instanceof UploadFileAdapterOtherViewHolder) {
            UploadFileAdapterOtherViewHolder otherViewHolder = (UploadFileAdapterOtherViewHolder) holder;
            UploadFileEntity uploadFileEntity = uploadFileEntities.get(position);
            String filePath = uploadFileEntity.getFilePath();
            if (filePath.endsWith("jpg") || filePath.endsWith("jpeg") || filePath.endsWith("png")) {
                otherViewHolder.mFileImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(filePath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(otherViewHolder.mFileImage);
            } else {
                otherViewHolder.mFileImage.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(filePath)) {
                otherViewHolder.mFilePathView.setVisibility(View.GONE);
            } else {
                otherViewHolder.mFilePathView.setVisibility(View.VISIBLE);
                otherViewHolder.mFilePathView.setText(filePath);
            }
            otherViewHolder.mNameEditView.setText(uploadFileEntity.getName());
            String type = uploadFileEntity.getType();
            if (!TextUtils.isEmpty(type)) {
                otherViewHolder.mSpinner.setSelection(otherViewHolder.mTypesMap.get(type));
            } else {
                otherViewHolder.mSpinner.setSelection(0);
            }
            if (uploadFileEntity.isSubmit) {
                otherViewHolder.mSpinner.setEnabled(false);
                otherViewHolder.mNameEditView.setFocusable(false);
                otherViewHolder.mNameEditView.setFocusableInTouchMode(false);
            } else {
                otherViewHolder.mSpinner.setEnabled(true);
                otherViewHolder.mNameEditView.setFocusable(true);
                otherViewHolder.mNameEditView.setFocusableInTouchMode(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return uploadFileEntities.size() + 1;
    }

    public class UploadFileAdapterOtherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SelectUploadFileTypeDialogFragment.SelectUploadFileTypeDialogFragmentListener {

        private ImageView mFileImage;
        private HashMap<String, Integer> mTypesMap;
        private EditText mNameEditView;
        private TextView mFilePathView;
        private Spinner mSpinner;
        private SelectUploadFileTypeDialogFragment mSelectUploadFileTypeDialogFragment;

        public UploadFileAdapterOtherViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameEditView = itemView.findViewById(R.id.et_information);
            mFilePathView = itemView.findViewById(R.id.file_path);
            mFileImage = itemView.findViewById(R.id.file_image);
            ImageView deleteImage = itemView.findViewById(R.id.file_delete);
            deleteImage.setOnClickListener(this);
            itemView.findViewById(R.id.btn_select_file).setOnClickListener(this);
            mSpinner = itemView.findViewById(R.id.spinner_type);
            String[] types = mContext.getResources().getStringArray(R.array.identity_file_spinner);
            mTypesMap = new HashMap<>();
            for (int i = 0; i < types.length; i++) {
                mTypesMap.put(types[i], i);
            }
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String type = types[position];
                    int layoutPosition = getLayoutPosition();
                    UploadFileEntity uploadFileEntity = uploadFileEntities.get(layoutPosition);
                    uploadFileEntity.setType(type);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mNameEditView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int layoutPosition = getLayoutPosition();
                    UploadFileEntity uploadFileEntity = uploadFileEntities.get(layoutPosition);
                    uploadFileEntity.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.file_delete:
                    int layoutPosition = getLayoutPosition();
                    uploadFileEntities.remove(layoutPosition);
                    notifyDataSetChanged();
                    break;
                case R.id.btn_select_file:
                    //图片
                    mSelectUploadFileTypeDialogFragment = SelectUploadFileTypeDialogFragment.getInstance("adapter");
                    mSelectUploadFileTypeDialogFragment.show(((UploadFileActivity) mContext).getSupportFragmentManager(), "select_upload_file");
                    mSelectUploadFileTypeDialogFragment.setUploadFileTypeDialogFragmentListeneristener(this);
                    break;
            }
        }

        @Override
        public void selectFileResult(String path, String source) {
            if (TextUtils.isEmpty(path)) {
                return;
            }
            if (path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png")) {
                mFileImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(mFileImage);
            } else {
                mFileImage.setVisibility(View.GONE);
            }
            mFilePathView.setVisibility(View.VISIBLE);
            mFilePathView.setText(path);
            int layoutPosition = getLayoutPosition();
            uploadFileEntities.get(layoutPosition).setFilePath(path);
            mSelectUploadFileTypeDialogFragment.dismiss();
        }
    }

    public class UploadFileAdapterBottomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public UploadFileAdapterBottomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (uploadFileEntities.size() > 0) {
                UploadFileEntity fileEntity = uploadFileEntities.get(uploadFileEntities.size() - 1);
                if (!fileEntity.isSubmit) {
                    ToastUtils.showLongToast(mContext.getString(R.string.wallet_upload_file_add_tip));
                    return;
                }
//                String filePath = fileEntity.getFilePath();
//                String name = fileEntity.getName();
//                String type = fileEntity.getType();
//                //检查前面的选择之后在可以进行之后的新增
//                if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(name) || TextUtils.isEmpty(type)) {
//                    ToastUtils.showLongToast(mContext.getString(R.string.wallet_upload_file_add_tip));
//                    return;
//                }
            }
            uploadFileEntities.add(new UploadFileEntity());
            notifyDataSetChanged();
        }
    }
}
