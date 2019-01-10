package com.company.models;

import org.jsefa.csv.annotation.CsvField;
import org.jsefa.csv.annotation.CsvSubRecord;

import java.util.Date;

public class Booking {

    @CsvSubRecord(pos = 1, prefix = "RO")
    Room room;

    @CsvSubRecord(pos = 1, prefix = "USR")
    User user;

    @CsvField(pos = 2, format = "dd.MM.yyyy")
    Date fromDate;

    @CsvField(pos = 2, format = "dd.MM.yyyy")
    Date toDate;

    public Booking(){}

}
