package id.yuana.pengeluaran.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import id.yuana.pengeluaran.App;
import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.dialogs.LupaPinDialog;
import id.yuana.pengeluaran.helper.Helper;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/12/17
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPin;
    private Button btnPinSubmit;
    private TextView tvLupaPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        checkSetPin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void checkSetPin() {
        if (App.getPin() == null) {
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK
        );
        startActivity(intent);
    }

    private void initView() {
        etPin = (EditText) findViewById(R.id.etPin);
        btnPinSubmit = (Button) findViewById(R.id.btnPinSubmit);
        tvLupaPin = (TextView) findViewById(R.id.tvLupaPin);

        btnPinSubmit.setOnClickListener(this);
        tvLupaPin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPinSubmit:

                login(etPin.getText().toString());

                break;

            case R.id.tvLupaPin:

                DialogFragment dialogFragment = LupaPinDialog.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "DIALOG");

                break;
        }
    }

    private void login(String pin) {
        if (TextUtils.isEmpty(pin)) {
            Helper.toast(this, "Harus diisi");
            return;
        }

        if (pin.equals(App.getPin())) {
            startMainActivity();
        } else {
            Helper.toast(this, "PIN yang Anda masukkan salah");
        }
    }
}
