package com.company;

import com.company.models.User;
import com.company.persistance.ModelManagerSingleton;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ModelManagerSingleton.getInstance().Users().deleteAllUsers();
        ModelManagerSingleton.getInstance().Users().addUser(new User("Marius", "SUPER"));
        ModelManagerSingleton.getInstance().Users().addUser(new User("Antonio", "ADMIN"));
        ModelManagerSingleton.getInstance().Users().addUser(new User("Lubomir", "ADMIN"));
        ArrayList<User> users = ModelManagerSingleton.getInstance().Users().getAllUsers();
        System.out.println("NICEE!");
    }

}
