package team.infra;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

@RestController
public class OrderController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
      this.commandGateway = commandGateway;
      this.queryGateway = queryGateway;
  }
  

  @RequestMapping(value = "/orders",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
  public  CompletableFuture<Object> order(@RequestBody OrderCommand orderCommand)
        throws Exception {
          System.out.println("##### /order/order  called #####");

          return commandGateway
            .send(orderCommand)
            .thenApply(
                  id -> {
                        Order order = new Order((Long)id);
                        EntityModel<Order> model = EntityModel.of(order);
                        model
                              .add(Link.of("/orders/" + order.getId()).withSelfRel());

                        return new ResponseEntity<>(model, HttpStatus.OK);

                  }
            );
            // .exceptionally(ex -> ex
                  
            // )

            // ;
  }


  @RequestMapping(value = "/orders/{id}",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8")
  public void orderCancel(@PathVariable("id") Long id)
        throws Exception {
          System.out.println("##### /order/orderCancel  called #####");
          // make command
          OrderCancelCommand orderCancel = new OrderCancelCommand();
          orderCancel.setId(id);
          // TODO set attribute
          // send command
          commandGateway.send(orderCancel);
  }

  @GetMapping("/orders")
  public CompletableFuture<List<Order>> findAllOrders() {
      return queryGateway.query(new FindAllOrdersQuery(), ResponseTypes.multipleInstancesOf(Order.class));
  }

}
