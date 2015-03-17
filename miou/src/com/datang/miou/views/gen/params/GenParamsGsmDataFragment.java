package com.datang.miou.views.gen.params;

import android.widget.TableLayout;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.gen.GenParamsFragment;

@AutoView(R.layout.gen_params_gsm_data)
public class GenParamsGsmDataFragment extends FragmentSupport {
	private TableLayout gsmDataInfoTable;
	private TextView rlcBlerTextView;
	private TextView rlcRetransRateTextView;
	private TextView rlcUploadThroughputTextView;
	private TextView rlcDownloadThroughputTextView;
	private TextView rxLevFullTextView;
	private TextView rxLevSubTextView;
	private TextView msTxPowerTextView;

	@AfterView
	private void init() {
		AddServingCellInfoTable();
	}

	private void AddServingCellInfoTable() {
		// TODO 自动生成的方法存根
		gsmDataInfoTable = (TableLayout) f(R.id.gsm_data_info_table);

		GenParamsFragment.tableRowStyleFlag = true;
		
		rlcBlerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), gsmDataInfoTable, "RLC BLER[DL]");
		rlcRetransRateTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), gsmDataInfoTable, "RLC retrans rate[UL]");
		rlcUploadThroughputTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), gsmDataInfoTable, "RLC throughput[UL]");
		rlcDownloadThroughputTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), gsmDataInfoTable, "RLC throughput[DL]");
		rxLevFullTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), gsmDataInfoTable, "RxLev Full");
		rxLevSubTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), gsmDataInfoTable, "RxLev Sub");
		msTxPowerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), gsmDataInfoTable, "MS TxPower");
	}
}
