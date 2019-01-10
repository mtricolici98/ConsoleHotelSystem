package com.company.persistance;

import com.company.models.Booking;
import com.company.models.User;
import org.jsefa.Deserializer;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class BookingObjectManager {

    private ArrayList<Booking> allBookings;

    //Object manager for Room objects, used to save/delete/add/get all of the Room objects :)
    BookingObjectManager() {
        allBookings = new ArrayList<>();
        initBookings();
    }


    public ArrayList<Booking> getAllBookings() {
        return allBookings;
    }

    public void addUser(Booking booking) {
        this.allBookings.add(booking);
        saveBookings();
    }

    public void deleteAllBookings() {
        this.allBookings.clear();
    }

    public void deleteBooking(String username, int roomNr) {
        this.allBookings.removeIf(booking ->
                (booking.getUser().getUsername().equals(username) && booking.getRoom().getNumber() == roomNr));
        saveBookings();
    }

    private void saveBookings() {
        Serializer serializer = CsvIOFactory.createFactory(Booking.class).createSerializer(); //setup csv serializer
        StringWriter writer = new StringWriter(); //need the writer to write the
        serializer.open(writer);
        for (Booking booking : this.allBookings) {
            serializer.write(booking); //write each object from memory
        }
        serializer.close(true);
        FileIO.writeToFile("bookings.csv", writer); //Assuming data is already in memory so not appending but overriding file.
    }

    private void initBookings() {
        StringReader reader = FileIO.getFromFile("bookings.csv");
        Deserializer deserializer = CsvIOFactory.createFactory(Booking.class).createDeserializer();
        deserializer.open(reader);
        while (deserializer.hasNext()) {
            Booking b = deserializer.next();
            this.allBookings.add(b);
        }
        deserializer.close(true);
    }

}
