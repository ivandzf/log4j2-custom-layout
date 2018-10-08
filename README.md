# log4j2-custom-layout v1.1
[![CircleCI](https://circleci.com/gh/ivandzf/log4j2-custom-layout.svg?style=svg)](https://circleci.com/gh/ivandzf/log4j2-custom-layout)

## Features
* Custom Console Layout
* Custom Json Layout (Support Kafka Logging)

## How to use 
```
<dependency>
    <groupId>com.github.ivandzf</groupId>
    <artifactId>log4j2-custom-layout</artifactId>
    <version>1.1</version>
</dependency>
```

## Configuration
### Environment
set environment for kafka json logging
```
LogEnvironment.Builder.build("127.0.0.1","9099","yourApplicationName");
```
### Custom Console Layout
add layout to your appender
```
  <Console name="customConsole">
    <CustomConsoleLayout/>
  </Console>
```
example logging
```
09-10-2018 01:32:55:746 INFO [main] org.ivandzf.logging.Application - Started Application in 2.483 seconds (JVM running for 3.981)
```
### Custom Json Layout
console json
```
<Console name="customJsonLayout">
  <CustomJsonLayout/>
</Console>
```
or kafka logging json
```
<Kafka name="kafkaAppender" topic="your_topic">
  <CustomJsonLayout/>
  <Property name="bootstrap.servers">http://your_kafka_server</Property>
</Kafka>
```
example logging output json
```
{"applicationName":"logging-test","ipAddress":"127.0.0.1","port":"9099","level":"INFO","thread":"main","threadId":1,"loggerName":"org.ivandzf.logging.Application","message":"Started Application in 1.81 seconds (JVM running for 3.096)"}
```
add hideEnvironmentWhenNull="false" for show all message when ipAddress/port/applicationName is not set
```
<Console name="customJsonLayout">
  <CustomJsonLayout hideEnvironmentWhenNull="false"/>
</Console>
```

## Custom Message Json
just inject object CustomMessage in your message log
```
List<String> strings = new ArrayList<>();
strings.add("1");
strings.add("2");

List<Integer> integers = new ArrayList<>();
integers.add(1);
integers.add(2);

Map<String, String> stringMap = new HashMap<>();
stringMap.put("string key", "string value");

Map<String, Object> map = new HashMap<>();
map.put("test key", "test value");
map.put("test key list strings", strings);
map.put("test key list integers", integers);
map.put("test key map", stringMap);
map.put("test key boolean", true);

log.debug(CustomMessage.builder().message("test custom debug log").newField(map).build().toJson());
```
then the result is
```
{"applicationName":"logging-test","ipAddress":"127.0.0.1","port":"9099","level":"DEBUG","thread":"qtp1688150025-32","threadId":32,"loggerName":"org.ivandzf.logging.Application","message":"test custom debug log","test key map":"{\"string key\":\"string value\"}","test key boolean":true,"test key list integers":"[1.0,2.0]","test key":"test value","test key list strings":"[\"1\",\"2\"]"}

```
