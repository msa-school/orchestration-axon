package team.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class SetInventoryCommand{

    @TargetAggregateIdentifier
    String id;
    Long stock;

}
