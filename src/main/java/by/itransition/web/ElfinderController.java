package by.itransition.web;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.UserService;
import by.itransition.service.user.exception.UserExistsException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Ilya Ivanov
 */
@Controller
@RequestMapping(value = {"/"})
public class ElfinderController {
    private static final Logger log = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/connectorr")
    @ResponseBody
    public ResponseEntity<String> getFiles(@RequestBody String body) {
        log.warn("Body: " + body);
        return ResponseEntity.ok("OK");
    }
}
