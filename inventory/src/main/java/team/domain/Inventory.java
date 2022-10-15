package team.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import lombok.Data;
import static org.axonframework.modelling.command.AggregateLifecycle.*;

@Aggregate//(snapshotTriggerDefinition = "inventoryAggregateSnapshotTriggerDefinition")
@Data
public class Inventory  {
    
    @AggregateIdentifier
    private String id;
    
    private Long stock;

    public Inventory(){} //cons: need empty constructor

    @CommandHandler
    public Inventory(SetInventoryCommand command){
        setId(command.getId());

        InventoryCreated event = new InventoryCreated();
        BeanUtils.copyProperties(command, event);
        apply(event);
    }

    @EventSourcingHandler
    public void on(InventoryCreated event){
        System.out.println("----- stock = " + event.getStock());
        BeanUtils.copyProperties(event, this);
    }


    @CommandHandler
    public void handle(DecreaseInventoryCommand command){
        InventoryDecreased event = new InventoryDecreased();
        BeanUtils.copyProperties(command, event);
        apply(event);
    }

    @EventSourcingHandler
    public void on(InventoryDecreased event){
        setStock(getStock() - event.getQty());
    }


}
