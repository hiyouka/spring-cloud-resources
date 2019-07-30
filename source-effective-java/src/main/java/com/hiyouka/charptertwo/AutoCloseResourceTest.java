package com.hiyouka.charptertwo;

import com.hiyouka.base.BaseTest;

import java.io.IOException;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class AutoCloseResourceTest extends BaseTest {

    public void test(){
        try(AutoCloseTest fileInputStream = new AutoCloseTest("D:\\work word\\group\\test.xlsx")){
            logger.info("get file resource");
            logger.info("first char : " + fileInputStream.read());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getCause().getSuppressed());
        }
    }


    public void test2() throws IOException {
        AutoCloseTest fileInputStream = null;
        try{
            fileInputStream = new AutoCloseTest("D:\\work word\\group\\test.xlsx");
            logger.info("first char : " + fileInputStream.read());
        }catch (IOException e){
            e.printStackTrace();
            logger.error(e.getCause().getSuppressed());
        }finally {
            if(fileInputStream != null){
                fileInputStream.close();
            }
        }
    }

}