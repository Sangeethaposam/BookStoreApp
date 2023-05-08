package com.bookstore.service;

import java.util.*;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bookstore.dto.LoginDto;
import com.bookstore.dto.UserDto;
import com.bookstore.entities.UserModel;
import com.bookstore.exception.UserExceptions;
import com.bookstore.mailController.EmailSenderService;
import com.bookstore.repository.UserRepository;
import com.bookstore.utility.UserToken;

@Service
public class UserService implements IUserService{
	List<UserModel> userModelList = new ArrayList<>();
    @Autowired
    UserRepository userRepository;
    @Autowired
	ModelMapper modelmapper;
    @Autowired
    EmailSenderService iEmailService;
    @Autowired
	UserToken jwt;

    @Override
    public UserModel registerUser(UserDto userDto){
        Optional<UserModel> newUser = userRepository.findByEmail(userDto.getEmail());
        //checks if user has already registered using this email
        if(newUser.isPresent()){
            throw new UserExceptions("User Already exists...");
        }else {
            //if email doesn't exists then new user will register
            UserModel userModel = modelmapper.map(userDto, UserModel.class);
//            userModel.setRole("admin");
            userRepository.save(userModel);
            iEmailService.sendEmail(userModel.getEmail(),"Account Sign-up successfully "," Hello "+ userModel.getFirstName()
            + " Your Account has been created on 'Book Store'");
            return userModel;
        }
    }

    @Override
    public String  userLogin(LoginDto logindto) {
        Optional<UserModel> newUser = userRepository.findByEmail(logindto.getEmail());
        if (newUser.isPresent() &&
                logindto.getPassword().equals(newUser.get().getPassword())) {
        	newUser.get().setLogin(true);
        	userRepository.save(newUser.get());
        	String token = jwt.generateToken(logindto);
            return token;
        } else {
            throw new UserExceptions("User doesn't exists");
        }
    }
    
	@Override
	public String userLogout(String email) {
		Optional<UserModel> newUser = userRepository.findByEmail(email);
        if (newUser.isPresent()) {
        	if (newUser.get().isLogin()==false) {
        		throw new UserExceptions(newUser.get().getRole().toString()+" not Logged In");
        	}
        	newUser.get().setLogin(false);
        	userRepository.save(newUser.get());
            return "SuccessFully Logged Out";
        } else {
            throw new UserExceptions("User doesn't exists");
        }
	}

    @Override
    public UserModel getByToken(String token){
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
        if (userModel.isPresent()){
            return userModel.get();
        }else {
            throw new UserExceptions("Token doesn't Exists...");
        }
    }
    
    @Override
    public List<UserModel> getAll(String token) {
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
        List<UserModel> userModelList = userRepository.findAll();
        if (userModel.get().getRole().toString().equals("admin")) {
        	if (userModel.get().isLogin()==false) {
        		throw new UserExceptions("Admin not Logged In");
        	}
        	return userModelList;
    	}
        throw new UserExceptions("User Access Denied.");       
    }

    @Override
    public UserModel updateByEmail(UserDto userDto){
    	Optional<UserModel> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
        	if (user.get().isLogin()==false) {
        		throw new UserExceptions(user.get().getRole().toString()+" not Logged In");
        	}
            UserModel newAdd = modelmapper.map(userDto, UserModel.class);
            newAdd.setRole(user.get().getRole());
            newAdd.setUserId(user.get().getUserId());
            userRepository.save(newAdd);
            iEmailService.sendEmail(newAdd.getEmail(),"Data Updated Using Email.","Data Updated");
            return newAdd;
        } else {
            throw new UserExceptions("Email not found");
        }
    }

    @Override
    public UserModel changePassword(String pass, String email) {
        Optional<UserModel> user = userRepository.findByEmail(email);
        if(user.isPresent()){
        	if (user.get().isLogin()==false) {
        		throw new UserExceptions(user.get().getRole().toString()+" not Logged In");
        	}
        	user.get().setPassword(pass);
            userRepository.save(user.get());
            iEmailService.sendEmail(user.get().getEmail(),"Password Updated..",
                    "Hi "+user.get().getFirstName()+" Your Password for 'Book Store' has been changed successfully..");
            return user.get();
        }else {
            throw new UserExceptions("Sorry we could not find your email: "+email);
        }
    }

	@Override
	public UserModel DeleteUser(Long userId) {
		Optional<UserModel> user = userRepository.findById(userId);
		if(user.isEmpty())
		{
			throw new UserExceptions("Id is not present in database");
		}else {
			userRepository.deleteById(userId);
		    return null;
	  }
	}
    
}
