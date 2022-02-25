package com.up42.qa;


import io.restassured.http.ContentType;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.up42.qa.RequestBody.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PlatformTest extends BaseTest {


    private String workflowID;
    private String jobID;

    @AfterClass
    public void tearDown() {
        request()
                .header("Authorization", "Bearer " + getToken())
                .delete(Config.Endpoints.SINGLE_WORKFLOW, Config.projectID, workflowID).then().statusCode(204);
    }

    @Test
    public void createWorkflow() {

        workflowID = request()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(workflow())
                .when()
                .post(Config.Endpoints.WORKFLOWS, Config.projectID)
                .then()
                .statusCode(200)//.extract().response().prettyPrint();
                .assertThat()
                .body(matchesJsonSchemaInClasspath("workflow.json").using(jsonSchemaFactory))
                .extract().jsonPath().getString("data.id");

    }

    @Test (dependsOnMethods = { "createWorkflow" })
    public void createTask() {
        request()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(task())
                .when()
                .post(Config.Endpoints.TASKS, Config.projectID, workflowID)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("task.json").using(jsonSchemaFactory));

    }

    @Test (dependsOnMethods = { "createTask" })
    public void runJob() {
        jobID =
                request()
                        .header("Authorization", "Bearer " + getToken())
                        .contentType(ContentType.JSON)
                        .body(job())
                        .when()
                        .post(Config.Endpoints.JOBS, Config.projectID, workflowID)
                        .then()
                        .statusCode(200)
         .body(matchesJsonSchemaInClasspath("job.json").using(jsonSchemaFactory))
         .extract().jsonPath().getString("data.id");
    }

    @Test (dependsOnMethods = { "runJob" })
    public void getJobDetails() {
        Awaitility.await().atMost(Durations.FIVE_MINUTES)
                .pollInSameThread()
                .pollInterval(Durations.FIVE_SECONDS)
                .untilAsserted(() -> {
                    request()
                            .header("Authorization", "Bearer " + getToken())
                            .get(Config.Endpoints.SINGLE_JOB, Config.projectID, jobID)
                            .then()
                            .body(matchesJsonSchemaInClasspath("jobDetails.json").using(jsonSchemaFactory))
                            .assertThat().extract().jsonPath().getString("data.status").equals("SUCCEEDED");
                });
    }

}
