package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.RoleType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

    // UserRepository 메모리에 올라가있는 상태
    // 그럼 어떻게 가져오나요? DI
    @Autowired
    private UserRepository userRepository;

    // REST POST
    @PostMapping("/dummy/join")
    public String join(@RequestBody User user) {

        System.out.println("===============================");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        System.out.println("===============================");

        System.out.println(user.getId());
        System.out.println(user.getCreateDate());
        System.out.println(user.getRole()); // null값 들어감 -> default 값 불가

        user.setRole(RoleType.USER);
        userRepository.save(user);

        return "회원가입 완료 되었습니다.";
    }
    
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
    	// Optional -> null safety를 위해서 씀. Optional로 감싸서 User Entity를 가지고 오겠다.
//    	User userTemp1 = userRepository.findById(id).get();
//    	User userTemp2 = userRepository.findById(id).orElseGet(() -> {
//    		return new User();
//    	}); //get이 없으면 다른거를 가져와라
    	
    	User user = userRepository.findById(id).orElseThrow(() -> {
    		return new IllegalArgumentException("해당 유저는 없는 사용자입니다.");
    	}); // Entity를 가져올때 null 이면 예외처리하라
    	
//    	User user = userRepository.findById(id);
    	return user;
    }
}
