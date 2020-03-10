package com.idhub.wallet.auth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.magic.clientlib.ApiFactory;
import com.idhub.magic.common.parameter.MagicResponse;
import com.idhub.magic.common.ustorage.entity.BasicInfo;
import com.idhub.magic.common.ustorage.entity.IdentityArchive;
import com.idhub.magic.common.ustorage.entity.IdentityInfo;
import com.idhub.magic.common.ustorage.entity.component.Address;
import com.idhub.magic.common.ustorage.entity.component.AddressElement;
import com.idhub.magic.common.ustorage.entity.component.Name;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.UploadFileDbManager;
import com.idhub.wallet.greendao.UploadIDHubInfoDbManager;
import com.idhub.wallet.main.MainActivity;
import com.idhub.wallet.me.information.FileType;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.FileUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthInfoActivity extends BaseActivity {

    private BaseInformationLayout mBaseInformationLayout;
    private IdentityInformationLayout mIdentityInformationLayout;
    private TaxpayerInformationLayout mTaxpayerInformationLayout;
    private CreditInformationLayout mCreditInformationLayout;
    private AddressInformationLayout mAddressInformationLayout;
    private AssetsInformationLayout mAssetsInformationLayout;
    private UploadIDHubInfoEntity mUploadIDHubInfoEntity;
    private UploadIDHubInfoDbManager uploadIDHubInfoDbManager;
    private LoadingAndErrorView mLoadingAndErrorView;
    private WalletKeystore mDefaultKeystore;
    private UploadFileDbManager mUploadFileDbManager;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AuthInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDefaultKeystore = WalletManager.getDefaultKeystore();
        if (mDefaultKeystore == null) {
            finish();
            return;
        }
        setContentView(R.layout.wallet_activity_auto_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
        }
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_id_hub_information_title));

        mLoadingAndErrorView = findViewById(R.id.loading_and_error);

        InformationContentView baseInfoView = findViewById(R.id.base_info);
        mBaseInformationLayout = new BaseInformationLayout(this);
        baseInfoView.setContent(R.mipmap.wallet_base_info_icon, getString(R.string.basic_information), mBaseInformationLayout);

        InformationContentView identityInfoView = findViewById(R.id.identity_info);
        mIdentityInformationLayout = new IdentityInformationLayout(this);
        identityInfoView.setContent(R.mipmap.wallet_identity_info_icon, getString(R.string.identity_information), mIdentityInformationLayout);

        InformationContentView taxpayerInfoView = findViewById(R.id.taxpayer_info);
        mTaxpayerInformationLayout = new TaxpayerInformationLayout(this);
        taxpayerInfoView.setContent(R.mipmap.wallet_tax_info_icon, getString(R.string.taxpayer_information), mTaxpayerInformationLayout);

        InformationContentView creditInfoView = findViewById(R.id.credit_info);
        mCreditInformationLayout = new CreditInformationLayout(this);
        creditInfoView.setContent(R.mipmap.wallet_credit_info_icon, getString(R.string.credit_information), mCreditInformationLayout);

        InformationContentView addressInfoView = findViewById(R.id.address_info);
        mAddressInformationLayout = new AddressInformationLayout(this);
        addressInfoView.setContent(R.mipmap.wallet_address_info_icon, getString(R.string.address_information), mAddressInformationLayout);

        InformationContentView proofInfoView = findViewById(R.id.assets_info);
        mAssetsInformationLayout = new AssetsInformationLayout(this);
        proofInfoView.setContent(R.mipmap.wallet_assets_info_icon, getString(R.string.assets_information), mAssetsInformationLayout);


        uploadIDHubInfoDbManager = new UploadIDHubInfoDbManager();
        uploadIDHubInfoDbManager.queryById(1, operation -> {
            mUploadIDHubInfoEntity = (UploadIDHubInfoEntity) operation.getResult();
            if (mUploadIDHubInfoEntity != null) {
                mBaseInformationLayout.setUploadInfo(mUploadIDHubInfoEntity);
                mIdentityInformationLayout.setUploadInfo(mUploadIDHubInfoEntity);
                mTaxpayerInformationLayout.setUploadInfo(mUploadIDHubInfoEntity);
                mAddressInformationLayout.setUploadInfo(mUploadIDHubInfoEntity);
            }
        });
        mUploadFileDbManager = new UploadFileDbManager();
        mUploadFileDbManager.queryAll(operation -> {
            List<UploadFileEntity> entities = (List<UploadFileEntity>) operation.getResult();
            Map<String, UploadFileEntity> map = new HashMap<>();
            if (entities != null && entities.size() > 0) {
                for (UploadFileEntity entity : entities) {
                    map.put(entity.getType(), entity);
                }
                mIdentityInformationLayout.setFileInfo(map);
                mTaxpayerInformationLayout.setFileInfo(map);
                mCreditInformationLayout.setFileInfo(map);
                mAddressInformationLayout.setFileInfo(map);
                mAssetsInformationLayout.setFileInfo(map);
            }
        });
        mBaseInformationLayout.setSaveClickListener(new SaveClickListener() {
            @Override
            public void click(UploadIDHubInfoEntity uploadIDHubInfoEntity) {
                inputPasswordDialogShow(uploadIDHubInfoEntity);
            }
        });

        mIdentityInformationLayout.setSaveClickListener(new SaveClickListener() {
            @Override
            public void click(UploadIDHubInfoEntity uploadIDHubInfoEntity) {

            }
        });
    }

    private void upload(UploadIDHubInfoEntity uploadIDHubInfoEntity, String privateKey) {
        IdentityArchive identityArchive = new IdentityArchive();
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setTaxId(uploadIDHubInfoEntity.getTaxNumber());
        basicInfo.setEmail(uploadIDHubInfoEntity.getEmail());
        basicInfo.setSsn(uploadIDHubInfoEntity.getSsnNumber());

        IdentityInfo identityInfo = new IdentityInfo();
        Name name = new Name();
        name.setFirstName(uploadIDHubInfoEntity.getFirstName());
        name.setMiddleName(uploadIDHubInfoEntity.getMiddleName());
        name.setLastName(uploadIDHubInfoEntity.getLastName());
        Address address = new Address();
        address.setPostalCode(uploadIDHubInfoEntity.getPostalCode());
        ArrayList<AddressElement> elements = new ArrayList<>();

        AddressElement addressDetailElement = new AddressElement();
        addressDetailElement.name = getString(R.string.wallet_street);
        addressDetailElement.value = uploadIDHubInfoEntity.getStreet();
        elements.add(addressDetailElement);

        AddressElement addressCountryElement = new AddressElement();
        addressCountryElement.name = getString(R.string.wallet_address_country);
        addressCountryElement.value = uploadIDHubInfoEntity.getAddressCountryCode();
        elements.add(addressCountryElement);

        AddressElement addressCityElement = new AddressElement();
        addressCityElement.name = getString(R.string.wallet_city);
        addressCityElement.value = uploadIDHubInfoEntity.getCity();
        elements.add(addressCityElement);

        AddressElement addressStateElement = new AddressElement();
        addressStateElement.name = getString(R.string.wallet_state);
        addressStateElement.value = uploadIDHubInfoEntity.getState();
        elements.add(addressStateElement);

        AddressElement addressNeighborhoodElement = new AddressElement();
        addressNeighborhoodElement.name = getString(R.string.wallet_neighborhood);
        addressNeighborhoodElement.value = uploadIDHubInfoEntity.getNeighborhood();
        elements.add(addressNeighborhoodElement);

        address.setAddressSequence(elements);
        identityInfo.setName(name);
        identityInfo.setAddress(address);
        identityInfo.setGender(uploadIDHubInfoEntity.getGender());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(uploadIDHubInfoEntity.getBirthday());
            identityInfo.setBirthday(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        identityInfo.setCountry(uploadIDHubInfoEntity.getCountryIsoCode());
        identityInfo.setResidenceCountry(uploadIDHubInfoEntity.getResidenceCountryIsoCode());
        identityInfo.setIdcardNumber(uploadIDHubInfoEntity.getIdcardNumber());
        identityInfo.setPassportNumber(uploadIDHubInfoEntity.getPassportNumber());
        identityInfo.setPhoneNumber(uploadIDHubInfoEntity.getPhone());

        identityArchive.setIdentityInfo(identityInfo);
        identityArchive.setBasicInfo(basicInfo);
        IDHubCredentialProvider.setDefaultCredentials(privateKey);
        Call<MagicResponse> call = ApiFactory.getArchiveStorage().storeArchive(identityArchive, WalletManager.getDefaultAddress());
        call.enqueue(new Callback<MagicResponse>() {
            @Override
            public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
                mLoadingAndErrorView.onGone();
                MagicResponse body = response.body();
                if (body != null && body.isSuccess()) {
                    ToastUtils.showShortToast(getString(R.string.wallet_upload_success));
                    MainActivity.startAction(AuthInfoActivity.this, "upload_information");
                    finish();
                } else {
                    ToastUtils.showShortToast(getString(R.string.wallet_upload_fail));
                }
            }

            @Override
            public void onFailure(Call<MagicResponse> call, Throwable t) {
                mLoadingAndErrorView.onGone();
                ToastUtils.showShortToast(getString(R.string.wallet_upload_fail));
            }
        });

    }


    private void inputPasswordDialogShow(UploadIDHubInfoEntity uploadIDHubInfoEntity) {
        InputDialogFragment instance = InputDialogFragment.getInstance("idhub_information", getString(R.string.wallet_default_address_password), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener((data, source) -> {
            WalletInfo walletInfo = new WalletInfo(mDefaultKeystore);
            walletInfo.verifyPassword(data, new DisposableObserver<Boolean>() {
                @Override
                protected void onStart() {
                    super.onStart();
                    mLoadingAndErrorView.onLoading();
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean) {
                        submit(uploadIDHubInfoEntity, walletInfo.exportPrivateKey(data));
                    } else {
                        ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                        mLoadingAndErrorView.onGone();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                    mLoadingAndErrorView.onGone();
                }

                @Override
                public void onComplete() {

                }
            });

        });
    }

    private void submit(UploadIDHubInfoEntity uploadIDHubInfoEntity, String exportPrivateKey) {
        if (uploadIDHubInfoEntity != null) {
            if (mUploadIDHubInfoEntity == null) {
                Log.e("LYW", "click: null ");
                uploadIDHubInfoDbManager.insertData(uploadIDHubInfoEntity, null);
            } else {
                Log.e("LYW", "click: ");
                uploadIDHubInfoDbManager.update(uploadIDHubInfoEntity);
            }
            upload(uploadIDHubInfoEntity, exportPrivateKey);
        }
    }


    private void uploadFile() {
        //弹框
//        boolean isSubmit = mUploadFileEntity.isSubmit;
//        if (isSubmit) {
//            ToastUtils.showShortToast(getString(R.string.wallet_already_upload_success));
//            return;
//        }
//        File file = new File(mUploadFileEntity.getFilePath());
//        double fileSize = FileUtils.FormetMBFileSize(FileUtils.getFileSize(file));
//        Log.e("LYW", "uploadFile: " +fileSize );
//        if (fileSize > 20) {
//            ToastUtils.showShortToast(getString(R.string.wallet_file_size_20));
//            return;
//        }
//        mLoadingAndErrorView.setVisibility(View.VISIBLE);
//        IDHubCredentialProvider.setDefaultCredentials(mPrivateKey);
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//
//        String defaultAddress = NumericUtil.prependHexPrefix(mDefaultKeystore.getAddress());
//        ApiFactory.getArchiveStorage().uploadMaterial(defaultAddress, FileType.fileTypes.get(mUploadFileEntity.getType()), mUploadFileEntity.getName(), filePart).enqueue(new Callback<MagicResponse>() {
//            @Override
//            public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
//                MagicResponse magicResponse = response.body();
//                if (magicResponse != null && magicResponse.isSuccess()) {
//                    //接口提交成功之后
//                    mUploadFileEntity.isSubmit = true;
//                    mUploadFileAdapter.setDataItem(mUploadFileEntity);
//                    if (mRepeatTypesList.size() > 0) {
//                        for (String type : mRepeatTypesList) {
//                            mUploadFileDbManager.deleteByType(type);
//                        }
//                    }
//                    mUploadFileDbManager.insertData(mUploadFileEntity, operation -> {
//                        boolean completed = operation.isCompleted();
//
//                    });
//                    ToastUtils.showShortToast(getString(R.string.wallet_upload_success));
//                    mLoadingAndErrorView.setVisibility(View.GONE);
//                } else {
//                    ToastUtils.showShortToast(getString(R.string.wallet_upload_fail));
//                    mLoadingAndErrorView.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MagicResponse> call, Throwable t) {
//                Log.e("LYW", "onFailure: " + t.getMessage());
//                t.printStackTrace();
//                ToastUtils.showShortToast(getString(R.string.wallet_upload_fail));
//                mLoadingAndErrorView.setVisibility(View.GONE);
//            }
//        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                ToastUtils.showShortToast(getString(R.string.scan_camera_permission));
                finish();
            }
        }
    }
}
