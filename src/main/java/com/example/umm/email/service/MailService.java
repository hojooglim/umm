package com.example.umm.email.service;


import com.example.umm.email.dto.CodeDto;
import com.example.umm.email.entity.Mail;
import com.example.umm.email.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailRepository mailRepository;
    private final JavaMailSender emailSender;

    private String ePw; // 인증번호


    // 메일 내용 작성
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
//		System.out.println("보내는 대상 : " + to);
//		System.out.println("인증 번호 : " + ePw);

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);// 보내는 대상
        message.setSubject("회원가입 이메일 인증");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> umm 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("hojooglim@naver.com", "hojooglim"));// 보내는 사람

        return message;
    }

    // 랜덤 인증 코드 전송
    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
    @Transactional
    public String sendSimpleMessage(String to) throws Exception {

        //이메일 중복이 없으면 실행
        if(!mailRepository.existsByEmail(to)){
            ePw = createKey(); // 랜덤 인증번호 생성
            Mail mail = new Mail(to, ePw);

            // TODO Auto-generated method stub
            MimeMessage message = createMessage(to); // 메일 발송

            try {// 예외처리
                emailSender.send(message);
            } catch (MailException es) {
                es.printStackTrace();
                throw new IllegalArgumentException();
            }
            mailRepository.save(mail);
            return "인증번호가 전송되었습니다."+ePw;
        }

        return "전송된 인증코드를 입력해주세요"; // 메일로 보냈던 인증 코드를 서버로 반환
    }



    public String compareCode(CodeDto codeDto){
        Mail mail = mailRepository.findByEmail(codeDto.getEmail()).orElse(null);

        if(!codeDto.getCode().equals(mail.getEmail_code())){
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
        System.out.println("인증완료");
        return "인증되었습니다.";
    };
}
