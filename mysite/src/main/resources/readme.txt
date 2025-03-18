//비밀번호 암호화
단방향 암호화는 풀기 거의 풀가능
암호화 작업은 대부분 Service 파일 내 메서드 수정만으로 가능
그 전에 암호화 라이브러리 추가 필수=>build.gradle
	1.암호화 라이브러리 BCrtpy 추가 =>build.gradle
	implementation group:'org.mindrot', name:'jbcrypt', version:'0.3m'
	2.Service > insert() 수정
	사용자가 입력한 비밀번호 평문 -> 암호화
	new Member() 생성 후 세터 메서드로 객체 셋팅 전 암호화해서 셋팅
	비밀번호 암호화는? BCrypt 알고리즘 사용: BCrypt는 비밀번호 솔트와 함께 해시하여 60자리 문자열로 생성
	String hashedUserPw = BCrypt.hashpw(memberCreateDTO.getPw(), BCrypt.gensalt());
	3.Service > delete() 수정 (비밀번호 비교 메서드는 모두 수정 필요)
	삭제 시 사용자가 입력한 비번과 암호화된 비번을 비교하는 방법
	기존: userPw.equals(member.getPw())  -> 평문.equals(암호화 비번) 틀림
	결론: BCrypt.checkpw() 메서드 사용
	BCrypt 암호화 문자열-> 같은 비번이라도 랜덤 솔트 사용해서 매번 다른 암호화 문자열 생성
	equals()로 비교하면 항상 일치하지 않음
	
	
//로그인 구현
Login.controller
@RequestMapping("/cookie")
	@GetMapping(value={"","/"})
	@GetMApping("/login")
	@PostMapping("/login")
	@GetMapping("/mypage")
MemberService
	getLoginMemberById()
	login()
MemberRepository
	Optional<Member> findById(String id)
뷰
	login/main.html
	login/login.html