package com.datang.miou.views.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.datang.miou.R;

/**
 * Created by dingzhongchang on 2015/3/6.
 */
public class TestPlanManagerFragment extends Fragment {


    private static final int REQUEST_NEWPLAN = 1000;
    public static final String RESULT_NEWPLAN = "NEW_PALN";
    private TestPlanListAdapter mPlanListAdapter;
    private FragmentActivity mContext;
    private ListView mPlanListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_testplan, container, false);
        mPlanListView = (ListView) root.findViewById(R.id.test_plan_listView);
        mPlanListAdapter = new TestPlanListAdapter(this.getActivity(), new TestPlanInfo[]{});
        mPlanListView.setAdapter(mPlanListAdapter);
        mPlanListView.setTextFilterEnabled(true);
        initOnClick(root);

        return root;
    }

    private void initOnClick(final View root) {
        //搜索
        final EditText mEtSearch = (EditText) root.findViewById(R.id.et_search);
        final Button mBtnClearSearchText = (Button) root.findViewById(R.id.btn_clear_search_text);
        final LinearLayout mLayoutClearSearchText = (LinearLayout) root.findViewById(R.id.layout_clear_search_text);
        mEtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlanListAdapter.getFilter().filter(mEtSearch.getText().toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int textLength = mEtSearch.getText().length();
                if (textLength > 0) {
                    mLayoutClearSearchText.setVisibility(View.VISIBLE);
                } else {
                    mLayoutClearSearchText.setVisibility(View.GONE);
                }
            }
        });
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_CLEAR) {
                    mPlanListAdapter.clearFilter();
                }
                return false;
            }
        });
        mBtnClearSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.setText("");
                mLayoutClearSearchText.setVisibility(View.GONE);
                mPlanListAdapter.clearFilter();
            }
        });

        root.findViewById(R.id.bt_plan_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 新增测试序列
                Intent intent = new Intent(mContext, NewPlanListActivity.class);
                TestPlanManagerFragment.this.startActivityForResult(intent, REQUEST_NEWPLAN);

            }
        });
        root.findViewById(R.id.bt_plan_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 编辑测试任务
                TestPlanInfo info = mPlanListAdapter.getSelected();
            }
        });
        root.findViewById(R.id.bt_plan_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 测试序列下载
            }
        });
        root.findViewById(R.id.bt_plan_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 全选
                mPlanListAdapter.selectAll(true);
            }
        });

        root.findViewById(R.id.bt_plan_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  删除测试序列
                mPlanListAdapter.del();
                scrollToBottom();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_NEWPLAN) {
                String planName = data.getExtras().getString(RESULT_NEWPLAN);
                //TODO 返回新建的测试序列
                Toast.makeText(mContext, planName, Toast.LENGTH_SHORT).show();
                mPlanListAdapter.add(new TestPlanInfo(planName, "admin"));
                scrollToBottom();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scrollToBottom() {
        mPlanListView.setSelection(mPlanListAdapter.getCount() - 1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}