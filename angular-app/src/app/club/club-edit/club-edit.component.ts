import {Component, OnInit} from '@angular/core';
import {ClubModel} from "../club.model";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ClubService} from "../club.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-club-edit',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './club-edit.component.html',
  styleUrl: './club-edit.component.css'
})
export class ClubEditComponent implements OnInit {
  clubId: string = '';
  club: ClubModel = new ClubModel("name", "city");
  clubForm: FormGroup = new FormGroup({
    'name': new FormControl('', Validators.required),
    'city': new FormControl('', Validators.required)
  });

  constructor(private clubService: ClubService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.url.subscribe(() => {
      this.clubId = this.activatedRoute.snapshot.params['id'];
      this.initForm();
    })
  }

  onSubmit() {
    this.clubService.putClubById(this.clubForm.value, this.clubId).subscribe(() => {
      this.goBack();
    })
  }

  goBack() {
    this.router.navigate(['../..'], {relativeTo: this.activatedRoute})
  }

  initForm() {
    let clubName = '';
    let clubCity = '';

    this.clubService.getClubById(this.clubId).subscribe(response => {
      this.club = response;

      clubName = this.club.name;
      clubCity = this.club.city;

      this.clubForm.patchValue({
        'name': clubName,
        'city': clubCity
      })
    })
  }
}
