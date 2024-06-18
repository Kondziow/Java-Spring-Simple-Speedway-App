import {Component, OnInit} from '@angular/core';
import {ClubModel} from "../club.model";
import {ClubService} from "../club.service";
import {NgForOf} from "@angular/common";
import {ClubItemComponent} from "./club-item/club-item.component";

@Component({
  selector: 'app-club-list',
  standalone: true,
  imports: [
    NgForOf,
    ClubItemComponent,
  ],
  templateUrl: './club-list.component.html',
  styleUrl: './club-list.component.css'
})
export class ClubListComponent implements OnInit{
  clubs: ClubModel[] = [];

  constructor(private clubService: ClubService) {
  }

  ngOnInit(): void {
    this.clubService.getClubs().subscribe((response: any) => {
      console.log(response);
      this.clubs = response.clubs;
    })
    //console.log(this.clubService.getClubs());
  }


}
