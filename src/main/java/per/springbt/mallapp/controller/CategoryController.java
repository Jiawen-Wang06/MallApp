package per.springbt.mallapp.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import per.springbt.mallapp.common.APIRestResponse;
import per.springbt.mallapp.common.Constant;
import per.springbt.mallapp.exception.MallappExceptionEnum;
import per.springbt.mallapp.model.pojo.User;
import per.springbt.mallapp.model.request.AddCategoryReq;
import per.springbt.mallapp.model.request.UpdateCategoryReq;
import per.springbt.mallapp.model.vo.CategoryVO;
import per.springbt.mallapp.service.CategoryService;
import per.springbt.mallapp.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {
    @Resource
    private UserService userService;
    @Resource
    private CategoryService categoryService;
    @ApiOperation("Admin add new category")//Swagger annotation
    @PostMapping("/admin/category/add")
    @ResponseBody
    public APIRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        //Check if the user login in
        User currentUser = (User) session.getAttribute(Constant.Mooc_MALL_USER);
        if (currentUser == null) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_TO_LOGIN);
        }
        //Check if the user is an admin
        if (userService.checkAdminRole(currentUser)) {
            categoryService.add(addCategoryReq);
            return APIRestResponse.success();
        } else {
            return APIRestResponse.error(MallappExceptionEnum.NOT_ADMIN_ROLE);
        }
    }
    @ApiOperation("Admin update the category")//Swagger annotation
    @PostMapping("/admin/category/update")
    @ResponseBody
    public APIRestResponse update(@Valid @RequestBody UpdateCategoryReq updateCategoryReq, HttpSession session) {
        //Check if the user login in
        User currentUser = (User) session.getAttribute(Constant.Mooc_MALL_USER);
        if (currentUser == null) {
            return APIRestResponse.error(MallappExceptionEnum.NEED_TO_LOGIN);
        }
        //Check if the user is an admin
        if (userService.checkAdminRole(currentUser)) {
            /*Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq, category);*/
            categoryService.update(updateCategoryReq);
            return APIRestResponse.success();
        } else {
            return APIRestResponse.error(MallappExceptionEnum.NOT_ADMIN_ROLE);
        }
    }
    @ApiOperation("Admin delete the category")//Swagger annotation
    @PostMapping("/admin/category/delete")
    @ResponseBody
    public APIRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return APIRestResponse.success();
    }
    @ApiOperation("Admin list the category")//Swagger annotation
    @PostMapping("/admin/category/list")
    @ResponseBody
    public APIRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return APIRestResponse.success(pageInfo);
    }
    @ApiOperation("User list the category")//Swagger annotation
    @PostMapping("/category/list")
    @ResponseBody
    public APIRestResponse listCategoryForCustomer() {
        List<CategoryVO> cVO = categoryService.listForCustomer();
        return APIRestResponse.success(cVO);
    }
}