package com.example.stuff_management_reactivejava.ActivityLog.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "activity")
public class ActivityEntity implements Persistable<String> {
    @Id
    private String id;
    private String stuffId;
    private String traceId;
    private String status;
    // private String failureReason;
    private LocalDateTime createdOn;
    private String createdBy;
    private String httpMethod;


    @Override
    public boolean isNew() {
        boolean isNull = Objects.isNull(this.id);
        this.id = isNull ? UUID.randomUUID().toString() : this.id;
        return isNull;
    }
}
