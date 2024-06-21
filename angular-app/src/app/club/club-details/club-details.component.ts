import {Component, OnInit} from '@angular/core';
import {ClubModel} from "../club.model";
import {PlayerModel} from "../../player/player.model";
import {ClubService} from "../club.service";
import {PlayerService} from "../../player/player.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PlayerListComponent} from "../../player/player-list/player-list.component";

@Component({
  selector: 'app-club-details',
  standalone: true,
  imports: [
    PlayerListComponent
  ],
  templateUrl: './club-details.component.html',
  styleUrl: './club-details.component.css'
})
export class ClubDetailsComponent implements OnInit{
  clubId: string = '';
  club?: ClubModel;
  players?: PlayerModel[];

  constructor(private clubService: ClubService,
              private playerService: PlayerService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.clubId = params['id'];
      this.clubService.getClubById(this.clubId).subscribe(response => {
        this.club = response;
      })
      this.playerService.getPlayersByClubId(this.clubId).subscribe(response => {
        this.players = response.players;
      })
    })
  }
}
