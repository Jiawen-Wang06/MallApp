package per.springbt.mallapp.service.impl;

import org.springframework.stereotype.Service;
import per.springbt.mallapp.exception.MallappException;
import per.springbt.mallapp.exception.MallappExceptionEnum;
import per.springbt.mallapp.model.dao.UserMapper;
import per.springbt.mallapp.model.pojo.User;
import per.springbt.mallapp.service.UserService;
import per.springbt.mallapp.utils.Md5Utils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }
    @Override
    public void register(String userName, String password) throws MallappException {
        //Check if userName already exists
        User result = userMapper.selectByName(userName);
        if (result != null) {
            throw new MallappException(MallappExceptionEnum.NAME_EXISTED);
        }
        //Update database
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(Md5Utils.getMDStr(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //user.setPassword(password); Too dangerous to save the unencrypted password
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new MallappException(MallappExceptionEnum.INSERT_FAILED);
        }
    }
    @Override
    public User login(String userName, String password) throws MallappException {
        String md5Pass = null;
        try {
            md5Pass = Md5Utils.getMDStr(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName, md5Pass);
        if (user == null) {
            throw new MallappException(MallappExceptionEnum.INCORRECT_PASSWORD);
        }
        return user;
    }
    @Override
    public void updateUsersig(User user) throws MallappException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new MallappException(MallappExceptionEnum.UPDATE_FAIED);
        }
    }
    @Override
    public boolean checkAdminRole(User user) {
        return user.getRole().equals(2);
    }
}