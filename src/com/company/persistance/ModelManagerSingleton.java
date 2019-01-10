package com.company.persistance;

import com.company.models.Room;
import com.company.models.RoomCategory;
import com.company.models.RoomType;
import org.jsefa.Deserializer;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ModelManagerSingleton {

    //Using singleton is nice so we work with same data from memory at any point in the program.
    private static final ModelManagerSingleton instance = new ModelManagerSingleton();
    private RoomObjectManager rooms;
    private UserObjectManager users;

    private ModelManagerSingleton() {
        rooms = new RoomObjectManager();
        users = new UserObjectManager();
    }

    public static ModelManagerSingleton getInstance() {
        return instance;
    }

    public RoomObjectManager Rooms() {
        return rooms;
    }

    public UserObjectManager Users() {
        return users;
    }
}

