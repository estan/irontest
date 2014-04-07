package se.dose.irontest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestCasesAdapter extends BaseAdapter {
    private List<TestCase> testCases;
    private Context context;

    public TestCasesAdapter(Context context) {
        this.testCases = new ArrayList<TestCase>();
        this.context = context;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return testCases.size();
    }

    @Override
    public TestCase getItem(int position) {
        return testCases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return testCases.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.test_case_item, parent, false);
        TestCase testCase = testCases.get(position);

        TextView name = (TextView) layout.findViewById(R.id.test_case_title);
        name.setText(testCase.getTitle());

        TextView status = (TextView) layout.findViewById(R.id.test_case_status);
        String statusStr = testCase.getStatus();
        if (statusStr.equals("Passed")) {
            status.setTextColor(context.getResources().getColor(R.color.green));
        } else if (statusStr.equals("Failed")) {
            status.setTextColor(context.getResources().getColor(R.color.red));
        }
        status.setText(testCase.getStatus());

        return layout;
    }
}
