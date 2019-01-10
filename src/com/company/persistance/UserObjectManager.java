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

    public void addUser(User user) {
        this.allUsers.add(user);
        saveUsers();
    }

    public void deleteAllUsers(){
        this.allUsers.clear();
    }

    public void deleteUser(String username) {
        this.allUsers.removeIf(user -> (user.getUsername().equals(username))); //Read docs for RemoveIF (pretty nice method)
        saveUsers();
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
