import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {backendUrl} from "../shared/http.config";

@Injectable({providedIn: 'root'})
export class ClubService {
  constructor(private http: HttpClient) {
  }

  getClubs() {
    const url = `${backendUrl}/clubs`;
    //const url = `/api/v1/clubs/clubs`;
    console.log("URL: " + url)
    return this.http.get(url)
  }
}
