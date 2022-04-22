package per.springbt.mallapp.model.dao;

import org.springframework.stereotype.Repository;
import per.springbt.mallapp.model.pojo.Product;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Product record);
    int insertSelective(Product record);
    Product selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Product record);
    int updateByPrimaryKey(Product record);
    Product selectByName(String name);
}