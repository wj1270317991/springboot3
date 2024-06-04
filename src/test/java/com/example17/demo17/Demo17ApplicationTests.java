package com.example17.demo17;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Demo17ApplicationTests {

    @Test
    public void workerid2() throws Exception {
        String string = "afdsfdsfds$$12312321";
        System.out.println(string.lastIndexOf("$$"));
        String aa = string.substring(string.lastIndexOf("$$")+2);
        System.out.println(aa);
    }


    @Test
    public void workerid() throws Exception {
        long id = IdWorker.getId();
        System.out.println("id: "+id);
    }

    @Test
    public void testEncrypt() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");          // 加密的算法，这个算法是默认的
        config.setPassword("secrect");                        // 加密的密钥，随便自己填写，很重要千万不要告诉别人
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "dba.com";         //自己的密码
        String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println("密码：" + encryptedText);
    }

    @Test
    public void testDe() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("secrect");
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = "oOuQJhZQF9JcHz0dba78cw==";   //加密后的密码
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }


    @Test
    public void encode1(){
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = delegatingPasswordEncoder.encode("11111");
        System.out.println(encode);
    }


    @Test
    public void strCase(){
        String hello_world = NamingCase.toPascalCase("HELLO_WORLD");
        System.out.println(hello_world);
    }
}
