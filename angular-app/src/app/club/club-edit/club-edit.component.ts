import { Component } from '@angular/core';
import {ClubModel} from "../club.model";

@Component({
  selector: 'app-club-edit',
  standalone: true,
  imports: [],
  templateUrl: './club-edit.component.html',
  styleUrl: './club-edit.component.css'
})
export class ClubEditComponent {
  club: ClubModel = new ClubModel("name", "city");
}
