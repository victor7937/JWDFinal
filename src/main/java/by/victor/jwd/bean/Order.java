package by.victor.jwd.bean;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 872063231245620651L;

    private Customer customer;
    private LocalDateTime date;
    private OrderStatus orderStatus;
    private List<FootwearItem> items;
    private Float price;
    private Integer id;



    public Order (Customer customer, LocalDateTime date, List<FootwearItem> items) {
        this.customer = customer;
        this.date = date;
        this.items = items;
        this.orderStatus = OrderStatus.WAITING;
        this.price = 0.0f;
    }

    public Order(Customer customer, LocalDateTime date) {
        this.customer = customer;
        this.date = date;
        this.items = new ArrayList<>();
        this.orderStatus = OrderStatus.WAITING;
        this.price = 0.0f;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<FootwearItem> getItems() {
        return items;
    }

    public void setItems(List<FootwearItem> items) {
        this.items = items;
    }

    public void addItem(FootwearItem footwearItem) {
        this.items.add(footwearItem);
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customer, order.customer) && Objects.equals(date, order.date) && orderStatus == order.orderStatus && Objects.equals(items, order.items) && Objects.equals(price, order.price) && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, date, orderStatus, items, price, id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer.getEmail() +
                ", date=" + date +
                ", orderStatus=" + orderStatus +
                ", items=" + items +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
