package com.learning.ecommerceapi.controller;

import com.learning.ecommerceapi.model.dto.response.ApiResponse;
import com.learning.ecommerceapi.model.dto.response.BookmarkResponse;
import com.learning.ecommerceapi.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bookmark")
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{bookmarkId}")
    @Operation(summary = "Create bookmark with product id")
    public ResponseEntity<ApiResponse<BookmarkResponse>> createBookmark(@PathVariable UUID bookmarkId) {
        ApiResponse response = ApiResponse.<BookmarkResponse>builder()
                .message("Successfully created bookmark")
                .status(HttpStatus.CREATED)
                .payload(bookmarkService.addBookmark(bookmarkId))
                .code(201)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{bookmarkId}")
    @Operation(summary = "Update bookmark with product id")
    public ResponseEntity<ApiResponse<BookmarkResponse>> updateBookmark(@RequestBody UUID bookmarkId, @RequestParam Boolean status) {
        ApiResponse response = ApiResponse.<BookmarkResponse>builder()
                .message("Successfully updated bookmark")
                .status(HttpStatus.OK)
                .payload(bookmarkService.updateBookmark(bookmarkId, status))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(response);
    }
}
