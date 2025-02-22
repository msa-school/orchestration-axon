
package team.domain;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class OrderPlaced{

    private Long id;
    private String productId;
    private Integer qty;
    private String customerId;
    private Double amount;
    private String status;
    private String address;

}
