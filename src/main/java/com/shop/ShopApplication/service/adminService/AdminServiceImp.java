package com.shop.ShopApplication.service.adminService;
import com.shop.ShopApplication.dto.adminDTO.AdminLoginDto;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeList;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeRegisterDto;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeUpdateDto;
import com.shop.ShopApplication.dto.employeeDTO.SingleEmployeeDto;
import com.shop.ShopApplication.jwt.JwtService;
import com.shop.ShopApplication.entity.Filial;
import com.shop.ShopApplication.entity.enums.Role;
import com.shop.ShopApplication.entity.WorkingTime;
import com.shop.ShopApplication.repo.FilialRepository;
import com.shop.ShopApplication.repo.WorkingTimeRepository;
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

import java.text.ParseException;
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
    private final WorkingTimeRepository workingTimeRepository;


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
    public String saveEmployee(EmployeeRegisterDto employeeRegisterDto) throws ParseException {
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
        WorkingTime workingTime = WorkingTime.builder()
                .monday(employeeRegisterDto.getWorkingTimeDto().getMonday())
                .tuesday(employeeRegisterDto.getWorkingTimeDto().getTuesday())
                .wednesday(employeeRegisterDto.getWorkingTimeDto().getWednesday())
                .thursday(employeeRegisterDto.getWorkingTimeDto().getThursday())
                .friday(employeeRegisterDto.getWorkingTimeDto().getFriday())
                .saturday(employeeRegisterDto.getWorkingTimeDto().getSaturday())
                .sunday(employeeRegisterDto.getWorkingTimeDto().getSunday())
                .build();

        Filial filial = optionalFilial.get();
        var user = User.builder()
                .login(employeeRegisterDto.getLogin())
                .password(passwordEncoder.encode(employeeRegisterDto.getPassword()))
                .firstName(employeeRegisterDto.getFirstName())
                .lastName(employeeRegisterDto.getLastName())
                .role(role)
                .birthDate(employeeRegisterDto.getBirthDate())
                .phoneNumber(employeeRegisterDto.getPhoneNumber())
                .workingTime(workingTime)
                .filial(filial)
                .enabled(true)
                .build();

        workingTime.setUser(user);
        userRepository.save(user);

        return "Новый работник успешно добавлен";
    }




    @Override
    public String updateEmployee(EmployeeUpdateDto employeeUpdateDto) {
        Optional<User> optionalUser = userRepository.findById(employeeUpdateDto.getUser_id());

        if (optionalUser.isEmpty()) {
            return "Пользователь с указанным идентификатором не найден";
        }

        User user = optionalUser.get();

        if (!user.getUser_id().equals(employeeUpdateDto.getUser_id())) {
            return "Указанный идентификатор пользователя не совпадает с идентификатором пользователя для обновления.";
        }

        if (employeeUpdateDto.getPhoneNumber() != null) {
            Optional<User> userWithNewPhoneNumber = userRepository.findByPhoneNumber(employeeUpdateDto.getPhoneNumber());
            if (userWithNewPhoneNumber.isPresent() && !userWithNewPhoneNumber.get().getUser_id().equals(user.getUser_id())) {
                return "Новый номер телефона уже занят другим пользователем.";
            }
        }

        if (employeeUpdateDto.getLogin() != null) {
            user.setLogin(employeeUpdateDto.getLogin());
        }
        if (employeeUpdateDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(employeeUpdateDto.getPassword()));
        }
        if (employeeUpdateDto.getFirstName() != null) {
            user.setFirstName(employeeUpdateDto.getFirstName());
        }
        if (employeeUpdateDto.getLastName() != null) {
            user.setLastName(employeeUpdateDto.getLastName());
        }
        if (employeeUpdateDto.getRole() != null) {
            user.setRole(employeeUpdateDto.getRole());
        }
        if (employeeUpdateDto.getBirthDate() != null) {
            user.setBirthDate(employeeUpdateDto.getBirthDate());
        }
        if (employeeUpdateDto.getPhoneNumber() != null) {
            user.setPhoneNumber(employeeUpdateDto.getPhoneNumber());
        }

        if (employeeUpdateDto.getFilial_id() != null) {
            Optional<Filial> optionalFilial = filialRepository.findById(employeeUpdateDto.getFilial_id());
            if (optionalFilial.isPresent()) {
                Filial filial = optionalFilial.get();
                user.setFilial(filial);
            } else {
                return "Такого филиала не существует";
            }
        }

        userRepository.save(user);
        return "Информация о работнике успешно обновлена";
    }

    @Override
    public String deleteEmployee(Long user_id) {
        Optional<User> optionalUser = userRepository.findById(user_id);
        if(optionalUser.isEmpty()){
            return "Такого работника не существует";
        }
        User user = optionalUser.get();
        user.setEnabled(false);
        return "Работник успешно удален";
    }

    @Override
    public List<EmployeeList> employeeList() {
        List<User> employees = userRepository.findWaitersAndBaristas();
        return employees.stream()
                .filter(User::isEnabled)
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
        if (optionalUser.isEmpty()) {
            return null;
        }
            User user = optionalUser.get();
            if(!user.isEnabled()){
                return null;
            }
            Filial filial = user.getFilial();
            String filialName = (filial != null) ? filialRepository.findFilialNameByFilialId(filial.getFilial_id()).orElse(null) : null;

        Optional<WorkingTime> optionalWorkingTime = workingTimeRepository.findByUser(user.getUser_id());
        WorkingTime workingTime = optionalWorkingTime.orElse(null);


            return SingleEmployeeDto.builder()
                    .login(user.getLogin())
                    .password(user.getPassword())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .birthDate(user.getBirthDate())
                    .phoneNumber(user.getPhoneNumber())
                    .filialName(filialName)
                    .workingTime(workingTime)
                    .build();
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




