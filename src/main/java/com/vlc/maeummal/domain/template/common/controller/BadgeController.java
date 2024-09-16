package com.vlc.maeummal.domain.template.common.controller;


import com.vlc.maeummal.domain.template.common.entity.BadgeEntity;
import com.vlc.maeummal.domain.template.common.service.BadgeService;
import com.vlc.maeummal.global.enums.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/badges")
public class BadgeController {
    private final BadgeService badgeService;

    @Autowired
    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping("/award")
    public BadgeEntity awardBadge(@RequestParam Long memberId, @RequestParam TemplateType templateType) {
        return badgeService.awardBadge(memberId, templateType);
    }

    // 유저가 받은 모든 뱃지를 조회하는 API
    @GetMapping("/user/{userId}")
    public List<BadgeEntity> getUserBadges(@PathVariable Long userId) {
        return badgeService.getBadgesByUserId(userId);
    }

}
