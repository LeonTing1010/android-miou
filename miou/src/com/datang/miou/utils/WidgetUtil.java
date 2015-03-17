package com.datang.miou.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by dingzhongchang on 2015/3/7.
 */
public class WidgetUtil {

    /**
     * 调用文件选择软件来选择文件 *
     */
    public static void showFileChooser(Activity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            context.startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),  1000);

        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(context, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
