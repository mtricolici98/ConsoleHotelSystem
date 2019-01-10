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

    public Booking(Room room, User user, Date fromDate, Date toDate) {
        this.room = room;
        this.user = user;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
