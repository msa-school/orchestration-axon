package team.infra;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;


import team.domain.*;
import team.domain.AddToDeliveryListCommand;
import team.domain.ReturnDeliveryCommand;

@RestController
public class DeliveryController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public DeliveryController(CommandGateway commandGateway, QueryGateway queryGateway) {
      this.commandGateway = commandGateway;
      this.queryGateway = queryGateway;
  }


  @RequestMapping(value = "/returndelivery",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture returnDelivery(@RequestBody ReturnDeliveryCommand returnDeliveryCommand)
        throws Exception {
      System.out.println("##### /delivery/returnDelivery  called #####");

      // send command
      return commandGateway.send(returnDeliveryCommand);
  }

  @RequestMapping(value = "/deliveries",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture addToDeliveryList(@RequestBody AddToDeliveryListCommand addToDeliveryListCommand)
        throws Exception {
      System.out.println("##### /delivery/addToDeliveryList  called #####");

      // send command
      return commandGateway.send(addToDeliveryListCommand);
  }

}
