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
export class ClubListComponent implements OnInit {
  clubs: ClubModel[] = [];

  constructor(private clubService: ClubService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getClubs();
  }

  onNewClub() {
    this.router.navigate(['new'], {relativeTo:this.activatedRoute})
  }

  onDetails(clubId: string) {
    this.router.navigate([clubId], {relativeTo: this.activatedRoute})
  }

  onEdit(clubId: string) {
    this.router.navigate([clubId + "/edit"], {relativeTo: this.activatedRoute})
  }

  onDelete(club: ClubModel) {
      if(confirm(`You are about to delete club ${club.name} ${club.city}`)) {
      this.clubService.deleteClubById(club.id).subscribe(() => {
        this.getClubs();
      })
    }
  }

  getClubs() {
    this.clubService.getClubs().subscribe((response: any) => {
      this.clubs = response.clubs;
    })
  }
}
