package com.company.persistance;

import com.company.models.Room;
import org.jsefa.Deserializer;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class RoomObjectManager {

    private ArrayList<Room> allRoooms;
    //Object manager for Room objects, used to save/delete/add/get all of the Room objects :)
    RoomObjectManager() {
        allRoooms = new ArrayList<>();
        initRooms();
    }


    public ArrayList<Room> getAllRoooms() {
        return allRoooms;
    }

    public void addRoom(Room room) {
        for(Room r: this.allRoooms){
            if(r.number == room.number){
                System.out.println("Room with such number already exists, please delete it first.");
                return;
            }
        }
        this.allRoooms.add(room);
        saveRooms();
    }

    public void deleteRoom(int number) {
        this.allRoooms.removeIf(room -> (room.number == number)); //Read docs for RemoveIF (pretty nice method)
        saveRooms();
    }


    public void deleteAllRooms(){
        this.allRoooms.clear();
    }

    private void saveRooms() {
        Serializer serializer = CsvIOFactory.createFactory(Room.class).createSerializer(); //setup csv serializer
        StringWriter writer = new StringWriter(); //need the writer to write the objects
        serializer.open(writer);
        for (Room room : this.allRoooms) {
            serializer.write(room); //write each object from memory
        }
        serializer.close(true);
        FileIO.writeToFile("rooms.csv", writer); //Assuming data is already in memory so not appending but overriding file.
    }

    private void initRooms() {
        StringReader reader = FileIO.getFromFile("rooms.csv");
        Deserializer deserializer = CsvIOFactory.createFactory(Room.class).createDeserializer();
        deserializer.open(reader);
        while (deserializer.hasNext()) {
            Room r = deserializer.next();
            this.allRoooms.add(r);
        }
        deserializer.close(true);
    }

}
