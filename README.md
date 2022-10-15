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
http :8081/orders   # shows the status is "APPROVED"
```

- check the events and aggregate's data with Axon Server Dashboard:
```
http://localhost:8024  

```

# Implementation Details

See the saga definition:
```
# order/OrderProcess.java

package team.domain;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderProcess {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(OrderPlaced orderPlaced){
        System.out.println("Saga invoked");

        //associate Saga
        SagaLifecycle.associateWith("orderId", orderPlaced.getId());
        SagaLifecycle.associateWith("productId", orderPlaced.getProductId());
        

        //send the commands
        DecreaseInventoryCommand command = new DecreaseInventoryCommand();
        command.setId(orderPlaced.getProductId());
        command.setQty(orderPlaced.getQty().intValue());
        command.setOrderId(orderPlaced.getId());

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "id", keyName = "productId")
    public void handle(InventoryDecreased event){

        System.out.println("Saga continued with orderId = " + event.getOrderId());

        //send the create shipping command
        ApproveOrderCommand command = new ApproveOrderCommand();
        command.setId(event.getOrderId());

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "id", keyName = "orderId")
    public void handle(OrderApproved event){

        System.out.println("end saga");
        SagaLifecycle.end();
    }

}

```


# Limitations

- when I try to place an order while shutting down the inventory service, the process won't rety the process until the inventory service is coming back.
- I needed to carry the saga instance data inside the Event object. I couldn't find how to get the saga instance data from the SagaLifecycle object.