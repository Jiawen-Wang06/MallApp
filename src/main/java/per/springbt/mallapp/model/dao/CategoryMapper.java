package per.springbt.mallapp.model.dao;

import org.springframework.stereotype.Repository;
import per.springbt.mallapp.model.pojo.Category;

import java.util.List;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Category record);
    int insertSelective(Category record);
    Category selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Category record);
    int updateByPrimaryKey(Category record);
    Category selectByName(String name);
    List<Category> selectList();
    List<Category> selectCategoriesByParentId(Integer parentId);
}