package se.dose.irontest;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;

public class ResultCreateTask extends AsyncTask<ResultCreateTask.Job, Integer, Boolean> {
    private Job job;
    
    public static class Job {
        public TestCaseActivity testCaseActivity;
        public long productID;
        public long testCaseID;
        Result result;
        public Job(long productID, long testCaseID, TestCaseActivity testCaseActivity, Result result) {
            this.testCaseActivity = testCaseActivity;
            this.productID = productID;
            this.testCaseID = testCaseID;
            this.result = result;
        }
    }
    
    @Override
    protected Boolean doInBackground(Job... params) {
        job = params[0];
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("result[result]", job.result.getResult());
        postParams.put("result[tester]", job.result.getTester());
        postParams.put("result[comment]", job.result.getComment());
        try {
            WebUtils.postRequest(WebAPI.createResultURL(job.productID, job.testCaseID), postParams);
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
        job.testCaseActivity.refresh();
    }
}
