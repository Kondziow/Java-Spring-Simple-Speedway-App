import {ClubModel} from "../../club/model/club.model";

export class PlayerModel {
  constructor(public id: string, public name: string, public surname: string, public birthDate: string, public club: ClubModel) {}
}
