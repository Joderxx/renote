package top.renote.service.address;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/4/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;
    @Test
    public void sharedNote() throws Exception {
        System.out.println(addressService.sharedNote(8L,6974L));
    }

}