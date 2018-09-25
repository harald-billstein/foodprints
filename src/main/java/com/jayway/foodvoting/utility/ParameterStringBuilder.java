package com.jayway.foodvoting.utility;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {

  public static String getParameterString(Map<String, String> parameters) {

    StringBuilder result = new StringBuilder();

    result.append("?");
    for (Map.Entry<String, String> entry : parameters.entrySet()) {
      result.append(URLEncoder.encode(entry.getKey(), UTF_8));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), UTF_8));
      result.append("&");
    }

    if (result.length() > 0) {
      return result.substring(0, (result.length() - 1));
    } else {
      return null;
    }
  }
}
