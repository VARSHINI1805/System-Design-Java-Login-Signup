package org.example.service;

import org.example.dto.User;
import org.example.repository.UserRepository;


public class UserService {

    private UserRepository repo;

    public UserService() {

        repo = UserRepository.getInstance();
    }

    public boolean signup(User user) {

        if (validateemail(user.getEmail()) && validatepassword(user.getPassword())) {
            return repo.add(user);
        }

        return false;
    }

    public boolean login(String email, String password) {

        User user = repo.findEmail(email);

        if (user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }

    private boolean validateemail(String email){
        if(email==null || email.isEmpty())
            return false;
        return email.matches("^[A-Za-z0-9_.-]+@gmail\\.com$");
    }

    private boolean validatepassword(String password){
        if(password == null || password.isEmpty()){
            return false;
        }
        if (password.length() < 8 ){
            return false;
        }
        return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[&@!]).{8,}$");
    }
}
