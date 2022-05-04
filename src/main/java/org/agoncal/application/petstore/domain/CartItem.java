package org.agoncal.application.petstore.domain;

import lombok.*;

import javax.validation.constraints.Min;

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
public class CartItem {

    // ======================================
    // =             Attributes             =
    // ======================================

    @NonNull
    private Item item;
    @NonNull
    @Min(1)
    private Integer quantity;

    // ======================================
    // =              Public Methods        =
    // ======================================

    public Float getSubTotal() {
        return item.getUnitCost() * quantity;
    }

}