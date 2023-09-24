package com.desafiopicpaybackend.controller;

import com.desafiopicpaybackend.domain.user.User;
import com.desafiopicpaybackend.dto.UserDTO;
import com.desafiopicpaybackend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    //injeção de dependência
    @Autowired
    private UserService userService;

    //cria usuário
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //valida Login
    @PostMapping
    public ResponseEntity<User> validarSenha(@RequestBody UserDTO userDTO){
        Boolean valid = userService.validarSenha(userDTO);
        if(!valid)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

    //lista todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        var users  = userService.getAllUsers();
        if(users.isEmpty()){
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    //atualiza usuário por ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody UserDTO userDTO){
        User user = userService.upDateUserById(id, userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //deleta usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id){
        if(userService.deleteUserById(id))
            return ResponseEntity.noContent().build();
        else throw new EntityNotFoundException();
    }

}
