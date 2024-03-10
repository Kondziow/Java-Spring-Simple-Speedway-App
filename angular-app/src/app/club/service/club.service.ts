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
    return this.http.get<any[]>(this.apiClubUrl);
  }

  getClubById(id: string): Observable<ClubModel> {
    return this.http.get<ClubModel>(this.apiClubUrlId.replace('{clubId}', id));
  }
}
