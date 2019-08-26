package com.idhub.wallet.common.country;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.idhub.wallet.R;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CountryPickerDialog extends AppCompatDialog {

    private List<Country> mList;
    private CountryPickerCallbacks cpcCallbacks;
    private ListView lvCountry;
    private boolean showDialingCode;

    public CountryPickerDialog(Context context, CountryPickerCallbacks callbacks, boolean showDialingCode, int raw) {
        super(context);
        this.cpcCallbacks = callbacks;
        this.showDialingCode = showDialingCode;
        mList = CountryUtils.parseCountries(CountryUtils.getCountriesJSON(this.getContext(), raw));
        Collections.sort(mList, new Comparator<Country>() {
            @Override
            public int compare(Country country1, Country country2) {
                final Locale locale = getContext().getResources().getConfiguration().locale;
                final Collator collator = Collator.getInstance(locale);
                collator.setStrength(Collator.PRIMARY);
                return collator.compare(
                        new Locale(locale.getLanguage(), country1.getIsoCode()).getDisplayCountry(),
                        new Locale(locale.getLanguage(), country2.getIsoCode()).getDisplayCountry());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wallet_common_country_picker);

        lvCountry = (ListView) findViewById(R.id.lvCountry);

        CountryListAdapter adapter = new CountryListAdapter(this.getContext(), mList, showDialingCode);
        lvCountry.setAdapter(adapter);
        lvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hide();
                Country country = mList.get(position);
//                cpcCallbacks.onCountrySelected(country, CountryUtils.getMipmapResId(getContext(),
//                        country.getIsoCode().toLowerCase(Locale.ENGLISH) + "_flag"));
                cpcCallbacks.onCountrySelected(country);
            }
        });
    }
}
