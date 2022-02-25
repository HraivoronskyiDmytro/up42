package com.up42.qa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBody {

    public static Map workflow() {
        Map<String, Object> workflow = new LinkedHashMap<>();
        workflow.put("name", "QA coding challenge workflow");
        workflow.put("description", "Workflow description");

        return workflow;
    }

    public static String task() {

        JSONArray task = new JSONArray();
        task.put(new JSONObject()
                        .put("name", "nasa-modis:1")
                        .put("parentName", JSONObject.NULL)
                        .put("blockId", "ef6faaf5-8182-4986-bce4-4f811d2745e5"))
                .put(new JSONObject()
                        .put("name", "sharpening:1")
                        .put("parentName", "nasa-modis:1")
                        .put("blockId", "e374ea64-dc3b-4500-bb4b-974260fb203e"));


        return task.toString();
    }

    public static String job() {

        JSONObject job = new JSONObject();
        job.put("nasa-modis:1", new JSONObject()
                        .put("time", "2018-12-01T00:00:00+00:00/2020-12-31T23:59:59+00:00")
                        .put("limit", 1)
                        .put("zoom_level", 9)
                        .put("imagery_layers", new JSONArray()
                                .put("MODIS_Terra_CorrectedReflectance_TrueColor"))
                        .put("bbox", new JSONArray()
                                .put(13.365373)
                                .put(52.49582)
                                .put(13.385796)
                                .put(52.510455)))
                .put("sharpening:1", new JSONObject()
                        .put("strength", "medium"));

        return job.toString();
    }
}
