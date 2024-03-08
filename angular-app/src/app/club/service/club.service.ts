import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import any = jasmine.any;

@Injectable({
  providedIn: 'root'
})
export class ClubService {
  private readonly apiClubUrl = '/api/v1/clubs';
  private readonly apiClubUrlId = `${this.apiClubUrl}/{clubId}`;

  constructor(private http: HttpClient) {
  }

  getAllClubs(): Observable<any[]> {
    return this.http.get<any[]>(this.apiClubUrl);
  }

}
