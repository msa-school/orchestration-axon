package labshopeventsourcing.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ReturnDeliveryCommand {

    @TargetAggregateIdentifier
    private Long id;
    private String address;
    private String customerId;
    private Integer quantity;
    private Long orderId;

}
