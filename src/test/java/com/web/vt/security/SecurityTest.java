package com.web.vt.security;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.employee.EmployeeVO;
import com.web.vt.domain.user.AdminVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTest extends RestDocsTestSupport {

    @BeforeEach
    public void setup(WebApplicationContext context){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test @DisplayName("시스템 관리자로 로그인합니다.")
    public void adminLogin() throws Exception {
        AdminVO vo = new AdminVO()
                .id("test")
                .password("1234");

        mvc.perform(post("/admin/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vo)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("동물병원 관리자로 로그인합니다")
    public void employeeLogin() throws Exception {
        EmployeeVO vo = new EmployeeVO()
                .id("test")
                .password("1234");

        mvc.perform(post("/client/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("시스템 관리자 권한이 필요한 api를 요청합니다")
    public void adminApi() throws Exception {
        mvc.perform(get("/v1/admin/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlkIjoidGVzdCIsImV4cCI6MTY5MTA0Mzk4MH0.5N5wP1xqnxOVUQB-MNYPpXvYr12CQVTkIocDP9ED5mU"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("동물병원 관리자 권한이 필요한 api를 요청합니다")
    public void employeeAdminApi() throws Exception {
        mvc.perform(get("/v1/client/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjbGllbnQiLCJpZCI6InRlc3QiLCJleHAiOjE2OTEwNDQ5MDV9.CN01PwiENuiSZ2W2HsdSxgJfYL4i49kdGsaf8fg7N5o"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
