package itmo.leo.cdrGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UdrDto {
    private String msisdn;
    private CallTimeDto incomingCall;
    private CallTimeDto outcomingCall;
}
