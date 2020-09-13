package com.fourmakers.mapapp.bookmark.api;

import com.fourmakers.mapapp.bookmark.service.BookmarkService;
import com.fourmakers.mapapp.domain.bookmark.dto.BookmarkDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {

    private final BookmarkService bookMarkService;

    @GetMapping
    public ResponseEntity getAllBookmarks(){
        List<BookmarkDto.BookmarkInfo> bookmarks = bookMarkService.findBookmarks();
        return ResponseEntity.ok(bookmarks);
    }

    @GetMapping("{id}")
    public ResponseEntity getBookmark(@PathVariable("id") Long id){
        return ResponseEntity.ok(bookMarkService.findBookmark(id));
    }

    @PostMapping
    public ResponseEntity saveBookmark(@RequestBody BookmarkDto.SaveBookmarkInfo request) throws ParseException {
        Long savedBookmarkId = bookMarkService.saveBookmark(request);
        return ResponseEntity.created(URI.create("/api/v1/bookmarks/" + savedBookmarkId)).build();
    }

}


