package com.idhub.wallet.dapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.idhub.wallet.BuildConfig;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.dapp.entity.Address;
import com.idhub.wallet.dapp.entity.Message;
import com.idhub.wallet.dapp.entity.Transaction;
import com.idhub.wallet.dapp.entity.TypedData;
import com.idhub.wallet.dapp.util.Hex;
import com.idhub.wallet.dapp.view.DappBrowserSwipeInterface;
import com.idhub.wallet.dapp.view.DappBrowserSwipeLayout;
import com.idhub.wallet.dapp.view.SignMessageDialog;
import com.idhub.wallet.dapp.web3.OnSignMessageListener;
import com.idhub.wallet.dapp.web3.OnSignPersonalMessageListener;
import com.idhub.wallet.dapp.web3.OnSignTransactionListener;
import com.idhub.wallet.dapp.web3.OnSignTypedMessageListener;
import com.idhub.wallet.dapp.web3.Web3View;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.transaction.EthereumSign;
import com.idhub.wallet.didhub.transaction.EthereumTransaction;
import com.idhub.wallet.network.C;
import com.idhub.wallet.utils.ToastUtils;

import java.math.BigInteger;

import io.reactivex.observers.DisposableObserver;

public class Web3Activity extends AppCompatActivity implements OnSignMessageListener, OnSignPersonalMessageListener, OnSignTransactionListener, OnSignTypedMessageListener, View.OnClickListener, DappBrowserSwipeInterface, InputDialogFragment.InputDialogFragmentListener {

    private Web3View mWeb3View;
    private String mUrlStr;
    private ProgressBar mProgressBar;
    private DappBrowserSwipeLayout mSwipeRefreshLayout;
    private SignMessageDialog mDialog;
    private String mAddress;
    private LoadingAndErrorView mLoadingAndErrorView;
    private final String SIGN = "Sign";
    private final String SIGN_PERSONAL = "SignPersonal";
    private final String SIGN_TRANSACTION = "SignTransaction";
    private Message<String> mCurrentSignMessage;
    private Transaction mCurrentTransaction;
    private String mChainID = "1";
    private String mRpcUrl = C.API_BASE;
    private TitleLayout mTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mUrlStr = intent.getStringExtra("data");
        if (TextUtils.isEmpty(mUrlStr)) {
            finish();
            return;
        }
        setContentView(R.layout.wallet_activity_web3);
        initView();
        initData();
    }

    private void initData() {
        mAddress = WalletManager.getCurrentKeyStore().getAddress();
        mWeb3View.loadUrl(mUrlStr);
        mWeb3View.requestFocus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        mWeb3View.setChainId(Integer.parseInt(mChainID));
        mWeb3View.setRpcUrl(mRpcUrl);
        mWeb3View.setWalletAddress(new Address(mAddress));
        mWeb3View.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitleLayout.setTitle(title);
            }
        });
        mWeb3View.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWeb3View.setOnSignMessageListener(this);
        mWeb3View.setOnSignPersonalMessageListener(this);
        mWeb3View.setOnSignTransactionListener(this);
        mWeb3View.setOnSignTypedMessageListener(this);
    }

    private void initView() {
        mWeb3View = findViewById(R.id.web3_view);
        mTitleLayout = findViewById(R.id.title);
        mProgressBar = findViewById(R.id.progressBar);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setRefreshInterface(this);
        ImageView mImageBack = findViewById(R.id.web_view_back);
        mImageBack.setOnClickListener(this);
        ImageView mImageGo = findViewById(R.id.web_view_go);
        mImageGo.setOnClickListener(this);
        ImageView mImageRefresh = findViewById(R.id.web_view_refresh);
        mImageRefresh.setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);

    }

    public static void startAction(Context context, String url) {
        Intent intent = new Intent(context, Web3Activity.class);
        intent.putExtra("data", url);
        context.startActivity(intent);
    }

    @Override
    public void onSignMessage(Message<String> message) {
        mCurrentSignMessage = message;
        showSignDialog(message,SIGN);
    }

    @Override
    public void onSignPersonalMessage(Message<String> message) {
        mCurrentSignMessage = message;
        showSignDialog(message,SIGN_PERSONAL);

    }

    @Override
    public void onSignTransaction(Transaction transaction) {
        mCurrentTransaction = transaction;
        Message<String> message = new Message<>(mCurrentTransaction.toString(), mUrlStr, 0L);
        showSignDialog(message, SIGN_TRANSACTION);
    }

    @Override
    public void onSignTypedMessage(Message<TypedData[]> message) {
       //TODO:

    }

    private void showSignDialog(Message<String> message,String source) {
        try {
            mDialog = new SignMessageDialog(this, message);
            mDialog.setAddress(mAddress);
            if (SIGN_PERSONAL.equals(source)) {
                mDialog.setMessage(Hex.hexToUtf8(message.value));
            }
            mDialog.setOnApproveListener(v -> {
                mDialog.dismiss();
                showPasswordDialog(source);
            });
            mDialog.setOnRejectListener(v -> {
                mWeb3View.onSignCancel(message);
                mDialog.dismiss();
            });
            mDialog.show();
        } catch (Exception e) {
            ToastUtils.showShortToast(e.getMessage());
        }
    }

    private void showPasswordDialog(String source) {
        InputDialogFragment instance = InputDialogFragment.getInstance(source, getString(R.string.wallet_please_input_password), InputType.TYPE_CLASS_TEXT);
        instance.show(getSupportFragmentManager(), "input_dialog_fragment");
        instance.setInputDialogFragmentListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_view_back:
                goToPreviousPage();
                break;
            case R.id.web_view_go:
                goToNextPage();
                break;
            case R.id.web_view_refresh:
                mWeb3View.reload();
                break;
        }
    }


    private void goToPreviousPage() {
        if (mWeb3View.canGoBack()) {
            mWeb3View.goBack();
        }
    }

    private void goToNextPage() {
        if (mWeb3View.canGoForward()) {
            mWeb3View.goForward();
        }
    }

    @Override
    public void RefreshEvent() {
        if (mWeb3View.getScrollY() == 0) {
            mWeb3View.reload();
        }
    }

    @Override
    public int getCurrentScrollPosition() {
        return mWeb3View.getScrollY();
    }

    @Override
    public void inputConfirm(String data, String source) {
        WalletInfo walletInfo = new WalletInfo(WalletManager.findKeystoreByAddress(mAddress));
        DisposableObserver<Boolean> observer = new DisposableObserver<Boolean>() {
            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                   //密码正确 签名
                    if (SIGN_PERSONAL.equals(source)) {
                        signPersonalMessage(walletInfo, data);
                    } else if (SIGN.equals(source)) {
                        signMessage(walletInfo, data);
                    } else if (SIGN_TRANSACTION.equals(source)) {
                        signTransaction(walletInfo, data);
                    }
                    mLoadingAndErrorView.setVisibility(View.GONE);
                } else {
                    mLoadingAndErrorView.setVisibility(View.GONE);
                    ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
        walletInfo.verifyPassword(data,observer);
    }

    private void signTransaction(WalletInfo walletInfo, String data) {
        String signedTx = new EthereumTransaction(new BigInteger(String.valueOf(mCurrentTransaction.nonce)), mCurrentTransaction.gasPrice,
                new BigInteger(String.valueOf(mCurrentTransaction.gasLimit)),
                mCurrentTransaction.recipient.toString(), mCurrentTransaction.value, "")
                .signTransaction(mChainID, walletInfo.exportPrivateKey(data)).getSignedTx();
        Log.e("LYW", "onNext:SIGN_TRANSACTION_SUCCESS " + signedTx);
        mWeb3View.onSignTransactionSuccessful(mCurrentTransaction,signedTx);
    }

    private void signMessage(WalletInfo walletInfo, String data) {
        String sign = EthereumSign.sign(mCurrentSignMessage.value, walletInfo.exportPrivateKey(data));
        Log.e("LYW", "onNext:SIGN_SUCCESS " + sign);
        mWeb3View.onSignMessageSuccessful(mCurrentSignMessage, sign);
    }

    private void signPersonalMessage(WalletInfo walletInfo, String data) {
        String sign = EthereumSign.personalSign(mCurrentSignMessage.value,walletInfo.exportPrivateKey(data));
        Log.e("LYW", "onNext:SIGN_PERSONAL_SUCCESS " + sign);
        mWeb3View.onSignPersonalMessageSuccessful(mCurrentSignMessage, sign);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeb3View.destroy();
    }
}
