package com.service.kivatos.controllers;


import com.service.kivatos.models.*;
import com.service.kivatos.payload.request.LoginRequest;
import com.service.kivatos.payload.request.SignupRequest;
import com.service.kivatos.payload.response.JwtResponse;
import com.service.kivatos.payload.response.MessageResponse;
import com.service.kivatos.repository.NasabahRepository;
import com.service.kivatos.repository.RekeningRepository;
import com.service.kivatos.repository.RoleRepository;
import com.service.kivatos.repository.UserRepository;
import com.service.kivatos.security.jwt.JwtUtils;
import com.service.kivatos.security.services.UserDetailsImpl;
import com.service.kivatos.services.EwalletService;
import com.service.kivatos.services.impl.EwalletServiceImpl;
import com.service.kivatos.utils.enumeration.EwalletType;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorities Controller", description = "API endpoints for managing authorities")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    NasabahRepository nasabahRepository;
    @Autowired
    RekeningRepository rekeningRepository;
    @Autowired
    EwalletService ewalletService;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Value("${kivatos.app.jwtSecret}")
    private String jwtSecret;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        Instant expirationDate = jwtUtils.getExpirationDate(jwt);
        ZoneId zoneId = ZoneId.of("Asia/Jakarta");
        LocalDateTime expiredToken = LocalDateTime.ofInstant(expirationDate, zoneId);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Mengambil user dari database berdasarkan email
//        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userDetails.getEmail()));
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userDetails.getEmail()));


        // Update token user
        user.setAccessToken(jwt);
        user.setExpiredToken(expiredToken);
        userRepository.save(user);

        System.out.println(userDetails.getPhoneNumber());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPhoneNumber(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        Nasabah nasabah = new Nasabah();
        nasabah.setUser(user);
        nasabah.setNamaLengkap(signUpRequest.getFullName());
        nasabah.setTanggalLahir(signUpRequest.getDateOfBirth());
        nasabah.setAlamat(signUpRequest.getAddress());
        nasabah.setCreatedAt(LocalDateTime.now());
        nasabah.setUpdatedAt(LocalDateTime.now());
        nasabahRepository.save(nasabah);

        Rekening rekening = new Rekening();
        rekening.setNasabah(nasabah);
        rekening.setJenisRekening("Tabungan");
        rekening.setSaldo(BigDecimal.ZERO);
        rekening.setCreatedAt(LocalDateTime.now());
        rekening.setUpdatedAt(LocalDateTime.now());
        rekeningRepository.save(rekening);

        List<Ewallet> ewalletList = new ArrayList<>();
        for (EwalletType ewalletType : EwalletType.values()) {
            Ewallet ewallet = new Ewallet();
            ewallet.setNasabah(nasabah);
            ewallet.setJenisEwallet(ewalletType.getEwalletType());
            ewallet.setSaldo(BigDecimal.ZERO);
            ewallet.setBiayaAdmin(ewalletType.getPrice());
            ewallet.setCreatedAt(LocalDateTime.now());
            ewallet.setUpdatedAt(LocalDateTime.now());
            ewallet.setId(nasabah.getId());
            ewalletList.add(ewallet);
        }
        ewalletService.saveAllEwallet(ewalletList);



        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
