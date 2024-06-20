import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {backendUrl} from "../shared/http.config";
import {ClubModel} from "./club.model";

@Injectable({providedIn: 'root'})
export class ClubService {
  constructor(private http: HttpClient) {
  }

  getClubs() {
    const url = `${backendUrl}/clubs`;

    return this.http.get<ClubModel[]>(url);
  }

  getClubById(id: string) {
    const url = `${backendUrl}/clubs/${id}`;
    return this.http.get<ClubModel>(url);
  }

  putClubById(club: ClubModel, id:string) {
    const url = `${backendUrl}/clubs/${id}`;
    return this.http.put<ClubModel>(url, club);
  }

  deleteClubById(id: string) {
    const url = `${backendUrl}/clubs/${id}`;
    return this.http.delete(url);
  }
}
