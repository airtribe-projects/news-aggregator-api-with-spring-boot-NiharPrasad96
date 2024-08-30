package com.news.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.news.app.entity.UserInfo;
import com.news.app.repository.UserInfoRepository;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService{

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public UserDetails loadUserByUsername1(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByUsername(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return (UserDetails) userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public UserInfo loadUserByUsername2(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail =repository.findByUsername(username);

        // Converting UserInfo to UserDetails
        return (UserInfo) userDetail
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String getPreferenceByUsername(String username){
        String returnString = "";
        Optional<UserInfo> userDetail = repository.findByUsername(username); 
        if(userDetail.isPresent()){
             returnString = "Welcome back "+userDetail.get().getName();
             if(userDetail.get().getPreferred_category()==null){
                returnString = returnString.concat(".You Currently do not have a Preferred Category set.Please set it.");
                return returnString;
             }
             else{
                returnString = returnString.concat("Your Preferred news category is "+userDetail.get().getPreferred_category());
                return returnString;
             }
        } else {
            return "USER DOES NOT EXIST. PLEASE TRY WITH DIFFERENT USERNAME";
        } 
    }

    public String getPreferenceByUsernameForNews(String username){
        String returnString = "";
        Optional<UserInfo> userDetail = repository.findByUsername(username); 
        if(userDetail.isPresent()){
             if(userDetail.get().getPreferred_category().isBlank()){
                return "";
             }
             else{
                return userDetail.get().getPreferred_category();
             }
        } else {
            return "USER DOES NOT EXIST";
        } 
    }


    public String setPreferenceByUsername(String username,String preference){
        String returnString = "";
        Optional<UserInfo> userDetail = repository.findByUsername(username); 
        if(userDetail.isPresent()){
             returnString = "Welcome back "+userDetail.get().getName();
             if(userDetail.get().getPreferred_category()==null){
                returnString = returnString.concat(".Your Currently do not have a Preferred Category set.Setting it to "+preference);
                userDetail.get().setPreferred_category(preference);
                repository.save(userDetail.get());
                return returnString;
             }
             else{
                returnString = returnString.concat("Your Current Preferred news category is "+userDetail.get().getPreferred_category()+".Setting it to "+preference);
                return returnString;
             }
        } else {
            return "USER DOES NOT EXIST. PLEASE TRY WITH DIFFERENT USERNAME";
        } 
    }


    public String addUser(UserInfo userInfo) {
        // Encode password before saving the user
        System.out.print("In UserInfoService password value"+userInfo.getPassword());
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
}

