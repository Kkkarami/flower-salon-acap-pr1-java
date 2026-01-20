package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;
import java.time.LocalDateTime;
import java.util.UUID;

public class Review extends BaseEntity {

    public static final String FIELD_COMMENT = "comment";

    private String comment;
    private LocalDateTime createdAt;
    private UUID userId;
    private UUID orderId;

    private Review() {
        super();
    }

    public Review(UUID id, String comment, UUID userId, UUID orderId) {
        this();
        setComment(comment);
        this.createdAt = LocalDateTime.now();
        this.userId = userId;
        this.orderId = orderId;

        if (!isValid()) {
            throw new EntityValidationException(getErrors());
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        clearError(FIELD_COMMENT);
        if (comment == null || comment.trim().isEmpty()) {
            addError(FIELD_COMMENT, ValidationError.COMMENT_EMPTY.message());
        }
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("%s %s", comment, createdAt);
    }
}

