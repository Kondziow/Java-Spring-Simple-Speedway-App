import {Component, OnInit} from '@angular/core';
import {ClubModel} from "../club.model";
import {ClubService} from "../club.service";
import {NgForOf} from "@angular/common";
import {ClubItemComponent} from "./club-item/club-item.component";
import {ActivatedRoute, Router} from "@angular/router";

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

  constructor(private clubService: ClubService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.clubService.getClubs().subscribe((response: any) => {
      console.log(response);
      this.clubs = response.clubs;
    })
    //console.log(this.clubService.getClubs());
  }

  onEdit(clubId: string | undefined) {
    this.router.navigate([clubId + "/edit"], {relativeTo: this.activatedRoute})
  }
}
