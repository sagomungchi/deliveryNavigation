package com.fourmakers.mapapp.bookmark.service;

import lombok.RequiredArgsConstructor;
import com.fourmakers.mapapp.domain.bookmark.Bookmark;
import com.fourmakers.mapapp.domain.bookmark.dto.BookmarkDto.BookmarkInfo;
import com.fourmakers.mapapp.domain.bookmark.dto.BookmarkDto.SaveBookmarkInfo;
import com.fourmakers.mapapp.bookmark.exception.NoSuchBookmarkException;
import com.fourmakers.mapapp.bookmark.repository.BookmarkRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookMarkRepository;

    public List<BookmarkInfo> findBookmarks() {
        return bookMarkRepository
                .findAll()
                .stream()
                .map(bookmark ->
                        new BookmarkInfo(bookmark.getId(),
                        bookmark.getLocation().getX(),
                        bookmark.getLocation().getY(),
                        bookmark.getAddress()))
                .collect(Collectors.toList());
    }

    public BookmarkInfo findBookmark(Long bookId){
        return bookMarkRepository
                .findById(bookId)
                .map(bookmark ->
                        new BookmarkInfo(bookmark.getId(),
                                bookmark.getLocation().getX(),
                                bookmark.getLocation().getY(),
                                bookmark.getAddress()))
                .orElseThrow(NoSuchBookmarkException::new);
    }

    @Transactional
    public Long saveBookmark(SaveBookmarkInfo dto) throws ParseException {
        Bookmark bookMark = dto.toEntity();
        return bookMarkRepository.save(bookMark).getId();
    }
}
