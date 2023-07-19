package com.example.umm.umm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//해당 추상 클래스를 상속받을떄
//created,modifiedadate같은
//추상클래서에서 선언된 멤버변수를 colume으로 인식해줌.
//걍 상속받으면 저거 칼럼이 상속됨.
//추상클래스 아니어도 상관없음. 근데 굳이 객체 엔티티로 쓸일이 없어서.
@EntityListeners(AuditingEntityListener.class)
//해당 클래스에 autting기능을 준다.
//그리고 memoapplication에 @enablejpaauditing써주어야함.
//반환 dto에 데이터 추가해주는것도 잊지마세용
public abstract class Timestamped {

    @CreatedDate
    //객체가 생성되서 저장될떄 시간값이 자동으로 저장됨, 최조값만.
    @Column(updatable = false)
    //그 이후에는 없데이트가 안되게 주는 옵션.
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    //조회한 앤티티 값을 변경할때 변경한 시간이 저장됨. 당연히 처음값도 저장이됨.
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    //자바의 data,캘랜더 같은 타입을 맵핑할 때 사용함.
    private LocalDateTime modifiedAt;
}