package com.CarSaleWebsite.Kolesa.Services;

import com.CarSaleWebsite.Kolesa.Configuration.MyUserPrincipal;
import com.CarSaleWebsite.Kolesa.Models.Usr;
import com.CarSaleWebsite.Kolesa.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailService implements UserDetailsService {
    @Autowired
    private final UsersRepository usersRepository;

    public UsersDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
       Usr user= usersRepository.findUsersByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
