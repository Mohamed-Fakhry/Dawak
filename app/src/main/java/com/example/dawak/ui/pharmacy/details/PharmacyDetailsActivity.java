package com.example.dawak.ui.pharmacy.details;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dawak.R;
import com.example.dawak.di.component.ActivityComponent;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseActivity;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PharmacyDetailsActivity extends BaseActivity implements PharmacyDetailsContract.View {

    private static final String EXTRA_PHARMACY = "EXTRA_PHARMACY";
    private static final String MEDICIEN_NAME = "MEDICIEN_NAME";
    private static final String QUANTITY_KEY = "QUANTITY_KEY";

    private Pharmacy pharmacy;

    @Inject
    PharmacyDetailsContract.Presenter<PharmacyDetailsContract.View> presenter;

    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.openTV)
    TextView openTV;
    @BindView(R.id.closeTV)
    TextView closeTV;
    @BindView(R.id.medicineNameET)
    EditText medicineNameET;
    @BindView(R.id.quantityNP)
    NumberPicker quantityNP;

    public static Intent getStartIntent(Context context, Pharmacy pharmacy) {
        Intent intent = new Intent(context, PharmacyDetailsActivity.class);
        intent.putExtra(EXTRA_PHARMACY, (Parcelable) pharmacy);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_details);
        ButterKnife.bind(this);
        if (getIntent().getParcelableExtra(EXTRA_PHARMACY) != null) {
            pharmacy = getIntent().getParcelableExtra(EXTRA_PHARMACY);
        }

        if (savedInstanceState != null) {
            String medicineName = savedInstanceState.getString(MEDICIEN_NAME);
            int quantity = savedInstanceState.getInt(QUANTITY_KEY, 1);

            if (medicineName != null) {
                medicineNameET.setText(medicineName);
            }
            quantityNP.setValue(quantity);
        }

        setUp();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String medicineName = medicineNameET.getText().toString();
        int quantity = quantityNP.getValue();
        outState.putString(MEDICIEN_NAME, medicineName);
        outState.putInt(QUANTITY_KEY, quantity);
    }

    @Override
    protected void setUp() {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this));
            presenter.onAttach(this);
        }

        nameTV.setText(pharmacy.getName());
        phoneTV.setText(pharmacy.getPhone());
        openTV.setText(pharmacy.getOpenDate());
        closeTV.setText(pharmacy.getCloseDate());
    }

    @OnClick(R.id.askB)
    public void onViewClicked() {
        String medicineName = medicineNameET.getText().toString();
        int quantity = quantityNP.getValue();
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", null);
        presenter.askMedicine(medicineName, quantity, userId, pharmacy);
    }

    @Override
    public void onSuccess() {
        showMessage(R.string.add_successfully);
        finish();
    }
}