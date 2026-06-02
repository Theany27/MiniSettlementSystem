package net.javaguides.MiniSettlement.Models;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User {
    private int id;
    private String username;
    private String password;
    private String confirmPass;
    private String role;
    private LocalDateTime expiredPassword;
    private int FirstLog;
}
