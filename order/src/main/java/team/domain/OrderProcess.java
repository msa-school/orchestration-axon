package team.domain;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
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

        commandGateway
            .send(command)
            .exceptionally(ex -> {
                OrderCancelCommand orderCancelCommand = new OrderCancelCommand();
                orderCancelCommand.setId(orderPlaced.getId());

                return commandGateway.send(orderCancelCommand);
            });
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "id")
    public void handle(InventoryDecreased event){

        System.out.println("Saga continued with orderId = " + event.getOrderId());

        //send the create shipping command
        AddToDeliveryListCommand command = new AddToDeliveryListCommand();
        command.setId(event.getOrderId());
        command.setOrderId(event.getOrderId());
    
        commandGateway.send(command)
            .exceptionally(ex ->{
                IncreaseInventoryCommand increaseInventoryCommand = new IncreaseInventoryCommand();
                increaseInventoryCommand.setId(event.getId());
                increaseInventoryCommand.setOrderId(event.getOrderId());
                increaseInventoryCommand.setQty(event.getQty());

                return commandGateway.send(increaseInventoryCommand);
            });
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "id")
    public void compensateOrder(InventoryIncreased event){
        OrderCancelCommand orderCancelCommand = new OrderCancelCommand();
        orderCancelCommand.setId(event.getOrderId());

        commandGateway.send(orderCancelCommand);
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "id")
    public void handle(DeliveryStartedEvent event){

        System.out.println("Saga continued with orderId = " + event.getOrderId());

        //send the create shipping command
        ApproveOrderCommand command = new ApproveOrderCommand();
        command.setId(event.getOrderId());

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "id", keyName = "orderId")
    @EndSaga
    public void handle(OrderApproved event){

        System.out.println("end saga");
        //SagaLifecycle.end();
    }

}