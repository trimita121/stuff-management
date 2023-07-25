package com.example.stuff_management_reactivejava.Stuff.Entity;

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
@Table(name = "stuff")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class StuffEntity implements Persistable<String> {

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
        return isNull;
    }


}
