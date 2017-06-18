package by.itransition.service.admin;

import by.itransition.data.model.NewsType;
import by.itransition.data.model.User;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.news.NewsSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private UserRepository userRepository;

    private NewsSaver newsSaver;

    @Autowired
    public void setNewsSaver(NewsSaver newsSaver) {
        this.newsSaver = newsSaver;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void changeUserRole(long userId, String userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (userRole) {
            case "ROLE_ADMIN":
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            case "ROLE_MANAGER":
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            case "ROLE_DEVELOPER":
                authorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));
                break;
            default:
                throw new RuntimeException("Unknown role for user");
        }
        User user = userRepository.getOne(userId);
        if(user == null){
            throw new RuntimeException("User with id " + userId + " is not found");
        }
        user.setAuthorities(authorities);
        userRepository.save(user);
        newsSaver.save(null,userId, NewsType.DEVELOPER_UPGRADE);
    }

    public void fireUser(long userId) {
        newsSaver.save(null,1l,NewsType.DEVELOPER_FIRE);
        userRepository.delete(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
