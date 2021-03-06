# logtrust-http-log-send
small library to send data to Logtrust using Http Protocol

## Documentation


Please refer to [Logtrust documentation](https://docs.logtrust.com/confluence/docs/system-configuration/sending-the-data/http-sending#HTTPsending-Configuringtheendpoints) to configure account to be able to send
data to Logtrust


## End points

By default the library is configured to use the USA endPoint

As soon as new endpoints are available, will be added to the library

### How to use it

add the maven repository ( if you do not already have it)

```
maven {
       url "https://oss.sonatype.org/content/groups/public/"
   }

```

add the following to the dependencies

```
compile 'com.freesoullabs:logtrust-http-log-send:0.2.1'
```

## Code Example

#### Sending one event

```
        String token = "{your_token}";
        String tag = "{your table tag}";
        String domain = "{your_domain}";

        LogtrustLogger httpLogger =  new LogtrustLogger.Builder()
                .domain(domain)
                .token(token)
                .defaultTag(tag)
                .build();

        httpLogger.send("hello http data ");

```

#### Sending Multiple events in one request

You can optimize the number of request sending data to Logtrust, sending and
array of events. use the sendAll method in that case.

```
        String token = "{your_token}";
        String tag = "{your table tag}";
        String domain = "{your_domain}";
        
        LogtrustLogger httpLogger = new LogtrustLogger.Builder()
            .domain(domain)
            .token(token)
            .defaultTag(tag)
            .build();
            
        int max = 10;
        String[] data = new String[10];
        for (int i = 0; i < max; i++){
         data[i] = "Hello world Buffered Data = " + i;
        }

    httpLogger.sendAll(data);
    
```