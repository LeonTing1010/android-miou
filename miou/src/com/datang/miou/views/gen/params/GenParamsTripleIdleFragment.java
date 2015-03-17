package com.datang.miou.views.gen.params;

import android.widget.TableLayout;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.gen.GenParamsFragment;

@AutoView(R.layout.gen_params_idle_triple)
public class GenParamsTripleIdleFragment extends FragmentSupport {
	private TableLayout TripleIdleTable;
	private TextView lteRsrpTextView;
	private TextView tdRsrpTextView;
	private TextView gsmRsrpTextView;

	@AfterView
	private void init() {
		AddServingCellInfoTable();
	}

	private void AddServingCellInfoTable() {
		// TODO 自动生成的方法存根
		TripleIdleTable = (TableLayout) f(R.id.idle_info_table);

		GenParamsFragment.tableRowStyleFlag = true;
			
		lteRsrpTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), TripleIdleTable, "LTE RSRP");
		tdRsrpTextView  = GenParamsFragment.addTwoColumnsForTable(getActivity(), TripleIdleTable, "TD RSRP");
		gsmRsrpTextView  = GenParamsFragment.addTwoColumnsForTable(getActivity(), TripleIdleTable, "GSM RSRP");
	}
}
