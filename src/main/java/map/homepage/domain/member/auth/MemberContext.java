package map.homepage.domain.member.auth;

import map.homepage.domain.member.Member;

public class MemberContext {
    private static final ThreadLocal<Member> authenticatedMember = new ThreadLocal<>();

    public static void setMember(Member member){
        authenticatedMember.set(member);
    }
    public static Member getMember(){
        return authenticatedMember.get();
    }
    public static void clearMember(){
        authenticatedMember.remove();
    }
}
