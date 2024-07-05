import {Component, Input} from '@angular/core';
import {PlayerModel} from "../../player.model";

@Component({
  selector: 'app-player-item',
  standalone: true,
  imports: [],
  templateUrl: './player-item.component.html',
  styleUrl: './player-item.component.css'
})
export class PlayerItemComponent {
  @Input() player?: PlayerModel;

}
