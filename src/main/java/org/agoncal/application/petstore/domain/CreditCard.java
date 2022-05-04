package org.agoncal.application.petstore.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */

@Data
@NoArgsConstructor
@Embeddable
public class CreditCard {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(name = "credit_card_number", length = 30)
    @NotNull
    @Size(min = 1, max = 30)
    private String creditCardNumber;
    @Column(name = "credit_card_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private CreditCardType creditCardType;
    @Column(name = "credit_card_expiry_date", length = 5)
    @NotNull
    @Size(min = 1, max = 5)
    private String creditCardExpDate;

}