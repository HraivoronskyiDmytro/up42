package com.up42.qa;

public class Config {

    public static String baseUri = "https://api.up42.com";
    public static String projectID = "";
    public static String apiKey = "";
    public interface Endpoints {
        String TOKEN = "/oauth/token";
        String PROJECT = "/projects/{projectId}";
        String WORKFLOWS = PROJECT + "/workflows";
        String SINGLE_WORKFLOW = WORKFLOWS + "/{workflowid}";
        String TASKS = SINGLE_WORKFLOW + "/tasks";
        String JOBS = SINGLE_WORKFLOW + "/jobs";
        String SINGLE_JOB = PROJECT + "/jobs/{jobid}";

    }
}


