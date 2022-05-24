# data boxes



## Overview

**What is the problem?**

First lets take the basic example of a web application that is handling user-data:

```mermaid
flowchart TB
    classDef vulnerable fill:#f1ac56
    classDef critical fill:#ffcccb
    classDef safe fill:#daf7a6
    classDef resetColor fill:#ffffff

    subgraph UI[frontend]
        raw7[raw-data]
    end
    service ----- raw1[raw-data] --> UI:::vulnerable

    subgraph context[service runtime]
        service --- raw2[raw-data] --> logger:::vulnerable
    end
    class context resetColor

    subgraph service
        raw5[raw-data]
    end
    class service resetColor

    subgraph logger
        raw6[raw-data]
    end
    
    service <-- AuthZ by Service </br> on behalf of User --> DB[(database 1)]
    class DB safe
    subgraph DB
        subgraph wrappedData[wrapped data]
            direction LR
            pii-attributes
            ownership-attributes
            raw4[raw-data]
        end 
    end
    class wrappedData safe
    service ----- raw3[raw-data] -- No User AuthZ because </br> no metadata attached ---> DB2[(database 2)]
    class DB2 vulnerable
    class raw1,raw2,raw3,raw4,raw5,raw6,raw7 critical
```

**And, so what is the solution?**

The metadata needs to live with the data at all times! Then a policy agent can evaluate when sharing is appropriate based on the service context.
```mermaid
flowchart TB
    classDef wrapped fill:#fffacd
    classDef vulnerable fill:#f1ac56
    classDef critical fill:#ffcccb
    classDef safe fill:#daf7a6
    classDef resetColor fill:#ffffff

    subgraph UI[frontend]
        cleaned1[cleaned-data]
    end
    class UI resetColor
	
    service ----- cleaned2[cleaned-data] ---> UI

    subgraph context[service runtime]
        subgraph PA[Policy Agent]
        end
        service --- cleaned3[cleaned-data] --> logger:::vulnerable
    end
    class context,PA resetColor

	  subgraph service
        WD1[wrapped data]
    end
    class service resetColor
    WD1 <-- raw-data access </br> authenticated ---> PA

    subgraph logger
        cleaned4[cleaned-data]
    end
    class logger resetColor
    
    service <-- AuthZ by Service </br> on behalf of User --> DB1
    subgraph DB1[database 1]
        subgraph WD2[wrapped data]
            direction LR
            pii1[pii-attributes]:::resetColor
            own1[ownership-attributes]:::resetColor
            raw1[raw-data]
        end 
    end
    
	  service ----- WD3[wrapped-data] -- AuthZ by Service </br> on behalf of User ---> DB2[(database 2)]

    class raw1 critical
	  class cleaned1,cleaned2,cleaned3,cleaned4 safe
    class DB1,DB2 resetColor
    class WD1,WD2,WD3 wrapped
```

Now, any time a "step down" from `wrapped-data` to `raw-data` is needed an adapter will need to review the combination:    
`(auth context, metadata, policy)`

Examples of where this is done is before sharing to the user-interface, to the logger.
All datastores should not accept `raw-data` (unless there's some intake flow elsewhere) only `wrapped-data` so ownership, privacy metadata can be upheld.
Operations between 2 pieces of wrapped-data in a service (depending upon policy) should result in the union of their metadata e.g. if you are manipulating wrapped _social insurance number_ with _address_ then the resulting wrapped data would have both of those attributes.

## Building 

_TODO: need to figure out how to use Bazel, but not right now..._

```
mvn compile exec:java -Dexec.mainClass="com.joek.databoxes.Main"
```
