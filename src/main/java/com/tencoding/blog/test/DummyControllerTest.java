package com.tencoding.blog.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.RoleType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	// UserRepository 메모리에 올라가 있는 상태
	// 그럼 어떻게 가져올수 있는가? DI - 의존성 주임
	@Autowired // 자동으로 new 때려지는 것임. 래퍼런스 변수가 담김.
	private UserRepository userRepository;

	@GetMapping("/dummy/users")
	public List<User> 전체사용자검색() {
		return userRepository.findAll();
	}

	// 페이징 처리
	// List로 리턴해도 되고, Page로 리턴해도 된다.
	// http://Localhost:9090/blog/dummy/user?page=1
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 1, sort = "id", direction = Direction.DESC) Pageable pageable) {
//		Page<User> pageUser = userRepository.findAll(pageable);

		Page<User> pageUser = userRepository.findAll(pageable);

//		Page page = userRepository.findAll(pageable);
//		List<User> user = page.getContent();

		return pageUser;
	}

	// RestAPI 중 GET (X), POST 방식 써야함.
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("==============================");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println("==============================");

		System.out.println(user.getId());
		System.out.println(user.getCreateDate());
		System.out.println(user.getRole()); // null 이 들어가서 default값 불가

		user.setRole(RoleType.USER);

		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}

	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// Optional -> null safety를 위해서 씀. Optional로 감싸서 User Entity를 가지고 오겠다.
//    	User userTemp1 = userRepository.findById(id).get();
//    	User userTemp2 = userRepository.findById(id).orElseGet(() -> {
//    		return new User();
//    	}); //get이 없으면 다른거를 가져와라

		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자입니다." + id);
		}); // Entity를 가져올때 null 이면 예외처리하라

//    	User user = userRepository.findById(id);
		return user;
	}

	// 업데이트
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + reqUser.getPassword());
		System.out.println("email : " + reqUser.getEmail());

		// 받은 값을 DB에 저장해야 한다. 없으면 insert 처리해주고 있으면 update알아서 해줌.
		// userName 값이 없어서 오류 발생함.
		// update하려면 먼저 select 해와서 추가적인 처리를 해야한다.
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자입니다." + id);
		});
		userEntity.setPassword(reqUser.getPassword());
		userEntity.setEmail(reqUser.getEmail());
		User user = userRepository.save(userEntity);

		return null;
	}

	// 삭제
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {

		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return "해당 유저는 존재하지 않습니다.";
		}
		return id + "는 삭제되었습니다.";
	}
}
