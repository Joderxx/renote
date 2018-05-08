package top.renote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ModelMap;
import top.renote.controller.note.NoteController;
import top.renote.dto.LoginDTO;
import top.renote.model.User;
import top.renote.redis.UserRedis;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.json.JsonUtil;
import top.renote.util.mailUtil.MailSend;
import top.renote.util.model.Mail;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Project1ApplicationTests {

	@Autowired
	private NoteController noteController;

	@Test
	public void contextLoads() {
		ModelMap modelMap = noteController.updateNote(13L,6982L,"1234","1234567<p>123</p>","",null);
		System.out.println(modelMap);
	}

	@Autowired
	private UserRedis userRedis;

	@Test
	public void test4() throws IOException {
		String hashId = "eyJhY2NvdW50IjoiMTIzNDU2NyIsInBhc3N3b3JkIjoiMTIzNDU2NyIsInRpbWUiOiIyMDE4LTA0LTE4IDIwOjIxOjEyIn0=";
		LoginDTO loginDTO = JsonUtil.JsonToModel(EncryptUtil.base64Decode(hashId),LoginDTO.class);
		User user = userRedis.get(loginDTO.getAccount());
		System.out.println(loginDTO);
	}

	@Autowired
	private MailSend mailSend;
	@Test
	public void test5(){
		String url = "activeAccount?activeCode=123456"+"&account=123456";
		mailSend.sendRegisterMail(new Mail(null,"974318723@qq.com",null,
				"Hello World",url,"123456"));
	}

}
