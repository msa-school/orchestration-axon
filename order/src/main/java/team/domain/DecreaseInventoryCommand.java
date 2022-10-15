package team.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class DecreaseInventoryCommand {
    @TargetAggregateIdentifier
    String id;
    Long orderId;
    int qty;
}
