package org.agoncal.application.petstore.domain;

import lombok.*;
import org.agoncal.application.petstore.constraint.Price;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@NamedQueries({
        @NamedQuery(name = Item.FIND_BY_PRODUCT_ID, query = "SELECT i FROM Item i WHERE i.product.id = :productId"),
        @NamedQuery(name = Item.SEARCH, query = "SELECT i FROM Item i WHERE UPPER(i.name) LIKE :keyword OR UPPER(i.product.name) LIKE :keyword ORDER BY i.product.category.name, i.product.name"),
        @NamedQuery(name = Item.FIND_ALL, query = "SELECT i FROM Item i")
})
@XmlRootElement
public class Item {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 30)
    @NonNull
    @Size(min = 1, max = 30)
    private String name;
    @NonNull
    @Column(nullable = false)
    @Price
    private Float unitCost;
    @NonNull
    private String imagePath;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "product_fk", nullable = false)
    @XmlTransient
    private Product product;
    @NonNull
    @Column(length = 3000)
    private String description;

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String FIND_BY_PRODUCT_ID = "Item.findByProductId";
    public static final String SEARCH = "Item.search";
    public static final String FIND_ALL = "Item.findAll";

}