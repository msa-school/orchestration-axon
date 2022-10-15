# Event Sourcing and Choreography with Axon Example


## How to run

- Run axon server firstly

You need the Axon server to run this application.
You can download the Axon server from below url:
https://axoniq.io/download  


```
cd axon-server
java -jar axonserver-4.3.5.jar
```

axon server UI  
http://localhost:8024  

or You can run the axon server with docker:

```
docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver
```


- Running each microservices

```
cd order
mvn spring-boot:run   #run on 8081

#another terminal
cd inventory
mvn spring-boot:run   #run on 8082

```

## Test

```
http :8082/inventories id="TV" stock=10
http :8081/orders id=1 productId="TV" qty=1

http :8082/inventories   # shows the stock of TV is 9
```

- check the events and aggregate's data with Axon Server Dashboard:
```
http://localhost:8024  

```

# Implementation Details

TBD