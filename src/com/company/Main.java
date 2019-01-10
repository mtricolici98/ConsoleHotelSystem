package com.company;

import com.company.models.Room;
import com.company.models.RoomCategory;
import com.company.models.RoomType;
import com.company.models.User;
import com.company.persistance.ModelManagerSingleton;
import com.company.persistance.RoomObjectManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        RoomObjectManager rooms_proxy = ModelManagerSingleton.getInstance().Rooms();
        ArrayList<String> opts = new ArrayList<>();
        opts.add("WIFI");
        rooms_proxy.addRoom(
                new Room(
                        new RoomCategory("Single",
                                new RoomType("BASIC", opts), 300),
                        101)
        );
        rooms_proxy.addRoom(
                new Room(
                        new RoomCategory("Single",
                                new RoomType("BASIC", opts), 300),
                        101)
        );
        System.out.println("NICEE!");
    }

}
