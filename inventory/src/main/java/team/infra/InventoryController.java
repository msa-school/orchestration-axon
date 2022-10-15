package team.infra;
import team.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
// @RequestMapping(value="/inventories")
public class InventoryController {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    QueryGateway queryGateway;

    @RequestMapping(path="/inventories", method=RequestMethod.POST)
    public CompletableFuture<String> setInventory(@RequestBody SetInventoryCommand command){

        return commandGateway.send(command);
        
    }

    @GetMapping("/inventories")
    public CompletableFuture<List<Inventory>> findAllInventories() {
        return queryGateway.query(new FindAllInventoryQuery(), ResponseTypes.multipleInstancesOf(Inventory.class));
    }
  
}
