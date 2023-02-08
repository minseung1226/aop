package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyCastingTest {

    @Test
    void jdkProxy(){
        MemberServiceImpl target=new MemberServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.setProxyTargetClass(false); //JDK 동적 프록시


        //프록시를 인터페이스로 캐스팅
        MemberService memberServiceproxy =(MemberService) factory.getProxy();


        //JDK 동적 프록시를 구현 클래스로 캐스팅 시 실패 : ClassCastException 발생
        Assertions.assertThatThrownBy(()-> {
            MemberServiceImpl memberServiceCastingproxy = (MemberServiceImpl) memberServiceproxy;
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void cglibProxy(){
        MemberServiceImpl target=new MemberServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.setProxyTargetClass(true); //CGLIB


        //프록시를 인터페이스로 캐스팅
        MemberService memberServiceproxy =(MemberService) factory.getProxy();


        //CGLIB 프록시를 구현 클래스로 캐스팅 시 성공
        MemberServiceImpl memberServiceCastingproxy = (MemberServiceImpl) memberServiceproxy;

    }
}
