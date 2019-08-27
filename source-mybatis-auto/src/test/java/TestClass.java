import com.alibaba.druid.support.json.JSONUtils;
import com.hiyouka.source.Main;
import com.hiyouka.source.config.PortConfig;
import com.hiyouka.source.model.ConfigDataEntry;
import com.hiyouka.source.properties.WeChat;
import com.hiyouka.source.service.ConfigEntryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hiyouka
 * @since JDK 1.8
 */

@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
public class TestClass {

    @Autowired
    private PortConfig portConfig;

    @Autowired
    private WeChat weChat;

    @Test
    public void test1() throws IOException {
//        "transactionid":"150000489417651"
//        Main.BeanA beanA = new Main.BeanA(1,"2");
//        beanA.setBeanB(new Main.BeanB(4,"kk"));
//        Map<String,Object> entity = new HashMap<>();
////        BeanUtils.copyProperties(beanA,entity);
//        ReflectUtils.copyProperties(beanA,entity);
//        entity.forEach((k,v)->{
//            System.out.println(k + "====" + v);
//        });
        System.out.println(weChat.getApplication()+">>>>>>>>>>>>>>>>>");
        System.out.println(portConfig + ">>>>>>>>>>>>>>>>>>>");
        System.out.println(portConfig);
        Date date = new Date(1560572436000L);
        SimpleDateFormat format = new SimpleDateFormat();
        String format1 = format.format(date);
        System.out.println(format1);
        Map<String,Object> map = new LinkedHashMap();
        map.put(null,111);
        System.out.println(map.get(null));
        OutputStream outputStream = new FileOutputStream("");
        outputStream.flush();


    }

    @Test
    public void test(){
        ConfigDataEntry configDataEntry = new ConfigDataEntry();
        configDataEntry.setNodeId("2");
        configDataEntry.setAlarmConfig("5");
        configDataEntry.setCategory("2");
        configDataEntry.setCategoryName("t");
        configDataEntry.setLine("2");
        configDataEntry.setLowerLimit("2");
        configDataEntry.setName("3");
        configDataEntry.setNodeType("3");
        configDataEntry.setOrderNo(1);
        configDataEntry.setOrgNo("3");
        configDataEntry.setTypeName("3");
        configDataEntry.setUpperLimit("4");
        configDataEntry.setType("3");
        System.out.println(JSONUtils.toJSONString(configDataEntry));
    }


    @Test
    public void test2(){
        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.execute(new TestR());
    }

    @Autowired
    private ConfigEntryService configEntryService;

    @Test
    public void testTransaction(){
        configEntryService.insertAsync();
    }

    class TestR implements Runnable{

        @Override
        public void run() {
            System.out.println("ok");
        }
    }

}