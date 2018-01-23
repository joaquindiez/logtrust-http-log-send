import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class LogtrustLogger {


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
      System.out.println("Response Code : " + responseCode);
      System.out.println("Response: " + con.getResponseMessage());
    } catch (UnsupportedEncodingException e) {
      System.err.println("Cannot encode message: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static String encodeToHttp(String str)
          throws UnsupportedEncodingException {
    return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
  }

  public static String generateUrl(String serverPath, String domain,
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


  public static class Builder {
    public static final String ENDPOINT_USA = "https://http-us.logtrust.io";

    private String endPoint = ENDPOINT_USA;
    private String domain;
    private String token;
    private String host = "-";
    private String defaultTag;


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
