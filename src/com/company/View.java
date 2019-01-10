package com.company;

import com.company.models.Booking;
import com.company.models.Room;
import com.company.models.User;
import com.company.persistance.ModelManagerSingleton;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class View {

    ModelManagerSingleton models;
    User activeUser;
    ArrayList<String> allowedGroups;
    Scanner sc;

    public View() {
        this.models = ModelManagerSingleton.getInstance();
        this.activeUser = null;
        sc = new Scanner(System.in);
        allowedGroups = new ArrayList<>();
        allowedGroups.add("guest");
        allowedGroups.add("administrator");
        allowedGroups.add("front desk");
    }

    public void init() {
        printMenu();
    }

    private void printMenu() {
        printSeparator();
        if (activeUser == null) {
            System.out.println("You are not logged in, please enter username.");
            awaitLogin();
        } else {
            switch (activeUser.getUserGroup()) {
                case "guest":
                    printChoicesGuest();
                    break;
                case "front desk":
                    printChoicesFrontDesk();
                    break;
                case "administrator":
                    printChoicesAdmin();
                    break;
                default:
                    System.out.println("NOT IMPLEMENTED");
                    break;
            }
        }
    }

    private void awaitLogin() {
        printSeparator();
        String input = sc.nextLine();
        checkIfExit(input);
        if (input.equals("register")) {
            register();
            return;
        }
        User user = models.Users().getUser(input);
        if (user != null) {
            activeUser = user;
            System.out.println("Welcome back, " + activeUser.getUsername());
            printMenu();
        } else {
            System.out.println("Wrong username, please try again, or type register to register new user. Type quit to exit.");
            awaitLogin();
        }
    }

    private void register() {
        printSeparator();
        Scanner sc = new Scanner(System.in);
        System.out.println("In order to register, first type your desired username: ");
        String inputUsername = sc.nextLine();
        System.out.println("Now type who you are ( choices are [guest, administrator, front desk] case sensitive ): ");
        String inputUserGroup = sc.nextLine();
        if (!allowedGroups.contains(inputUserGroup)) {
            System.out.println("You picked a invalid user group, returning to main menu.");
            printMenu();
            return;
        }
        User user = new User(inputUsername, inputUserGroup);
        if (!models.Users().addUser(user)) {
            System.out.println("User with specified name already exists. Try to log in.");
            printMenu();
            return;
        }
        activeUser = user;
        System.out.println("Registration successful, automatically logged you in.");
        printMenu();
    }

    private void checkIfExit(String input) {
        if (input.equals("quit")) {
            sc.close();
            System.exit(0);
        }
    }

    private void printChoicesGuest() {
        System.out.println("You are logged in as guest:");
        System.out.println("Select from options bellow:");
        System.out.println("Type 'book' to book a room:");
        System.out.println("Type 'seeavailable' to book a room:");
        System.out.println("Type 'mybookings' to see all your bookings:");
        System.out.println("Type 'cancelrsv' to cancel a reservation:");
        System.out.println("NOTE: Type quit anytime to exit the application.");
        String input = sc.nextLine();
        checkIfExit(input);
        switch (input) {
            case "book":
                printBookSelector();
                break;
            case "cancelrsv":
                printCancelSelectorGuest();
                break;
            case "seeavailable":
                printAvailableRooms(true);
                break;
            case "mybookings":
                printAllSelfBookings(false);
                break;
            default:
                printSeparator();
                System.out.println("No such option or you don't have enough permissions.");
                printMenu();
                break;

        }
    }

    private void printChoicesFrontDesk() {
        System.out.println("You are logged in as front desk manager:");
        System.out.println("Select from options bellow:");
        System.out.println("Type 'book' to book a room:");
        System.out.println("Type 'seeavailable' to see available rooms:");
        System.out.println("Type 'bookings' to see all bookings:");
        System.out.println("Type 'cancelrsv' to cancel a reservation:");
        System.out.println("NOTE: Type quit anytime to exit the application.");
        String input = sc.nextLine();
        checkIfExit(input);
        switch (input) {
            case "book":
                printBookSelectorFrontDesk();
                break;
            case "cancelrsv":
                printCancelSelectorFrontDesk();
                break;
            case "seeavailable":
                printAvailableRooms(true);
                break;
            case "bookings":
                printAllBookings(false);
                break;
            default:
                printSeparator();
                System.out.println("No such option or you don't have enough permissions.");
                printMenu();
                break;

        }
    }

    private void printChoicesAdmin() {
        System.out.println("You are logged in as ADMIN:");
        System.out.println("Select from options bellow:");
        System.out.println("Type 'allrooms' too see all existing rooms:");
        System.out.println("NOTE: Type quit anytime to exit the application.");
        String input = sc.nextLine();
        checkIfExit(input);
        switch (input) {
            case "allrooms":
                printAllRooms();
                break;
            default:
                printSeparator();
                System.out.println("No such option or you don't have enough permissions.");
                printMenu();
                break;

        }
    }

    private void printAllRooms(){
        printSeparator();
        for(Room r: models.Rooms().getAllRoooms())
            System.out.println(r.toString());
        printMenu();
    }

    private void printBookSelectorFrontDesk() {
        printSeparator();
        System.out.println("Please select the number of the room you want to book.");
        printAvailableRooms(false);
        System.out.println("Type the number of the room you want to book");
        String input = sc.nextLine();
        checkIfExit(input);
        Room chosenRoom = models.Rooms().getRoom(Integer.parseInt(input));
        if (chosenRoom == null) {
            System.out.println("Room with such number does not exits, please try again.");
            printBookSelector();
            return;
        }
        System.out.println("Please input the date you want to book the room from ( format DD/MM/YY )");
        input = sc.nextLine();
        checkIfExit(input);
        Date dateFrom = parseDate(input);
        System.out.println("Please input the date you want to book the room until ( format DD/MM/YY )");
        input = sc.nextLine();
        checkIfExit(input);
        Date dateTo = parseDate(input);
        System.out.println("Please input the name of the guest.");
        input = sc.nextLine();
        checkIfExit(input);
        User userToLink = models.Users().getUser(input);
        if (userToLink != null) {
            System.out.println("Found an existing user with this name, linking it.");
        } else {
            userToLink = new User(input, "guest");
        }
        Booking newBooking = new Booking(chosenRoom, userToLink, dateFrom, dateTo);
        if (models.Bookings().addBooking(newBooking)) {
            System.out.println("Successfully booked: " + newBooking.toString());
            models.Users().addUser(userToLink);
            printMenu();
        } else {
            System.out.println("The booking you are trying to create overlaps with another booking, try again with other room or some other dates.");
            printMenu();
        }
    }

    private void printCancelSelectorGuest() {
        printSeparator();
        int res = printAllSelfBookings(true);
        if (res != 0) {
            System.out.println("Type in the ID of the booking you want to delete.");
            String input = sc.nextLine();
            checkIfExit(input);
            models.Bookings().deleteBookingByIndex(Integer.parseInt(input) - 1);
            System.out.println("Successfully deleted");
        }
        printMenu();

    }

    private void printCancelSelectorFrontDesk() {
        printSeparator();
        printAllBookings(true);
        System.out.println("Type in the ID of the booking you want to delete.");
        String input = sc.nextLine();
        checkIfExit(input);
        models.Bookings().deleteBookingByIndex(Integer.parseInt(input) - 1);
        System.out.println("Successfully deleted");
        printMenu();
    }

    private void printAvailableRooms(boolean triggerMenu) {
        printSeparator();
        System.out.println("The following rooms are available today.");
        for (Room room : models.Rooms().getAllRoooms()) {
            if (isRoomAvailable(room.getNumber())) {
                System.out.println(room.toString());
            }
        }
        if (triggerMenu)
            printMenu();
    }

    private boolean isRoomAvailable(int roomNr) {
        Date now = new Date();
        for (Booking booking : models.Bookings().getAllBookings()) {
            if (booking.getRoom().getNumber() == roomNr) {
                if (booking.getFromDate().compareTo(now) < 0 && booking.getToDate().compareTo(now) > 0)
                    return false;
            }
        }
        return true;
    }

    private void printBookSelector() {
        printSeparator();
        System.out.println("Please select the number of the room you want to book.");
        printAvailableRooms(false);
        System.out.println("Type the number of the room you want to book");
        String input = sc.nextLine();
        checkIfExit(input);
        Room chosenRoom = models.Rooms().getRoom(Integer.parseInt(input));
        if (chosenRoom == null) {
            System.out.println("Room with such number does not exits, please try again.");
            printBookSelector();
            return;
        }
        System.out.println("Please input the date you want to book the room from ( format DD/MM/YY )");
        input = sc.nextLine();
        checkIfExit(input);
        Date dateFrom = parseDate(input);
        System.out.println("Please input the date you want to book the room until ( format DD/MM/YY )");
        input = sc.nextLine();
        checkIfExit(input);
        Date dateTo = parseDate(input);
        Booking newBooking = new Booking(chosenRoom, activeUser, dateFrom, dateTo);
        if (models.Bookings().addBooking(newBooking)) {
            System.out.println("Successfully booked: " + newBooking.toString());
            printMenu();
        } else {
            System.out.println("The booking you are trying to create overlaps with another booking, try again with other room or some other dates.");
            printMenu();
        }
    }

    private int printAllSelfBookings(boolean showIndex) {
        printSeparator();
        System.out.println("Your bookings in our hotel are");
        int i = 1;
        int found = 0;
        for (Booking booking : models.Bookings().getAllBookings()) {
            if (booking.getUser().getUsername().equals(activeUser.getUsername())) {
                if (showIndex)
                    System.out.println("ID:" + i + ": " + booking.toString());
                else
                    System.out.println(booking.toString());
                found++;
            }
            i++;
        }
        if (found == 0) {
            System.out.println("It appears as you dont have any bookings in our hotel.");
        }
        if (!showIndex) //Not returning to menu because a cancel action;
            printMenu();
        return found;
    }

    private void printAllBookings(boolean showIndex) {
        printSeparator();
        System.out.println("All bookings in the hotel are.");
        int i = 1;
        for (Booking booking : models.Bookings().getAllBookings()) {
            if (showIndex)
                System.out.println("ID:" + i + ": " + booking.toString());
            else
                System.out.println(booking.toString());
            i++;
        }
        if (!showIndex) //Not returning to menu because a cancel action;
            printMenu();
    }

    private Date parseDate(String stringDate) {
        String[] to_arr = stringDate.split("/");
        return new Date(Integer.parseInt(to_arr[2]), Integer.parseInt(to_arr[1]), Integer.parseInt(to_arr[0]));
    }

    private static void printSeparator() {
        System.out.println("<----------------------------------------------------------------------->");
    }

}
