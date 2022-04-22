package per.springbt.mallapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import per.springbt.mallapp.exception.MallappException;
import per.springbt.mallapp.exception.MallappExceptionEnum;
import per.springbt.mallapp.model.dao.CategoryMapper;
import per.springbt.mallapp.model.pojo.Category;
import per.springbt.mallapp.model.request.AddCategoryReq;
import per.springbt.mallapp.model.request.UpdateCategoryReq;
import per.springbt.mallapp.model.vo.CategoryVO;
import per.springbt.mallapp.service.CategoryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);//Same as using category.setName(addCategoryReq.getName())
        Category dataInDB = categoryMapper.selectByName(addCategoryReq.getName());
        //Make sure the record doesn't exist in the DB
        if (dataInDB != null) {
            throw new MallappException(MallappExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new MallappException(MallappExceptionEnum.ADD_FAILED);
        }
    }
    @Override
    public void update(UpdateCategoryReq updateCategory) {
        if (!StringUtils.isNullOrEmpty(updateCategory.getName())) {
            Category categoryDB = categoryMapper.selectByName(updateCategory.getName());
            if (categoryDB != null && !categoryDB.getId().equals(updateCategory.getId())) {
                throw new MallappException(MallappExceptionEnum.NAME_EXISTED);
            }
            Category category = new Category();
            BeanUtils.copyProperties(updateCategory, category);
            int count = categoryMapper.updateByPrimaryKeySelective(category);
            if (count == 0) {
                throw new MallappException(MallappExceptionEnum.UPDATE_FAIED);
            }
        }
    }
    @Override
    public void delete(Integer id) {
        Category categoryDB = categoryMapper.selectByPrimaryKey(id);
        if (categoryDB != null) {
            int count = categoryMapper.deleteByPrimaryKey(id);
            if (count == 0) {
                throw new MallappException(MallappExceptionEnum.DELETE_FAILED);
            }
        } else {
            throw new MallappException(MallappExceptionEnum.DELETE_FAILED);
        }
    }
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type, order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }
    @Override
    @Cacheable(value = "listForCustomer")
    public List<CategoryVO> listForCustomer() {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<CategoryVO>();
        getCategoryRecursive(categoryVOList, 0);
        return categoryVOList;
    }
    private void getCategoryRecursive(List<CategoryVO> categoryVOList, Integer parentId) {
        //递归获取所有子类别，并且组合成一个”目录树“
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);
                getCategoryRecursive(categoryVO.getChildCategory(), categoryVO.getId());
            }
        }
    }
}