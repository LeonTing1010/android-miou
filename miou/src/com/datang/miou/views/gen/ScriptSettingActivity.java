package com.datang.miou.views.gen;

import java.util.ArrayList;
import java.util.Iterator;

import com.datang.miou.R;
import com.datang.miou.datastructure.TestPlan;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ScriptSettingActivity extends FragmentActivity {
	
    private static final String KEY_TEST_PLANS = "TestPlans";
	private TextView mTitleTextView;
	private TextView mSaveButton;
	private ImageView mBackButton;
	private ScriptSettingActivity mAppContext;
	private ListView mTestPlanListView;
	private TestPlanAdapter mAdapter;
	private ArrayList<TestPlan> mTestPlans;
	private Button mAddButton;
	private View mDeleteButton;
	private Spinner mTypeSpinner;
	
	private class TestPlanAdapter extends ArrayAdapter<TestPlan> {
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			if (convertView == null) {
				convertView = mAppContext.getLayoutInflater().inflate(R.layout.test_plan_list_item, null);
			}
			
			TestPlan plan = this.getItem(position);
			
			TextView mId = (TextView) convertView.findViewById(R.id.id_textView);
			String idString = null;
			if (plan.getId() < 10) {
				idString = "0" + String.valueOf(plan.getId());
			} else {
				idString = String.valueOf(plan.getId());
			}
			mId.setText(idString);
			
			TextView mName = (TextView) convertView.findViewById(R.id.name_textView);
			mName.setText(plan.getName().toString());
			
			TextView mTimes = (TextView) convertView.findViewById(R.id.times_textView);
			mTimes.setText(String.valueOf(plan.getTimes()));
			
			CheckBox selectCheckBox = (CheckBox) convertView.findViewById(R.id.select_checkBox);
			/*
			 * 这个isChecked属性只是为了表明当前是否被选中，加载时当做都没有选中处理
			 * 如果此处想从类中获取属性值设置CheckBox，则会触发OnCheckedChangeListener监听的事件重复设置
			 * 还是有问题，旋转的时候
			 */
			selectCheckBox.setChecked(plan.isChecked());
			selectCheckBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO 自动生成的方法存根
					TestPlan plan = getItem(position);
					if (((CheckBox) view).isChecked()) {
						plan.setChecked(true);
					} else {
						plan.setChecked(false);
					}
				}
			});
			
			Button increaseButton = (Button) convertView.findViewById(R.id.increase_button);	
			increaseButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO 自动生成的方法存根
					TestPlan plan = getItem(position);
					plan.setTimes(plan.getTimes() + 1);
					mAdapter.notifyDataSetChanged();
				}
			});
			
			Button decreaseButton = (Button) convertView.findViewById(R.id.decrease_button);
			if (plan.getTimes() == 0) {
				decreaseButton.setEnabled(false);
			} else {
				decreaseButton.setEnabled(true);
			}
			decreaseButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO 自动生成的方法存根
					TestPlan plan = getItem(position);
					plan.setTimes(plan.getTimes() - 1);
					mAdapter.notifyDataSetChanged();
				}
			});
			
			return convertView;
		}

		public TestPlanAdapter(Context context, ArrayList<TestPlan> plans) {
			super(context, 0, plans);
			// TODO 自动生成的构造函数存根
		}	
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onSaveInstanceState(savedInstanceState);
		/*
		 * 旋转之前清除所有TestPlan的isChecked属性
		 */
		//for (TestPlan plan : mTestPlans) {
		//	plan.setChecked(false);
		//}
		savedInstanceState.putSerializable(KEY_TEST_PLANS, this.mTestPlans);
	}

	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
        if (savedInstanceState != null) {
        	mTestPlans = (ArrayList<TestPlan>) savedInstanceState.getSerializable(KEY_TEST_PLANS);
        }
        
        setContentView(R.layout.script_setting);   
      
        mAppContext = this;
        
        mTitleTextView = (TextView) findViewById(R.id.app_title_value);
		mTitleTextView.setText(R.string.gen_script_title);
		
		mSaveButton = (TextView) findViewById(R.id.app_title_right_txt);
		mSaveButton.setText(R.string.gen_script_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO 自动生成的方法存根
				saveTestPlans();
			}
		});
        
		mBackButton = (ImageView) findViewById(R.id.app_title_left);   
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO 自动生成的方法存根
				if (NavUtils.getParentActivityName(mAppContext) != null) {
					NavUtils.navigateUpFromSameTask(mAppContext);
				}
			}
		});
		
		mAddButton = (Button) findViewById(R.id.add_testing_plan_button);
		mAddButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO 自动生成的方法存根
				addTestPlan();
			}
		});
		
		mDeleteButton = (Button) findViewById(R.id.delete_testing_plan_button);
		mDeleteButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO 自动生成的方法存根
				deleteTestPlan();
			}
		});
		
		mTypeSpinner = (Spinner) findViewById(R.id.scripts_spinner);
		ArrayAdapter<CharSequence> adapterXml = ArrayAdapter.createFromResource(this, R.array.testtype, android.R.layout.simple_spinner_item);
		mTypeSpinner.setAdapter(adapterXml);
		
		mTestPlanListView = (ListView) findViewById(R.id.test_plan_listView);
		fillPlanList();
    }  
    
	private void deleteTestPlan() {
		// TODO 自动生成的方法存根
		Iterator<TestPlan> iter = mTestPlans.listIterator();
		while (iter.hasNext()) {
			TestPlan plan = iter.next();
			if (plan.isChecked()) {
				iter.remove();
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	private void addTestPlan() {
		// TODO 自动生成的方法存根
		TestPlan plan = new TestPlan();
		int type = mTypeSpinner.getSelectedItemPosition();
		plan.setType(type);
		mTestPlans.add(plan);
		mAdapter.notifyDataSetChanged();
	}

	private void fillPlanList() {
		// TODO 自动生成的方法存根
		if (mTestPlans == null) {
			mTestPlans = new ArrayList<TestPlan>();
		}
		mAdapter = new TestPlanAdapter(mAppContext, mTestPlans);
		this.mTestPlanListView.setAdapter(mAdapter);
	}

	private void saveTestPlans() {
		// TODO 自动生成的方法存根
		
	}
}
