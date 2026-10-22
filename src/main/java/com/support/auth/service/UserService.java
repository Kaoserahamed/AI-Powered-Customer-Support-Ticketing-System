package com.support.auth.service;

import com.support.auth.entity.User;
import com.support.auth.entity.User.Role;
import com.support.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class UserService implements UserDetailsService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String BASE32 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }
        return user;
    }

    @Transactional
    public User register(String fullName, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }
        User user = new User(email, passwordEncoder.encode(password), fullName, Role.CUSTOMER);
        return userRepository.save(user);
    }

    @Transactional
    public User registerAdmin(String fullName, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }
        User user = new User(email, passwordEncoder.encode(password), fullName, Role.ADMIN);
        return userRepository.save(user);
    }

    public boolean authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }

    public String generateToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }
        return jwtService.generateToken(email, user.getRole().name());
    }

    @Transactional
    public String enableMfa(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }
        String secret = generateBase32Secret();
        user.setMfaSecret(secret);
        user.setMfaEnabled(true);
        userRepository.save(user);
        return secret;
    }

    public boolean verifyMfa(String email, String code) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.isMfaEnabled() || user.getMfaSecret() == null) {
            return false;
        }
        return validateTotpCode(user.getMfaSecret(), code);
    }

    public String generateBase32Secret() {
        byte[] randomBytes = new byte[20];
        RANDOM.nextBytes(randomBytes);
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int bits = 0;
        for (byte b : randomBytes) {
            index = (index << 8) | (b & 0xFF);
            bits += 8;
            while (bits >= 5) {
                sb.append(BASE32.charAt((index >>> (bits - 5)) & 0x1F));
                bits -= 5;
            }
        }
        if (bits > 0) {
            sb.append(BASE32.charAt((index << (5 - bits)) & 0x1F));
        }
        return sb.toString();
    }

    private boolean validateTotpCode(String secret, String code) {
        try {
            byte[] key = base32Decode(secret);
            long timeStep = System.currentTimeMillis() / 30000L;
            for (long offset = -1; offset <= 1; offset++) {
                long hash = hmacSha256Truncated(key, timeStep + offset);
                if (String.format("%06d", hash).equals(code)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private long hmacSha256Truncated(byte[] key, long time) throws Exception {
        byte[] data = longToBytes(time);
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        byte[] hmac = mac.doFinal(data);
        int offset = hmac[hmac.length - 1] & 0x0F;
        long binary = ((hmac[offset] & 0x7F) << 24)
                | ((hmac[offset + 1] & 0xFF) << 16)
                | ((hmac[offset + 2] & 0xFF) << 8)
                | (hmac[offset + 3] & 0xFF);
        return binary % 1000000;
    }

    private byte[] base32Decode(String secret) {
        StringBuilder bits = new StringBuilder();
        for (char c : secret.toCharArray()) {
            int val = BASE32.indexOf(Character.toUpperCase(c));
            if (val < 0) continue;
            bits.append(String.format("%5s", Integer.toBinaryString(val)).replace(' ', '0'));
        }
        byte[] bytes = new byte[bits.length() / 8];
        for (int i = 0; i < bytes.length; i++) {
            String byteStr = bits.substring(i * 8, i * 8 + 8);
            bytes[i] = (byte) Integer.parseInt(byteStr, 2);
        }
        return bytes;
    }

    private byte[] longToBytes(long value) {
        byte[] bytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bytes[i] = (byte) (value & 0xFF);
            value >>>= 8;
        }
        return bytes;
    }
}
