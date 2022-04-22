package per.springbt.mallapp.model.dao;

import org.springframework.stereotype.Repository;
import per.springbt.mallapp.model.pojo.OrderItem;
@Repository
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}