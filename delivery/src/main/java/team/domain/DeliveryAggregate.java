package team.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.*;
import org.axonframework.spring.stereotype.Aggregate;

import org.springframework.beans.BeanUtils;
import lombok.Data;
import lombok.ToString;


@Aggregate
@Data
@ToString
public class DeliveryAggregate {

    @AggregateIdentifier
    private Long id;
    private String address;
    private String customerId;
    private Integer quantity;
    private Long orderId;

    public DeliveryAggregate(){}

    @CommandHandler
    public void handle(ReturnDeliveryCommand command){

        DeliveryReturnedEvent event = new DeliveryReturnedEvent();
        BeanUtils.copyProperties(command, event);     
        apply(event);

    }

    @CommandHandler
    public DeliveryAggregate(AddToDeliveryListCommand command){

        DeliveryStartedEvent event = new DeliveryStartedEvent();
        event.setId(command.getOrderId());
        BeanUtils.copyProperties(command, event);     
        apply(event);

    }








    @EventSourcingHandler
    public void on(DeliveryStartedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
    public void on(DeliveryReturnedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


}
