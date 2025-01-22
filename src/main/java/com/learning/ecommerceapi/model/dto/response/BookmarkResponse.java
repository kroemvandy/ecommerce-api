package com.learning.ecommerceapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkResponse {
    private UUID bookmarkId;
    private UUID productId;
    private Boolean status;
    private LocalDateTime createdAt;
    private UUID userId;
}
