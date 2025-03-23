package itmo.leo.cdrGenerator.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая абонента.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "subscriber")
public class SubscriberDao {
    /**
     * Уникальный идентификатор абонента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальный номер абонента (MSISDN).
     */
    @Column(nullable = false, unique = true)
    private String msisdn;
}
