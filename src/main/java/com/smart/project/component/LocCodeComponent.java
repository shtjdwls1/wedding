package com.smart.project.component;

import com.smart.project.component.data.CodeObject;
import com.smart.project.component.data.LocCodeObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**********************************************************************************************
 * @FileName  : LocCodeComponent.java
 * @Date      :  2019-01-28
 * @작성자      : 김성훈
 * @설명       : locCode.json 관련 컴포넌트
 **********************************************************************************************/
@Slf4j
@Component
public class LocCodeComponent extends CodeComponent {

	private final String LOC_L_PREFIX = "m002";
	private final String LOC_M_PREFIX = "m003";
	private final String LOC_S_PREFIX = "m004";
	private final String LOC_CITY_LOCATION = "cityLocation";

	@Override
	String jsonPath() {
		return "classpath:json/locCode.json";
	}

	/**********************************************************************************************
	 * @Method 설명  : 지정된 코드값이 속한 분류의 한단계 하위의 전체 리스트
	 *                ex) 공백이     파라미터로 오면 대분류 codeList ( i, b, h, k ...)  =>  이 케이스는 파라미터 없이 호출해서 사용 가능
	 *                    i가       파라미터로 오면 i(서울)의 하위 중분류인 m003i 의 codeList  ( i01, i02, i03, i04 ...)
	 *                    i01이     파라미터로 오면 i01(강남구)의 하위 소분류인 m004i01의 codeList ( i01001, i01002, i01003 ...)
	 *                    i01001이  파라미터로 오면 해당 소분류가 속한 m004i01의 codeList ( i01001, i01002, i01003 ...)  =>  이런 요청은 아마 거의 없을듯 (없어야 함)
	 * @작성일   :  2019-01-28
	 * @작성자   : 김성훈s
	 * @변경이력  :
	 **********************************************************************************************/
	public List<CodeObject.Code> getListByCode(String paramCode) {

		List<CodeObject.Code> dummyResult = new ArrayList<>();

		// paramCode에 해당하는 codeObject 객체 검색
		CodeObject codeObject = null;

		if(StringUtils.isEmpty(paramCode)) {
			// 대분류 codeList
			codeObject = codeMap.get(LOC_L_PREFIX);
		} if(paramCode.length() == 1) {
			// 중분류 codeList
			codeObject = codeMap.get(LOC_M_PREFIX + paramCode);
		} else if (paramCode.length() == 3) {
			// 소분류 codeList
			codeObject = codeMap.get(LOC_S_PREFIX + paramCode);
		} else if (paramCode.length() == 6) {
			// 소분류 codeList
			codeObject = codeMap.get(LOC_S_PREFIX + paramCode.substring(0, 3));
		}

		if(codeObject == null) { return dummyResult; }

		return codeObject.getCodeList();
	}

	/** 파라미터 없이 호출해서 대분류 리스트 얻도록 오버로드 */
	public List<CodeObject.Code> getListByCode() {
		return this.getListByCode("");
	}


	/**********************************************************************************************
	 * @Method 설명  : 지정된 지역 코드값에 해당하는 Code객체 반환. (코드명, 한글명, 위도, 경도 등등..)
	 *                대/중/소 분류 가리지 않고 받은 코드 값에 해당하는 객체 반환함.
	 * @작성일   :  2019-01-28
	 * @작성자   : 김성훈
	 * @변경이력  :
	 **********************************************************************************************/
	public CodeObject.Code getOneByCode(String paramCode) {

		CodeObject.Code dummyResult = new CodeObject().new Code();
		if(StringUtils.isEmpty(paramCode)) { return dummyResult; }

		// paramCode에 해당하는 codeObject 객체 검색
		CodeObject codeObject = null;

		if(paramCode.length() == 1) {
			// 대분류에서 검색
			codeObject = codeMap.get(LOC_L_PREFIX);
		} else if (paramCode.length() == 3) {
			// 중분류에서 검색
			codeObject = codeMap.get(LOC_M_PREFIX + paramCode.substring(0, 1));
		} else if (paramCode.length() == 6) {
			// 소분류에서 검색
			codeObject = codeMap.get(LOC_S_PREFIX + paramCode.substring(0, 3));
		}

		if(codeObject == null) { return dummyResult; }

		for (CodeObject.Code code : codeObject.getCodeList()) {
			if(StringUtils.equals(paramCode, code.getCode())) {
				return code;
			}
		}

		return dummyResult;
	}

	/**********************************************************************************************
	 * @Method 설명  : 지번주소(시도, 시군구, 읍면동) 를 받아서 대,중,소 분류 코드로 변환해 반환함
	 * @작성일   :  2019-02-01
	 * @작성자   : 김성훈
	 * @변경이력  :
	 **********************************************************************************************/
	public LocCodeObject parseAddressToCode(String sido, String sigungu, String bname) {

		sido = StringUtils.isEmpty(sido) ? "" : sido;
		sigungu = StringUtils.isEmpty(sigungu) ? "" : sigungu;
		bname = StringUtils.isEmpty(bname) ? "" : bname;

		// 세종 예외 (세종시 -> 세종)
		if(sido.contains("세종") && !sigungu.contains("세종")) {
			bname = sigungu;
			sigungu = "세종";
			sido = "세종";
		}
		sido = sido.replace("세종특별자치시", "세종");
		sigungu = sigungu.replace("세종시", "세종");

		// 제주특별자치도 예외 (제주특별자치도 -> 제주)
		if(sido.contains("제주")) {
			sido = "제주";
		}

		// 대분류가 4글자면 XX남도 or XX북도 임.. 이걸 -> X남 / X북 으로 변경
		if(sido.length() == 4) {
			sido = sido.substring(0,1) + sido.substring(2,3);
		}

		// 최종적으로 대분류는 2글자뿐이므로(json기준) 2글자로 자름
		sido = sido.substring(0, 2);

		String regLoc = searchCode(sido, "");
		String regLcode = searchCode(sigungu, regLoc);
		String regLdetail = searchCode(bname, regLcode);

		// 탐색결과 정리
		if(StringUtils.isEmpty(regLoc)) {
			sido = "";
		}
		if(StringUtils.isEmpty(regLcode)) {
			sigungu = "";
		}
		if(StringUtils.isEmpty(regLdetail)) {
			bname = "";
		}

		// log.info("한글주소로 주소코드값 검색 완료 ==> {}({}), {}({}), {}({})", sido, regLoc, sigungu, regLcode, bname, regLdetail);

		return new LocCodeObject(regLoc, sido, regLcode, sigungu, regLdetail, bname);
	}

	// 주소 탐색
	private String searchCode(String target, String upper) {
		List<CodeObject.Code> codeList = this.getListByCode(upper);
		for (CodeObject.Code code : codeList) {
			if(target.equals(code.getCodeName())) {
				return code.getCode();
			}
		}
		return "";
	}

	/**********************************************************************************************
	 * @Method 설명  : 지역대분류 코드를 받아서 기본 좌표값 반환
	 * @작성일   :  2022-03-28
	 * @작성자   : 박제원
	 * @변경이력  :
	 **********************************************************************************************/
	public CodeObject.Code getBasicCoordinate(String paramCode){
		CodeObject codeObject = codeMap.get(LOC_CITY_LOCATION);
		List<CodeObject.Code> codeList = codeObject.getCodeList();
		for (CodeObject.Code code : codeList) {
			if(code.getCode().equals(paramCode)){
				return code;
			}
		}
		return null;
	}


}
