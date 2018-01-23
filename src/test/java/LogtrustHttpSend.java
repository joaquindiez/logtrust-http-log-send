/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import com.freesoullabs.LogtrustLogger;

public class LogtrustHttpSend {
     public void testSomeLibraryMethod() {
        String token = "{your_token}";
        String tag = "{your table tag}";
        String domain = "{your_domain}";
        
        LogtrustLogger httpLogger =  new LogtrustLogger.Builder()
                .domain(domain)
                .token(token)
                .defaultTag(tag)
                .build();
        int max = 1;
        for (int i = 0; i < max; i++)
          httpLogger.send("hello http data " + i);

    }
}
