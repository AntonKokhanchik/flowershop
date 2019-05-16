package flowershop.webService.rest;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import static org.junit.Assert.*;

public class LoginCheckServiceTest {
//    @Test
//    public void checkLogin() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String fooResourceUrl = "http://localhost:8080//rest/check_login/";
//
//        String login = "admin";
//        ResponseEntity<Boolean> response = restTemplate.getForEntity(fooResourceUrl + login, Boolean.class);
//        assertThat(response.getStatusCode(), CoreMatchers.equalTo(HttpStatus.OK));
//        assertTrue(response.hasBody());
//        assertTrue(response.getBody());
//
//        login = "admin2";
//        response = restTemplate.getForEntity(fooResourceUrl + login, Boolean.class);
//        assertThat(response.getStatusCode(), CoreMatchers.equalTo(HttpStatus.OK));
//        assertTrue(response.hasBody());
//        assertFalse(response.getBody());
//    }

    @Test
    public void checkLoginWithMock() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        Mockito.when(restTemplate.getForEntity("http://localhost:8080//rest/check_login/admin", Boolean.class))
                .thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity("http://localhost:8080//rest/check_login/admin2", Boolean.class))
                .thenReturn(new ResponseEntity<>(false, HttpStatus.OK));

        String fooResourceUrl = "http://localhost:8080//rest/check_login/";

        String login = "admin";
        ResponseEntity<Boolean> response = restTemplate.getForEntity(fooResourceUrl + login, Boolean.class);
        assertThat(response.getStatusCode(), CoreMatchers.equalTo(HttpStatus.OK));
        assertTrue(response.hasBody());
        assertTrue(response.getBody());

        login = "admin2";
        response = restTemplate.getForEntity(fooResourceUrl + login, Boolean.class);
        assertThat(response.getStatusCode(), CoreMatchers.equalTo(HttpStatus.OK));
        assertTrue(response.hasBody());
        assertFalse(response.getBody());
    }
}