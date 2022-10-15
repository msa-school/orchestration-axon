package team.domain;

import lombok.*;

@Data
@ToString
public class OrderPlaced {

    private Long id;
    private String productId;
    private Long qty;
    private String address;


}


