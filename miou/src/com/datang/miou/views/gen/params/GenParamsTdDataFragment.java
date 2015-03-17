package com.datang.miou.views.gen.params;

import android.widget.TableLayout;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.gen.GenParamsFragment;

@AutoView(R.layout.gen_params_td_data)
public class GenParamsTdDataFragment extends FragmentSupport {
	private TableLayout tdDataInfoTable;
	private TextView pccpchRscpTextView;
	private TextView ueTxPowerTextView;
	private TextView uploadThroughputTextView;
	private TextView downloadThroughputTextView;

	@AfterView
	private void init() {
		AddServingCellInfoTable();
	}

	private void AddServingCellInfoTable() {
		// TODO 自动生成的方法存根
		tdDataInfoTable = (TableLayout) f(R.id.td_data_info_table);

		GenParamsFragment.tableRowStyleFlag = true;
		
		pccpchRscpTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), tdDataInfoTable, "PCCPCH_RSCP");
		ueTxPowerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), tdDataInfoTable, "UE发射功率");
		uploadThroughputTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), tdDataInfoTable, "上传应用层平均吞吐量");
		downloadThroughputTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), tdDataInfoTable, "下载应用层平均吞吐量");
	}
}
