package dio.budgeting.infrastructure.http.request;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;

import java.time.Instant;

public record TransactionRequest(String description, Category category, long amount, Instant createdAt) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, amount, category);
    }

    public Instant resolvedCreatedAt() {
        return createdAt != null ? createdAt : Instant.now();
    }
}
