package per.springbt.mallapp.model.dao;

import org.springframework.stereotype.Repository;
import per.springbt.mallapp.model.pojo.Cart;

@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}