import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {PlayerModel, PostPlayerRequest} from "../player.model";
import {ClubService} from "../../club/club.service";
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
    'birthday': new FormControl('')
  })

  constructor(private playerService: PlayerService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.activatedRoute.url.subscribe(() => {
      this.clubId = this.activatedRoute.snapshot.params['id'];
    })
  }

  onSubmit() {
    let postPlayerRequest = new PostPlayerRequest(this.playerForm.value.name,
      this.playerForm.value.surname,
      this.playerForm.value.birthday,
      {
        id: this.clubId
      })
    console.log(this.playerForm.value)
    console.log(postPlayerRequest)
    this.playerService.postNewPlayer(postPlayerRequest).subscribe(() => {
      this.goBack()
    })
  }

  goBack() {
    this.router.navigate(['..'], {relativeTo: this.activatedRoute})
  }

}
