package dio.budgeting.application;

import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ListTransactionsByMonthUseCase {
    private final TransactionRepository transactionRepository;

    public ListTransactionsByMonthUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionOutput> execute(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12, got: " + month);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        Instant start = yearMonth.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = yearMonth.plusMonths(1).atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        return transactionRepository.findAllByCreatedAtBetween(start, end)
                .stream()
                .map(TransactionOutput::from)
                .toList();
    }
}
