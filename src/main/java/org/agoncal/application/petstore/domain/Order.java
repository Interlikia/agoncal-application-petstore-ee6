package org.agoncal.application.petstore.domain;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "t_order")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = Order.FIND_ALL, query = "SELECT o FROM Order o")
})
public class Order {

    // ======================================
    // =             Attributes             =
    // ======================================

    public static final String FIND_ALL = "Order.findAll";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "order_date", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_fk", nullable = false)
    private Customer customer;
    @NonNull
    @Embedded
    private CreditCard creditCard;
    @Embedded
    @NonNull
    private Address deliveryAddress;


    // ======================================
    // =             Constants              =
    // ======================================
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_order_order_line",
            joinColumns = {@JoinColumn(name = "order_fk")},
            inverseJoinColumns = {@JoinColumn(name = "order_line_fk")})
    private List<OrderLine> orderLines;

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @PrePersist
    private void setDefaultData() {
        orderDate = new Date();
    }

    // ======================================
    // =              Public Methods        =
    // ======================================

    public Float getTotal() {
        if (orderLines == null || orderLines.isEmpty())
            return 0f;

        Float total = 0f;

        // Sum up the quantities
        for (OrderLine orderLine : orderLines) {
            total += (orderLine.getSubTotal());
        }

        return total;
    }

}
