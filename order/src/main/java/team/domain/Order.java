package team.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.*;
import org.axonframework.spring.stereotype.Aggregate;

import org.springframework.beans.BeanUtils;

import lombok.Data;




@Aggregate(snapshotTriggerDefinition = "orderAggregateSnapshotTriggerDefinition")
@Data
public class Order {

    @AggregateIdentifier
    private Long id;
    private String productId;
    private Long qty;
    private String address;
    private String status;

    
    protected Order(){}

    public Order(Long id){
        this.id = id;
    }

    @CommandHandler
    public Order(OrderCommand command){
        this.id = command.getId();

        OrderPlaced event = new OrderPlaced();
        BeanUtils.copyProperties(command, event);     
        
        apply(event);
    }

    // @CommandHandler
    // public void handle(OrderCommand command){
    //     // TODO send Event
    //     // AggregateLifecycle.apply( Event );

    //     }

    @CommandHandler
    public void handle(OrderCancelCommand command){

        OrderCanceled orderCanceledEvent = new OrderCanceled();
        BeanUtils.copyProperties(command, orderCanceledEvent);
        apply(orderCanceledEvent);

    }

    @CommandHandler
    public void handle(ApproveOrderCommand command){

        OrderApproved event = new OrderApproved();
        BeanUtils.copyProperties(command, event);
        apply(event);

    }


    @EventSourcingHandler
    public void on(OrderPlaced event) {
        BeanUtils.copyProperties(event, this);
        setStatus("READY");
    }


    @EventSourcingHandler
    public void on(OrderCanceled event) {
        this.status = "CANCELLED";
       // markDeleted();
    }

    @EventSourcingHandler
    public Order on(OrderApproved event) {
        this.status = "APPROVED";
        return this;
    }



}
