package itmo.leo.cdrGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object для представления данных UDR (Usage Detail Record),
 * содержащего информацию об абоненте и времени входящих и исходящих вызовов.
 */
@AllArgsConstructor
@Getter
public class UdrDto {
    /**
     * Номер абонента (MSISDN).
     */
    private String msisdn;
    /**
     * Время входящего вызова.
     */
    private CallTimeDto incomingCall;
    /**
     * Время исходящего вызова.
     */
    private CallTimeDto outcomingCall;
}
