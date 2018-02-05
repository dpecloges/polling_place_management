package com.ots.dpel.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.auth.args.UserArgs;
import com.ots.dpel.auth.dto.UserDto;
import com.ots.dpel.auth.dto.list.UserListDto;
import com.ots.dpel.auth.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/auth/users")
public class UserController {
    
    private static final Logger logger = LogManager.getLogger(UserController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserService userService;
    
    @PreAuthorize("hasAuthority('sa.user')")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public @ResponseBody Page<UserListDto> getUserIndex(HttpServletRequest request, Pageable pageable) {
        
        String searchArgs = request.getParameter("searchString");
        UserArgs args = new UserArgs();
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, UserArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return userService.getUserIndex(args, pageable);
    }
    
    @PreAuthorize("hasAuthority('sa.user')")
    @RequestMapping(value = "find/{id}", method = RequestMethod.GET)
    public @ResponseBody UserDto findUser(@PathVariable("id") Long id) {
        return userService.findUser(id);
    }
    
    @PreAuthorize("hasAuthority('sa.user')")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody UserDto saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }
    
    @PreAuthorize("hasAuthority('sa.user')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
