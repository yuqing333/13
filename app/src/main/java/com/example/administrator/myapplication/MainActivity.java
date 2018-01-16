package com.example.administrator.myapplication;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etId, etPwd;
    private Button btLogin;
    private TextView tvShow, tvSize;
    private SeekBar seekBar;
    private CheckBox checkBox;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = (EditText) findViewById(R.id.et_phonenumber);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btLogin = (Button) findViewById(R.id.bt_login);
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        btLogin.setOnClickListener(myOnClickListener);

        EditChangeListener editChangeListener = new EditChangeListener();
        etId.addTextChangedListener(editChangeListener);
        etPwd.addTextChangedListener(editChangeListener);

        tvShow = (TextView) findViewById(R.id.tv_show);
        tvSize = (TextView) findViewById(R.id.tv_size);
        seekBar = (SeekBar) findViewById(R.id.sb_adjust);
        MySeekBarListener mySeekBarListener = new MySeekBarListener();
        seekBar.setOnSeekBarChangeListener(mySeekBarListener);

        checkBox = (CheckBox) findViewById(R.id.cb_singleline);
        MyCheckedBoxListener myCheckedBoxListener = new MyCheckedBoxListener();
        checkBox.setOnCheckedChangeListener(myCheckedBoxListener);

        radioGroup = (RadioGroup) findViewById(R.id.rg_omit);
        MyRadioGroupListener myRadioGroupListener = new MyRadioGroupListener();
        radioGroup.setOnCheckedChangeListener(myRadioGroupListener);


    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "登录", Toast.LENGTH_SHORT).show();

        }
    }

    class EditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (etId.getText().length() == 11 && etPwd.getText() != null) {
                btLogin.setEnabled(Boolean.TRUE);
            } else {
                btLogin.setEnabled(Boolean.FALSE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class MyCheckedBoxListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            if (arg1) {
                tvShow.setSingleLine();
            } else {
                tvShow.setSingleLine(false);
            }

        }
    }

    class MyRadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup arg0, int arg1) {
            switch (arg1) {
                case R.id.rb_start:
                    if (checkBox.isChecked()) {
                        tvShow.setEllipsize(TextUtils.TruncateAt.valueOf("START"));
                    }
                    break;
                case R.id.rb_middle:
                    if (checkBox.isChecked()) {
                        tvShow.setEllipsize(TextUtils.TruncateAt.valueOf("MIDDLE"));
                    }
                    break;
                case R.id.rb_end:
                    if (checkBox.isChecked()) {
                        tvShow.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    class MySeekBarListener implements SeekBar.OnSeekBarChangeListener {
        private int minNumber = 6;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            progress = minNumber + progress;
            tvShow.setTextSize(progress);
            tvSize.setText(String.valueOf(progress) + "sp");

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}
