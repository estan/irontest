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

public class ProductsAdapter extends BaseAdapter {
    private List<Product> products;
    private Context context;

    public ProductsAdapter(Context context) {
        this.products = new ArrayList<Product>();
        this.context = context;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.product_item, parent, false);
        Product product = products.get(position);

        TextView name = (TextView) layout.findViewById(R.id.product_name);
        name.setText(product.getName());

        TextView status = (TextView) layout.findViewById(R.id.product_status);
        status.setText(product.getStatus());

        TextView description = (TextView) layout.findViewById(R.id.product_description);
        description.setText(product.getDescription());

        return layout;
    }
}
