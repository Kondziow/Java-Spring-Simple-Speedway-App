import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {backendUrl} from "../shared/http.config";
import {PlayerModel, PlayersResponse, PostPlayerRequest} from "./player.model";

@Injectable({providedIn: 'root'})
export class PlayerService {
  constructor(private http: HttpClient) {
  }

  getPlayerById(playerId: string) {
    const url = `${backendUrl}/players/${playerId}`;
    return this.http.get<PlayerModel>(url);
  }

  getPlayersByClubId(clubId: string) {
    const url = `${backendUrl}/clubs/${clubId}/players`;
    return this.http.get<PlayersResponse>(url);
  }

  postNewPlayer(player: PostPlayerRequest) {
    const url = `${backendUrl}/players`;
    return this.http.post<PlayerModel>(url, player);
  }

  putPlayerById(player: PostPlayerRequest, playerId: string) {
    const url = `${backendUrl}/players/${playerId}`;
    return this.http.put<PlayerModel>(url, player);
  }

  deletePlayerById(playerId: string) {
    const url = `${backendUrl}/players/${playerId}`;
    return this.http.delete(url);
  }
}
