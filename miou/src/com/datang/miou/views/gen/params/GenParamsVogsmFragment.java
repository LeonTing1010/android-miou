package com.datang.miou.views.gen.params;

import android.widget.TableLayout;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.gen.GenParamsFragment;

@AutoView(R.layout.gen_params_vogsm)
public class GenParamsVogsmFragment extends FragmentSupport {
	private TableLayout vogsmInfoTable;
	private TextView rxQualFullTextView;
	private TextView rxQualSubTextView;
	private TextView rxLevFullTextView;
	private TextView rxLevSubTextView;
	private TextView msTxPowerTextView;
	private TextView ferTextView;

	@AfterView
	private void init() {
		AddServingCellInfoTable();
	}

	private void AddServingCellInfoTable() {
		// TODO 自动生成的方法存根
		vogsmInfoTable = (TableLayout) f(R.id.vogsm_info_table);

		GenParamsFragment.tableRowStyleFlag = true;
		
		rxQualFullTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), vogsmInfoTable, "RxQual Full");
		rxQualSubTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), vogsmInfoTable, "RxQual Sub");
		rxLevFullTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), vogsmInfoTable, "RxLev Full");
		rxLevSubTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), vogsmInfoTable, "RxLev Sub");
		msTxPowerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), vogsmInfoTable, "MS TxPower");
		ferTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), vogsmInfoTable, "FER");
	}
}
