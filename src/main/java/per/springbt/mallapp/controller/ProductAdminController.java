package per.springbt.mallapp.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import per.springbt.mallapp.common.APIRestResponse;
import per.springbt.mallapp.common.Constant;
import per.springbt.mallapp.exception.MallappException;
import per.springbt.mallapp.exception.MallappExceptionEnum;
import per.springbt.mallapp.model.request.AddProductReq;
import per.springbt.mallapp.service.ProductService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
public class ProductAdminController {
    @Resource
    private ProductService productService;
    @PostMapping("/admin/product/add")
    @ResponseBody
    public APIRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.addProduct(addProductReq);
        return APIRestResponse.success();
    }
    @PostMapping("/admin/upload/file")
    public APIRestResponse upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //Generate UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffix;
        File fileDIR = new File(Constant.FILE_UPLOAD_DIR);
        File desFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDIR.exists()) {
            if (!fileDIR.mkdir()) {
                throw new MallappException(MallappExceptionEnum.CREATE_FILE_FAILED);
            }
        }
        try {
            file.transferTo(desFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return APIRestResponse.success(getHost(new URI(request.getRequestURL() + "")) + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            return APIRestResponse.error(MallappExceptionEnum.UPDATE_FAIED);
        }
    }
    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }
}