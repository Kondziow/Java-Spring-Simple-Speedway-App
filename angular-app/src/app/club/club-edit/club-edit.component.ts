import {Component, OnInit} from '@angular/core';
import {ClubModel} from "../club.model";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ClubService} from "../club.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-club-edit',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './club-edit.component.html',
  styleUrl: './club-edit.component.css'
})
export class ClubEditComponent implements OnInit {
  clubId: string = '';
  club: ClubModel = new ClubModel('null', "name", "city");
  clubForm: FormGroup = new FormGroup({
    'name': new FormControl('', Validators.required),
    'city': new FormControl('', Validators.required)
  });
  editMode: boolean = false;

  constructor(private clubService: ClubService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.url.subscribe(urlSegments => {
      this.editMode = urlSegments.some(segment => segment.path === 'edit');
    });

    if (this.editMode) {
      this.activatedRoute.url.subscribe(() => {
        this.clubId = this.activatedRoute.snapshot.params['id'];
        this.initForm();
      })
    }

  }

  onSubmit() {
    if (this.editMode) {
      this.clubService.putClubById(this.clubForm.value, this.clubId).subscribe(() => {
        this.goBack();
      })
    } else {
      this.clubService.postNewClub(this.clubForm.value).subscribe(() => {
        this.goBack();
      })
    }

  }

  goBack() {
    if (this.editMode) {
      this.router.navigate(['../..'], {relativeTo: this.activatedRoute})
    } else {
      this.router.navigate(['..'], {relativeTo: this.activatedRoute})
    }
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
