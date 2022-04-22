package per.springbt.mallapp.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constant {
    public static final String SALT = "56565&84fddfd@dfdfd";
    public static final String Mooc_MALL_USER = "mooc_mall_user";
    public static String FILE_UPLOAD_DIR;
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }
}