package com.datang.miou.views.gen.params;

import android.widget.TableLayout;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.gen.GenParamsFragment;

@AutoView(R.layout.gen_params_volte)
public class GenParamsVolteFragment extends FragmentSupport {

	private TableLayout volteInfoTable;
	private TextView upRbValueTextView;
	private TextView downRbValueTextView;
	private TextView upMcsValueTextView;
	private TextView downMcs_valueTextView;
	private TextView blerTextView;
	private TextView callDelayTextView;
	private TextView switchDelayTextView;
	private TextView voiceDelayTextView;

	@AfterView
	private void init() {
		AddServingCellInfoTable();
	}

	private void AddServingCellInfoTable() {
		// TODO 自动生成的方法存根
		volteInfoTable = (TableLayout) f(R.id.volte_info_table);

		GenParamsFragment.tableRowStyleFlag = true;
		
		upRbValueTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "上行RB数");
		downRbValueTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "下行RB数");
		upMcsValueTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "上行MCS");
		downMcs_valueTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "下行MCS");
		blerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "BLER");
		callDelayTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "呼叫建立时延");
		switchDelayTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "切换中断时延");
		voiceDelayTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), volteInfoTable, "语音挂机时延");
	}
}
