package se.dose.irontest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;

public class TestCaseSyncTask extends AsyncTask<TestCaseSyncTask.Job, Integer, Boolean> {
    private List<Result> results;
    private String title;
    private String instructions;
    private SimpleDateFormat dateFormat;
    private Job job;
    private static String html =
            "<html><head><style>body {background-color:black;color:white;}</style></head><body>%{content}</body></html>";
    
    public static class Job {
        public TestCaseActivity testCaseActivity;
        public long productID;
        public long testCaseID;
        public Job(long productID, long testCaseID, TestCaseActivity testCaseActivity) {
            this.testCaseActivity = testCaseActivity;
            this.productID = productID;
            this.testCaseID = testCaseID;
        }
    }
    
    @Override
    protected void onPreExecute() {
        results = new ArrayList<Result>();
        title = "";
        instructions = "";
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US);
    }
    
    @Override
    protected Boolean doInBackground(Job... params) {
        job = params[0];
        try {

            Document doc = WebUtils.getDocument(WebAPI.showTestCaseURL(job.productID, job.testCaseID));
            Node testCaseNode = doc.getFirstChild();
            NodeList testCaseNodes = testCaseNode.getChildNodes();
            for (int i = 0; i < testCaseNodes.getLength(); i++) {
                Node node = testCaseNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                String nodeName = node.getNodeName();
                String nodeContent = node.getTextContent().trim();
                if (nodeName.equals("title")) {
                    title = nodeContent;
                } else if (nodeName.equals("instructions")) {
                    instructions = nodeContent;
                } else if (nodeName.equals("results")) {
                    NodeList nodeList = node.getChildNodes();
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node resultNode = nodeList.item(j);
                        if (resultNode.getNodeType() != Node.ELEMENT_NODE)
                            continue;
                        Result result = parseResult(resultNode);
                        results.add(result);
                    }
                }
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
        job.testCaseActivity.setResults(results);
        job.testCaseActivity.setTitle(title);
        job.testCaseActivity.setInstructions(html.replace("%{content}", instructions));
    }
    
    private Result parseResult(Node testCaseNode) throws ParseException {
        Result result = new Result();
        NodeList nodeList = testCaseNode.getChildNodes();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node node = nodeList.item(j);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            String nodeName = node.getNodeName();
            String nodeContent = node.getTextContent().trim();
            if (nodeName.equals("date")) {
                result.setDate(dateFormat.parse(nodeContent));
            } else if (nodeName.equals("comment")) {
                result.setComment(nodeContent);
            } else if (nodeName.equals("id")) {
                result.setId(Long.parseLong(nodeContent));
            } else if (nodeName.equals("result")) {
                result.setResult(nodeContent);
            } else if (nodeName.equals("tester")) {
                result.setTester(nodeContent);
            }
        }
        return result;
    }
}
