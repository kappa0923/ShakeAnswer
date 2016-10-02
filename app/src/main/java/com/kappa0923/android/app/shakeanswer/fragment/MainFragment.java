package com.kappa0923.android.app.shakeanswer.fragment;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.kappa0923.android.app.shakeanswer.R;
import com.kappa0923.android.app.shakeanswer.services.AnswerPhoneService;

import java.util.List;

/**
 * 起動時画面の表示用のFragment
 */
public class MainFragment extends Fragment {


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
        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getActivity().startService(new Intent(getContext(), AnswerPhoneService.class));
                } else {
                    getActivity().stopService(new Intent(getContext(), AnswerPhoneService.class));
                }
            }
        });
    }
}
