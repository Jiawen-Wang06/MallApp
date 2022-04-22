package per.springbt.mallapp.service.impl;

import org.springframework.beans.BeanUtils;
import per.springbt.mallapp.exception.MallappException;
import per.springbt.mallapp.exception.MallappExceptionEnum;
import per.springbt.mallapp.model.dao.ProductMapper;
import per.springbt.mallapp.model.pojo.Product;
import per.springbt.mallapp.model.request.AddProductReq;
import per.springbt.mallapp.service.ProductService;

import javax.annotation.Resource;

public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Override
    public void addProduct(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product productDB = productMapper.selectByName(addProductReq.getName());
        if (productDB != null) {
            throw new MallappException(MallappExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new MallappException(MallappExceptionEnum.ADD_FAILED);
        }
    }
}