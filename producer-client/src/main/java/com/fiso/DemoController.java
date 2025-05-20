package com.fiso;


import com.fiso.service.UserDTO;
import com.fiso.service.UserProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class DemoController {

    private final UserProducer userProducer;


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/greeting")
    void updateUser(@RequestBody UserDTO request) {
        userProducer.notifyOnUserUpdate(request);
    }



}
