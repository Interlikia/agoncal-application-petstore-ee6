package org.agoncal.application.petstore.domain;

import lombok.*;
import org.agoncal.application.petstore.constraint.Email;
import org.agoncal.application.petstore.constraint.Login;
import org.agoncal.application.petstore.exception.ValidationException;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        @NamedQuery(name = Customer.FIND_BY_LOGIN, query = "SELECT c FROM Customer c WHERE c.login = :login"),
        @NamedQuery(name = Customer.FIND_BY_LOGIN_PASSWORD, query = "SELECT c FROM Customer c WHERE c.login = :login AND c.password = :password"),
        @NamedQuery(name = Customer.FIND_ALL, query = "SELECT c FROM Customer c")
})
@XmlRootElement
public class Customer implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    public static final String FIND_BY_LOGIN = "Customer.findByLogin";
    public static final String FIND_BY_LOGIN_PASSWORD = "Customer.findByLoginAndPassword";
    public static final String FIND_ALL = "Customer.findAll";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    @NonNull
    @Size(min = 2, max = 50)
    private String firstname;
    @Column(nullable = false)
    @NonNull
    @Size(min = 2, max = 50)
    private String lastname;
    @NonNull
    @Column(unique = true, nullable = false, length = 10)
    @Login
    private String login;
    @Column(nullable = false, length = 10)
    @NonNull
    @Size(min = 1, max = 10)
    private String password;
    @Email
    @NonNull
    private String email;
    private String telephone;

    // ======================================
    // =             Constants              =
    // ======================================
    @Embedded
    @Valid
    @NonNull
    private Address homeAddress;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Transient
    private Integer age;


    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    /**
     * This method calculates the age of the customer
     */
    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateAge() {
        if (dateOfBirth == null) {
            age = null;
            return;
        }

        Calendar birth = new GregorianCalendar();
        birth.setTime(dateOfBirth);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
    }

    // ======================================
    // =              Public Methods        =
    // ======================================

    /**
     * Given a password, this method then checks if it matches the user
     *
     * @param pwd Password
     * @throws ValidationException thrown if the password is empty or different than the one
     *                             store in database
     */
    public void matchPassword(String pwd) {
        if (pwd == null || "".equals(pwd))
            throw new ValidationException("Invalid password");

        // The password entered by the customer is not the same stored in database
        if (!pwd.equals(password))
            throw new ValidationException("Passwords don't match");
    }
}
