import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ClubModel} from "../model/club.model";

@Injectable({
  providedIn: 'root'
})
export class ClubService {
  private readonly apiClubUrl = '/api/v1/clubs';
  private readonly apiClubUrlId = `${this.apiClubUrl}/{clubId}`;

  constructor(private http: HttpClient) {
  }

  getAllClubs(): Observable<ClubModel[]> {
    return this.http.get<ClubModel[]>(this.apiClubUrl);
  }

  getClubById(id: string): Observable<ClubModel> {
    return this.http.get<ClubModel>(this.apiClubUrlId.replace('{clubId}', id));
  }

  updateClubById(club: ClubModel): Observable<ClubModel> {
    return this.http.put<ClubModel>(this.apiClubUrlId.replace('{clubId}', club.id), club);
  }

  deleteClubById(id: string): Observable<any> {
    return this.http.delete<any>(this.apiClubUrlId.replace('{clubId}', id))
  }
}
