7월 9일 배포 예정 내역
배포 예정 서비스: partnership-ui, partnership-management-ui , partnership-api,  partnership-management-api, parntership-batch 총 5개
배포 사항
partnership-ui
에러 화면 추가(취약점 조치)
partnership-api
에러 시 에러 화면으로 리다이렉트(취약점 조치),
로그 삽입 시 prodId 검증
parntership-batch
system log 삽입 시 카테고리별 로그 추가 삽입,
클릭수 집계 쿼리 수정
partnership-management-api & partnership-management-ui
이름 마스킹 수정 + 사용자 목록, 작업 공지에서 마스킹된 이름 노출,
제휴카드 - 프로모션 링크&문구 /프로모션 QR 이미지 분리(사업부 요청)

7월 17일 배포 예정 내역
배포 예정 서비스: partnership-ui, partnership-management-ui , partnership-api,  partnership-management-api, parntership-batch 총 5개
배포 사항
management ui, management api : 초이스 전용 디바이스 상품 관련 crud 기능 추가
batch: 초이스 디바이스 버튼 관련 클릭수 집계 추가
api: 초이스 디바이스용 상품 조회 기능 추가, 기타 예외 처리 분기 수정
ui: 초이스 디바이스용 상품 조회 화면 추가(옵션에 따라 신청 버튼 2개 or 1개 노출)
초이스 디바이스 요금제별 url 추가
+ 초이스디바이스 관련 DB 배포도 있습니다

7월 24일 배포 예정 내역
배포 예정 서비스: partnership-ui, partnership-management-ui , partnership-api,  partnership-management-api, parntership-batch 총 5개
배포 사항
-초이스 디바이스 버튼 문구 커스텀 기능 추가 및 컬럼 추가에 따른 변경 사항 반영
-초이스 디바이스 페이징 오류 수정
-취약점 조치
개인정보 저장 시 이름, 이메일 마스킹 조치(1번)
개인 정보 마스킹 조치, 내정보조회 -> 마스킹 해제 기능 삭제(5번)
토큰에서 미사용 개인정보 삭제 조치(6번)
엑셀 다운로드 기능 권한 레벨 지정(7번)
파일 확장자 제한을 통한 악성 파일 업로드 취약점 조치(8번)
default 에러 페이지 대신 200 응답(9번)
-------------------------------------------
db 배포: 초이스디바이스 버튼 문구 커스텀 관련 컬럼 추가, 중복 로그인 방지를 위한 토큰 정보 저장 컬럼 추가

