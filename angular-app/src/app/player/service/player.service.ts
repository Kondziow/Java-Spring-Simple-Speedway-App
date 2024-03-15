import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PlayerModel} from "../model/player.model";

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private readonly apiPlayerUrl = '/api/v1/players';
  private readonly apiPlayerUrlId = `${this.apiPlayerUrl}/{playerId}`;

  constructor(private http: HttpClient) {
  }

  getAllPlayers(): Observable<PlayerModel[]> {
    return this.http.get<PlayerModel[]>(this.apiPlayerUrl);
  }

  getPlayersByClubId(id: string): Observable<PlayerModel[]> {
    return this.http.get<PlayerModel[]>(("api/v1/clubs/{clubId}/players").replace("{clubId}", id));
  }

  deletePlayerById(id: string): Observable<any> {
    return this.http.delete(this.apiPlayerUrlId.replace("{playerId}", id));
  }


}
