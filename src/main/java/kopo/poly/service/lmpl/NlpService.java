package kopo.poly.service.lmpl;

import kopo.poly.dto.NlpDTO;
import kopo.poly.service.INlpService;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NlpService implements INlpService {

    // Komoran 은 형태소 분석을 위한 라이브러리
    // Komoran의 매개변수는 학습용 데이터인데, 그 크기를 최대 크기로 잡았다.
    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);  // 학습용 데이터는 가장 큰 파일 사용


    //형태소를 추출하는 기능.
    @Override
    public NlpDTO getPlainText(String text) {

        //this.getClass().getName() ==> 현재 클래스의 이름 반환
        log.info(this.getClass().getName() + ".getPlainText Start!");

        // Komoran 라이브러리가 제공하는 객체
        // komoran 객체를 사용하여 text 변수에 저장된 한국어 텍스트를 형태소 분석하여
        // 그 결과를 komoranResult 변수에 저장하는 역할
        // analyze() 메서드는 주어진 텍스트를 인자로 받아 형태소 분석을 수행한다.
        KomoranResult komoranResult = komoran.analyze(text);

        //komoranResult 가 제공하는 객체
        //형태소 분석 결과로부터 원본 텍스트를 추출하여 문자열로 반환한다.
        String result = komoranResult.getPlainText();

        NlpDTO rDTO = new NlpDTO();
        rDTO.setResult(result);
        
        //this.getClass().getName() ==> 현재 클래스의 이름 반환
        log.info(this.getClass().getName() + ".getPlainText End!");

        return rDTO;
    }

    // 명사를 추출하는 기능
    @Override
    public NlpDTO getNouns(String text) {

        log.info(this.getClass().getName() + ".getNouns Start !");

        KomoranResult komoranResult = komoran.analyze(text);    // 인식된 문자열 분석 결과

        List<String> nouns = komoranResult.getNouns();  //NNG, NNP 품사만 추출

        NlpDTO rDTO = new NlpDTO();
        rDTO.setNouns(nouns);

        log.info(this.getClass().getName() + ".getNouns End !");

        return rDTO;
    }
}
