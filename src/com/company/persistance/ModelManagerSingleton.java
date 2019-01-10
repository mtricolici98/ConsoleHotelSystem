package com.company.persistance;

public class ModelManagerSingleton {

    //Using singleton is nice so we work with same data from memory at any point in the program.
    private static final ModelManagerSingleton instance = new ModelManagerSingleton();
    private RoomObjectManager rooms;
    private UserObjectManager users;
    private BookingObjectManager bookings;

    private ModelManagerSingleton() {
        rooms = new RoomObjectManager();
        users = new UserObjectManager();
        bookings = new BookingObjectManager();
    }

    public static ModelManagerSingleton getInstance() {
        return instance;
    }

    public RoomObjectManager Rooms() {
        return this.rooms;
    }

    public UserObjectManager Users() {
        return this.users;
    }

    public BookingObjectManager Bookings() {
        return this.bookings;
    }
}

