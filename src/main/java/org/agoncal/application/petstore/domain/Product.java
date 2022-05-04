package org.agoncal.application.petstore.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
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
@NamedQueries({
        // TODO fetch doesn't work with GlassFish
//        @NamedQuery(name = Product.FIND_BY_CATEGORY_NAME, query = "SELECT p FROM Product p LEFT JOIN FETCH p.items LEFT JOIN FETCH p.category WHERE p.category.name = :pname"),
        @NamedQuery(name = Product.FIND_BY_CATEGORY_NAME, query = "SELECT p FROM Product p WHERE p.category.name = :pname"),
        @NamedQuery(name = Product.FIND_ALL, query = "SELECT p FROM Product p")
})
@XmlRootElement
public class Product {

    // ======================================
    // =             Attributes             =
    // ======================================

    public static final String FIND_BY_CATEGORY_NAME = "Product.findByCategoryName";
    public static final String FIND_ALL = "Product.findAll";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 30)
    @NonNull
    @Size(min = 1, max = 30)
    private String name;
    @NonNull
    @Column(nullable = false)
    private String description;

    // ======================================
    // =             Constants              =
    // ======================================
    @NonNull
    @ManyToOne
    @JoinColumn(name = "category_fk", nullable = false)
    @XmlTransient
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    @XmlTransient
    private List<Item> items;

    public void addItem(Item item) {
        if (items == null)
            items = new ArrayList<Item>();
        items.add(item);
    }

}