package kopo.poly.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//DTO(Data Transfer Object) ==> "데이터 전달 객체"
//많은 변수를 DTO에 모아 담음으로써 한번에 전달하기 위함.
//수정사항을 한번에 처리할 수 있고, 객체 내 변수명으로 어떠한 데이터가 전달되는지 이해하기 쉬움.
//자바 함수에 정보를 전달 및 전달 받기 위한 목적으로 만드는 객체.


@Getter
@Setter
@Data   //Getter, Setter 의 부모 객체
public class OcrDTO {
    private String filePath;    // 저장된 이미지 파일의 파일 저장 경로

    private String fileName;    // 저장된 이미지 파일 이름

    private String result;      // 저장된 이미지로부터 읽은 글씨

    // Getter, Setter 는 Lombok 프레임워크 에서 제공하는 이노테이션 함수임.
    // 기존에는 아래 코드와 같이 private 변수에 접근하기 위해 get 과 set 변수를 만들어
    // 간접적으로 접근하는 방식을 취하였는데 계속해서 사용하다보니 코드가 길어지고 가독성이 좋지 않아
    // Lombok 프레임워크에서 이노테이션을 통해 Data.Getter 와 Data.Setter 를 제공함.
    // Getter, Setter 를 사용함으로써, 아래 코드를 사용하지 않아도 자동으로 생성이 됨.
    // 이로써, private 변수만 입력하더라도 get 과 set변수가 자동으로 생성되니,
    // 개발자 입장에서 코드가 줄어들고 가독성이 좋아짐.
    /*
    public String getFileName() {
        return fileName;
    }
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }
     */
}

