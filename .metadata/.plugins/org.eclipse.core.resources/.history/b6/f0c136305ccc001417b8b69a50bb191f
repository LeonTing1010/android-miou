package com.datang.miou.views.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.datang.miou.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dingzhongchang on 2015/3/6.
 */
public class TestPlanListAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private List<TestPlanInfo> mItems;
    private List<TestPlanInfo> mOrigItems;
    private final LayoutInflater mLayoutInflater;
    private final Object mLock = new Object();
    private Filter mFilter;


    public TestPlanListAdapter(Context context, TestPlanInfo[] objects) {
        super();
        this.mContext = context;
        this.mItems = new LinkedList<>(Arrays.asList(objects));
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = mLayoutInflater.inflate(R.layout.data_list_item, parent, false);
        }

        if (getCount() > position) {

            final TestPlanInfo info = (TestPlanInfo) getItem(position);

            if (info != null) {
                final CheckBox checkBox = (CheckBox) v.findViewById(R.id.cb_checked);
                checkBox.setChecked(info.isChecked);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        info.isChecked = checkBox.isChecked();
                    }
                });
                ((TextView) v.findViewById(R.id.tv_name)).setText(info.name);
                ((TextView) v.findViewById(R.id.tv_creator_id)).setText(info.creatorId);
                ((TextView) v.findViewById(R.id.tv_modified_time)).setText(info.modifiedTime);
                ((TextView) v.findViewById(R.id.tv_create_time)).setText(info.createTime);
                ((TextView) v.findViewById(R.id.tv_exed_num)).setText(info.exedNum);
            }
        }

        return v;
    }

    public void selectAll(boolean isSelected) {
        synchronized (mLock) {
            if (mOrigItems != null) {
                for (TestPlanInfo info : mOrigItems) {
                    if (info == null) continue;
                    info.isChecked = isSelected;
                }
            } else {
                if (mItems == null || mItems.isEmpty()) return;
                for (TestPlanInfo info : mItems) {
                    if (info == null) continue;
                    info.isChecked = isSelected;
                }
            }
        }
        notifyDataSetChanged();
    }

    public void del() {
        synchronized (mLock) {
            if (mOrigItems != null) {
                Iterator<TestPlanInfo> iterator = mOrigItems.iterator();
                while (iterator.hasNext()) {
                    TestPlanInfo info = iterator.next();
                    if (info.isChecked) {
                        iterator.remove();
                    }
                }
            } else {
                if (mItems == null || mItems.isEmpty()) return;
                Iterator<TestPlanInfo> iterator = mItems.iterator();
                while (iterator.hasNext()) {
                    TestPlanInfo info = iterator.next();
                    if (info.isChecked) {
                        iterator.remove();
                    }
                }
            }
        }
        clearFilter();
        notifyDataSetChanged();
    }


    public TestPlanInfo getSelected() {
        synchronized (mLock) {
            if (mOrigItems != null) {
                Iterator<TestPlanInfo> iterator = mOrigItems.iterator();
                while (iterator.hasNext()) {
                    TestPlanInfo info = iterator.next();
                    if (info.isChecked) {
                        return info;
                    }
                }
            } else {
                if (mItems == null || mItems.isEmpty()) return null;
                Iterator<TestPlanInfo> iterator = mItems.iterator();
                while (iterator.hasNext()) {
                    TestPlanInfo info = iterator.next();
                    if (info.isChecked) {
                        return info;
                    }
                }
            }
        }
        return null;
    }

    public void add(TestPlanInfo admin) {
        if (admin != null) {
            synchronized (mLock) {
                if (mOrigItems != null) {
                    mOrigItems.add(admin);
                } else {
                    mItems.add(admin);
                }
            }

        }
        clearFilter();
        notifyDataSetChanged();

    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new PlanListFilter();
        }
        return mFilter;

    }

    public void clearFilter() {
        synchronized (mLock) {
            if (mOrigItems != null) {
                mItems = mOrigItems;
                mOrigItems = null;
            }
        }

    }

    private class PlanListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (null == mOrigItems) {
                synchronized (mLock) {
                    mOrigItems = new LinkedList<>(mItems);
                }
            }
            if (null == constraint || constraint.length() == 0) {
                ArrayList<TestPlanInfo> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOrigItems);
                }

                result.values = list;
                result.count = list.size();
            } else {
                ArrayList<TestPlanInfo> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOrigItems);
                }
                List<TestPlanInfo> founded = new ArrayList<>();
                for (TestPlanInfo item : list) {
                    if (item.toString().toLowerCase().contains(constraint)) {
                        founded.add(item);
                    }
                }

                result.values = founded;
                result.count = founded.size();
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mItems = (List<TestPlanInfo>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }

        }
    }
}
