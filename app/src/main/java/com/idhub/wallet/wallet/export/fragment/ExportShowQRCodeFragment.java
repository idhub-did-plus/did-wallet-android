package com.idhub.wallet.wallet.export.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.idhub.wallet.R;
import com.idhub.wallet.utils.QRCodeEncoder;
import com.idhub.wallet.utils.ToastUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExportShowQRCodeFragment extends Fragment implements View.OnClickListener {


    private ImageView mQRCode;
    private String mData;

    public ExportShowQRCodeFragment() {
        // Required empty public constructor
    }
    public static ExportShowQRCodeFragment newInstance(String data) {
        ExportShowQRCodeFragment fragment = new ExportShowQRCodeFragment();
        Bundle args = new Bundle();
        args.putString("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            mData = bundle.getString("data");
        }
        View view = inflater.inflate(R.layout.wallet_fragment_show_qrcode, container, false);
        mQRCode = view.findViewById(R.id.wallet_QR_code);
        view.findViewById(R.id.wallet_show).setOnClickListener(this);
        setQRCode();
        return view;
    }

    private void setQRCode() {
        Observable.create((ObservableOnSubscribe<Bitmap>) emitter -> {
                  Bitmap bitmap = QRCodeEncoder.createQRImage(mData, 150, 150);
            emitter.onNext(bitmap);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            mQRCode.setImageBitmap(bitmap);
                        } else {
                            ToastUtils.showShortToast("生成二维码失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.wallet_show:
                //show QRcode
                break;
        }
    }
}
