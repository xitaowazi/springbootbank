package com.springboot.bank.controller;

import javax.servlet.http.HttpServletRequest;

import com.springboot.bank.domain.User;
import com.springboot.bank.security.JwtTokenUtil;
import com.springboot.bank.security.domain.JwtUser;
import com.springboot.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 获取已授权用户信息
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
public class UserRestController {

  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  @Qualifier("jwtUserDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public JwtUser getAuthenticatedUser(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader).substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
    return user;
  }

  @RequestMapping(value = "changepassword",method = RequestMethod.POST)
  public ResponseEntity<?>changepassword(@RequestParam("password")String password,HttpServletRequest request){
    //System.out.println("加密前:"+password);
    password = passwordEncoder.encode(password);
    //System.out.println("加密后:"+password);

    String token = request.getHeader(tokenHeader).substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
    //System.out.println("id:"+user.getId());
    int count = userService.changePassword(user.getId(),password);

    return ResponseEntity.ok(count);
  }

  //    Restful
  @RequestMapping(value = "/users",method = RequestMethod.GET)
  public ResponseEntity<?> getUsers(){
    List<User> users = userService.find();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
  public ResponseEntity<?> getUser(@PathVariable("id")Integer id){
    User user = userService.find(id);
    return new ResponseEntity<>(user,HttpStatus.OK);
  }

  @RequestMapping(value = "/users",method = RequestMethod.POST)
  public ResponseEntity<?> add(@RequestBody User user){
    //加密
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    //设置默认时间
    user.setLastPasswordResetDate(new Date());
    user.setLoginDate(new Date());

    int count = userService.add(user);
    return ResponseEntity.ok(count);
  }

  @RequestMapping(value = "/users",method = RequestMethod.PUT)
  public ResponseEntity<?> modify(@RequestBody User user){
    int count = userService.modify(user);
    return ResponseEntity.ok(count);
  }

  @RequestMapping("/userauthority")
  public ResponseEntity<?> addUserAuthority(@RequestParam("userId") Integer userId,@RequestParam("authorityIds")Integer[] authorityIds){
    System.out.println(authorityIds+"..........");
    System.out.println(new Integer[]{3,2});
    int count =userService.addUserAuthority(userId,authorityIds);
    return ResponseEntity.ok(count);
  }

  @GetMapping("/finduserauthority")
  public ResponseEntity<?> findUserAuthority(@RequestParam("userId") Integer userId){
   List<Integer> authorityIds = userService.findUserAuthority(userId);
   return new ResponseEntity<>(authorityIds,HttpStatus.OK);
  }
}
