package com.company;

import com.company.models.Room;
import com.company.models.RoomCategory;
import com.company.models.RoomType;
import com.company.persistance.FileIO;
import com.company.persistance.ModelManagerSingleton;
import org.jsefa.Deserializer;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Room> rooms = ModelManagerSingleton.getInstance().Rooms().getAllRoooms();
    }

}
