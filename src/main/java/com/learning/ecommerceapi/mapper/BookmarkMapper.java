package com.learning.ecommerceapi.mapper;

import com.learning.ecommerceapi.model.dto.response.BookmarkResponse;
import com.learning.ecommerceapi.model.entity.Bookmark;

public class BookmarkMapper {
    public static BookmarkResponse mapToBookmarkResponse(Bookmark bookmark) {
        BookmarkResponse response = new BookmarkResponse();
        response.setBookmarkId(bookmark.getBookmarkId());
        response.setProductId(bookmark.getProductId());
        response.setStatus(bookmark.getStatus());
        response.setCreatedAt(bookmark.getCreatedAt());
        response.setUserId(bookmark.getUserId().getUserId());
        return response;
    }
}
