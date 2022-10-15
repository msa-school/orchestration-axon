package team.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class ApproveOrderCommand {

    @TargetAggregateIdentifier
    Long id;

}
