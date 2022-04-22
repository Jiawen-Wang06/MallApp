package per.springbt.mallapp.service;

import com.github.pagehelper.PageInfo;
import per.springbt.mallapp.model.request.AddCategoryReq;
import per.springbt.mallapp.model.request.UpdateCategoryReq;
import per.springbt.mallapp.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);
    void update(UpdateCategoryReq updateCategory);
    void delete(Integer id);
    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
    List<CategoryVO> listForCustomer();
}