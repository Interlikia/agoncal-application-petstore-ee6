package org.agoncal.application.petstore.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

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
@Embeddable
public class Address {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(nullable = false)
    @NonNull
    @Size(min = 5, max = 50)
    private String street1;
    private String street2;
    @Column(nullable = false, length = 100)
    @NonNull
    @Size(min = 2, max = 50)
    private String city;
    private String state;
    @Column(name = "zip_code", nullable = false, length = 10)
    @NonNull
    @Size(min = 1, max = 10)
    private String zipcode;
    @Column(nullable = false, length = 50)
    @NonNull
    @Size(min = 2, max = 50)
    private String country; // TODO use an enum

}
