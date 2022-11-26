package team.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AddToDeliveryListCommand {

    //@TargetAggregateIdentifier
    private Long id;
    private String address;
    private String customerId;
    private Integer quantity;
    private Long orderId;

}
