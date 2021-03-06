package per.springbt.mallapp.model.dao;

import org.springframework.stereotype.Repository;
import per.springbt.mallapp.model.pojo.Order;
@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}