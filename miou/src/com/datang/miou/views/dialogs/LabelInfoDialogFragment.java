package com.datang.miou.views.dialogs;

import com.datang.miou.R;
import com.datang.miou.datastructure.Event;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

public class LabelInfoDialogFragment extends DialogFragment {

	private static final String EXTRA_EVENT = "extra_event";
	
	public static LabelInfoDialogFragment newInstance(Event event) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_EVENT, event);
		LabelInfoDialogFragment fragment = new LabelInfoDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}

	private EditText mLabelEditText;
	private Event mEvent;
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		mEvent = (Event) getArguments().getSerializable(EXTRA_EVENT);
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_label_info, null);
		mLabelEditText = (EditText) view.findViewById(R.id.label_editText);
		
		return new AlertDialog.Builder(getActivity())
					.setView(view)
					.setTitle(R.string.dialog_label_info_title)
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO 自动生成的方法存根
							String label = mLabelEditText.getText().toString();
							mEvent.setLabel(label);
						}
					})
					.setNegativeButton(android.R.string.cancel, null)
					.create();
	}

	
}
