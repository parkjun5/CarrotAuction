package com.carrot.core.carrotauction;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.UseMainMethod.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(useMainMethod = WHEN_AVAILABLE)
class SwaggerUnitTest {

	@Autowired
	protected MockMvc mockMvc;

	@Test
	@DisplayName("스웨거 UI 페이지가 생성되었는지 확인")
	void shouldDisplaySwaggerUiPage() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/swagger-ui/index.html"))
				.andExpect(status().isOk())
				.andReturn();
		String contentAsString = mvcResult.getResponse().getContentAsString();
		assertThat(contentAsString).contains("Swagger UI");
	}

}
