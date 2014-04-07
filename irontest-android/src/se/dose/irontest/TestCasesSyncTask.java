package se.dose.irontest;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;

public class TestCasesSyncTask extends AsyncTask<TestCasesSyncTask.Job, Integer, Boolean> {
    private List<TestCase> testCases;
    private Job job;
    
    public static class Job {
        public TestCasesAdapter testCasesAdapter;
        public long productID;
        public Job(long productID, TestCasesAdapter testCasesAdapter) {
            this.testCasesAdapter = testCasesAdapter;
            this.productID = productID;
        }
    }
    
    @Override
    protected void onPreExecute() {
        testCases = new ArrayList<TestCase>();
    }
    
    @Override
    protected Boolean doInBackground(Job... params) {
        job = params[0];
        try {
            Document doc = WebUtils.getDocument(WebAPI.listTestCasesURL(job.productID));
            NodeList testCaseNodes = doc.getElementsByTagName("test-case");
            for (int i = 0; i < testCaseNodes.getLength(); i++) {
                TestCase testCase = parseTestCase(testCaseNodes.item(i));
                testCases.add(testCase);
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
        job.testCasesAdapter.setTestCases(testCases);
    }
    
    private TestCase parseTestCase(Node testCaseNode) {
        TestCase testCase = new TestCase();
        NodeList nodeList = testCaseNode.getChildNodes();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node node = nodeList.item(j);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            String nodeName = node.getNodeName();
            String nodeContent = node.getTextContent().trim();
            if (nodeName.equals("title")) {
                testCase.setTitle(nodeContent);
            } else if (nodeName.equals("instructions")) {
                testCase.setInstructions(nodeContent);
            } else if (nodeName.equals("id")) {
                testCase.setId(Long.parseLong(nodeContent));
            } else if (nodeName.equals("status")) {
                testCase.setStatus(nodeContent);
            }
        }
        return testCase;
    }
}
