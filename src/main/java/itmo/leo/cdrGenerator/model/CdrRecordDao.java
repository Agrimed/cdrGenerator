package itmo.leo.cdrGenerator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая запись Call Detail Record (CDR) в базе данных.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "cdr_record")
public class CdrRecordDao {
    /**
     * Уникальный идентификатор записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип вызова.
     */
    @Column(name = "call_type")
    private Integer callType;

    /**
     * Абонент, инициировавший вызов.
     */
    @ManyToOne
    @JoinColumn(name = "subsA_id", referencedColumnName = "id")
    private SubscriberDao subsA;

    /**
     * Абонент, получивший вызов.
     */
    @ManyToOne
    @JoinColumn(name = "subsB_id", referencedColumnName = "id")
    private SubscriberDao subsB;

    /**
     * Время начала вызова.
     */
    @Column(name = "call_start")
    private LocalDateTime callStart;
    /**
     * Время завершения вызова.
     */
    @Column(name = "call_end")
    private LocalDateTime callEnd;

    @Override
    public String toString() {
        return String.format("%02d, %s, %s, %s, %s",
                callType, subsA.getMsisdn(), subsB.getMsisdn(), callStart.toString(), callEnd.toString());
    }

}
