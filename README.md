# AutomatedPerf

This project aims to gather, process, and transmit data about the JVM's state to ElasticSearch.

## Getting Started

### Prerequisites

To run this project locally, you need to have the following installed:

- Java 11
- [Docker]("https://www.docker.com/")

### Installation

1. Clone the repository

```sh
git clone https://github.com/Drill4J/auto-perf-testing.git
```

2. Config env vars at app.properties file:

`app-jmx-connection-url` - connection URL to JMX of app. Example: service:jmx:rmi://0.0.0.0:9011/jndi/rmi://0.0.0.0:
9010/jmxrmi

`admin-jmx-connection-url` - connection URL to JMX of admin. Example: service:jmx:rmi://0.0.0.0:9013/jndi/rmi://0.0.0.0:
9012/jmxrmi

`execution-command-path` - path to your project using for run all commands. Example: C:
\\projects\\Drill4j\\realworld-java-and-js-coverage

`command-to-run-tests` - command using which tests will be run. Example: ./gradlew.bat clean :build1:test

2. Start elastic-stack using start.sh file

```sh
.\elastic\start.sh
```

3. Import plots.ndjson to Kibana. Useful
   link: https://www.elastic.co/guide/en/kibana/current/managing-saved-objects.html
4. You can switch to feature/automated-perf-set-up at realworld app or modify JAVA_TOOL_OPTIONS at admin and AUT to
   enable jmx:
   For the Drill4J admin panel:
    ```
    -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=9012 -Dcom.sun.management.jmxremote.rmi.port=9013 -Djava.rmi.server.hostname=0.0.0.0 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
    ```
   For the AUT:
    ```
    -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.rmi.port=9011 -Djava.rmi.server.hostname=0.0.0.0 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
    ```
4. Start the app using Main method
5. Navigate to http://localhost:5601/
6. Sign-in at Kibana using:

    - Username: `elastic`
    - Password: `mysecretpassword1`

7. Go to the "**Analytics -> Dashboard**" section. This page will display 6 charts showing the states of the Drill4J
   admin panel and the application.
