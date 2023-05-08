package com.bookstore.utility;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.dto.LoginDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class UserToken {
	@Autowired
	LoginDto lto;
	String secretKey="pushpendra";
	
	public String generateToken(LoginDto lto) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("Email", lto.getEmail());
		claims.put("Password", lto.getPassword());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}
	
	public LoginDto decode(String str ) {
		Claims claims=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(str).getBody();
		lto.setEmail((String) claims.get("Email"));
		lto.setPassword((String) claims.get("Password"));	
		return lto;
	}

}
