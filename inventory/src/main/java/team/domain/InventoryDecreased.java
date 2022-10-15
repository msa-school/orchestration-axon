package team.domain;

import lombok.Data;

@Data
public class InventoryDecreased {

    String id;
    int qty;
    Long orderId;

}
