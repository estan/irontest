package se.dose.irontest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultsAdapter extends BaseAdapter {
    private List<Result> results;
    private SimpleDateFormat dateFormat;
    private Context context;

    public ResultsAdapter(Context context) {
        this.results = new ArrayList<Result>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd\nHH:mm", Locale.US);
        this.context = context;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Result getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return results.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.result_item, parent, false);
        Result result = results.get(position);

        TextView date = (TextView) layout.findViewById(R.id.result_date);
        date.setText(dateFormat.format(result.getDate()));
        
        TextView tester = (TextView) layout.findViewById(R.id.result_tester);
        tester.setText("by " + result.getTester());
        
        TextView comment = (TextView) layout.findViewById(R.id.result_comment);
        comment.setText(result.getComment());
        
        TextView resultView = (TextView) layout.findViewById(R.id.result_result);
        String resultStr = result.getResult();
        if (resultStr.equals("Passed")) {
            resultView.setTextColor(context.getResources().getColor(R.color.green));
        } else if (resultStr.equals("Failed")) {
            resultView.setTextColor(context.getResources().getColor(R.color.red));
        }
        resultView.setText(result.getResult());

        return layout;
    }
}
