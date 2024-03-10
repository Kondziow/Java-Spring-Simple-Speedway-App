import {Component, OnInit} from '@angular/core';
import {ClubModel} from "../../model/club.model";
import {ActivatedRoute} from "@angular/router";
import {ClubService} from "../../service/club.service";

@Component({
  selector: 'app-club-details',
  templateUrl: './club-details.component.html',
  styleUrl: './club-details.component.css'
})
export class ClubDetailsComponent implements OnInit {
  clubId!: string;
  club!: ClubModel;

  constructor(
    private route: ActivatedRoute,
    private clubService: ClubService
  ) {
  }

  ngOnInit(): void {
    this.clubId = this.route.snapshot.params['id'];

    this.clubService.getClubById(this.clubId).subscribe((club: ClubModel) => {
      this.club = club;
    })

  }
}
