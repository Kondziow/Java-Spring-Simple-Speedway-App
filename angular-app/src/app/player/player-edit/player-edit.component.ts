import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {PlayerModel, PostPlayerRequest} from "../player.model";
import {ActivatedRoute, Router} from "@angular/router";
import {PlayerService} from "../player.service";

@Component({
  selector: 'app-player-edit',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './player-edit.component.html',
  styleUrl: './player-edit.component.css'
})
export class PlayerEditComponent implements OnInit {
  clubId: string = '';
  playerId: string = '';
  player: PlayerModel = new PlayerModel('','','','');
  playerForm: FormGroup = new FormGroup({
    'name': new FormControl('', Validators.required),
    'surname': new FormControl('', Validators.required),
    'birthDate': new FormControl('')
  })
  editMode: boolean  = false;

  constructor(private playerService: PlayerService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.activatedRoute.url.subscribe(urlSegments => {
      this.editMode = urlSegments.some(segment => segment.path === 'edit');
    });

    this.activatedRoute.url.subscribe(() => {
      this.clubId = this.activatedRoute.snapshot.params['id'];
    })

    if (this.editMode) {
      this.activatedRoute.url.subscribe(() => {
        this.playerId = this.activatedRoute.snapshot.params['playerId'];
        this.initForm();
      })
    }
  }

  onSubmit() {
    let postPlayerRequest = new PostPlayerRequest(
      this.playerForm.value.name,
      this.playerForm.value.surname,
      this.playerForm.value.birthDate,
      {
        id: this.clubId
      })
    console.log(this.playerForm.value)
    console.log(postPlayerRequest)

    if (this.editMode) {
      this.playerService.putPlayerById(postPlayerRequest, this.playerId).subscribe(() => {
        this.goBack()
      })
    } else {
      this.playerService.postNewPlayer(postPlayerRequest).subscribe(() => {
        this.goBack()
      })
    }

  }

  goBack() {
    if (this.editMode) {
      this.router.navigate(['../../..'], {relativeTo: this.activatedRoute});
    } else {
      this.router.navigate(['..'], {relativeTo: this.activatedRoute});
    }
  }

  initForm() {
    let playerName = '';
    let playerSurname = '';
    let playerBirthDate = '';

    this.playerService.getPlayerById(this.playerId).subscribe(response => {
      this.player = response;

      playerName = this.player.name;
      playerSurname = this.player.surname;
      playerBirthDate = this.player.birthDate;

      this.playerForm.patchValue({
        'name': playerName,
        'surname': playerSurname,
        'birthDate': playerBirthDate
      })
    })
  }

}
