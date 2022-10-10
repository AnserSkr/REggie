package com.reggie;

import com.reggie.common.Result;
import com.reggie.utils.ValidateCodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 10:59
 * @Description: com.reggie
 * @version: 1.0
 */
@SpringBootTest
public class test {

    @Test
    public void sendMsg(){
        Random random = new Random();
        for (int j=0;j<10;j++) {
            Integer[] i = new Integer[]{4,6};
            String code = ValidateCodeUtils.generateValidateCode(i[random.nextInt(2)]).toString();
            System.out.println(code);
        }
    }

    @Test
    public void testRandom(){
        Random random = new Random();
        random.nextInt(1);
        System.out.println(random.nextInt(1));
    }
}
