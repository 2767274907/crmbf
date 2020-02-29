package com.t248.lmf.crm;

import com.t248.lmf.crm.entity.User;
import com.t248.lmf.crm.repository.CstLostRepostitor;
import com.t248.lmf.crm.repository.CustomerRepostitor;
import com.t248.lmf.crm.repository.DictRepostitor;
import com.t248.lmf.crm.repository.OrdersRepostitor;
import com.t248.lmf.crm.service.IUserService;
import com.t248.lmf.crm.service.MailService;
import com.t248.lmf.crm.vo.DictInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class RedisApplicationTests {

    @Resource
    private IUserService userService;

    @Test
    void contextLoads() {
        User user = userService.getUser(2L);
        System.out.println("对象输出>>"+user);
        User user2 = userService.getUser(2L);
        System.out.println("对象输出>>"+user2);

    }

    @Resource
    OrdersRepostitor ordersRepostitor;
    @Resource
    CustomerRepostitor customerRepostitor;
    @Resource
    CstLostRepostitor cstLostRepostitor;
    @Resource
    DictRepostitor dictRepostitor;

    @Test
    public void test2(){
        List<DictInfo> list = dictRepostitor.getDicttype();
        for (DictInfo d:
             list) {
            System.out.println(d.getId());
            System.out.println(d.getCount());
            System.out.println(d.getName());
        }
    }

    @Autowired
    MailService mailService;
    @Test
    public void testMail() throws  Exception {
            String to = "2767274907@qq.com";
            String subject ="测试一下";
            String content="给你检查作业";
            try {
                mailService.sendSimpleMail(to,subject,content);
                System.out.println("成功了");
            }catch (MailException e){
                System.out.println("失败了");
                e.printStackTrace();
            }
    }

}
