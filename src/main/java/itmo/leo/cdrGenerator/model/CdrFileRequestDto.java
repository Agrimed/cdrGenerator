package itmo.leo.cdrGenerator.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CdrFileRequestDto {
    private String msisdn;
    private LocalDateTime start;
    private LocalDateTime end;

    public CdrFileRequestDto(String msisdn, String start, String end) {
        this.msisdn = msisdn;
        this.start = LocalDateTime.parse(start + "T00:00:00");
        this.end = LocalDateTime.parse(end + "T00:00:00");
    }
}
