import {Component, Input} from '@angular/core';
import {ClubItemComponent} from "../../club/club-list/club-item/club-item.component";
import {NgForOf} from "@angular/common";
import {PlayerModel} from "../player.model";
import {PlayerItemComponent} from "./player-item/player-item.component";
import {ActivatedRoute, Router} from "@angular/router";
import {PlayerService} from "../player.service";
import {relative} from "@angular/compiler-cli";

@Component({
  selector: 'app-player-list',
  standalone: true,
  imports: [
    ClubItemComponent,
    NgForOf,
    PlayerItemComponent
  ],
  templateUrl: './player-list.component.html',
  styleUrl: './player-list.component.css'
})
export class PlayerListComponent {
  @Input() players?: PlayerModel[];
  @Input() clubId?: string;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private playerService: PlayerService) {
  }

  onAddNewPlayer() {
    this.router.navigate(['newPlayer'], {relativeTo: this.activatedRoute})
  }

  onEdit(playerId: string) {
    this.router.navigate(['player/' + playerId + '/edit'], {relativeTo: this.activatedRoute})
  }

  onPlayerDetails(playerId: string) {
    this.router.navigate(['player/' + playerId], {relativeTo: this.activatedRoute})
  }

  onDelete(player: PlayerModel) {
    if (confirm(`You are about to delete player ${player.name} ${player.surname}`)) {
      this.playerService.deletePlayerById(player.id).subscribe(() => {
        this.getPlayers();
      })
    }
  }

  getPlayers() {
    this.playerService.getPlayersByClubId(this.clubId!).subscribe(response => {
      this.players = response.players;
    })
  }
}
