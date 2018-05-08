package top.renote.controller.note;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/4/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();  //构造MockMvc

    }

    @Test
    public void updateNote() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/user/notebooks/13/notes/6525") // //调用接口
                .contentType(MediaType.TEXT_HTML_VALUE)
                .param("content", "1211").param("name", "henry")//传入添加的用户参数
                .param("tag","")
                .accept(MediaType.APPLICATION_JSON))  //接收的类型
                //.andExpect(MockMvcResultMatchers.status().isOk())   //判断接收到的状态是否是200
                .andDo(MockMvcResultHandlers.print());  //打印内容

    }


}