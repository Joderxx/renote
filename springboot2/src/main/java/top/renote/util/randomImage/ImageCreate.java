package top.renote.util.randomImage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Joder_X on 2017/10/13.
 *
 * 验证码图片产生
 * 可修改classpath：spring/spring-code.xml更改默认参数
 */


public class ImageCreate {
    private String random = "0123456789";
    private int width = 80;//宽，横向长度
    private int height = 30;//高，纵向长度
    private int textLength = 4;//验证码字母个数
    private Color backColor = Color.WHITE;//背景色
    private ImageFactory imageFactory = ImageFactory.getInstance();
    private Font font = new Font("Arial",Font.PLAIN,20);//字体类型
    private boolean interference = false;//是否加干扰线和点

    public ImageCreate() {
    }

    public ImageCreate(String random, int width, int height, Font font, boolean interference) {
        this();
        this.random = random;
        this.width = width;
        this.height = height;
        this.font = font;
        this.interference = interference;
    }

    public BufferedImage createImage(String id){
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(backColor);
        g.fillRect(0,0,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,width-2,height-2);
        String rand = randomText();
        imageFactory.getCodeFactory().put(id,rand);
        int w = width/textLength;//间隔
        g.setFont(font);
        for (int i=0;i<textLength;i++){
            g.setColor(new Color(randInt(),randInt(),randInt()));
            int x = i*w+randInt((w-font.getSize())/3);
            int y = font.getSize()+randInt((height-font.getSize())/6);
            double d = (randInt(2)==1?1:-1)*Math.random()*30;
            g.rotate(Math.toRadians(d),x,y);
            g.drawString(rand.charAt(i)+"",x,y);
            g.rotate(Math.toRadians(-d),x,y);
        }
        if (interference){
            int size = randInt((width*height)/15);
            g.setColor(new Color(randInt(),randInt(),randInt()));
            for (int i=0;i<size;i++){
                int x = randInt(width);
                int y = randInt(height);
                g.drawLine(x,y,x,y);
            }
            size = randInt(width/10);
            g.setColor(new Color(randInt(),randInt(),randInt()));
            for (int i=0;i<size;i++){
                int x1 = randInt(width);
                int y1 = randInt(height);
                int x2 = randInt(width);
                int y2 = randInt(height);
                g.drawLine(x1,y1,x2,y2);
            }
        }
        g.dispose();
        return bufferedImage;
    }

    private String randomText(){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<textLength;i++){
            int index = (int) (Math.random()*random.length());
            sb.append(random.charAt(index));
        }
        return sb.toString();
    }

    private int randInt(){
        return randInt(256);
    }

    private int randInt(int r){
        return (int) (Math.random()*r);
    }

    public void setRandom(String random) {
        random = random.trim();
        this.random = random;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setInterference(boolean interference) {
        this.interference = interference;
    }

    public String getRandom() {
        return random;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Font getFont() {
        return font;
    }

    public boolean isInterference() {
        return interference;
    }
}
