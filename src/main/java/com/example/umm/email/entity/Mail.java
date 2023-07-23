package com.example.umm.email.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String email_code;

    public Mail(String to, String ePw) {
        this.email=to;
        this.email_code=ePw;
    }

    public void update(Mail mail) {
        this.email=mail.getEmail();
        this.email_code=mail.getEmail_code();
    }
}
