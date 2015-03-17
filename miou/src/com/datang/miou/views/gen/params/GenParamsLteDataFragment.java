package com.datang.miou.views.gen.params;

import android.widget.TableLayout;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.gen.GenParamsFragment;

@AutoView(R.layout.gen_params_lte_data)
public class GenParamsLteDataFragment extends FragmentSupport {
	private TableLayout lteDataInfoTable;
	private TextView rsrpTextView;
	private TextView sinrTextView;
	private TextView ueTxPowerTextView;
	private TextView ftpDownloadRateTextView;
	private TextView ftpUploadRateTextView;

	@AfterView
	private void init() {
		AddServingCellInfoTable();
	}

	private void AddServingCellInfoTable() {
		// TODO 自动生成的方法存根
		lteDataInfoTable = (TableLayout) f(R.id.lte_data_info_table);

		GenParamsFragment.tableRowStyleFlag = true;
		
		rsrpTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), lteDataInfoTable, "RSRP");
		sinrTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), lteDataInfoTable, "SINR");
		ueTxPowerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), lteDataInfoTable, "UE发射功率");
		ftpDownloadRateTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), lteDataInfoTable, "FTP下载速率");
		ftpUploadRateTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), lteDataInfoTable, "FTP上传速率");
	}
}
