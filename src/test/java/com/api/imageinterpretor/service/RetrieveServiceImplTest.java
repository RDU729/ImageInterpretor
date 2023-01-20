package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.ImageRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class RetrieveServiceImplTest {

    EasyRandom easyRandom = new EasyRandom();

    @Mock
    FlowRepo flowRepo;

    @Autowired
    UserRepo userRepo;

    @Mock
    ImageRepo imageRepo;

    RetrieveServiceImpl retrieveService;

    @BeforeEach
    void setUp(){
        retrieveService = new RetrieveServiceImpl(flowRepo,userRepo,imageRepo);
    }

    @Test
    @DisplayName("Given user not present in database should throw error ")
    @WithMockUser(username = "admin", password = "password", authorities = {"ADMIN", "USER"})
    void test_all_by_user_no_user_found() {
        //Given

        //When
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            retrieveService.getAllByAUser();
        });

        //Then
        AssertionsForClassTypes.assertThat(exception.getMessage())
                .isEqualTo("Optional, contained no value");
    }

    @Test
    @DisplayName("Given logged in user and no images should retrieve an empty list ")
    @WithMockUser(username = "sa2@sa", password = "sa", authorities = {"ADMIN", "USER"})
    void test_all_by_user_no_images() {
        //Given

        //When
        List<Long> allByAUser = retrieveService.getAllByAUser();

        //Then
        assertThat(allByAUser.size()).isEqualTo(0);
    }


    @Test
    @DisplayName("Given logged in user and one image should retrieve 1 image ")
    @WithMockUser(username = "sa2@sa", password = "sa", authorities = {"ADMIN", "USER"})
    void test_all_by_user_one_image() {
        //Given

        List<Flow> flowList = buildFlow();

        when(flowRepo.findAllByUser(anyObject())).thenReturn(flowList);

        //When
        List<Long> allByAUser = retrieveService.getAllByAUser();

        //Then
        assertThat(allByAUser.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Given a user with an image , retrieve that image")
    @WithMockUser(username = "sa2@sa", password = "sa", authorities = {"ADMIN", "USER"})
    void test_get_image_success() throws IOException {
        //Given
        List<Flow> flowList = buildFlow();
        when(flowRepo.findAllByUser(anyObject())).thenReturn(flowList);

        Image image = new Image();
        image.setId(1L);
        image.setBase64(new byte[]{20});

        when(imageRepo.findById(anyLong())).thenReturn(Optional.of(image));

        //When
        InputStream image1 = retrieveService.getImage(1L);

        //Then
        int firstByte = image1.read();
        assertThat(firstByte).isEqualTo(20);
    }

    @Test
    @DisplayName("Given a user with and image with null base 64 , should throw null pointer")
    @WithMockUser(username = "sa2@sa", password = "sa", authorities = {"ADMIN", "USER"})
    void test_get_image_null(){
        List<Flow> flowList = buildFlow();
        when(flowRepo.findAllByUser(anyObject())).thenReturn(flowList);

        Image image = new Image();
        image.setId(1L);

        when(imageRepo.findById(anyLong())).thenReturn(Optional.of(image));

        //When
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            retrieveService.getImage(1L);
        });

        //Then
        AssertionsForClassTypes.assertThat(exception.getMessage())
                .isEqualTo("Cannot read the array length because \"buf\" is null");
    }

    @Test
    @DisplayName("Given a user with an image should throw optional empty")
    @WithMockUser(username = "sa2@sa", password = "sa", authorities = {"ADMIN", "USER"})
    void test_get_image_optional_empty(){
        List<Flow> flowList = buildFlow();
        when(flowRepo.findAllByUser(anyObject())).thenReturn(flowList);

        //When

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            retrieveService.getImage(1L);
        });

        //Then
        AssertionsForClassTypes.assertThat(exception.getMessage())
                .isEqualTo("Optional, contained no value");
    }


    @Test
    @DisplayName("Given a user with no image , retrieve an  image that does not exist")
    @WithMockUser(username = "sa2@sa", password = "sa", authorities = {"ADMIN", "USER"})
    void test_get_image_should_throw_error(){

        //When

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            retrieveService.getImage(999999999999999L);
        });

        //Then
        AssertionsForClassTypes.assertThat(exception.getMessage())
                .isEqualTo("Image not found");
    }


    private List<Flow> buildFlow() {
        User user = new User();
        user.setEmail("sa2@sa");
        user.setPassword("sa");
        user.setName("sa");
        user.setEnabled(1);
        user.setActivationCode("n/a");
        user.setOffence(0);

        Image image = new Image();
        image.setId(1L);

        List<Flow> flowList = new ArrayList<>();

        Flow flow = easyRandom.nextObject(Flow.class);
        flow.setId(1L);
        flow.setUser(user);
        flow.setImage(image);

        flowList.add(flow);
        return flowList;
    }
}