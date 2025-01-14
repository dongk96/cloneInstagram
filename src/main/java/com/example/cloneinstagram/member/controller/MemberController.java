package com.example.cloneinstagram.member.controller;

import com.example.cloneinstagram.common.ResponseMsgDto;
import com.example.cloneinstagram.member.dto.*;
import com.example.cloneinstagram.member.service.MemberService;
import com.example.cloneinstagram.security.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMsgDto<Void>> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return memberService.signUp(signUpRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMsgDto<Void>> logIn(@RequestBody LogInRequestDto logInRequestDto, HttpServletResponse httpServletResponse){
        return memberService.logIn(logInRequestDto, httpServletResponse);
    }

    @GetMapping("/followers")
    public Page<MemberResponseDto> getFollowerMemberList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberService.getFollowerUserList(userDetails.getUser(), pageable);
    }

    @GetMapping("/recommends")
    public Page<MemberResponseDto> getUnFollowerMemberList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberService.getUnFollowerList(userDetails.getUser(), pageable);
    }

    @PostMapping("/{nickName}")
    public ResponseEntity<ResponseMsgDto<Void>> follow(@PathVariable String nickName, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberService.follow(nickName, userDetails.getUser());
    }

    @GetMapping("/{nickName}")
    public MyFeedResponseDto memberInfo(@PathVariable String nickName, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberService.memberInfo(nickName, userDetails.getUser());
    }

    @PutMapping(value = "/{nickName}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMsgDto<Void>> updateInfo(@PathVariable String nickName,
                                                        @RequestPart(value = "image") MultipartFile image,
                                                        @Valid @RequestPart(value = "feed") MyFeedRequestDto myFeedRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return memberService.updateInfo(nickName, image, myFeedRequestDto, userDetails.getUser());
    }
}
