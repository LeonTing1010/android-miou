package com.datang.miou.views.percept;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datang.miou.R;

public abstract class BasePageFragment extends Fragment {

    protected FragmentActivity mContext;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = BasePageFragment.this.getActivity();
        root = initUI(inflater, container, savedInstanceState);
//        TextView mTitleTextView = (TextView) root.findViewById(R.id.app_title_value);
//        mTitleTextView.setText("用户感知");
//        ImageView mBackButton = (ImageView) root.findViewById(R.id.app_title_left);
//        mBackButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                try {
//                    if (NavUtils.getParentActivityName(mContext) != null) {
//                        NavUtils.navigateUpFromSameTask(mContext);
//                    }
//                } catch (Exception e) {
//                    mContext.finish();
//                }
//            }
//        });
        return root;
    }

    /**
     * 由实现类去初始化具体的UI
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View initUI(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}