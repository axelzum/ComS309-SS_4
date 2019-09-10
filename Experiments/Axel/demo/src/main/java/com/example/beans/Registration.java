package com.example.beans;

import java.util.ArrayList;
import java.util.List;

public class Registration {
    private List<User> userRecords;

    private static Registration registration = null;

    private Registration() {
        userRecords = new ArrayList<User>();
    }

    public static Registration getInstance() {

        if (registration == null) {
            registration = new Registration();
            return registration;
        } else {
            return registration;
        }
    }

    public void add(User user) {
        userRecords.add(user);
    }

    public String upDateUser(User user) {
        for(int i=0; i<userRecords.size(); i++)
        {
            User usr = userRecords.get(i);
            if(usr.getRegistrationNumber().equals(user.getRegistrationNumber())) {
                userRecords.set(i, user);//update the new record
                return "Update successful";
            }
        }
        return "Update un-successful";
    }

    public String deleteUser(String registrationNumber) {
        for(int i=0; i<userRecords.size(); i++)
        {
            User usr = userRecords.get(i);
            if(usr.getRegistrationNumber().equals(registrationNumber)){
                userRecords.remove(i);//update the new record
                return "Delete successful";
            }
        }
        return "Delete un-successful";
    }

    public List<User> getStudentRecords() {
        return userRecords;
    }

}
