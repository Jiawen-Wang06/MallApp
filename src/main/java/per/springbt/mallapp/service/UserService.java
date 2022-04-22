package per.springbt.mallapp.service;

import per.springbt.mallapp.exception.MallappException;
import per.springbt.mallapp.model.pojo.User;

public interface UserService {
    public User getUser();
    public void register(String userName, String password) throws MallappException;
    public User login(String userName, String password) throws MallappException;
    public void updateUsersig(User user) throws MallappException;
    boolean checkAdminRole(User user);
}