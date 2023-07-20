package org.testovoe;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ValueArt {

    private LocalDate date;
    private String valuteName;
    private String value;
    private String valuteFullName;
    private int nominal;

}
