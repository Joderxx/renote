package top.renote.util.randomImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Joder_X on 2017/10/13.
 * 验证码产生和验证
 */

@Service
public class ValidateCodeService {

    @Autowired
    private ImageCreate imageCreate;

    private ImageFactory imageFactory = ImageFactory.getInstance();

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getSession().getId();
        BufferedImage bufferedImage = imageCreate.createImage(id);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage,"JPG",outputStream);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/jpeg");
        outputStream.flush();
        outputStream.close();
    }

    /**
     *
     * @param id 传入的sessionId
     * @param code 验证码
     * @return 验证码正确true，错误false
     */
    public boolean validateForID(String id,String code){
        Map<String,String> map = imageFactory.getCodeFactory();
        if (!map.containsKey(id)){
            return false;
        }
        boolean is = map.get(id).toLowerCase().equals(code.toLowerCase());
        map.remove(id);
        return is;
    }
}
