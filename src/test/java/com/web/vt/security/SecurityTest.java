package com.web.vt.security;

import com.web.vt.common.ControllerTestSupporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Disabled
public class SecurityTest extends ControllerTestSupporter {

    @Test @DisplayName("시스템 관리자로 로그인합니다.")
    public void adminLogin() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest()
                .id("admin")
                .password("1234");

        mvc.perform(post("/admin/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authenticationRequest)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.STRING).description("로그인 id"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("refreshToken").ignored()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("시스템 관리자 권한의 refresh token으로 token을 재발급 받습니다")
    public void adminRefreshToken() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest()
                .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJyZWZyZXNoIiwiaXNzIjoibG9jYWxob3N0OjgwODAiLCJpZCI6ImFkbWluIiwiZXhwIjoxNjkyMDAyMjI2fQ.dJ-HlnNYV7CCP3nMVcL--F8WDWxAExOiyyE12ptIxCA");

        mvc.perform(post("/admin/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authenticationRequest)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").ignored(),
                                        fieldWithPath("password").ignored(),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("refresh token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("동물병원 관리자로 로그인합니다")
    public void employeeAdminLogin() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest()
                .id("test")
                .password("1234");

        mvc.perform(post("/client/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authenticationRequest)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.STRING).description("로그인 id"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("refreshToken").ignored()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("동물병원 관리자 권한의 refresh token으로 token을 재발급 받습니다")
    public void clientRefreshToken() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest()
                .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJyZWZyZXNoIiwiaXNzIjoibG9jYWxob3N0OjgwODAiLCJpZCI6InRlc3QiLCJleHAiOjE2OTIwMDIyOTV9.I13A6uSg-GI413g3akrSgkGTapEzY-XuBVmBpCP3QKw");

        mvc.perform(post("/client/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authenticationRequest)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").ignored(),
                                        fieldWithPath("password").ignored(),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("refresh token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("동물병원 일반 사용자로 로그인합니다")
    public void employeeUserLogin() throws Exception {
        AuthenticationRequest vo = new AuthenticationRequest()
                .id("user")
                .password("1234");

        mvc.perform(post("/client/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.STRING).description("로그인 id"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                        fieldWithPath("refreshToken").ignored()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("동물병원 일반 사용자로 로그인합니다")
    public void notExistEmployeeUserLogin() throws Exception {
        AuthenticationRequest vo = new AuthenticationRequest()
                .id("not-user")
                .password("1234");

        mvc.perform(post("/admin/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.STRING).description("로그인 id"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                        fieldWithPath("refreshToken").ignored()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test @DisplayName("시스템 관리자 권한이 필요한 api를 요청합니다")
    public void adminUserApi() throws Exception {
        mvc.perform(get("/v1/admin/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoiYWRtaW4iLCJleHAiOjE2OTE5OTg2NTF9.VMjR8QiUXZChKAbH1TAYWFr1dxWKRo11axRL9iseJNE"))
                .andDo(
                        docs.document(
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("시스템 관리자 권한이 필요한 api를 요청합니다")
    public void notAdminUserApi() throws Exception {
        mvc.perform(get("/v1/admin/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoidXNlciIsImV4cCI6MTY5MTk3NTAyMX0.43T8-t4JvgjzLKgd7K7acvV1weAFu0e4UMrzb6E2i80"))
                .andDo(
                        docs.document(
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test @DisplayName("동물병원 관리자 권한이 필요한 api를 요청합니다")
    public void employeeAdminApi() throws Exception {
        mvc.perform(get("/v1/client/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoidGVzdCIsImV4cCI6MTY5MTk3MTAzOX0.2nex9iFFzxHIzq4Ja8H_2PnQ6GxWpf04gV8qvopIAzg"))
                .andDo(
                        docs.document(
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("동물병원 관리자 권한이 필요한 api를 요청합니다")
    public void employeeUserApi() throws Exception {
        mvc.perform(get("/v1/client/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoiYWRtaW4iLCJleHAiOjE2OTE5OTQ1NDl9.Z4Q4tg-N27_rzXUTj5Mei7QVfHBzHyUvLJwVBLXS47s"))
                .andDo(
                        docs.document(
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test @DisplayName("admin 계정에서 로그아웃 합니다")
    @WithUserDetails(userDetailsServiceBeanName = "adminDetailService", value = "admin")
    public void adminUserLogout() throws Exception {
        mvc.perform(post("/admin/logout"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("client 계정에서 로그아웃 합니다")
    @WithUserDetails(userDetailsServiceBeanName = "employeeDetailService", value = "test")
    public void employeeUserLogout() throws Exception {
        mvc.perform(post("/client/logout"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
