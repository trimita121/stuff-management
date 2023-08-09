package com.example.stuff_management_reactivejava.activityLogClean.adapter.out.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Component
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
    private String httpMethod;
    private LocalDateTime createdOn;
    private String editedBy;
    private String requestUrl;

    @Override
    public boolean isNew() {
        boolean isNull = Objects.isNull(this.id);
        this.id = isNull ? UUID.randomUUID().toString() : this.id;
        return isNull;
    }
}

