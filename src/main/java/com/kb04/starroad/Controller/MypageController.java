package com.kb04.starroad.Controller;

import com.kb04.starroad.Dto.MemberDto;
import com.kb04.starroad.Dto.SubscriptionDto;
import com.kb04.starroad.Service.MemberService;
import com.kb04.starroad.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MemberService memberService;

    private static MemberDto getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("currentUser");
        return loginMember;
    }

    @GetMapping("/starroad/mypage/asset")
    public ModelAndView asset(
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("mypage/asset");
        MemberDto memberDto = getLoginMember(request);

        mav.addObject("memberAssets", memberService.getAssets(memberDto.getNo()));
        return mav;
    }

    @GetMapping("/starroad/mypage/board")
    public ModelAndView board(
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("mypage/board");
        MemberDto memberDto = getLoginMember(request);

        mav.addObject("writings", memberService.getWritings(memberDto.getNo()));
        return mav;
    }

    @GetMapping("/starroad/mypage/comment")
    public ModelAndView comment(
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("mypage/comment");
        MemberDto memberDto = getLoginMember(request);

        mav.addObject("comments", memberService.getComments(memberDto.getNo()));
        return mav;
    }

    @GetMapping("/starroad/mypage/challenge")
    public ModelAndView challenge(
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("mypage/challenge");
        MemberDto memberDto = getLoginMember(request);

        List<SubscriptionDto> subscriptions = memberService.getSubscriptions(memberDto);
        mav.addObject("subscriptions", subscriptions);
        List<String> paymentLogs = new ArrayList<>();
        for (SubscriptionDto sub : subscriptions) {
            paymentLogs.add(memberService.getPayLog(sub.getNo(), sub.getPeriod(), sub.getReceived()));
        }
        mav.addObject("paymentLogs", paymentLogs);
        return mav;
    }

    @PostMapping("/starroad/mypage/reward")
    public ModelAndView reward(@RequestParam("sub_no") int subNo,
                               @RequestParam("name") String name,
                               @RequestParam("period") int period) {
        ModelAndView mav = new ModelAndView("mypage/reward");
        mav.addObject("reward", memberService.getReward(period));
        mav.addObject("sub_no", subNo);
        mav.addObject("name", name);
        mav.addObject("period", period);
        return mav;
    }

    @PostMapping("/starroad/mypage/save-reward")
    public ModelAndView getReward(@RequestParam("sub_no") int subNo,
                                  @RequestParam("reward") int reward) {
        ModelAndView mav = new ModelAndView("redirect:/starroad/mypage/asset");
        memberService.saveReward(1, subNo, reward);
        return mav;
    }
    @GetMapping("/starroad/mypage/info")
    public ModelAndView info() {
        ModelAndView mav = new ModelAndView("mypage/info");
        return mav;
    }

    //회원정보 수정하는 부분
    @PostMapping("/starroad/mypage/info")
    public ModelAndView info(
            HttpServletRequest request,
            @ModelAttribute MemberDto changeDto) {
        ModelAndView mav = new ModelAndView("redirect:/starroad");

        MemberDto memberDto = getLoginMember(request);

        mav.addObject("member", memberDto);

        memberService.memberUpdate(memberDto, changeDto);

        return mav;
    }


    @GetMapping("/starroad/mypage/password")
    public ModelAndView password() {
        ModelAndView mav = new ModelAndView("mypage/password");
        return mav;
    }

    @PostMapping("/starroad/mypage/password")
    public ModelAndView password(
            @RequestParam("password") String password,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/starroad");

        MemberDto memberDto = getLoginMember(request);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encPass = encoder.encode(password);

        memberService.memberPasswordUpdate(memberDto,encPass);

        return mav;
    }

    @PostMapping("/api/starroad/mypage/check-password")
    public String checkPassword(@RequestParam("inputPw") String inputPw,
                                HttpServletRequest request) {
        String msg = "";
        MemberDto memberDto = getLoginMember(request);


        if (!memberService.checkPassword(memberDto.getNo(), inputPw)) {
            msg = "비밀번호를 잘못 입력했습니다. 다시 입력해주세요.";
        }
        return msg;
    }
}