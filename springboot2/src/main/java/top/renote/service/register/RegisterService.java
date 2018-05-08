package top.renote.service.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.renote.daoMapper.NotebookMapper;
import top.renote.daoMapper.UserMapper;
import top.renote.model.Notebook;
import top.renote.model.RegisterUser;
import top.renote.model.Root;
import top.renote.model.User;
import top.renote.redis.RegisterRedis;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.digestUtil.RandomCode;
import top.renote.util.mailUtil.MailSend;
import top.renote.util.model.Mail;

import java.util.Date;

/**
 * Created by Joder_X on 2018/1/12.
 */
@Service
public class RegisterService {

    @Value("${web.host}")
    private String host;

    public static final int HAS_ACCOUNT=0,//账号存在
            HAS_EMAIL=1,//邮箱存在
            SUCCESS=2;// 成功
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailSend mailSend;
    @Autowired
    private NotebookMapper notebookMapper;

    @Autowired
    private RegisterRedis registerRedis;

    /**
     *
     * @param registerUser
     * @return
     */
    @Transactional
    public int addInRegister(RegisterUser registerUser){
        registerUser.setCode(RandomCode.randNumber());
        registerUser.setPassword(EncryptUtil.SHA256(registerUser.getPassword()));

        if (userMapper.gethasUserWithAccount(registerUser.getAccount())>0){
            return HAS_ACCOUNT;
        }else if (userMapper.gethasUserWithEmail(registerUser.getEmail())>0){
            return HAS_EMAIL;
        }

        registerRedis.add(registerUser);
        String url = host+"activeAccount?activeCode="+registerUser.getCode()+"&account="+registerUser.getAccount();
        mailSend.sendRegisterMail(new Mail(null,registerUser.getEmail(),null,
                "Hello World",url,registerUser.getAccount()));
        return SUCCESS;

    }

    @Transactional
    public boolean active(String account,String activeCode){
        System.out.println("account: "+account+", code: "+activeCode);
        RegisterUser registerUser = registerRedis.get(account);
        if (registerUser==null||!activeCode.equals(registerUser.getCode()))return false;
        Date date = new Date();
        User user = new User(registerUser.getAccount(),registerUser.getSex(),
                "22.jpg",registerUser.getAccount(),registerUser.getPassword(),
                registerUser.getEmail(),date,date,date);
        int n = userMapper.insertOneUser(user);
        assert n>0;
        Notebook publiz = new Notebook("public_default",date,date,user, Root.PUBLIC,false);
        Notebook collect = new Notebook("collect_default",date,date,user, Root.COLLECT,false);
        Notebook share = new Notebook("share_default",date,date,user, Root.SHARED,false);
        notebookMapper.insertOne(publiz);
        notebookMapper.insertOne(collect);
        notebookMapper.insertOne(share);
        registerRedis.delete(registerUser);
        return n>0;
    }
}
