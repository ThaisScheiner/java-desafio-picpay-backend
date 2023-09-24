package com.desafiopicpaybackend.service;

import com.desafiopicpaybackend.domain.user.User;
import com.desafiopicpaybackend.domain.user.UserType;
import com.desafiopicpaybackend.dto.UserDTO;
import com.desafiopicpaybackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    public UserService(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRep;

    private final PasswordEncoder passwordEncoder;

    //criando usuario
    public User createUser(UserDTO userDTO){
        User newUser = new User(userDTO);

        //Criptografia na senha com spring security
        newUser.setPassword(passwordEncoder.encode(userDTO.password()));
        this.saveUser(newUser);
        return newUser;

    }

    public void saveUser(User newUser){
        userRep.save(newUser);
    }

    public List<User> getAllUsers(){
        return userRep.findAll();
    }

    public User findUserById(Long userId) throws Exception{
        return userRep.findById(userId).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public boolean validateUser(User payer, BigDecimal amount) throws Exception{
        if(payer.getUserType() == UserType.MERCHANT)
            throw new Exception("Usuário lojista não pode realizar essa transação.");

        if(payer.getBalance().compareTo(amount) < 0)
            throw new Exception("Saldo insulficiente.");

        return true;
    }

    @Transactional
    public User upDateUserById(Long id, UserDTO userDTO){
        return userRep.findById(id).map((existingUser) ->{
            existingUser.setName(userDTO.name());
            existingUser.setDocument(userDTO.document());
            existingUser.setEmail(userDTO.email());
            existingUser.setPassword(passwordEncoder.encode(userDTO.password()));
            existingUser.setUserType(userDTO.userType());
            return userRep.save(existingUser);
        } ).orElse(null);
    }

    public boolean deleteUserById(Long id){
        if(userRep.existsById(id)){
            userRep.deleteById(id);
            return true;
        }
        else return false;
    }


    //confere com o banco de dados para validar se a senha é valida
    @Transactional
    public Boolean validarSenha(UserDTO userRequest){
        User user = userRep.findByEmail(userRequest.email());
        if(user != null && user.getPassword() != null){
            return passwordEncoder.matches(userRequest.password(), user.getPassword());
        }
        throw new EntityNotFoundException();
    }
}
