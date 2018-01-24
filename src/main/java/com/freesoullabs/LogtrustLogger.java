package com.freesoullabs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class LogtrustLogger {

  private Logger log = LoggerFactory.getLogger(LogtrustLogger.class);


  private String endPoint;
  private String domain;
  private String token;
  private String host = "-";
  private String tag;


  public LogtrustLogger(String endPoint,
                        String domain,
                        String token,
                        String host,
                        String defaultTag) {
    this.endPoint = endPoint;
    this.domain = domain;
    this.token = token;
    this.host = host;
    this.tag = defaultTag;
  }

  public void send(final String data) {

    try {
      final String url = generateUrl(
              endPoint,
              domain,
              token,
              host,
              tag,
              encodeToHttp(data));

      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      log.debug("Response Code : {}", responseCode);
      log.debug("Response: {}", con.getResponseMessage());
    } catch (UnsupportedEncodingException e) {
      log.error("Cannot encode message: {}", e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }


  public void sendAll(final String... dataList) {

    try {
      final String url = generatePostUrl(
              endPoint,
              domain,
              token,
              host,
              tag);

      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("POST");
      con.setDoOutput(true);
      con.setInstanceFollowRedirects(false);
      con.setRequestProperty("charset", "utf-8");
      con.setRequestProperty("Content-Length",
              Integer.toString(checkSize(dataList)));

      try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
        for (String event : dataList) {
          wr.write(event.getBytes());
          wr.writeByte('\r');
          wr.writeByte('\n');
        }
      }

      int responseCode = con.getResponseCode();
      log.debug("Response Code : {}", responseCode);
      log.debug("Response: {}", con.getResponseMessage());
    } catch (UnsupportedEncodingException e) {
      log.error("Cannot encode message: {}", e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }

  private int checkSize(final String... dataList) {

    int sum = 0;
    for (String event : dataList) {
      sum += event.getBytes().length;
      sum += 2; //2 byte because of CRLF
    }
    return sum;
  }

  protected static String encodeToHttp(String str)
          throws UnsupportedEncodingException {
    return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
  }

  protected static String generateUrl(String serverPath, String domain,
                                      String token, String hostname,
                                      String tag, String msg) {

    return new StringBuilder(serverPath)
            .append("/event/")
            .append(domain).append("/token!")
            .append(token).append("/")
            .append(hostname).append("/")
            .append(tag).append("?")
            .append(msg)
            .toString();
  }

  protected static String generatePostUrl(String serverPath, String domain,
                                          String token, String hostname,
                                          String tag) {

    return new StringBuilder(serverPath)
            .append("/stream/")
            .append(domain).append("/token!")
            .append(token).append("/")
            .append(hostname).append("/")
            .append(tag)
            .toString();
  }


  public static class Builder {
    public static final String ENDPOINT_USA = "https://http-us.logtrust.io";

    private String endPoint = ENDPOINT_USA;
    private String domain;
    private String token;
    private String host = "-";
    private String defaultTag;


    public Builder endPoint(String v) {
      this.endPoint = v;
      return this;
    }

    public Builder domain(String v) {
      this.domain = v;
      return this;
    }

    public Builder token(String v) {
      this.token = v;
      return this;
    }

    public Builder host(String v) {
      this.host = v;
      return this;
    }

    public Builder defaultTag(String v) {
      this.defaultTag = v;
      return this;
    }

    public LogtrustLogger build() {
      return new LogtrustLogger(endPoint,
              domain,
              token,
              host,
              defaultTag);
    }

  }

}
