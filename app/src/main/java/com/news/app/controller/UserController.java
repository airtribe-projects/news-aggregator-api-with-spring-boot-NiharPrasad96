package com.news.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.news.app.entity.AuthRequest;
import com.news.app.entity.LoginInfo;
import com.news.app.entity.UserInfo;
import com.news.app.service.JwtService;
import com.news.app.service.UserInfoService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register")
    public String registerNewUser(@RequestBody UserInfo userInfo) {
        System.out.print("In controller password value" + userInfo.getPassword());
        return service.addUser(userInfo);
    }

    @PostMapping("/login")
    public String loginExistingUser(@RequestBody LoginInfo loginInfo) {
        UserInfoService userInfoService = new UserInfoService();
        try {
            UserDetails userDetails = userInfoService.loadUserByUsername(loginInfo.getName());
            return "Welcome back " + userDetails.getUsername();
            // logic to display news here
        } catch (UsernameNotFoundException e) {
            return "USER DOES NOT EXIST";
        }
    }

    @GetMapping("/preferences")
    public String getUserPreferences(@RequestParam String userName) {
        UserInfoService userInfoService = new UserInfoService();
        return userInfoService.getPreferenceByUsername(userName);
    }

    @PutMapping("/preferences")
    public String setUSerPreferences(@RequestParam String userName, @RequestParam String preference) {
        UserInfoService userInfoService = new UserInfoService();
        return userInfoService.setPreferenceByUsername(userName, preference);
    }

    @GetMapping("/news")
       public ResponseEntity<String> getPreferredNews(@RequestParam String userName) {
           UserInfoService userInfoService = new UserInfoService();
               String preferredString = userInfoService.getPreferenceByUsernameForNews(userName);
               ResponseEntity<String> result = new ResponseEntity<>(null);
               if(preferredString.contains("USER DOES NOT EXIST")){
                return result;
               }
               else{
                if(preferredString.isBlank()){
                    preferredString="general";
                }
               RestClient restClient = RestClient.create();
               result = restClient.get() 
               .uri("https://api.thenewsapi.com/v1/news/all?api_token=H0u6y7Dkqda8qFrBcALho7u4ZpJs4S7bMuTkwRL5&language=en&limit=3&categories="+preferredString) 
                  .retrieve()
                .toEntity(String.class); 
                return result;
          }
        
        }
    
}
