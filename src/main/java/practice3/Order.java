package practice3;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private List<OrderLineItem> orderLineItemList;
    private List<BigDecimal> discounts;
    private BigDecimal tax;

    public Order(List<OrderLineItem> orderLineItemList, List<BigDecimal> discounts) {
        this.orderLineItemList = orderLineItemList;
        this.discounts = discounts;
        this.tax = new BigDecimal(0.1);
    }

    public BigDecimal calculate() {
        BigDecimal subTotal = new BigDecimal(0);

        Calculations calculations = new Calculations(subTotal).invoke();

        subTotal = subTotal.add(calculations.getItemsTotal());
        subTotal = subTotal.subtract(calculations.getDiscount());

        BigDecimal tax = calculations.getTax(subTotal);

        // calculate GrandTotal
        BigDecimal grandTotal = subTotal.add(tax);

        return grandTotal;
    }

    private class Calculations {

        private BigDecimal itemsTotal = new BigDecimal(0);
        private BigDecimal discount = new BigDecimal(0);

        public Calculations(BigDecimal subTotal) {
        }

        public BigDecimal getTax(BigDecimal subTotal) {
            return subTotal.multiply(tax);
        }

        public BigDecimal getItemsTotal() {
            return itemsTotal;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public Calculations invoke() {
            // Total up line items
            for (OrderLineItem lineItem : orderLineItemList) {
                this.itemsTotal = this.itemsTotal.add(lineItem.getPrice());
            }

            // Subtract discounts
            for (BigDecimal discount : discounts) {
                this.discount = this.discount.add(discount);
            }

            return this;
        }
    }
}
