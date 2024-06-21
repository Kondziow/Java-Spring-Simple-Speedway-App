import {Component, Input} from '@angular/core';
import {ClubItemComponent} from "../../club/club-list/club-item/club-item.component";
import {NgForOf} from "@angular/common";
import {PlayerModel} from "../player.model";
import {PlayerItemComponent} from "./player-item/player-item.component";
import {ActivatedRoute, Router} from "@angular/router";

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

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  onAddNewPlayer() {
    this.router.navigate(['newPlayer'], {relativeTo: this.activatedRoute})
  }

}
