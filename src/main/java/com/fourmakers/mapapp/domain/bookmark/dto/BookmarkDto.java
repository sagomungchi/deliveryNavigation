package com.fourmakers.mapapp.domain.bookmark.dto;

import com.fourmakers.mapapp.domain.bookmark.Address;
import com.fourmakers.mapapp.domain.bookmark.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.locationtech.jts.io.ParseException;

public class BookmarkDto {

    @Data
    @Getter
    @AllArgsConstructor
    public static class SaveBookmarkInfo {

        private Double longitude;
        private Double latitude;
        private Address address;

        public Bookmark toEntity() throws ParseException {
            return Bookmark.builder()
                    .address(address)
                    .longitude(longitude)
                    .latitude(latitude)
                    .build();
        }
    }

    @Data
    @Getter
    @AllArgsConstructor
    public static class BookmarkInfo {

        private Long bookmarkId;
        private Double longitude;
        private Double latitude;
        private Address address;

    }
}
