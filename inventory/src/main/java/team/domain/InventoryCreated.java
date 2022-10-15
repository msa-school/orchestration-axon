package team.domain;

import lombok.Data;

@Data
public class InventoryCreated {

    String id;
    Long stock;
    Long orderId;

}
