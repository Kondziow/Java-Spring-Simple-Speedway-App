import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ClubModel} from "../../model/club.model";
import {ActivatedRoute, Router} from "@angular/router";
import {ClubService} from "../../service/club.service";

@Component({
  selector: 'app-club-edit',
  templateUrl: './club-edit.component.html',
  styleUrl: './club-edit.component.css'
})
export class ClubEditComponent implements OnInit {
  clubForm!: FormGroup;
  clubId!: string;
  club!: ClubModel;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private clubService: ClubService
  ) {}

  ngOnInit() {
    this.clubId = this.route.snapshot.params['id'];

    this.clubService.getClubById(this.clubId).subscribe((club:ClubModel) => {
      this.club = club;

      this.clubForm = this.fb.group( {
        id: [this.club.id, Validators.required],
        name: [this.club.name, Validators.required],
        city: [this.club.city, Validators.required],
      })
    })
  }

  updateClub() {
    if (this.clubForm.valid) {
      const updatedClub: ClubModel = {
        id: this.clubForm.value.id,
        name: this.clubForm.value.name,
        city: this.clubForm.value.city,
      }

      this.clubService.updateClubById(updatedClub).subscribe( () => {
        this.router.navigate(['/clubs']);
      })
    }
  }

}
