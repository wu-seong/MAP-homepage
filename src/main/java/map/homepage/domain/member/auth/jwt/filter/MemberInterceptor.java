package map.homepage.domain.member.auth.jwt.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.member.auth.jwt.service.JwtUtil;
import map.homepage.domain.member.service.MemberQueryService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
public class MemberInterceptor implements HandlerInterceptor {
    private final MemberQueryService memberQueryService;
    private final JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 토큰 파싱해서 id 얻어오기
        String accessToken = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        try{
            // 얻은 아이디로 유저 조회하기
            String memberId = jwtUtil.getMemberId(accessToken);
            Member member = memberQueryService.getMemberById(Long.valueOf(memberId));

            // 조회한 유저 ThreadLocal에 저장하기

            MemberContext.setMember(member);
            return true;
        }
        catch (IllegalArgumentException exception){
            // jwt 필터에서 검증된 토큰이라 예외 안나겠지만 없지만 혹시 모르니
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 요청 작업 끝나면 ThreadLocal 자원 해제
        MemberContext.clearMember();
    }
}
