package com.concertticketing.userapi.apis.concerts.mapper;

import static com.concertticketing.commonutils.TimeUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.concertticketing.domainrdb.domain.ConcertFixture;
import com.concertticketing.domainrdb.domain.VenueAreaFixture;
import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.domain.ConcertDetailImage;
import com.concertticketing.domainrdb.domain.venue.domain.VenueArea;
import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.userapi.common.annotation.MapperTest;

@MapperTest
@DisplayName("[Mapper] ConcertMapper")
class ConcertMapperTest {
    @Autowired
    ConcertMapper concertMapper;

    @DisplayName("toConcertDetailDto")
    @Test
    void toConcertDetailDto() {
        Long id = 1L;
        Concert concert = ConcertFixture.concertBuilder()
            .id(id)
            .build();
        List<VenueArea> areas = List.of(
            VenueAreaFixture.builder()
                .id(1)
                .build(),
            VenueAreaFixture.builder()
                .id(2)
                .build()
        );

        ConcertDetailRes result = concertMapper.toConcertDetailDto(concert, areas);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.title()).isEqualTo(concert.getTitle());
        assertThat(result.description()).isEqualTo(concert.getDescription());
        assertThat(result.thumbnail()).isEqualTo(concert.getThumbnail());
        assertThat(toLocalDateTime(result.startedAt())).isEqualTo(concert.getStartedAt());
        assertThat(toLocalDateTime(result.endedAt())).isEqualTo(concert.getEndedAt());
        assertThat(toLocalDateTime(result.bookingStartedAt())).isEqualTo(concert.getBookingStartedAt());
        assertThat(toLocalDateTime(result.bookingEndedAt())).isEqualTo(concert.getBookingEndedAt());

        assertThat(result.venue().name()).isEqualTo(concert.getVenue().getName());
        assertThat(result.venue().capacity()).isEqualTo(concert.getVenue().getCapacity());
        assertThat(result.venue().roadAddress()).isEqualTo(concert.getVenue().getRoadAddress());
        assertThat(result.venue().latitude()).isEqualTo(concert.getVenue().getLatitude());
        assertThat(result.venue().longitude()).isEqualTo(concert.getVenue().getLongitude());

        assertThat(result.areas()).hasSize(areas.size());
        assertThat(result.areas().get(0).name()).isEqualTo(areas.get(0).getName());
        assertThat(result.areas().get(0).price()).isEqualTo(areas.get(0).getPrice());
        assertThat(result.areas().get(1).name()).isEqualTo(areas.get(1).getName());
        assertThat(result.areas().get(1).price()).isEqualTo(areas.get(1).getPrice());

        assertThat(result.detailImages()).isNotNull().hasSize(concert.getDetailImages().size());
        assertThat(result.detailImages()).containsExactlyElementsOf(
            concert.getDetailImages().stream().map(ConcertDetailImage::getImageUrl).toList()
        );

        assertThat(result.categories()).isNotNull().hasSize(concert.getConcertCategories().size());
        assertThat(result.categories()).containsExactlyElementsOf(
            concert.getConcertCategories().stream()
                .map(concertCategory -> concertCategory.getCategory().getName())
                .toList()
        );
    }
}