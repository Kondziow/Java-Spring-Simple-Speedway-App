import {Component, OnInit} from '@angular/core';
import {ClubService} from "../../service/club.service";
import {ClubModel} from "../../model/club.model";

@Component({
  selector: 'app-club-list',
  templateUrl: './club-list.component.html',
  styleUrl: './club-list.component.css'
})
export class ClubListComponent implements OnInit{
  clubs: ClubModel[] | undefined;

  constructor(private clubService: ClubService) {
  }

  ngOnInit(): void {
    this.loadClubs();
  }

  loadClubs(): void {
    this.clubService.getAllClubs().subscribe( (data:any) => {
      this.clubs = data.clubs.map( (club:any) => {
        return new ClubModel(
          club.id,
          club.name,
          club.city
        )
      })
    })
  }

}
