package se.dose.irontest;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class TestCasesActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private TestCasesAdapter testCasesAdapter;
    private long productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testCasesAdapter = new TestCasesAdapter(this);
        productID = getIntent().getLongExtra(Product.PRODUCT_ID, -1);
        
        setListAdapter(testCasesAdapter);
        getListView().setOnItemClickListener(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_cases, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long testCaseID) {
        Intent intent = new Intent(this, TestCaseActivity.class);
        intent.putExtra(Product.PRODUCT_ID, productID);
        intent.putExtra(TestCase.TEST_CASE_ID, testCaseID);
        startActivity(intent);
    }
    
    private void refresh() {
        TestCasesSyncTask.Job job = new TestCasesSyncTask.Job(productID, testCasesAdapter);
        new TestCasesSyncTask().execute(job);
    }
}
