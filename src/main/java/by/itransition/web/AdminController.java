package by.itransition.web;

import by.itransition.data.model.Tag;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.admin.AdminService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = {"/admin"})
public class AdminController {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/change/")
    public void changeUserRole(long userId, String newAuthority) {
        adminService.changeUserRole(userId,newAuthority);
    }

    @GetMapping(value = "/")
    public ModelAndView showAllUsers() {
        return new ModelAndView("admin", "users", adminService.getAllUsers());
    }

}
