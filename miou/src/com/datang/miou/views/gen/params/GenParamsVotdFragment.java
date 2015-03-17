package com.datang.miou.views.gen.params;

import android.widget.TableLayout;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.gen.GenParamsFragment;

@AutoView(R.layout.gen_params_votd)
public class GenParamsVotdFragment extends FragmentSupport {
	private TableLayout votdInfoTable;
	private TextView ppcpchRscpTextView;
	private TextView ueTxPowerTextView;
	private TextView ueVoiceFerTextView;
	private TextView pccpchSirTextView;

	@AfterView
	private void init() {
		AddServingCellInfoTable();
	}
	

	private void AddServingCellInfoTable() {
		// TODO 自动生成的方法存根
		votdInfoTable = (TableLayout) f(R.id.votd_info_table);

		GenParamsFragment.tableRowStyleFlag = true;
		
		ppcpchRscpTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), votdInfoTable, "PCCPCH_RSCP");
		ueTxPowerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), votdInfoTable, "UE发射功率");
		ueVoiceFerTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), votdInfoTable, "UE_Voice_FER");
		pccpchSirTextView = GenParamsFragment.addTwoColumnsForTable(getActivity(), votdInfoTable, "PCCPCH_SIR");
	}
}
