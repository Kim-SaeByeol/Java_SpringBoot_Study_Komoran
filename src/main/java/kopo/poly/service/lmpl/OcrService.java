package kopo.poly.service.lmpl;

/*
 * servic 패키지는 SpringBoot Framework 에서 중요 핵심 로직을 작성하는 영역임.
 * @service 이노테이션을 추가함으로써, 클래스를 서비스 빈(Service Bean)으로 등록하는데 사용함.
 * 서비스 빈(Service Bean)은 하나의 규칙이며, Service Bean으로 지정된 클래스는
 * 기본 생성자, 멤버변수, getter, setter 메서드를 가지게 됨.
 *
 * 기본 생성자는 생성자를 지정하지 않았을 경우 자바 컴파일러가 자동으로 만드는 생성자.
 * 생성자는 클래스 이름을 띄고 있으며 다음 예시와 같음. public class Ex { pcucblic Ex() { }}
 *
 * 멤버변수는 메인 클래스 밖에서 생성된 변수로, 기본적으로 Heap 영역에 속하며, static 키워드를 통해 Stack 영역에 속하기도 함.
 *
 * getter 와 setter 를 사용하기 위해서는 Lombok 라이브러리를 사용해야하지만,
 * @service 로 지정된 클래스는 Lombok 없이 getter 와 setter 를 사용할 수 있음.
 * 또한, Service를 지정하면 SpringBootFrameWork 가 실행될때 함께 메모리에 올라감.
 * 즉슨 Service 는 메모리 관리를 개발자가 할 필요가 없어짐.
 */

import kopo.poly.dto.OcrDTO;
import kopo.poly.service.IOcrService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OcrService implements IOcrService {
    /*
     * implements 는 인터페이스의 객체들을 사용하기 위해 쓰는 것임.
     * extends 와 implements 는 상당히 비슷하면서 다른데,
     * extends 는 class - class / interface - interface.
     * 즉 서로 상속을 하는 의미이며,
     * implements 는 interface - class 만 가능하다.
     * implements 는 class 에서 interface에 내용을 사용할 수 있게 해주는 역할을 한다.
     */


    /*
    * 이미지 파일로부터 문자 읽어 오기.
    * @param pDTO 이미지 파일 정보
    * @return pDTO 이미지로부터 읽은 문자열
     */


    @Override
    public OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception {
        //log 는 해당 클래스가 @Slf4j 를 사용함에 따라 쓰는 것이며,
        //log 는 print 와 같은 역할을 함.
        // this.getClass().getName() 는 현재 객체의 클래스 이름을 반환하는 코드이다.
        // this.getClass() 는 현재 객체의 클래스를 나타내며
        // getclass().getName()는 Class 객체의 메소드로 클래스의 이름을 문자열로 반환합니다.
        log.info(this.getClass().getName() + "getReadforImageText start!");

        //ClassPathResource는 스프링 프레임워크에서 제공하는 클래스
        // 클래스 패스에 있는 리소스를 로드하는 데 사용되며, 생성자에게 로드할 리소스의 경로와 이름을 전달한다.
        // 로드할 리소스의 경로는 생성자의 매개변수가 해당되며,
        //pDTO.getFilePath()와 pDTO.getFileName() 메서드의 반환값을 /로 연결하여 전체 경로를 만든 후,
        //이를 ClassPathResource의 생성자에게 전달한다.
        // "/" 는 연결하는 의미로 pDTO.getFilePath()가 "resources"이고 pDTO.getFileName()이 "data.txt"라면,
        // pDTO.getFilePath() + "/" + pDTO.getFileName()은 "resources/data.txt"를 생성하게 된다.
        ClassPathResource resource = new ClassPathResource(pDTO.getFilePath() + "/" + pDTO.getFileName());

        //pom 에서 다운로드 받은 자바용 Tess4J Tesseract.
        //해당 라이브러리에 있는 ITesseract 인터페이스를 구현하는 Tesseract 클래스의 인스턴스를 생성했다.
        //instance 변수를 통해 Tesseract 클래스의 생성자를 구현할 수 있으며,
        //Tesseract 클래스는 OCR(광학 문자 인식)작업을 구현하므로, instance 객체를 통해 이미지나 문서에서 텍스트를 추출할 수 있다.
        ITesseract instance = new Tesseract();

        //Tesseract OCR 엔진에서 필요로 하는 모델 파일의 경로를 가리키는 것으로
        //OCR 엔진을 사용할 때 해당 Datapath, 데이터주소 값으로 들어가 수행하게 된다.
        instance.setDatapath(IOcrService.modelFile);

        //OCR 엔진에서 사용할 학습 언어데이터 이다.
        //해당 파일은     String modelFile = "C:\\model\\tessdata"; 을 통해 주소가 정의 되었으며,
        //OCR 엔진은 주소로 들어가 학습 데이터 언어를 kor 인 언어로 설정할 것이다.
        //기본 값은 영어(eng) 이다.
        instance.setLanguage("kor");

        // 이미지 파일로부터 텍스트 읽기
        //매개변수인 resource.getFile()는 내가 인식하고자 하는 파일을 뜻하며,
        //doOCR 은 그 파일에서 문자를 추출하는 수행을 하고 리턴 값으로 문자열을 반환한다.
        //해당 문자열은 result 에 저장된다.
        String result = instance.doOCR(resource.getFile());

        //현재 pDTO는 OcrDTO 클래스의 객체를 참조하는 변수로 사용되고 있다.
        // 즉슨, pDTO.setResult() 메소드는 OcrDTO 에 존재하는 메소드이며, 참조 중이다.
        // result 값을 OcrDTO 객체 내부에서 result 로 저장되어 필요할때 다른 작업을 수행할 것이다.
        // 또한, 현재 result 값은 이미지에서 추출한 문자열을 가지고 있다.
        pDTO.setResult(result);

        log.info(this.getClass().getName() + ".getReadforImageText End!");

        //현재 해당 OcrService 클래스는 인터페이스 IOcrService을 참조하고 있다.
        //OcrDTO getReadforImageText 는 IOcrService를 참조하여 오버라이딩 하고 있는 메소드이며,
        //OcrDTO는 데이터타입으로 선언된 매개변수이며,
        // getReadforImageText() 메서드는 OcrDTO 타입의 매개변수 pDTO를 받아서 메서드를 실행한 후 return 값으로 pDTO 를 받아
        // 해당 결과 값을 OcrDTO 객체에게 반환하여 IOcrService(인터페이스) -> OcrService(Class) -> Main Class 인 2회 상속을 하게 됨.
        return pDTO;
    }
}
