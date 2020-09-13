package com.fourmakers.mapapp.domain.bookmark;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Point location;

    @Embedded
    private Address address;

    public Point coordinatesToPoint(Double longitude, Double latitude) throws ParseException {

        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);

        try {
           return (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e){
            throw new ParseException(e.getMessage());
        }
    }


    @Builder
    public Bookmark(Double longitude, Double latitude, Address address) throws ParseException {
        this.location = coordinatesToPoint(longitude, latitude);
        this.address = address;
    }
}
