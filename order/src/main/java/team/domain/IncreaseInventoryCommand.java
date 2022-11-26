package team.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class IncreaseInventoryCommand {
    @TargetAggregateIdentifier
    String id;

    int qty;
    Long orderId;
}
