package com.learning.ecommerceapi.service;

import com.learning.ecommerceapi.model.dto.request.BookmarkRequest;
import com.learning.ecommerceapi.model.dto.response.BookmarkResponse;

import java.util.UUID;

public interface BookmarkService {
    BookmarkService findALlBookmarks(Integer pageNo, Integer pageNumber, String sortBy, String sortDirection);
    BookmarkResponse addBookmark(UUID bookmarkId);
    BookmarkResponse updateBookmark(UUID bookmarkId, Boolean status);
}
