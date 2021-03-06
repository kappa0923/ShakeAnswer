package com.kappa0923.android.app.shakeanswer.fragment;


import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.kappa0923.android.app.shakeanswer.R;
import com.kappa0923.android.app.shakeanswer.common.ShakeManager;
import com.kappa0923.android.app.shakeanswer.services.AnswerPhoneService;

import java.util.List;

/**
 * 起動時画面の表示用のFragment
 */
public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 0;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupLayout(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        ToggleButton serviceSwitch = (ToggleButton) getActivity().findViewById(R.id.service_switch);
        serviceSwitch.setChecked(isServiceRunning());
    }

    /**
     * サービスが起動中かどうかを検出
     * @return {@code true}起動中
     */
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> serviceInfoList = manager.getRunningServices(Integer.MAX_VALUE);
        for (RunningServiceInfo info : serviceInfoList) {
            if (TextUtils.equals(info.service.getClassName(), AnswerPhoneService.class.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * メイン画面のレイアウトの初期化
     * @param view 親view
     */
    private void setupLayout(View view) {
        ToggleButton serviceSwitch = (ToggleButton) view.findViewById(R.id.service_switch);
        serviceSwitch.setOnCheckedChangeListener(this);

        String shakeCount = Integer.toString(PreferenceManager.getDefaultSharedPreferences(getContext())
                .getInt(ShakeManager.PREF_KEY_SHAKE_COUNT, 5));
        AppCompatSpinner countSpinner = (AppCompatSpinner)view.findViewById(R.id.count_spinner);
        ArrayAdapter<CharSequence> countAdapter = ArrayAdapter.createFromResource(getContext(), R.array.count_array, R.layout.spinner_item);
        countAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        countSpinner.setAdapter(countAdapter);
        countSpinner.setSelection(countAdapter.getPosition(shakeCount));
        countSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int shakeCount = Integer.parseInt((String)parent.getSelectedItem());
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .edit()
                .putInt(ShakeManager.PREF_KEY_SHAKE_COUNT, shakeCount)
                .apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_REQUEST_READ_PHONE_STATE);
        } else {
            changeServiceState(isChecked);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    changeServiceState(true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * バックグラウンドサービスの状態を変更する
     * @param isChecked {@code true}でオン
     */
    private void changeServiceState(boolean isChecked) {
        if (isChecked) {
            getActivity().startService(new Intent(getContext(), AnswerPhoneService.class));
        } else {
            getActivity().stopService(new Intent(getContext(), AnswerPhoneService.class));
        }
    }
}
