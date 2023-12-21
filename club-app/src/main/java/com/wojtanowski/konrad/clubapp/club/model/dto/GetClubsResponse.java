package com.wojtanowski.konrad.clubapp.club.model.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetClubsResponse {
    private List<GetClubResponse> clubs;
}
