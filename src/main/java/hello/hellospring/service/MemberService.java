package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//test 할땐 command+shift+t
public class MemberService {

    private final MemberRepository memberRepository;

    //직접 new 로 생성하는게 아니라 외부에서 넣어주도록 바꾸는것
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member){
        //같은 이름 중복회원 안된다.
        //command+option+v
        /*
        *   Optional<Member> result = memberRepository.findByName(member.getName());
        *
        *   result.ifPresent(m -> {
        *        throw new IllegalAccessException("이미 존재하는 회원입니다.");
        *   });
        */
        //findByName 의 결과는 optional member 니까 바로 .ifPresent 사용해서
        validateDuplicateMember(member);//중복 회원 검증.. control+t 해서 메소드 추출

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}