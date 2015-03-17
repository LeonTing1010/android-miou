package com.datang.miou.views.data;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.datang.miou.R;
import com.datang.miou.utils.DevUtil;
import com.datang.miou.utils.WidgetUtil;
import com.datang.miou.widget.FileBrowserActivity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 数据上传设置
 * Created by dingzhongchang on 2015/3/6.
 */
public class DataUploadSettingFragment extends Fragment {
    public static final String TAG = "DataUploadSettingFragment";
    public static final int REQUEST_CODE_PICK_FILE_TO_SAVE_INTERNAL = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload_setting, container, false);
        final TextView wifi = (TextView) root.findViewById(R.id.btn_wifi);
        wifi.setTag(R.drawable.bg_half_left_btn);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DevUtil.isWifi(v.getContext())) {
                    Toast.makeText(v.getContext(), "wifi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "no wifi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final TextView btn3g = (TextView) root.findViewById(R.id.btn_3g);
        btn3g.setTag(R.drawable.bg_half_mid_default_btn);
        btn3g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DevUtil.is3G(v.getContext())) {
                    Toast.makeText(v.getContext(), "3G", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "no 3G", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final TextView any = (TextView) root.findViewById(R.id.btn_any);
        any.setTag(R.drawable.bg_half_right_default_btn);
        any.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DevUtil.isConnected(v.getContext())) {
                    Toast.makeText(v.getContext(), "connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "not connect", Toast.LENGTH_SHORT).show();
                }
            }
        });
        root.findViewById(R.id.choose_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fileExploreIntent = new Intent(
                        FileBrowserActivity.INTENT_ACTION_SELECT_DIR,
                        null,
                        v.getContext(),
                        FileBrowserActivity.class
                );
                DataUploadSettingFragment.this.getActivity().
                        startActivityForResult(fileExploreIntent, REQUEST_CODE_PICK_FILE_TO_SAVE_INTERNAL);

            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_CODE_PICK_FILE_TO_SAVE_INTERNAL) {
            if (resultCode == Activity.RESULT_OK) {
                String newDir = data.getStringExtra(
                        FileBrowserActivity.returnDirectoryParameter);
                Toast.makeText(
                        DataUploadSettingFragment.this.getActivity(),
                        "Received path from file browser:" + newDir,
                        Toast.LENGTH_LONG
                ).show();
            } else {//if(resultCode == this.RESULT_OK) {
                Toast.makeText(
                        DataUploadSettingFragment.this.getActivity(),
                        "Received NO result from file browser",
                        Toast.LENGTH_LONG)
                        .show();
            }//END } else {//if(resultCode == this.RESULT_OK) {
        }//if (requestCode == REQUEST_CODE_PICK_FILE_TO_SAVE_INTERNAL) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
