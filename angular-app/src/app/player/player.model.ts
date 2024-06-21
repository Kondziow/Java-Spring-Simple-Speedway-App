export class PlayerModel {
  constructor(public id: string,
              public name: string,
              public surname: string,
              public birthDate: string) {
  }
}

export class PostPlayerRequest extends PlayerModel {
  constructor(
    name: string,
    surname: string,
    birthDate: string,
    public club: { id: string }
  ) {
    super('', name, surname, birthDate);
  }
}

export interface PlayersResponse {
  players: PlayerModel[];
}

