import {Component, Input} from '@angular/core';
import {ClubModel} from "../../club.model";

@Component({
  selector: 'app-club-item',
  standalone: true,
  imports: [],
  templateUrl: './club-item.component.html',
  styleUrl: './club-item.component.css'
})
export class ClubItemComponent {
  @Input() club!: ClubModel;

  constructor() {
  }
}
