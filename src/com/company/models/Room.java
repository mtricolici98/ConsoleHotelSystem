package com.company.models;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;
import org.jsefa.csv.annotation.CsvSubRecord;

@CsvDataType(defaultPrefix = "RO")
public class Room {

    @CsvSubRecord(pos = 1, prefix = "RC")
    public RoomCategory category;

    @CsvSubRecord(pos = 2, prefix = "RT")
    public RoomType type;

    @CsvField(pos = 2)
    public int number;

    public Room() {

    }

    public Room(RoomCategory category, int number, RoomType type) {
        this.category = category;
        this.number = number;
        this.type = type;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }


    public int getPrice() {
        //TODO ADD LOGIC TO CALCULATE PRICE
        return 0;
    }
}
