package com.example.stuff_management_reactivejava.stuffClean.adapter.out.entity;

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
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "stuff")
public class StuffEntityClean implements Persistable {
    @Id
    private String id;
    private String stuffId;
    private String nickName;
    private String fullName;
    private String designation;
    private LocalDateTime createdOn;
    private String createdBy;
    @Override
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public boolean isNew() {
        boolean isNull = Objects.isNull(this.id);
        this.id = isNull ? UUID.randomUUID().toString() : this.id;
        return isNull;    }
}
