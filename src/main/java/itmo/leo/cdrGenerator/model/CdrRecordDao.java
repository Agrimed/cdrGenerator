package itmo.leo.cdrGenerator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "cdr_record")
public class CdrRecordDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "call_type")
    private Integer callType;
    @ManyToOne
    @JoinColumn(name = "subsA_id", referencedColumnName = "id")
    private SubscriberDao subsA;

    @ManyToOne
    @JoinColumn(name = "subsB_id", referencedColumnName = "id")
    private SubscriberDao subsB;

    @Column(name = "call_start")
    private LocalDateTime callStart;
    @Column(name = "call_end")
    private LocalDateTime callEnd;
}
