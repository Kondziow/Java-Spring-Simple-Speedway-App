import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClubService} from "../../service/club.service";
import {ClubModel} from "../../model/club.model";

@Component({
  selector: 'app-club-add',
  templateUrl: './club-add.component.html',
  styleUrl: './club-add.component.css'
})
export class ClubAddComponent {
  clubForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private clubService: ClubService) {
    this.clubForm = this.fb.group({
      name: ['', [Validators.required]],
      city: ['', [Validators.required, Validators.min(0)]]
    })
  }

  addClub() {
    if (this.clubForm.valid) {
      const newClub: ClubModel = {
        id: '',
        name: this.clubForm.value.name,
        city: this.clubForm.value.city
      }

      this.clubService.addNewClub(newClub).subscribe( () => {
        this.router.navigate(['/clubs']);
      })
    }
  }
}
