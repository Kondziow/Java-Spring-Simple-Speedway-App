import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {backendUrl} from "../shared/http.config";
import {ClubModel} from "../club/club.model";
import {PlayerModel} from "./player.model";

@Injectable({providedIn: 'root'})
export class PlayerService {
  constructor(private http: HttpClient) {
  }

  getPlayersByClubId(clubId: string) {
    const url = `${backendUrl}/clubs/${clubId}/players`;
    return this.http.get<PlayerModel[]>(url);
  }
}
