package com.up42.qa;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import javafx.util.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private final ThreadLocal<Pair<Long, String>> token = ThreadLocal.withInitial(() -> new Pair(0L, ""));
    /**
     * Set JSON Schema checking to draft-08
     * see https://json-schema.org/
     */
    JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
            .setValidationConfiguration(
                    ValidationConfiguration.newBuilder()
                            .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
            .freeze();

    public RequestSpecification request() {
        return given().baseUri(Config.baseUri);
    }

    private String callToken() {

        return
                request()
                        .auth().preemptive()
                        .basic(Config.projectID, Config.apiKey)
                        .contentType(ContentType.URLENC)
                        .body("grant_type=client_credentials")
                        .basePath(Config.Endpoints.TOKEN)
                        .when()
                        .post().
                        then()
                        .statusCode(200)
                        .extract()
                        .response().jsonPath().getString("access_token");
    }

    public String getToken() {

        long currentTimeInMillis = DateTime.now(DateTimeZone.UTC).getMillis();
        if ((currentTimeInMillis - token.get().getKey() > 300000))
            token.set(new Pair(currentTimeInMillis, callToken()));

        return token.get().getValue();
    }

}
