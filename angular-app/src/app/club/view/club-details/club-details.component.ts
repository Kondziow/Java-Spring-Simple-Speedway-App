import {Component, OnInit} from '@angular/core';
import {ClubModel} from "../../model/club.model";
import {ActivatedRoute} from "@angular/router";
import {ClubService} from "../../service/club.service";
import {PlayerModel} from "../../../player/model/player.model";
import {PlayerService} from "../../../player/service/player.service";

@Component({
  selector: 'app-club-details',
  templateUrl: './club-details.component.html',
  styleUrl: './club-details.component.css'
})
export class ClubDetailsComponent implements OnInit {
  clubId!: string;
  club!: ClubModel;
  players: PlayerModel[] = [];

  constructor(
    private route: ActivatedRoute,
    private clubService: ClubService,
    private playerService: PlayerService
  ) {
  }

  ngOnInit(): void {
    this.clubId = this.route.snapshot.params['id'];

    this.clubService.getClubById(this.clubId).subscribe((club: ClubModel) => {
      this.club = club;
      this.getPlayersByClubId(this.clubId!);
    })
  }

  getPlayersByClubId(clubId: string) {
    this.playerService.getPlayersByClubId(clubId).subscribe((response: any) => {
      this.players = response.players.map((player: any) => {
        return new PlayerModel(
          player.id,
          player.name,
          player.surname,
          player.birthDate,
          player.club
        )
      });
    })
  }

  deletePlayerById(id: string) {
    this.playerService.deletePlayerById(id).subscribe(() => {
      this.getPlayersByClubId(this.clubId);
    })
  }
}
