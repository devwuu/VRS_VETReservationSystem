package com.web.vt.security;

import com.web.vt.common.ControllerTestSupporter;
import org.junit.jupiter.api.Disabled;
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

@Disabled
public class SecurityTest extends ControllerTestSupporter {

    @Test @DisplayName("시스템 관리자로 로그인합니다.")
    public void adminUserLogin() throws Exception {
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
    public void adminUserReissueToken() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest()
                .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJyZWZyZXNoIiwiaXNzIjoibG9jYWxob3N0OjgwODAiLCJpZCI6ImFkbWluIiwiZXhwIjoxNjkyMzQwOTA1fQ.lnopYddP4Ies3ONIxAnxKNgSNO1aJWNu1v_O6p8o1DY");

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

    @Test @DisplayName("시스템 관리자 권한이 필요한 api를 요청합니다")
    public void adminUserApiRequest() throws Exception {
        mvc.perform(get("/v1/admin/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoiYWRtaW4iLCJleHAiOjE2OTIzMzczMzF9.n0CHaU79JrSUfMoU3q_gp0_PfMP6gaQDN-bZ0UOpXIs"))
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

    @Test @DisplayName("시스템 관리자 계정에서 로그아웃 합니다")
    @WithUserDetails(userDetailsServiceBeanName = "adminDetailService", value = "admin")
    public void adminUserLogout() throws Exception {
        mvc.perform(post("/admin/logout")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoiYWRtaW4iLCJleHAiOjE2OTIzMzk4NTV9.iMVbREdrjDSl5Mk0y6vCL-vWh1oS-TjpCQnpsXtRNoo")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        docs.document(
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("동물병원 관리자로 로그인합니다")
    public void clientUserLogin() throws Exception {

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
    public void clientUserReissueToken() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest()
                .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJyZWZyZXNoIiwiaXNzIjoibG9jYWxob3N0OjgwODAiLCJpZCI6InRlc3QiLCJleHAiOjE2OTIzNDA5Nzh9.VjpqMEpgE6ie0D1HEE6iVsDeU9xsU1uweW7s07o62h0");

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

    @Test @DisplayName("동물병원 관리자 권한이 필요한 api를 요청합니다")
    public void clientUserApiRequest() throws Exception {
        mvc.perform(get("/v1/client/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoidGVzdCIsImV4cCI6MTY5MjMzNzM5NX0.1iT9p_p99JP6inOy2AAg5jpzTu6gFQwDJ7ycCbwGyHY"))
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

    @Test @DisplayName("동물병원 관리자 계정에서 로그아웃 합니다")
    @WithUserDetails(userDetailsServiceBeanName = "employeeDetailService", value = "test")
    public void clientUserLogout() throws Exception {
        mvc.perform(post("/client/logout")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY2Nlc3MiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoidGVzdCIsImV4cCI6MTY5MjMzOTkzMn0.F4dqe_MfFtD-8oaeMLaN8XLisT5ATuKmhgD6_cVPgZ0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        docs.document(
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
