package se.dose.irontest;

public class WebAPI {
    private static final String BASE_URL = "http://dose.se:3000";
    
    public static final String listProductsURL() {
        return BASE_URL + "/products.xml";
    }
    
    public static final String listTestCasesURL(long productID) {
        return BASE_URL + "/products/" + String.valueOf(productID) + "/test_cases.xml";
    }
    
    public static final String showTestCaseURL(long productID, long testCaseID) {
        return BASE_URL + "/products/" + String.valueOf(productID) + "/test_cases/" + String.valueOf(testCaseID) + ".xml";
    }
    
    public static final String createResultURL(long productID, long testCaseID) {
        return BASE_URL + "/products/" + String.valueOf(productID) + "/test_cases/" + String.valueOf(testCaseID) + "/results";
    }
}
