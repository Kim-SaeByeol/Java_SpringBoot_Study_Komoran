package kopo.poly;

import kopo.poly.dto.NlpDTO;
import kopo.poly.dto.OcrDTO;
import kopo.poly.service.INlpService;
import kopo.poly.service.IOcrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class JavaSpringBootStudyKomoranApplication implements CommandLineRunner {

    private final IOcrService ocrService;   // 이미지 인식
    
    private final INlpService nlpService;   // 자연어 처리

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringBootStudyKomoranApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("자바 프로그래밍 시작!");

        String filePath = "image"; //문자열로 추출할 이미지가 있는 파일 경로
        String fileName = "news01.jpg"; //추출할 이미지 명
        //다른 이미지를 추출하고 싶다면 혹은 중복으로 추출하고 싶다면
        //해당 변수에 이름을 추가하거나 수정하면 됨.

        // 파라미터의 값을 OcrDTO 에 전달하기 위해 pDTO 객체를 생성함.
        // 파라미터 값을 넘겨주는 경우 변수 이름 앞에 p 를 붙임.
        OcrDTO pDTO = new OcrDTO(); // OcrService의 함수에 정보를 전달할 DTO를 메모리에 올리기.

        //OcrDTO에 생성된 변수에 값을 넣어줌.
        pDTO.setFilePath(filePath);
        pDTO.setFileName(fileName);

        //rDTO 는 result 값을 넣는 변수임.
        //보통 result 값을 넣는 변수 이름 앞에는 r 을 붙임.
        // rDTO 는 getReadforImageText() 메소드의 매개변수 pDTO를 result 값으로 받게 됨.
        // 해당 result 값은 ocrService에서 이미지 추출을 위해 기능한 문자열 추출한 결과물임.
        OcrDTO rDTO = ocrService.getReadforImageText(pDTO);

        //OctDTO 에 저장된 result 변수의 값을 불러옴.
        //이미지 추출을 한 문자열의 값이 rDTO 를 통해 전달되었음.
        String result = rDTO.getResult();   //인식된 문자열

        /*
         * 정리)
         * pDTO 는 OcrDTO 를 불러오기 위한 객체임.
         * pDTO 는 paramiter 값을 OcrDTO에 전달하기 위해 생성함
         * pDTO 는 filePath 와 fileName을 OcrDTO 에 전달하였고,
         * getReadforImageText() 객체의 파라미터 값(result 값) 을 받아
         * OcrDTO 에 전달하였음.
         *
         * rDTO 는 OcrDTO 를 불러오기 위한 또 다른 객체임.
         * OcrDTO 가 받은 result 값을 rDTO 에 전달받기 위해 생성하였으며,
         * 생성되자마자 OcrDTO로 부터 result 값을 받아
         * 해당 값을 String result 에 저장하여
         * 해당 클래스에서 사용할 수 있도록 하였음.
         */

        log.info("인식된 문자열");
        //result 값은 OcrDTO에서 받아온 rDTO의 값임.
//        log.info(rDTO.getResult());
        log.info(result);

        log.info("자바 프로그래밍 종료!");

        /*
         * 정리)
         * 이미지 혹은 문서에서 문자열을 추출할 수 있는 기능을 하는 자바용 Tess4J Tesseract
         * 해당 라이브러리를 사용하기 위해 pom에서 marven 라이브러리를 다운로드 받아 사용하였으며,
         * 이미지추출을 위한 클래스를 DTO 클래스와 Service 클래스, 메인 클래스로 나누었고
         * IService 인터페이스를 만들어 혹시나 모를 기능들을 다중상속 받을 수 있도록 하였음.
         * DTO 에는 자주 사용되는 변수들을 저장할 용도로 만들어 재사용성을 높였고
         * Service 에는 추출하는 기능을 기술하여 메인 클래스에서 코드 가독성을 높임.
         * 메인 클래스에서는 CommandLineRunner 인터페이스의 run() 를 참조 받아 실행할 환경을 만들었으며,
         * DTO 와 Service 에서 필요한 부분을 객체로 생성하여 받아와 짧은 코드로 가독성 좋게 프로그램을 실행함.
         */

        log.info("-------------------------------------------------");

        NlpDTO nlpDTO = nlpService.getPlainText(result);
        log.info("형태소별 품사 분석 결과 : " + nlpDTO.getResult());

        //명사 추출 결과
        nlpDTO = nlpService.getNouns(result);

        List<String> nouns = nlpDTO.getNouns(); //명사 추출 결과를 nouns 변수에 저장

        Set<String> distinct = new HashSet<>(nouns);    //중복 제거

        log.info("중복 제거 수행 전 단어 수 : " + nouns.size());

        log.info("중복 제거 수행 후 단어 수 : " + distinct.size());

        //단어, 빈도수를 Map 구조로 저장하기 위해 객체 생성
        // Map 구조의 키는 중복 불가능
        Map<String, Integer> rMap = new HashMap<>();

        //중복 제거된 전체 단어마다 반복
        for(String s : distinct){
            int count = Collections.frequency(nouns, s);    //중복 제거 전 단어 빈도수
            rMap.put(s, count); //단어, 빈도수를 Map 구조로 저장

            log.info(s + " : " + count);
        }
        //rMap.entrySet() 은 키와 값을 쌍으로 나타내는 객체들의 집합을 반환한다.
        // sortResult 는 Map의 결과물을 LinkedList 로 전환하여 받는다.
        // 이는 중복을 제거하기 위함이며, Map 은 LinkedList 로 전환이 가능하기에
        // LinkedList로 전환을 하여 이후 정렬을 할 수 있도록 하였다.
        List<Map.Entry<String, Integer>> sortResult = new LinkedList<>(rMap.entrySet());

        // sortResult 리스트를 내림차순으로 정렬하는 코드.
        // Collections.sort() 메서드는 리스트를 정렬하는 메서드이며, compareTo() 메서드의 반환 값을 기준으로 정렬을 수행한다.
        // o2 와 o1 을 비교하는 것이기에 내림차순이며, 오름차순으로 하고 싶을 경우 o1 과 o2 순서로 비교하면 된더.
        // 람다 표현식 ((o1, o2) -> o2.getValue().compareTo(o1.getValue()))는 Comparator 인터페이스의 compare() 메서드를 구현하는 것.
        // o1 과 o2 의 값을 compareTo() 메서드를 통해 비교하고 o2.getValue().compareTo(o1.getValue()) 코드를 통해 값을 기준으로 내림차순으로 정렬한다.
        // sortResult 는 LinkedList로써 주소를 통해 비교가 가능하기에 o1 과 o2를 통한 비교가 가능하다.
        Collections.sort(sortResult, ((o1, o2) -> o2.getValue().compareTo(o1.getValue())));

        log.info("가장 많이 사용된 단어는? : " + sortResult);

        log.info("자바 프로그래밍 종료 !");

    }
}

