package com.datang.miou.views.data;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.datang.miou.R;

/**
 * Created by dingzhongchang on 2015/3/13.
 */
public class NewPlanListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplan_list);
        TextView mTitleTextView = (TextView) findViewById(R.id.app_title_value);
        mTitleTextView.setText("新增序列");
        ImageView mBackButton = (ImageView) findViewById(R.id.app_title_left);
        mBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                NewPlanListActivity.this.setResult(RESULT_CANCELED);
                try {
                    if (NavUtils.getParentActivityName((Activity) getApplicationContext()) != null) {
                        NavUtils.navigateUpFromSameTask((Activity) getApplicationContext());
                    }
                } catch (Exception e) {
                    finish();
                }
            }
        });


        // initiate the listadapter
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                R.layout.newplan_list_item, R.id.tv_plan_name, this.getResources().getStringArray(R.array.testtype));

        // assign the list adapter
        setListAdapter(myAdapter);

    }

    // when an item of the list is clicked
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        String selectedItem = (String) getListView().getItemAtPosition(position);
        //String selectedItem = (String) getListAdapter().getItem(position);
        Intent result = this.getIntent();
        result.putExtra(TestPlanManagerFragment.RESULT_NEWPLAN, selectedItem);
        this.setResult(RESULT_OK, result);
//        Toast.makeText(this, "You clicked " + selectedItem + " at position " + position, Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
