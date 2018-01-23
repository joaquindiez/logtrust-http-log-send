# logtrust-http-log-send
small library to send data to Logtrust using Http Protocol

## Documentation


Please refer to [Logtrust documentation](https://docs.logtrust.com/confluence/docs/system-configuration/sending-the-data/http-sending#HTTPsending-Configuringtheendpoints) to configure account to be able to send 
data to Logtrust 


## End points

By default the library is configured to use the USA endPoint

As soon as new endpoints are available, will be added to the library

## Code Example

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