export class PlayerModel {
  constructor(public id: string,
              public name: string,
              public surname: string,
              public birthDate: string) {
  }
}

export class PostPlayerRequest extends PlayerModel {
  constructor(
    id: string,
    name: string,
    surname: string,
    birthDate: string,
    public club: { id: string }
  ) {
    super(id, name, surname, birthDate);
  }
}

export interface PlayersResponse {
  players: PlayerModel[];
}

