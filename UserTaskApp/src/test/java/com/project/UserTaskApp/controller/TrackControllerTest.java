package com.project.UserTaskApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(MockitoExtension.class)
//class TrackControllerTest {
//    private MockMvc mockMvc;
//
//    @Mock
//    private TrackServiceImpl trackService;
//
//    @InjectMocks
//    private TrackController trackController;
//    private Track track1, track2;
//    List<Track> trackList;
//    Artist artist1,artist2;
//    User user;
//
//    @BeforeEach
//    void setUp() {
//
//        artist1 = new Artist("1001","artist1");
//        track1 = new Track("TR001","track1","Good Track1",4,artist1);
//        artist2 = new Artist("1002","artist2");
//        track2 = new Track("TR002","track2","Good Track2",4,artist2);
//        trackList = Arrays.asList(track1,track2);
//        user = new User();
//        user.setUserId("U1234");
//        user.setAge(35);
//        user.setUserName("John");
//        user.setUserEmail("John@gmail.com");
//        user.setTrackList(trackList);
//        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
//    }
//    @AfterEach
//    void tearDown() {
//        user=null;
//    }
//    @Test
//    public void testRegisterUser() throws Exception {
//        when(trackService.registerUser(any())).thenReturn(user);
//        mockMvc.perform(post("/api/v2/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonToString(user)))
//                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
//        verify(trackService,times(1)).registerUser(any());
//
//    }
//    @Test
//    public void registerUserFailure() throws Exception {
//        when(trackService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);
//        mockMvc.perform(post("/api/v2/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonToString(user)))
//                .andExpect(status().isConflict())
//                .andDo(MockMvcResultHandlers.print());
//    }
//    private static String jsonToString(final Object ob) throws JsonProcessingException {
//        String result;
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonContent = mapper.writeValueAsString(ob);
//            result = jsonContent;
//        } catch(JsonProcessingException e) {
//            result = "JSON processing error";
//        }
//
//        return result;
//    }
//}