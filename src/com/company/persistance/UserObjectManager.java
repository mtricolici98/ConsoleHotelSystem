package com.company.persistance;

import com.company.models.User;
import org.jsefa.Deserializer;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class UserObjectManager {

    private ArrayList<User> allUsers;

    //Object manager for Room objects, used to save/delete/add/get all of the Room objects :)
    UserObjectManager() {
        allUsers = new ArrayList<>();
        initUsers();
    }


    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public boolean addUser(User user) {
        for (User u : this.allUsers) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        this.allUsers.add(user);
        saveUsers();
        return true;
    }

    public void deleteAllUsers() {
        this.allUsers.clear();
    }

    public boolean deleteUser(String username) {
        boolean deleted = this.allUsers.removeIf(user -> (user.getUsername().equals(username))); //Read docs for RemoveIF (pretty nice method)
        saveUsers();
        return deleted;
    }

    public User getUser(String username) {
        for (User u : this.allUsers) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }

    private void saveUsers() {
        Serializer serializer = CsvIOFactory.createFactory(User.class).createSerializer(); //setup csv serializer
        StringWriter writer = new StringWriter(); //need the writer to write the
        serializer.open(writer);
        for (User user : this.allUsers) {
            serializer.write(user); //write each object from memory
        }
        serializer.close(true);
        FileIO.writeToFile("users.csv", writer); //Assuming data is already in memory so not appending but overriding file.
    }

    private void initUsers() {
        StringReader reader = FileIO.getFromFile("users.csv");
        Deserializer deserializer = CsvIOFactory.createFactory(User.class).createDeserializer();
        deserializer.open(reader);
        while (deserializer.hasNext()) {
            User u = deserializer.next();
            this.allUsers.add(u);
        }
        deserializer.close(true);
    }

}
