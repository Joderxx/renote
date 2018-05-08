package top.renote.mapper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joder_X on 2018/4/9.
 */
public class Atest {

    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.subList(0,1));
    }
}
