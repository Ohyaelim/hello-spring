package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//데이터 베이스는 transaction 이 있어. ( aftereach 필요없어)커밋하기 전까지 반영이 안돼
@Transactional //테스트 실행시 transaction 을 실행하고 db data 를 인서트쿼리하고 넣은다음
    //테스트 끝나면 roll back 해서 db 에 넣은 데이터가 반영 안되고 지워
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    //clear 를 해주고 싶은데 멤버서비스밖에 없으니 멤버 리포지토리를 갖고 온다!
    @Autowired MemberRepository memoryRepository;
    //이제는 스프링 컨테이너한테 멤버 서비스, 멤버 리포지터리 내놔 해야함



    //test 는 한글 써도 된다.
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException  e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /* try {
            memberService.join(member2);
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        //then
    }

}