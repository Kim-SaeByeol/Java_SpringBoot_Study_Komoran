package kopo.poly.service;


import kopo.poly.dto.OcrDTO;

public interface IOcrService {

    //인터페이스에서 관리할 변수 생성.
    //인터페이스에서는 기본값으로 final 값이기에 객체들이 상수가 됨.
    // --------------------------------------------

    //modelFile 의 주소값을 상수로써 고정함.
    //tessdata 는 pom에서 다운받은 tess4j를 사용하기 위한 프로그램으로 보면 됨.
    // 기능은 OCR(이미지 광학 인식)
    String modelFile = "C:\\model\\tessdata";

    // 상속을 위한 메소드 생성.
    // OcrDTO 타입의 메소드 getReadforImageText() 이며,
    // OcrService 에서 오버라이딩 하여 원하는 메소드로 만들어 쓸 것임.
    OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception;
}
