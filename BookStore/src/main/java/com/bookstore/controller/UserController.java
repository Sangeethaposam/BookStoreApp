package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Response;
import com.bookstore.dto.LoginDto;
import com.bookstore.dto.UserDto;
import com.bookstore.entities.UserModel;
import com.bookstore.mailController.EmailSenderService;
import com.bookstore.service.IUserService;
import com.bookstore.utility.UserToken;

@RestController
@RequestMapping("/bookstore")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    EmailSenderService iEmailService;
    @Autowired
    UserToken userToken;
    @Autowired
	Response response;

    @PostMapping("/registerUser")
    public ResponseEntity<Response> registerUser( @RequestBody UserDto userDto){
        UserModel newUser = userService.registerUser(userDto);
        response.setMsg("Added Successfully");
		response.setObject(newUser);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<Response> userLogin(@RequestBody LoginDto loginDto){
        String userModel = userService.userLogin(loginDto);
        response.setMsg("Logged in Successfully");
		response.setObject(userModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
    }
    
    @PostMapping("/userLogout")
    public ResponseEntity<Response> userLogout(@RequestParam String email){
        String userModel = userService.userLogout(email);
        response.setMsg("Logged Out Successfully");
		response.setObject(userModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getRecordByToken")
    public ResponseEntity<Response> getByToken(@RequestHeader String token){
        UserModel userModel = userService.getByToken(token);
        response.setMsg("Token Fetched Successfully");
		response.setObject(userModel);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllRecords")
    public ResponseEntity<Response> getAll(@RequestHeader String token){
        List<UserModel> userModelList = userService.getAll(token);
        response.setMsg("All Record Fetched Successfully");
		response.setObject(userModelList);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PutMapping("/updateByEmail")
    public ResponseEntity<Response> updateByEmail(@RequestBody UserDto userDto){
        UserModel userModel = userService.updateByEmail(userDto);
        response.setMsg("Updated data of Given Email: "+userModel.getEmail());
		response.setObject(userModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Response> changePassword(@RequestParam String pass, @RequestParam String email){
        UserModel userModel = userService.changePassword(pass,email);
        response.setMsg("Password Changed ...");
		response.setObject(userModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/DeleteUser")
	public ResponseEntity<Response> DeleteUser(@RequestParam Long userId) {
		UserModel user = userService.DeleteUser(userId);
		response.setMsg("Deleted successfully");
		response.setObject(user);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}

