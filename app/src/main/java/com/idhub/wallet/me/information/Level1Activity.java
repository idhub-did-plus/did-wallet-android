package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.common.country.Country;
import com.idhub.wallet.common.country.CountryPickerCallbacks;
import com.idhub.wallet.common.country.CountryPickerDialog;
import com.idhub.wallet.common.date.DatePicker;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.me.information.view.InformationSelectItemView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Level1Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, Level1Activity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("Authentication Information");


    }


}
