package com.fourmakers.mapapp.bookmark.api;

import com.fourmakers.mapapp.domain.bookmark.Address;
import com.fourmakers.mapapp.domain.bookmark.dto.BookmarkDto;
import com.fourmakers.mapapp.util.AcceptanceTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.greaterThan;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookmarkAcceptanceTest {

    @Autowired
    private WebTestClient webTestClient;

    private String bookmarkId;

    @BeforeEach
    @DisplayName("북마크 저장 API 테스트")
    void createBookmark() throws ParseException {

        double baseLatitude = 37.511468;
        double baseLongitude = 127.121504;
        Address address = new Address("청주시", "풍산로 177번길 19", "12344");

        BookmarkDto.SaveBookmarkInfo bookmarkInfo = new BookmarkDto.SaveBookmarkInfo(baseLongitude, baseLatitude, address);

        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                .uri("/api/v1/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(bookmarkInfo), BookmarkDto.SaveBookmarkInfo.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader().valueMatches("Location", "/api/v1/bookmarks/[1-9]+[0-9]*");

        bookmarkId = AcceptanceTestUtils.extractDomainIdFromCreatedResourceAddress(responseSpec);

    }

    @Test
    @DisplayName("북마크 전체 조회 API 테스트")
    void showAllBookmarks(){
        webTestClient.get()
                .uri("/api/v1/bookmarks")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length", greaterThan(1));
    }

    @Test
    @DisplayName("북마크 단일 조회 API 테스트")
    void showOneBookmark(){
        webTestClient.get()
                .uri("/api/v1/bookmarks/" + bookmarkId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.bookmarkId", bookmarkId);
    }

}