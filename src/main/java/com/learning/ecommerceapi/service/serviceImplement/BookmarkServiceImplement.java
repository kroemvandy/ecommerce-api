package com .learning.ecommerceapi.service.serviceImplement;

import com.learning.ecommerceapi.config.GetCurrentUserConfig;
import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.mapper.BookmarkMapper;
import com.learning.ecommerceapi.model.dto.response.BookmarkResponse;
import com.learning.ecommerceapi.model.entity.Bookmark;
import com.learning.ecommerceapi.model.entity.Products;
import com.learning.ecommerceapi.model.entity.Users;
import com.learning.ecommerceapi.repository.BookmarkRepository;
import com.learning.ecommerceapi.repository.ProductsRepository;
import com.learning.ecommerceapi.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookmarkServiceImplement implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ProductsRepository productsRepository;
    private final GetCurrentUserConfig currentUser;

    @Override
    public BookmarkService findALlBookmarks(Integer pageNo, Integer pageNumber, String sortBy, String sortDirection) {
        return null;
    }

    @Override
    public BookmarkResponse addBookmark(UUID bookmarkId) {

        Users users = currentUser.getCurrentUser();
        Products products = productsRepository.findById(bookmarkId).orElseThrow(() -> new ResourceNotFountException("Products with id " + bookmarkId + " not found"));

        Bookmark bookmark = new Bookmark();
        bookmark.setStatus(true);
        bookmark.setProductId(products.getProductId());
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmark.setUserId(users);
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        return BookmarkMapper.mapToBookmarkResponse(savedBookmark);
    }

    @Override
    public BookmarkResponse updateBookmark(UUID bookmarkId, Boolean status) {
        Users users = currentUser.getCurrentUser();
        Products products = productsRepository.findById(bookmarkId).orElseThrow(() -> new ResourceNotFountException("Product not found with id " + bookmarkId));
        Bookmark bookmark = new Bookmark();
        bookmark.setProductId(products.getProductId());
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmark.setUserId(users);
        bookmark.setStatus(status);

        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        return BookmarkMapper.mapToBookmarkResponse(savedBookmark);
    }

}
