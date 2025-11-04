//package com.example17.demo17.satoken;
//
//import cn.dev33.satoken.oauth2.consts.GrantType;
//import cn.dev33.satoken.oauth2.data.loader.SaOAuth2DataLoader;
//import cn.dev33.satoken.oauth2.data.model.loader.SaClientModel;
//import org.springframework.stereotype.Component;
//
///**
// * com.example17.demo17.satoken
// * User: wangjun
// * Date: 2025/5/28 - 17:30
// * Description:
// */
//@Component
//public class SaOAuth2DataLoaderImpl implements SaOAuth2DataLoader {
//
//    @Override
//    public SaClientModel getClientModel(String clientId) {
//        return new SaClientModel()
//                .setClientId("1001")    // client id
//                .setClientSecret("aaaa-bbbb-cccc-dddd-eeee")    // client 秘钥
//                .addAllowRedirectUris("*")    // 所有允许授权的 url
//                .addContractScopes("openid", "userid", "userinfo")    // 所有签约的权限
//                .addAllowGrantTypes(     // 所有允许的授权模式
//                        GrantType.authorization_code, // 授权码式
//                        GrantType.implicit,  // 隐式式
//                        GrantType.refresh_token,  // 刷新令牌
//                        GrantType.password,  // 密码式
//                        GrantType.client_credentials  // 客户端模式
//                );
//    }
//
//    @Override
//    public SaClientModel getClientModelNotNull(String clientId) {
//        return SaOAuth2DataLoader.super.getClientModelNotNull(clientId);
//    }
//
//    @Override
//    public String getOpenid(String clientId, Object loginId) {
//        return SaOAuth2DataLoader.super.getOpenid(clientId, loginId);
//    }
//
//    @Override
//    public String getUnionid(String subjectId, Object loginId) {
//        return SaOAuth2DataLoader.super.getUnionid(subjectId, loginId);
//    }
//}
