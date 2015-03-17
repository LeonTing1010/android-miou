package com.datang.business;/* Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.StringBuilderPrinter;

import com.datang.business.measurements.HttpTask;
import com.datang.business.measurements.HttpTask.HttpDesc;
import com.datang.business.measurements.PingTask;
import com.datang.business.measurements.PingTask.PingDesc;
import com.datang.business.util.Logger;
import com.datang.business.util.MeasurementJsonConvertor;
import com.datang.business.util.Util;

import java.util.Formatter;
import java.util.HashMap;

/**
 * POJO that represents the result of a measurement
 *
 * @author wenjiezeng@google.com (Wenjie Zeng)
 * @see MeasurementDesc
 */
public class MeasurementResult {

    private String deviceId;
    private DeviceProperty properties;
    private long timestamp;
    private boolean success;
    private String taskKey;
    private String type;
    private MeasurementDesc parameters;
    private HashMap<String, String> values;

    /**
     * @param deviceProperty
     * @param type
     * @param timeStamp
     * @param success
     * @param measurementDesc
     */
    public MeasurementResult(String id, DeviceProperty deviceProperty, String type,
                             long timeStamp, boolean success, MeasurementDesc measurementDesc) {
        super();
        this.taskKey = measurementDesc.key;
        this.deviceId = id;
        this.type = type;
        this.properties = deviceProperty;
        this.timestamp = timeStamp;
        this.success = success;
        this.parameters = measurementDesc;
        this.parameters.parameters = null;
        this.values = new HashMap<String, String>();
    }

    /* Returns the type of this result */
    public String getType() {
        return parameters.getType();
    }

    /* Add the measurement results of type String into the class */
    public void addResult(String resultType, Object resultVal) {
        this.values.put(resultType, MeasurementJsonConvertor.toJsonString(resultVal));
    }

    /* Returns a string representation of the result */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        StringBuilderPrinter printer = new StringBuilderPrinter(builder);
        Formatter format = new Formatter();
        try {
            if (type == PingTask.TYPE) {
                getPingResult(printer, values);
            } else if (type == HttpTask.TYPE) {
                getHttpResult(printer, values);
            }
            return builder.toString();
        } catch (NumberFormatException e) {
            Logger.e("Exception occurs during constructing result string for user", e);
        } catch (ClassCastException e) {
            Logger.e("Exception occurs during constructing result string for user", e);
        } catch (Exception e) {
            Logger.e("Exception occurs during constructing result string for user", e);
        }
        return "Measurement has failed";
    }

    private void getPingResult(StringBuilderPrinter printer, HashMap<String, String> values) {
        PingDesc desc = (PingDesc) parameters;
        printer.println("[Ping]");
        printer.println("Target: " + desc.target);
        printer.println("IP address: " + removeQuotes(values.get("target_ip")));
        printer.println("Timestamp: " + Util.getTimeStringFromMicrosecond(properties.timestamp));

        if (success) {
            float packetLoss = Float.parseFloat(values.get("packet_loss"));
            int count = Integer.parseInt(values.get("packets_sent"));
            printer.println("\n" + count + " packets transmitted, " + (int) (count * (1 - packetLoss)) +
                    " received, " + (packetLoss * 100) + "% packet loss");

            float value = Float.parseFloat(values.get("mean_rtt_ms"));
            printer.println("Mean RTT: " + String.format("%.1f", value) + " ms");

            value = Float.parseFloat(values.get("min_rtt_ms"));
            printer.println("Min RTT: " + String.format("%.1f", value) + " ms");

            value = Float.parseFloat(values.get("max_rtt_ms"));
            printer.println("Max RTT: " + String.format("%.1f", value) + " ms");

            value = Float.parseFloat(values.get("stddev_rtt_ms"));
            printer.println("Std dev: " + String.format("%.1f", value) + " ms");
        } else {
            printer.println("Failed");
        }
    }

    private void getHttpResult(StringBuilderPrinter printer, HashMap<String, String> values) {
        HttpDesc desc = (HttpDesc) parameters;
        printer.println("[HTTP]");
        printer.println("URL: " + desc.url);
        printer.println("Timestamp: " + Util.getTimeStringFromMicrosecond(properties.timestamp));

        if (success) {
            int headerLen = Integer.parseInt(values.get("headers_len"));
            int bodyLen = Integer.parseInt(values.get("body_len"));
            int time = Integer.parseInt(values.get("time_ms"));
            printer.println("\nDownloaded " + (headerLen + bodyLen) + " bytes in " + time + " ms");
            printer.println("Bandwidth: " + (headerLen + bodyLen) * 8 / time + " Kbps");
        } else {
            printer.println("Download failed, status code " + values.get("code"));
        }
    }


    /**
     * Removes the quotes surrounding the string. If the string is less than 2 in length,
     * we returns null
     */
    private String removeQuotes(String str) {
        if (str != null && str.length() > 2) {
            return str.substring(1, str.length() - 2);
        } else {
            return null;
        }
    }
}
 