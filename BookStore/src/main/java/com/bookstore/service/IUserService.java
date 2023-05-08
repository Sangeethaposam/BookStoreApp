package com.bookstore.service;

import java.util.List;

import com.bookstore.dto.LoginDto;
import com.bookstore.dto.UserDto;
import com.bookstore.entities.UserModel;

public interface IUserService {
	public UserModel registerUser(UserDto userDto);
    public String  userLogin(LoginDto logindto);
    public String userLogout(String email);
    public UserModel getByToken(String token);
    public List<UserModel> getAll(String token);
    public UserModel updateByEmail(UserDto userDto);
    public UserModel changePassword(String pass, String email);
	public UserModel DeleteUser(Long userId);
}
