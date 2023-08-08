package com.web.vt.security;

import com.web.vt.common.ControllerTestSupporter;
import com.web.vt.domain.employee.EmployeeVO;
import com.web.vt.domain.user.AdminVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTest extends ControllerTestSupporter {

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
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoidGVzdCIsImV4cCI6MTY5MTU1MDI1N30.wmGiiuAIE6t6BPWhUE4aopYtW1mGTlFo9j6SFNxxveM"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("동물병원 관리자 권한이 필요한 api를 요청합니다")
    public void employeeAdminApi() throws Exception {
        mvc.perform(get("/v1/client/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsb2NhbGhvc3Q6ODA4MCIsImlkIjoidGVzdCIsImV4cCI6MTY5MTU1MDIyMn0.x4w_VnSFzdbkajvXwZTNEuCKz5WWNYVNLvp3Qf74mCg"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
