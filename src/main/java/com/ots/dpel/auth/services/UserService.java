package com.ots.dpel.auth.services;

import java.util.List;

import com.ots.dpel.auth.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ots.dpel.auth.dto.DpUserBasicDto;
import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.auth.dto.list.UserListDto;
import com.ots.dpel.common.args.SearchableArguments;

/**
 * Interface λειτουργιών που αφορούν τους χρήστες της εφαρμογής
 */
public interface UserService {
    public DpUserDetailsDTO findCrUserDetails(String username);
    
    public List<DpUserBasicDto> findAllUsersBasic();
    
    public void logoutUserByUsername(String username);
    
    /**
     * Ανάκτηση του ονοματεπωνύμου του χρήστη με το δεδομένο id
     */
    public String getUserFullNameById(Long id);
    
    Page<UserListDto> getUserIndex(SearchableArguments arguments, Pageable pageable);
    
    UserDto findUser(Long id);
    
    UserDto saveUser(UserDto userDto);
    
    void deleteUser(Long id);
    
    Boolean electionDepartmentHasUsers(Long electionDepartmentId);
}