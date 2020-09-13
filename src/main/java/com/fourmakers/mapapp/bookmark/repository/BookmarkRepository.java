package com.fourmakers.mapapp.bookmark.repository;

import com.fourmakers.mapapp.domain.bookmark.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository <Bookmark, Long> {
}

