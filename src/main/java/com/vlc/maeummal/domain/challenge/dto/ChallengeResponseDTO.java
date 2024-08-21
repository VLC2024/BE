package com.vlc.maeummal.domain.challenge.dto;

import com.vlc.maeummal.domain.challenge.entity.ChallengeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChallengeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetChallengeDTO {

        Long id;
        String title;
        List<String> completedMissions;
        List<String> unCompletedMissions;
        Integer percent;

        public static ChallengeResponseDTO.GetChallengeDTO convertChallenge(ChallengeEntity challenge) {
            List<String> missionDescriptions = List.of(
                    "낱말 카드 학습하기",
                    "자율 학습",
                    "강의 1개 이상 수강하기"
            );

            Map<String, Boolean> missionStatus = Map.of(
                    "낱말 카드 학습하기", challenge.isWordMission(),
                    "자율 학습", challenge.isPrepMission(),
                    "강의 1개 이상 수강하기", challenge.isTemplateMission()
            );

            List<String> completedMissions = filterMissions(missionDescriptions, missionStatus, true);
            List<String> unCompletedMissions = filterMissions(missionDescriptions, missionStatus, false);

            Integer percentage = calculatePercentage(completedMissions.size(), missionDescriptions.size());

            return GetChallengeDTO.builder()
                    .id(challenge.getId())
                    .title(challenge.getTitle())
                    .completedMissions(completedMissions)
                    .unCompletedMissions(unCompletedMissions)
                    .percent(percentage)
                    .build();
        }

        private static List<String> filterMissions(List<String> missionDescriptions, Map<String, Boolean> missionStatus, boolean completed) {
            return missionDescriptions.stream()
                    .filter(mission -> missionStatus.get(mission) == completed)
                    .collect(Collectors.toList());
        }

        private static Integer calculatePercentage(int completedCount, int totalCount) {
            return totalCount > 0 ? (completedCount * 100) / totalCount : 0;
        }
    }
}
