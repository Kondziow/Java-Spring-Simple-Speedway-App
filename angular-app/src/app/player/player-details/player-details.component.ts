import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PlayerService} from "../player.service";
import {PlayerModel} from "../player.model";
import {ClubModel} from "../../club/club.model";
import {ClubService} from "../../club/club.service";

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [],
  templateUrl: './player-details.component.html',
  styleUrl: './player-details.component.css'
})
export class PlayerDetailsComponent implements OnInit {
  playerId: string = '';
  player: PlayerModel = new PlayerModel('', '', '', '');
  clubId: string = '';
  club: ClubModel = new ClubModel('', '', '');

  constructor(private playerService: PlayerService,
              private clubService: ClubService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.playerId = params['playerId'];
      this.playerService.getPlayerById(this.playerId).subscribe(response => {
        this.player = response;
      })
      this.clubId = params['id'];
      this.clubService.getClubById(this.clubId).subscribe(response => {
        this.club = response;
      })
    })
  }

  onGoBack() {
    this.router.navigate(['../..'], {relativeTo: this.activatedRoute})
  }

}
