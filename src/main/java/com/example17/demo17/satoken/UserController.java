package com.example17.demo17.satoken;

import cn.dev33.satoken.session.SaTerminalInfo;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.dev33.satoken.stp.parameter.enums.SaLogoutMode;
import cn.dev33.satoken.stp.parameter.enums.SaReplacedRange;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.example17.demo17.satoken
 * User: wangjun
 * Date: 2025/11/4 - 16:14
 * Description:
 */
@RestController
@RequestMapping("sa-token/user/")
public class UserController {

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public Object doLogin(String username, String password) {
         StpUtil.login(10001, new SaLoginParameter()
                .setDeviceType("PC")             // 此次登录的客户端设备类型, 一般用于完成 [同端互斥登录] 功能
                .setDeviceId("xxxxxxxxx")        // 此次登录的客户端设备ID, 登录成功后该设备将标记为可信任设备
                //.setIsLastingCookie(true)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                .setTimeout(60 * 60 * 24 * 7)    // 指定此次登录 token 的有效期, 单位:秒，-1=永久有效
                .setActiveTimeout(60 * 60 * 24 * 7) // 指定此次登录 token 的最低活跃频率, 单位:秒，-1=不进行活跃检查
                .setIsConcurrent(true)           // 是否允许同一账号多地同时登录 （为 true 时允许一起登录, 为 false 时新登录挤掉旧登录）
                .setIsShare(false)                // 在多人登录同一账号时，是否共用一个 token （为 true 时所有登录共用一个token, 为 false 时每次登录新建一个 token）
                .setMaxLoginCount(12)            // 同一账号最大登录数量，-1代表不限 （只有在 isConcurrent=true, isShare=false 时此配置项才有意义）
                .setMaxTryTimes(12)              // 在每次创建 token 时的最高循环次数，用于保证 token 唯一性（-1=不循环尝试，直接使用）
                .setExtra("key", "value")        // 记录在 Token 上的扩展参数（只在 jwt 模式下生效）
                .setExtra("key2", "value2")        // 记录在 Token 上的扩展参数（只在 jwt 模式下生效）
                .setIsWriteHeader(false)         // 是否在登录后将 Token 写入到响应头
                .setTerminalExtra("key", "value")// 本次登录挂载到 SaTerminalInfo 的自定义扩展数据
                .setReplacedRange(SaReplacedRange.CURR_DEVICE_TYPE) // 顶人下线的范围: CURR_DEVICE_TYPE=当前指定的设备类型端, ALL_DEVICE_TYPE=所有设备类型端
                .setOverflowLogoutMode(SaLogoutMode.LOGOUT)         // 溢出 maxLoginCount 的客户端，将以何种方式注销下线: LOGOUT=注销下线, KICKOUT=踢人下线, REPLACED=顶人下线
                .setRightNowCreateTokenSession(true)                // 是否立即创建对应的 Token-Session （true=在登录时立即创建，false=在第一次调用 getTokenSession() 时创建）
         );
        SaTerminalInfo terminalInfo = StpUtil.getTerminalInfo();
        return SaResult.data(terminalInfo);
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}

