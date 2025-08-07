package com.concertticketing.userapi.fixture;

import static com.concertticketing.userapi.fixture.common.RandomStringData.*;

import java.util.ArrayList;
import java.util.List;

import com.concertticketing.userapi.apis.concerts.domain.ConcertDetailImage;

public final class ConcertDetailImageFixture {
    public static List<ConcertDetailImage> newConcertDetailImages(int length) {
        List<ConcertDetailImage> images = new ArrayList<>();

        for (int i = 1; i <= length; i++) {
            images.add(
                new ConcertDetailImage(
                    (long)i,
                    randomUrl()
                )
            );
        }

        return images;
    }
}
