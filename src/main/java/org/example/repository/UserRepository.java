package org.example.repository;

import org.example.dto.User;
import java.util.HashMap;
public class UserRepository {

    private static UserRepository instance;
    private UserRepository(){

    }
    public static UserRepository getInstance(){

        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    private HashMap<Integer, User> db = new HashMap<>();

    public boolean add(User user){
        if(find(user.getId())){
            return false;
        }
        else{
            db.put(user.getId(),user);
            return true;
        }

    }
    public boolean find(int id){
        return db.containsKey(id);
    }

    public boolean delete(User user){

        if(find(user.getId())){
            db.remove(user.getId());
            return true;
        }

        else{
            return false;
        }

    }

    public User findEmail(String email) {

        for (User user : db.values()) {

            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

}
