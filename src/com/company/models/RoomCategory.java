package com.company.models;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;
import org.jsefa.csv.annotation.CsvSubRecord;

@CsvDataType(defaultPrefix = "RC")
public class RoomCategory {

    /*
     * Room category ( Single/Double/Apartment )
     */

    @CsvField(pos = 1)
    public String name;

    @CsvField(pos = 3)
    public int price;

    public RoomCategory() {

    }


    public RoomCategory(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
