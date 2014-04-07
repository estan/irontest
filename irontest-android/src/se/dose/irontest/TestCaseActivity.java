package se.dose.irontest;

import java.util.List;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class TestCaseActivity extends ListActivity {
    private TestCaseActivity context = this;
    private ResultsAdapter resultsAdapter;
    private long productID;
    private long testCaseID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_case);
        resultsAdapter = new ResultsAdapter(this);
        productID = getIntent().getLongExtra(Product.PRODUCT_ID, -1);
        testCaseID = getIntent().getLongExtra(TestCase.TEST_CASE_ID, -1);
        
        setListAdapter(resultsAdapter);
        getListView().setClickable(false);
        
        WebView instructions = (WebView) findViewById(R.id.instructions);
        instructions.setInitialScale(90);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_case, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refresh();
            return true;
        } else if (item.getItemId() == R.id.action_add_result) {
            addResult();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void setInstructions(String instructions) {
        WebView instructionsView = (WebView) findViewById(R.id.instructions);
        instructionsView.loadData(instructions, "text/html", null);
    }
    
    public void setTitle(String title) {
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(title);
        
    }
    
    public void setResults(List<Result> results) {
        resultsAdapter.setResults(results);
    }
    
    public void addResult() {
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.dialog_create_result, null);

        final EditText tester = (EditText) dialogView.findViewById(R.id.tester_text);
        final EditText comment = (EditText) dialogView.findViewById(R.id.comment_text);
        final RadioButton passedButton = (RadioButton) dialogView.findViewById(R.id.passed_button);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        dialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Result result = new Result();
                        if (passedButton.isChecked())
                            result.setResult("Passed");
                        else
                            result.setResult("Failed");
                        result.setTester(tester.getText().toString());
                        result.setComment(comment.getText().toString());
                        
                        ResultCreateTask.Job job = new ResultCreateTask.Job(productID, testCaseID, context, result);
                        new ResultCreateTask().execute(job);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    
    public void refresh() {
        TestCaseSyncTask.Job job = new TestCaseSyncTask.Job(productID, testCaseID, this);
        new TestCaseSyncTask().execute(job);
    }
}
