package com.idhub.wallet.auth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthInfoActivity extends BaseActivity implements SaveClickListener {

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
    private String psd;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AuthInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDefaultKeystore = WalletManager.getDefaultKeystore();
        if (mDefaultKeystore == null) {
            ToastUtils.showShortToast(getString(R.string.wallet_upload_upgrade_tip));
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
        @SuppressLint("WrongViewCast") TitleLayout titleLayout = findViewById(R.id.title);
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
        mBaseInformationLayout.setSaveClickListener(this);
        mIdentityInformationLayout.setSaveClickListener(this);
        mTaxpayerInformationLayout.setSaveClickListener(this);
        mCreditInformationLayout.setSaveClickListener(this);
        mAddressInformationLayout.setSaveClickListener(this);
        mAssetsInformationLayout.setSaveClickListener(this);
    }

    private void inputPasswordDialogShow(UploadDataParam uploadDataParam) {
        WalletInfo walletInfo = new WalletInfo(mDefaultKeystore);
        if (!TextUtils.isEmpty(psd)) {
            submit(uploadDataParam, walletInfo.exportPrivateKey(psd));
            return;
        }
        InputDialogFragment instance = InputDialogFragment.getInstance("idhub_information", getString(R.string.wallet_default_address_password), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener((data, source) -> {
            walletInfo.verifyPassword(data, new DisposableObserver<Boolean>() {
                @Override
                protected void onStart() {
                    super.onStart();
                    mLoadingAndErrorView.onLoading();
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean) {
                        psd = data;
                        submit(uploadDataParam, walletInfo.exportPrivateKey(data));
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


    private void submit(UploadDataParam uploadDataParam, String exportPrivateKey) {
        //更新数据库
        mLoadingAndErrorView.onLoading();
        if (uploadDataParam.uploadIDHubInfoEntity != null) {
            if (mUploadIDHubInfoEntity == null) {
                uploadIDHubInfoDbManager.insertData(uploadDataParam.uploadIDHubInfoEntity, null);
            } else {
                uploadIDHubInfoDbManager.update(uploadDataParam.uploadIDHubInfoEntity);
            }
        }
        List<UploadFileEntity> entities = uploadDataParam.entities;
        if (entities != null && entities.size() > 0) {
            mUploadFileDbManager.insertListData(entities, null);
        }
        upload(uploadDataParam, exportPrivateKey);
    }

    private void upload(UploadDataParam uploadDataParam, String privateKey) {
        //初始化
        IDHubCredentialProvider.setDefaultCredentials(privateKey);
        ExecutorService exec = Executors.newCachedThreadPool();

        UploadIDHubInfoEntity uploadIDHubInfoEntity = uploadDataParam.uploadIDHubInfoEntity;
        List<UploadFileEntity> fileEntities = uploadDataParam.entities;
        //计数器
        int netNum = 0;
        if (uploadIDHubInfoEntity != null) {
            netNum++;
        }
        if (fileEntities != null && fileEntities.size() > 0) {
            netNum = netNum + fileEntities.size();
        }
        CountDownLatch countDownLatch = new CountDownLatch(netNum);

        //创建线程
        ArrayList<Thread> threads = new ArrayList<>();
        //个人信息上传
        if (uploadIDHubInfoEntity != null) {
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
            //网络请求，开启异步线程
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ApiFactory.getArchiveStorage().storeArchive(identityArchive, WalletManager.getDefaultAddress()).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            }));
        }

        //文件
        if (fileEntities != null && fileEntities.size() > 0) {

            for (UploadFileEntity fileEntity : fileEntities) {
                threads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File file = new File(fileEntity.getFilePath());
                            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileEntity.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                            ApiFactory.getArchiveStorage().uploadMaterial(WalletManager.getDefaultAddress(), FileType.fileTypes.get(fileEntity.getType()), fileEntity.getName(), filePart).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
                }));
            }
        }
        for (Thread thread : threads) {
            exec.execute(thread);
        }
        //计时器，所有线程执行完毕，更新提示ui
        exec.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingAndErrorView.onGone();
                            ToastUtils.showShortToast(getString(R.string.wallet_upload_finish));
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
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

    //保存回调事件
    @Override
    public void click(UploadDataParam uploadDataParam) {
        inputPasswordDialogShow(uploadDataParam);
    }
}
