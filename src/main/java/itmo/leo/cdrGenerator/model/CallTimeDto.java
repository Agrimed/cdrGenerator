package itmo.leo.cdrGenerator.model;

import lombok.Getter;

import java.time.Duration;


@Getter
public class CallTimeDto {
    private final String totalTime;

    public CallTimeDto(Long seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        totalTime = String.format("%d:%02d:%02d",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart());
    }

}
