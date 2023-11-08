package com.shop.ShopApplication.service.adminService;
import com.shop.ShopApplication.DTO.*;
import com.shop.ShopApplication.JWT.JwtService;
import com.shop.ShopApplication.entity.Filial;
import com.shop.ShopApplication.entity.Role;
import com.shop.ShopApplication.repo.FilialRepository;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final FilialRepository filialRepository;


    @Override
    public AuthResponse saveAdmin(AdminLoginDto adminLoginDto) {
        var user = User.builder()
                .login(adminLoginDto.getLogin())
                .password(passwordEncoder.encode(adminLoginDto.getPassword()))
                .role(Role.ADMIN)
                .enabled(true)
                .build();
        userRepository.save(user);

        return AuthResponse.builder()
                .message("now you registered")
                .build();
    }

    @Override
    public AdminAuthResponse login(AdminLoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                ));
        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AdminAuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public String saveWaiter(EmployeeRegisterDto employeeRegisterDto) {
        Role role = employeeRegisterDto.getRole();
        Optional<Filial> optionalFilial = filialRepository.findById(employeeRegisterDto.getFilial_id());
        Optional<User> optionalUserByPhone = userRepository.findByPhoneNumber(employeeRegisterDto.getPhoneNumber());
        if(optionalUserByPhone.isPresent()){
            return "Работник с таким номером телефона уже зарегестрирован";
        }
        Optional<User> optionalUserByLogin = userRepository.findByLogin(employeeRegisterDto.getLogin());
        if (optionalUserByLogin.isPresent()) {
            return "Работник с таким логином уже зарегестрирован";
        }

        if(optionalFilial.isEmpty()){
            return "Такого филиала не существует";
        }
        Filial filial = optionalFilial.get();
        var user = User.builder()
                .login(employeeRegisterDto.getLogin())
                .password(passwordEncoder.encode(employeeRegisterDto.getPassword()))
                .firstName(employeeRegisterDto.getFirstName())
                .lastName(employeeRegisterDto.getLastName())
                .role(role)
                .birthDate(employeeRegisterDto.getBirthDate())
                .phoneNumber(employeeRegisterDto.getPhoneNumber())
                .filial(filial)
                .enabled(true)
                .build();
        userRepository.save(user);

        return "Новый работник успешно добавлен";
    }

    @Override
    public List<EmployeeList> employeeList() {
        List<User> employees = userRepository.findWaitersAndBaristas();
        return employees.stream()
                .map(employee -> {
                    Filial filial = filialRepository.findById(employee.getFilial().getFilial_id()).orElse(null);
                    String filialName = (filial != null) ? filialRepository.findFilialNameByFilialId(filial.getFilial_id()).orElse(null) : null;
                    return new EmployeeList(
                            employee.getUser_id(),
                            employee.getFirstName(),
                            employee.getRole(),
                            filialName,
                            employee.getPhoneNumber()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public SingleEmployeeDto getEmployeeDetails(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Filial filial = user.getFilial();
            String filialName = (filial != null) ? filialRepository.findFilialNameByFilialId(filial.getFilial_id()).orElse(null) : null;

            return SingleEmployeeDto.builder()
                    .login(user.getLogin())
                    .password(user.getPassword())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .birthDate(user.getBirthDate())
                    .phoneNumber(user.getPhoneNumber())
                    .filial_id(user.getFilial().getFilial_id())
                    .build();
        } else {

            return null;
        }
    }


//    @Override
//    public UserFullDto getSingleUserByLogin(String login) {
//        Optional<User> user = userRepository.findByLogin(login);
//        if (user.isPresent()){
//            User userEntity = user.get();
//            return new UserFullDto(
//                    userEntity.getUser_id(),
//                    userEntity.getFirstName(),
//                    userEntity.getLastName(),
//                    userEntity.getLogin(),
//                    userEntity.getEmail(),
//                    userEntity.getAvatar(),
//                    userEntity.getPhoneNumber(),
//                    userEntity.getBirthDate()
//            );
//        }
//        throw new RuntimeException("User is not find " + login);
//    }



}
