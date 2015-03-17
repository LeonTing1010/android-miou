package com.datang.miou.views.gen;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.datastructure.Event;
import com.datang.miou.datastructure.Signal;
import com.datang.miou.views.dialogs.LabelInfoDialogFragment;

/**
 * 信令
 * 
 * @author suntongwei
 */
@AutoView(R.layout.gen_signal)
public class GenSignalFragment extends FragmentSupport {
	protected static final String EXTRA_SIGNAL = "extra_signal";
	private static final String DIALOG_LABEL_INFO = "label_info";
	private EditText searchEventEditText;
	private EditText searchSignalEditText;
	private ImageButton searchEventButton;
	private ImageButton searchSignalButton;
	private ListView eventListView;
	private ListView signalListView;
	private ArrayList<Event> mEvents;
	private ArrayList<Signal> mSignals;
	
	
	private void setupEventAdapter(ArrayList<Event> events) {
		// TODO 自动生成的方法存根
		if (getActivity() == null || eventListView == null) {
			return;
		}
		
		if (events != null) {
			eventListView.setAdapter(new ArrayAdapter<Event>(getActivity(), android.R.layout.simple_list_item_1, events));
		} else {
			eventListView.setAdapter(null);
		}
	}

	private void setupSignalAdapter(ArrayList<Signal> signals) {
		// TODO 自动生成的方法存根
		if (getActivity() == null || eventListView == null) {
			return;
		}
		
		if (signals != null) {
			eventListView.setAdapter(new ArrayAdapter<Signal>(getActivity(), android.R.layout.simple_list_item_1, signals));
		} else {
			eventListView.setAdapter(null);
		}
	}
	
	@AfterView
	private void init() {
		searchEventEditText = (EditText) f(R.id.search_event_editText);
		searchSignalEditText = (EditText) f(R.id.search_signal_editText);
		
		searchEventButton = (ImageButton) f(R.id.search_event_ImageButton);
		searchEventButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ArrayList<Event> result = new ArrayList<Event>();
				String string = searchEventEditText.getText().toString();
				for (Event e : mEvents) {
					if (e.getContent().contains(string)) {
						result.add(0, e);
					}
				}
				setupEventAdapter(result);
			}
		});
		
		searchSignalButton = (ImageButton) f(R.id.search_signal_ImageButton);
		searchSignalButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ArrayList<Signal> result = new ArrayList<Signal>();
				String string = searchSignalEditText.getText().toString();
				for (Signal s : mSignals) {
					if (s.getContent().contains(string)) {
						result.add(0, s);
					}
				}
				setupSignalAdapter(result);
			}
		});
		
		eventListView = (ListView) f(R.id.event_listView);
		eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO 自动生成的方法存根
				
			}
		});
		eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> listView, View view, int position, long id) {
				// TODO 自动生成的方法存根
				Event event = (Event) listView.getAdapter().getItem(position);
				FragmentManager fm = getActivity().getSupportFragmentManager();
				LabelInfoDialogFragment dialog = LabelInfoDialogFragment.newInstance(event);
				dialog.show(fm, DIALOG_LABEL_INFO);
				/*
				 * 貌似这个通知数据更改没有生效
				 */
				((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
				return true;
			}
			
		});

		mEvents = getEvents();
		ArrayAdapter<Event> eventAdapter = new ArrayAdapter<Event>(getActivity(), android.R.layout.simple_list_item_1, mEvents);
		eventListView.setAdapter(eventAdapter);
		
		signalListView = (ListView) f(R.id.signal_listView);
		signalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				// TODO 自动生成的方法存根
				Signal signal = (Signal) listView.getAdapter().getItem(position);
				Intent intent = new Intent(getActivity(), SignalDetailActivity.class);
				intent.putExtra(EXTRA_SIGNAL, signal);
				startActivity(intent);
			}
		});
		mSignals = getSignals();
		ArrayAdapter<Signal> signalAdapter = new ArrayAdapter<Signal>(getActivity(), android.R.layout.simple_list_item_1, mSignals);
		signalListView.setAdapter(signalAdapter);
	}

	private ArrayList<Signal> getSignals() {
		// TODO 自动生成的方法存根
		mSignals = new ArrayList<Signal>();
		for (int i = 0; i < 100; i++) {
			Signal signal = new Signal();
			signal.setContent("Signal " + i);
			mSignals.add(0, signal);
		}
		return mSignals;
	}

	private ArrayList<Event> getEvents() {
		// TODO 自动生成的方法存根
		mEvents = new ArrayList<Event>();
		for (int i = 0; i < 100; i++) {
			Event event = new Event();
			event.setContent("Event " + i);
			mEvents.add(0, event);
		}
		return mEvents;
	}
}
