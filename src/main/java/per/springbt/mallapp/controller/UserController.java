package per.springbt.mallapp.controller;

import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import per.springbt.mallapp.common.APIRestResponse;
import per.springbt.mallapp.common.Constant;
import per.springbt.mallapp.exception.MallappException;
import per.springbt.mallapp.exception.MallappExceptionEnum;
import per.springbt.mallapp.model.pojo.User;
import per.springbt.mallapp.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/*
 * UserController
 * */
@Controller
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/test")
    @ResponseBody
    public User personalPage() {
        return userService.getUser();
    }
    @PostMapping("/register")
    @ResponseBody
    public APIRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws MallappException {
        if (StringUtils.isNullOrEmpty(userName)) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < 8) {
            return APIRestResponse.error(MallappExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
        return APIRestResponse.success();
    }
    @PostMapping("/login")
    @ResponseBody
    public APIRestResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws MallappException {
        if (StringUtils.isNullOrEmpty(userName)) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        user.setPassword(null);
        session.setAttribute(Constant.Mooc_MALL_USER, user);
        return APIRestResponse.success(user);
    }
    @PostMapping("/user/update")
    @ResponseBody
    public APIRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws MallappException {
        User currentUser = (User) session.getAttribute(Constant.Mooc_MALL_USER);
        if (currentUser == null) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_TO_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateUsersig(user);
        return APIRestResponse.success();
    }
    @PostMapping("/user/logout")
    @ResponseBody
    public APIRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.Mooc_MALL_USER);
        return APIRestResponse.success();
    }
    @PostMapping("/adminLogin")
    @ResponseBody
    public APIRestResponse adminLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws MallappException {
        if (StringUtils.isNullOrEmpty(userName)) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        if (userService.checkAdminRole(user)) {
            user.setPassword(null);
            session.setAttribute(Constant.Mooc_MALL_USER, user);
            return APIRestResponse.success(user);
        } else {
            return APIRestResponse.error(MallappExceptionEnum.NOT_ADMIN_ROLE);
        }
    }
}