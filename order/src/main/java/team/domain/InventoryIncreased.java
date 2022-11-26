package team.domain;

import lombok.Data;

@Data
public class InventoryIncreased {

    String id;
    int qty;
    Long orderId;

}
