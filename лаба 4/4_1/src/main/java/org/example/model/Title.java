package org.example.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Title {
    private String isbn;
    private String title;
    private int editionNumber;
    private String year;
    private double price;
    private int publisherID;
    private String publisherName;

    @Override
    public String toString() {
        return "Title{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", editionNumber=" + editionNumber +
                ", year='" + year + '\'' +
                ", price=" + price +
                ", publisherName='" + publisherName + '\'' +
                '}';
    }
}
