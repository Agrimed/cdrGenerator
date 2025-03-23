package itmo.leo.cdrGenerator.model;

import lombok.Getter;

import java.time.Duration;

/**
 * Data Transfer Object для представления времени вызова в формате "час:мин:сек".
 * <p> Description of class
 */
@Getter
public class CallTimeDto {
    /**
     * Общее время вызова в формате "час:мин:сек".
     */
    private final String totalTime;

    /**
     * Создаёт объект {@code CallTimeDto} на основе количества секунд.
     *
     * @param seconds количество секунд, из которого формируется время вызова
     */
    public CallTimeDto(Long seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        totalTime = String.format("%d:%02d:%02d",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart());
    }

}
