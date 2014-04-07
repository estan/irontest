package se.dose.irontest;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;

public class ProductsSyncTask extends AsyncTask<ProductsAdapter, Integer, Boolean> {
    private List<Product> products;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onPreExecute() {
        products = new ArrayList<Product>();
    }

    @Override
    protected Boolean doInBackground(ProductsAdapter... params) {
        productsAdapter = params[0];
        try {
            Document doc = WebUtils.getDocument(WebAPI.listProductsURL());
            NodeList productNodes = doc.getElementsByTagName("product");
            for (int i = 0; i < productNodes.getLength(); i++) {
                Product product = parseProduct(productNodes.item(i));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (!success)
            return;
        productsAdapter.setProducts(products);
    }
    
    private Product parseProduct(Node productNode) {
        Product product = new Product();
        NodeList nodeList = productNode.getChildNodes();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node node = nodeList.item(j);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            String nodeName = node.getNodeName();
            String nodeContent = node.getTextContent().trim();
            if (nodeName.equals("name")) {
                product.setName(nodeContent);
            } else if (nodeName.equals("description")) {
                product.setDescription(nodeContent);
            } else if (nodeName.equals("id")) {
                product.setId(Long.parseLong(nodeContent));
            } else if (nodeName.equals("status")) {
                product.setStatus(nodeContent);
            }
        }
        return product;
    }
}
