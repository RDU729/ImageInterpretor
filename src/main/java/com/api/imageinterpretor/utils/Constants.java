package com.api.imageinterpretor.utils;

public interface Constants {

    //SERVICE//
    String BASE_ACTIVATION_LINK = "http://localhost:8080/api/v1/activate/";

    String TEMP_FILE_LOCATION = "/Users/radupopescu/Documents/GitHub/ImageInterpretor/src/main/resources/pyImages/d.jpg";

    String ACTIVATION_MAIL_MESSAGE = "Hi and thank you for registering. The activation link is  ";

    String ACTIVATION_MAIL_SUBJECT = "Acount Activation Email";

    //CONTROLLER//

    String SIGN_UP_RESPONSE = "Signup successfull. An email has been sent to your email address";

    String LOG_IN_RESPONSE = "Logged in";

    String ACCOUNT_ACTIVATED_RESPONSE = "Account activated";

    String LOGGED_OUT_RESPONSE = "Logged out";

    String[] PERMITED_LOCATIONS = new String[]{"/api/v1/hi", "/api/v1/signup"
            ,"/api/v1/activate/**","/api/v1/retrieve/**","/api/v1/hi/**"
            ,"/actuator/prometheus"};
}
