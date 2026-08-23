package dio.budgeting.application;

import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;

@Service
public class ListTransactionsByWeekUseCase {
    private final TransactionRepository transactionRepository;

    public ListTransactionsByWeekUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionOutput> execute(int year, int week) {
        if (week < 1 || week > 53) {
            throw new IllegalArgumentException("Week must be between 1 and 53, got: " + week);
        }

        WeekFields weekFields = WeekFields.ISO;
        LocalDate weekStart = LocalDate.ofYearDay(year, 1)
                .with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
                .plusWeeks(week - 1);
        LocalDate weekEnd = weekStart.plusDays(6);

        Instant start = weekStart.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = weekEnd.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        return transactionRepository.findAllByCreatedAtBetween(start, end)
                .stream()
                .map(TransactionOutput::from)
                .toList();
    }
}
