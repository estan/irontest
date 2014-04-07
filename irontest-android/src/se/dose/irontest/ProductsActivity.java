package se.dose.irontest;

import se.dose.irontest.R;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class ProductsActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productsAdapter = new ProductsAdapter(this);
        setListAdapter(productsAdapter);
        getListView().setOnItemClickListener(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.products, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long productID) {
        Intent intent = new Intent(this, TestCasesActivity.class);
        intent.putExtra(Product.PRODUCT_ID, productID);
        startActivity(intent);
    }
    
    private void refresh() {
        new ProductsSyncTask().execute(productsAdapter);
    }
}
