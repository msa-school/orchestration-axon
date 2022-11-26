package team.infra;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.domain.*;


@Service
@ProcessingGroup("orders")
public class PolicyHandler{

    @Autowired
    CommandGateway commandGateway;

    // Order service's Saga (OrderProcess.java) will do this in turn
//     @EventHandler
//     public void on(OrderPlaced orderPlaced){

// System.out.println("---- orderPlaced: " + orderPlaced);

//         DecreaseInventoryCommand decreaseInventoryCommand = new DecreaseInventoryCommand();

//         decreaseInventoryCommand.setId(orderPlaced.getProductId());
//         decreaseInventoryCommand.setQty(orderPlaced.getQty().intValue());
        
//         BeanUtils.copyProperties(orderPlaced, decreaseInventoryCommand);
//         commandGateway.send(decreaseInventoryCommand);
//     }

    private final Map<String, Inventory> inventories = new HashMap<>(); //must be changed to some kind of storage-based

    @EventHandler
    public void on(InventoryCreated event) {
        String orderId = event.getId();
        Inventory inventory = new Inventory();
        inventory.on(event);

        inventories.put(orderId, inventory);
    }

    @EventHandler
    public void on(InventoryDecreased event) {
        Inventory inventory = inventories.get(event.getId());
        inventory.on(event);
    }

    @EventHandler
    public void on(InventoryIncreased event) {
        Inventory inventory = inventories.get(event.getId());
        inventory.on(event);
    }

    @QueryHandler
    public List<Inventory> handle(FindAllInventoryQuery query) {
        return new ArrayList<>(inventories.values());
    }

}


