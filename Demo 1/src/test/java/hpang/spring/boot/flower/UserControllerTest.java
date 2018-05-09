package hpang.spring.boot.flower;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import hpang.spring.boot.flower.model.User;
import hpang.spring.boot.flower.service.UserClient;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserClient userClient;
	
	@Before
    public void setup() {
		Mockito.when(userClient.getUniqueUserIds()).thenReturn(10L);
		
		User mockUser = new User();
		mockUser.setTitle("test title");
		mockUser.setBody("test body");
		mockUser.setUserId("1");
		mockUser.setId("1");
		
		Mockito.when(userClient.getModifiedUser()).thenReturn(mockUser);
    }
	
	@After
    public void reset() {
        Mockito.reset(userClient);
    }
	
	@Test
	public void shouldGetUniqueUserCount() throws Exception{
		MvcResult result = this.mvc.perform(get("/user/uniqueids")).andExpect(status().isOk()).andReturn();
		JSONAssert.assertEquals("{\"uniqueCount\":10}", result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void shouldGetModifyUser() throws Exception{
		MvcResult result = this.mvc.perform(get("/user/modify")).andExpect(status().isOk()).andReturn();
		String expectedUserJson ="{\"userId\":\"1\",\"id\":\"1\",\"title\":\"test title\",\"body\":\"test body\"}";
		JSONAssert.assertEquals(expectedUserJson, result.getResponse().getContentAsString(), true);
	}
	
	
}
