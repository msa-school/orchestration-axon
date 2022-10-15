package team.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class OrderCancelCommand {

    @TargetAggregateIdentifier
    private Long id;
    private String productId;
    private Long qty;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
